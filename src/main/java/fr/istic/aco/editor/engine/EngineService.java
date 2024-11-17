package fr.istic.aco.editor.engine;

import org.springframework.stereotype.Service;

@Service
public class EngineService {

    private final EngineImpl engine;

    public EngineService(EngineImpl engine) {
        this.engine = engine;
    }

    public String getBufferContents() {
        return engine.getBufferContents();
    }

    public String getClipboardContents() {
        return engine.getClipboardContents();
    }

    public void updateSelection(int beginIndex, int endIndex) {
        if (beginIndex > endIndex) {
            throw new IndexOutOfBoundsException(
                    "A begin index of " + beginIndex +
                            " cannot be greater than an end index of " + endIndex + ".");
        } else {
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
        }
    }

    public void cutSelectedText() {
        engine.cutSelectedText();
    }

    public void copySelectedText() {
        engine.copySelectedText();
    }

    public void pasteClipboard() {
        engine.pasteClipboard();
    }

    public void insertText(String text) {
        engine.insert(text);
    }

    public void deleteSelectedText() {
        engine.delete();
    }
}
