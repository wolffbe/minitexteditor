package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.selection.SelectionImpl;

public class EngineDto {

    private final int mementoIndex;
    private final String buffer;
    private final String clipboard;
    private final int beginIndex;
    private final int endIndex;
    private final int bufferEndIndex;
    private final int lastMementoIndex;

    public EngineDto(int mementoIndex, CaretakerImpl<EngineImpl> caretaker) {
        EngineImpl engine = caretaker.getMemento(mementoIndex).state();
        SelectionImpl selection = (SelectionImpl) engine.getSelection();

        this.mementoIndex = mementoIndex;
        this.buffer = engine.getBufferContents();
        this.clipboard = engine.getClipboardContents();
        this.beginIndex = selection.getBeginIndex();
        this.endIndex = selection.getEndIndex();
        this.bufferEndIndex = selection.getBufferEndIndex();
        this.lastMementoIndex = caretaker.getLastMementoIndex();
    }

    public int getMementoIndex() {
        return mementoIndex;
    }

    public String getBuffer() {
        return buffer;
    }

    public String getClipboard() {
        return clipboard;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getBufferEndIndex() {
        return bufferEndIndex;
    }

    public int getLastMementoIndex() {
        return lastMementoIndex;
    }
}
