package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.memento.CaretakerImpl;

public class EngineSerializer {
    public static String toResponseEntityBody(int mementoIndex, CaretakerImpl caretaker) {
        EngineImpl engine = caretaker.getMemento(mementoIndex).getState();

        String buffer = engine.getBufferContents();
        String clipboard = engine.getClipboardContents();
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        int bufferEndIndex = engine.getSelection().getBufferEndIndex();

        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        return String.format(
                "{" +
                        "\"mementoIndex\": %d," +
                        "\"buffer\": \"%s\"," +
                        "\"clipboard\": \"%s\"," +
                        "\"beginIndex\": %d," +
                        "\"endIndex\": %d," +
                        "\"bufferEndIndex\": %d," +
                        "\"mementoLastItemIndex\": %d" +
                "}",
                mementoIndex, buffer, clipboard, beginIndex, endIndex, bufferEndIndex, mementoLastItemIndex
        );
    }
}
