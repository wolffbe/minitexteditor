package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.selection.SelectionImpl;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing the state of the editor engine.
 * This object is used to encapsulate and transfer engine state information, including buffer content,
 * clipboard content, selection indices, and memento information.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class EngineDto {

    /**
     * The index of the current memento in the editor's history.
     */
    private final int mementoIndex;

    /**
     * The content of the editor's buffer.
     */
    private final String buffer;

    /**
     * The content of the clipboard.
     */
    private final String clipboard;

    /**
     * The beginning index of the current selection.
     */
    private final int beginIndex;

    /**
     * The ending index of the current selection.
     */
    private final int endIndex;

    /**
     * The index representing the end of the buffer.
     */
    private final int bufferEndIndex;

    /**
     * The index of the last available memento in the editor's history.
     */
    private final int lastMementoIndex;

    /**
     * Constructs an {@code EngineDto} using the given engine state.
     *
     * @param mementoIndex     the current memento index.
     * @param engine           the engine from which the state is extracted. Must not be null.
     * @param lastMementoIndex the index of the last memento in the editor's history.
     * @throws NullPointerException if the provided {@code engine} is null.
     */
    public EngineDto(int mementoIndex, EngineImpl engine, int lastMementoIndex) {
        Objects.requireNonNull(engine, "An engine data transfer object requires an engine.");

        SelectionImpl selection = (SelectionImpl) engine.getSelection();

        this.mementoIndex = mementoIndex;
        this.buffer = engine.getBufferContents();
        this.clipboard = engine.getClipboardContents();
        this.beginIndex = selection.getBeginIndex();
        this.endIndex = selection.getEndIndex();
        this.bufferEndIndex = selection.getBufferEndIndex();
        this.lastMementoIndex = lastMementoIndex;
    }

    /**
     * Returns the index of the current memento.
     *
     * @return the current memento index.
     */
    public int getMementoIndex() {
        return mementoIndex;
    }

    /**
     * Returns the content of the editor's buffer.
     *
     * @return the buffer content.
     */
    public String getBuffer() {
        return buffer;
    }

    /**
     * Returns the content of the clipboard.
     *
     * @return the clipboard content.
     */
    public String getClipboard() {
        return clipboard;
    }

    /**
     * Returns the beginning index of the current selection.
     *
     * @return the beginning index of the selection.
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Returns the ending index of the current selection.
     *
     * @return the ending index of the selection.
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Returns the index representing the end of the buffer.
     *
     * @return the buffer's end index.
     */
    public int getBufferEndIndex() {
        return bufferEndIndex;
    }

    /**
     * Returns the index of the last available memento.
     *
     * @return the last memento index.
     */
    public int getLastMementoIndex() {
        return lastMementoIndex;
    }
}
