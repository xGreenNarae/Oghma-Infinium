#### DataSource 를 여러 개 사용하고 싶다면  
yml 은 대략 이런식
```  
spring:
  datasource:
    write:
      jdbc-url: jdbc:mysql://127.0.0.1:3307/testdb?allowPublicKeyRetrieval=true&useSSL=false
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: 'write-service'
      password: password
    read:
      jdbc-url: jdbc:mysql://127.0.0.1:3308/testdb?allowPublicKeyRetrieval=true&useSSL=false
      driver-class-name: com.mysql.cj.jdbc.Driver
      username: 'read-service'
      password: password
```  

DataSource 를 분기하여 사용할 수 있는데, 예를들어 Transational 의 readonly 를 기준으로 분기하고자 한다면.. 다음과 같은 구현이 가능하다.  

```
@Configuration
public class DataSourceConfiguration {
  public static final String WRITE_SERVER = "WRITE";
  public static final String READ_SERVER = "READ";

  @Bean
  @Primary
  public DataSource dataSource() {
    DataSource determinedDataSource = routingDataSource(writeDataSource(), readDataSource());
    return new LazyConnectionDataSourceProxy(determinedDataSource);
  }

  @Bean
  @Qualifier("ROUTER")
  public DataSource routingDataSource(
    @Qualifier(WRITE_SERVER) DataSource writeDataSource,
    @Qualifier(READ_SERVER) DataSource readDataSource
  ) {
    DataSourceRouter routingDataSource = new DataSourceRouter();

    HashMap<Object, Object> dataSourceMap = new HashMap<>();
    dataSourceMap.put(READ_SERVER, readDataSource);
    dataSourceMap.put(WRITE_SERVER, writeDataSource);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(writeDataSource);

    return routingDataSource;
  }

  @Bean
  @Qualifier(WRITE_SERVER)
  @ConfigurationProperties(prefix = "spring.datasource.write")
  public DataSource writeDataSource() {
    return DataSourceBuilder.create()
      .build();
  }

  @Bean
  @Qualifier(READ_SERVER)
  @ConfigurationProperties(prefix = "spring.datasource.read")
  public DataSource readDataSource() {
    return DataSourceBuilder.create()
      .build();
  }
}
```

```
public class DataSourceRouter extends AbstractRoutingDataSource {
  @Override
  protected Object determineCurrentLookupKey() {
    return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? 
      DataSourceConfiguration.READ_SERVER 
      : 
      DataSourceConfiguration.WRITE_SERVER;
  }
}
```  

[모범예제 참고: hudi](https://hudi.blog/database-replication-with-springboot-and-mysql/).  

---  

