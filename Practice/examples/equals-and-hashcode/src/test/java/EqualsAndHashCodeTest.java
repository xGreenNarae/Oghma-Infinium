import org.example.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EqualsAndHashCodeTest {

  @Nested
  @DisplayName("Primitive type Field 만을 가진 객체")
  class PlainObjectTest {
    @Test
    @DisplayName("동일한 객체에 대해서는 아무것도 하지 않아도 모두 같다.")
    void testIdentity() {
      TestObjectPureComplete obj1 = TestObjectPureComplete.builder()
        .id(1)
        .name("name1")
        .build();
      TestObjectPureComplete obj2 = obj1;

      assertAll(
        () -> assertThat(obj1 == obj2).isTrue(),
        () -> assertThat(obj1.equals(obj2)).isTrue(),
        () -> assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode()),
        () -> assertThat(System.identityHashCode(obj1)).isEqualTo(System.identityHashCode(obj2))
      );
    }

    @Nested
    @DisplayName("동등한 객체")
    class EqualityTest {
      @Test
      @DisplayName("아무것도 하지않으면 모두 다르다.")
      void testEquality() {
        TestObjectPureInComplete obj1 = TestObjectPureInComplete.builder()
          .id(1)
          .name("name1")
          .build();
        TestObjectPureInComplete obj2 = TestObjectPureInComplete.builder()
          .id(1)
          .name("name1")
          .build();

        assertAll(
          () -> assertThat(obj1 == obj2).isFalse(),
          () -> assertThat(obj1.equals(obj2)).isFalse(),
          () -> assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode()),
          () -> assertThat(System.identityHashCode(obj1)).isNotEqualTo(System.identityHashCode(obj2))
        );
      }

      @Test
      @DisplayName("Equals and Hashcode 를 Override 하면 == 과 identityHashCode가 다르다.")
      void testEqualityWithEqualsAndHashCode() {
        TestObjectPureComplete obj1 = TestObjectPureComplete.builder()
          .id(1)
          .name("name1")
          .build();
        TestObjectPureComplete obj2 = TestObjectPureComplete.builder()
          .id(1)
          .name("name1")
          .build();

        assertAll(
          () -> assertThat(obj1 == obj2).isFalse(),
          () -> assertThat(obj1.equals(obj2)).isTrue(),
          () -> assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode()),
          () -> assertThat(System.identityHashCode(obj1)).isNotEqualTo(System.identityHashCode(obj2))
        );
      }
    }
  }

  @Nested
  @DisplayName("Object type Field 를 가진 객체")
  class NestedObjectTest {
    @Test
    @DisplayName("동일한 객체에 대해서는 아무것도 하지 않아도 모두 같다.")
    void testIdentity() {
      TestObjectNestedInComplete obj1 = TestObjectNestedInComplete.builder()
        .id(1)
        .name("name1")
        .child(
          TestObjectChildInComplete.builder()
            .id(2)
            .name("name2")
            .build()
        )
        .build();
      TestObjectNestedInComplete obj2 = obj1;

      assertAll(
        () -> assertThat(obj1 == obj2).isTrue(),
        () -> assertThat(obj1.equals(obj2)).isTrue(),
        () -> assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode()),
        () -> assertThat(System.identityHashCode(obj1)).isEqualTo(System.identityHashCode(obj2))
      );
    }

    @Nested
    @DisplayName("동등한 객체")
    class EqualityTest {
      @Test
      @DisplayName("아무것도 하지않으면 모두 다르다.")
      void testEquality() {
        TestObjectNestedInComplete obj1 = TestObjectNestedInComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildInComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();
        TestObjectNestedInComplete obj2 = TestObjectNestedInComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildInComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();

        assertAll(
          () -> assertThat(obj1 == obj2).isFalse(),
          () -> assertThat(obj1.equals(obj2)).isFalse(),
          () -> assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode()),
          () -> assertThat(System.identityHashCode(obj1)).isNotEqualTo(System.identityHashCode(obj2))
        );
      }

      @Test
      @DisplayName("Equals and Hashcode 를 모두 Override 하면 == 과 identityHashCode가 다르다.")
      void testEqualityWithEqualsAndHashCode() {
        TestObjectNestedComplete obj1 = TestObjectNestedComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();
        TestObjectNestedComplete obj2 = TestObjectNestedComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();

        assertAll(
          () -> assertThat(obj1 == obj2).isFalse(),
          () -> assertThat(obj1.equals(obj2)).isTrue(),
          () -> assertThat(obj1.hashCode()).isEqualTo(obj2.hashCode()),
          () -> assertThat(System.identityHashCode(obj1)).isNotEqualTo(System.identityHashCode(obj2))
        );
      }

      @Test
      @DisplayName("Equals and Hashcode 를 부모만 Override 하면 결국 모두 다르다.")
      void testEqualityWithEqualsAndHashCodeOnlyParent() {
        TestObjectNestedInComplete obj1 = TestObjectNestedInComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildInComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();
        TestObjectNestedInComplete obj2 = TestObjectNestedInComplete.builder()
          .id(1)
          .name("name1")
          .child(
            TestObjectChildInComplete.builder()
              .id(2)
              .name("name2")
              .build()
          )
          .build();

        assertAll(
          () -> assertThat(obj1 == obj2).isFalse(),
          () -> assertThat(obj1.equals(obj2)).isFalse(),
          () -> assertThat(obj1.hashCode()).isNotEqualTo(obj2.hashCode()),
          () -> assertThat(System.identityHashCode(obj1)).isNotEqualTo(System.identityHashCode(obj2))
        );
      }
    }
  }

}
