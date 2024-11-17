package fr.istic.aco.editor.engine;

import org.springframework.stereotype.Service;

@Service
public class EngineService {

    private final EngineImpl engine;

    public EngineService(EngineImpl engine) {
        this.engine = engine;
    }

    public String getEngineState() {
        String buffer = engine.getBufferContents();
        String clipboard = engine.getClipboardContents();
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        int bufferEndIndex = engine.getSelection().getBufferEndIndex();

        return String.format(
                "{" +
                        "\"buffer\": \"%s\"," +
                        "\"clipboard\": \"%s\"," +
                        "\"beginIndex\": %d," +
                        "\"endIndex\": %d," +
                        "\"bufferEndIndex\": %d" +
                        "}",
                buffer, clipboard, beginIndex, endIndex, bufferEndIndex
        );
    }

    public String getBufferContents() {
        return engine.getBufferContents();
    }

    public String getClipboardContents() {
        return engine.getClipboardContents();
    }

    public void updateSelection(int beginIndex, int endIndex) {
        if(beginIndex == endIndex && beginIndex > engine.getSelection().getBeginIndex()){
            engine.getSelection().setEndIndex(endIndex);
            engine.getSelection().setBeginIndex(beginIndex);
        } else {
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
        }
    }

    public void cutSelectedText() {
        engine.cutSelectedText();
    }

    public void copySelectedText() {
        if(engine.getSelection().getBeginIndex() != engine.getSelection().getEndIndex()) {
            engine.copySelectedText();
        }
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
