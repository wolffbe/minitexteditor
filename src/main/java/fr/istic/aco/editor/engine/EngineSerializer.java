package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.memento.CaretakerImpl;

public class EngineSerializer {
    public static String toResponseEntityBody(int mementoIndex, CaretakerImpl<EngineImpl> caretaker) {
        EngineImpl engine = caretaker.getMemento(mementoIndex).state();

        String buffer = engine.getBufferContents();
        String clipboard = engine.getClipboardContents();
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        int bufferEndIndex = engine.getSelection().getBufferEndIndex();

        int lastMementoIndex = caretaker.getLastMementoIndex();

        return String.format(
                "{" +
                        "\"mementoIndex\": %d," +
                        "\"buffer\": \"%s\"," +
                        "\"clipboard\": \"%s\"," +
                        "\"beginIndex\": %d," +
                        "\"endIndex\": %d," +
                        "\"bufferEndIndex\": %d," +
                        "\"lastMementoIndex\": %d" +
                "}",
                mementoIndex, buffer, clipboard, beginIndex, endIndex, bufferEndIndex, lastMementoIndex
        );
    }
}
