package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.selection.Selection;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.springframework.stereotype.Component;

@Component
public class EngineImpl implements Engine {

    StringBuilder buffer;
    String clipboard;
    SelectionImpl selection;

    public EngineImpl() {
        this.buffer = new StringBuilder();
        this.clipboard = "";
        this.selection = new SelectionImpl(this.buffer);
    }

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return clipboard;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        clipboard = buffer.substring(selection.getBeginIndex(), selection.getEndIndex());
        buffer.delete(selection.getBeginIndex(), selection.getEndIndex());
        selection.setEndIndex(selection.getBeginIndex());
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        clipboard = buffer.substring(selection.getBeginIndex(), selection.getEndIndex());
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        this.insert(this.clipboard);
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        if(selection.getEndIndex() - selection.getBeginIndex() > s.length()) {
            buffer.replace(selection.getBeginIndex(), selection.getEndIndex(), s);
        }
        if(selection.getBeginIndex() == selection.getEndIndex()) {
            buffer.insert(selection.getBeginIndex(), s);
        }
        selection.setEndIndex(selection.getBeginIndex() + s.length());
        selection.setBeginIndex(selection.getBeginIndex() + s.length());
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        if(selection.getBeginIndex() == selection.getEndIndex()) {
            if(selection.getEndIndex() != 0){
                selection.setBeginIndex(selection.getBeginIndex() - 1);
                selection.setEndIndex(selection.getEndIndex() - 1);
                buffer.delete(selection.getEndIndex(),selection.getEndIndex()+1);
            }
        } else {
            buffer.delete(selection.getBeginIndex(), selection.getEndIndex());
            selection.setEndIndex(selection.getBeginIndex());
        }
    }
}
