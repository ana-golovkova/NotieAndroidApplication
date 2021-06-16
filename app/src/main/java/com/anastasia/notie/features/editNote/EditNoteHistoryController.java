package com.anastasia.notie.features.editNote;

import java.util.Stack;

public class EditNoteHistoryController {
    public static class EditNoteAction {
        private final EditNoteFieldType fieldType;
        private final String lastValue;

        public EditNoteAction(EditNoteFieldType fieldType, String lastValue) {
            this.fieldType = fieldType;
            this.lastValue = lastValue;
        }

        public EditNoteFieldType getFieldType() {
            return fieldType;
        }

        public String getLastValue() {
            return lastValue;
        }
    }

    enum EditNoteFieldType {
        TITLE,
        DESCRIPTION
    }

    private Stack<EditNoteAction> historyStack = new Stack<>();

    public void addItem(EditNoteAction action) {
        historyStack.push(action);
    }

    public EditNoteAction getLastActionAndRevert() {
        return historyStack.pop();
    }

    public boolean hasPreviousActions() {
        return historyStack.size() != 0;
    }

}
