```
[
    // 탐색기, 터미널 토글 전환
    {
        "key": "cmd+1",
        "command": "workbench.view.explorer",
        "when": "viewContainer.workbench.view.explorer.enabled"
    },
    {
        "key": "cmd+1",
        "command": "workbench.action.closeSidebar",
        "when": "filesExplorerFocus"
    },
    {
        "key": "cmd+[Backquote]",
        "command": "workbench.action.terminal.toggleTerminal"
    },
    {
        "key": "escape",
        "command": "workbench.action.focusFirstEditorGroup",
        "when": "terminalFocus"
    },
    {
        "key": "escape",
        "command": "workbench.action.focusFirstEditorGroup",
        "when": "explorerViewletVisible && filesExplorerFocus && !inputFocus"
    },

    // 탭 전환
    {
        "key": "ctrl+tab",
        "command": "workbench.action.nextEditorInGroup"
    },
    {
        "key": "ctrl+shift+tab",
        "command": "workbench.action.previousEditorInGroup"
    },

    // 줄 위 아래로 이동
    {
        "key": "cmd+up",
        "when": "editorTextFocus && !editorReadonly",
        "command": "editor.action.moveLinesUpAction"
      },
      {
        "key": "cmd+down",
        "when": "editorTextFocus && !editorReadonly",
        "command": "editor.action.moveLinesDownAction"
    },
    {
        "key": "alt+up",
        "command": "-editor.action.moveLinesUpAction",
        "when": "editorTextFocus && !editorReadonly"
    },
    {
        "key": "alt+down",
        "command": "-editor.action.moveLinesDownAction",
        "when": "editorTextFocus && !editorReadonly"
    },


    // copilot toggle
    {
        "key": "shift+alt+cmd+o",
        "command": "github.copilot.toggleCopilot"
    }
]
```