package fr.istic.aco.editor.engine;

public class EngineSerializer {
    public static String toString(EngineImpl engine) {
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
}
