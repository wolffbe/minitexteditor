package engine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.istic.aco.editor.engine.EngineSerializer;
import fr.istic.aco.editor.memento.MementoImpl;
import fr.istic.aco.editor.selection.Selection;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.engine.EngineImpl;

class EngineSerializerTest {

    @Test
    @DisplayName("Convert engine to response entity body")
    void testConvertEngineToResponseEntityBody() {
        int mementoIndex = 0;
        StringBuilder buffer = new StringBuilder("This is the given buffer content.");
        String clipboard = "given";

        Selection selection = new SelectionImpl(buffer);
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();
        int bufferEndIndex = selection.getBufferEndIndex();

        int lastMementoIndex = 0;

        @SuppressWarnings("unchecked")
        CaretakerImpl<EngineImpl> caretaker = mock(CaretakerImpl.class);
        MementoImpl memento = mock(MementoImpl.class);
        EngineImpl engine = mock(EngineImpl.class);

        when(caretaker.getMemento(mementoIndex)).thenReturn(memento);
        when(caretaker.getLastMementoIndex()).thenReturn(lastMementoIndex);
        when(memento.state()).thenReturn(engine);

        when(engine.getBufferContents()).thenReturn(buffer.toString());
        when(engine.getClipboardContents()).thenReturn(clipboard);
        when(engine.getSelection()).thenReturn(selection);

        String response = EngineSerializer.toResponseEntityBody(0, caretaker);

        String expectedResponseEntity = String.format(
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

        assertEquals(expectedResponseEntity, response);
    }
}
