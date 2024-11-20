package fr.istic.aco.editor.engine.dto;

public class Selection {

    private final int beginIndex;
    private final int endIndex;

    public Selection(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }
}
