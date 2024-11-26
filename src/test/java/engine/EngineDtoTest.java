package engine;

import fr.istic.aco.editor.engine.EngineDto;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EngineDtoTest {

    @Mock
    CaretakerImpl<EngineImpl> caretaker;

    @Mock
    EngineImpl engine;

    @Mock
    SelectionImpl selection;

    @Mock
    Memento<EngineImpl> memento;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testEngineDtoConstructor() {
        int mementoIndex = 2;
        String buffer = "This is the given buffer content.";
        String clipboard = "given";
        int beginIndex = 5;
        int endIndex = 10;
        int lastMementoIndex = 3;

        when(memento.state()).thenReturn(engine);
        when(caretaker.getMemento(mementoIndex)).thenReturn(memento);
        when(caretaker.getLastMementoIndex()).thenReturn(lastMementoIndex);

        when(engine.getBufferContents()).thenReturn(buffer);
        when(engine.getClipboardContents()).thenReturn(clipboard);
        when(engine.getSelection()).thenReturn(selection);

        when(selection.getBeginIndex()).thenReturn(beginIndex);
        when(selection.getEndIndex()).thenReturn(endIndex);

        EngineDto dto = new EngineDto(mementoIndex, caretaker);

        verify(caretaker).getMemento(mementoIndex);
        verify(engine).getBufferContents();
        verify(engine).getClipboardContents();
        verify(engine).getSelection();
        verify(selection).getBeginIndex();
        verify(selection).getEndIndex();
        verify(caretaker).getLastMementoIndex();

        assertEquals(buffer, engine.getBufferContents());
        assertEquals(clipboard, engine.getClipboardContents());
        assertEquals(beginIndex, selection.getBeginIndex());
        assertEquals(endIndex, selection.getEndIndex());
        assertEquals(lastMementoIndex, caretaker.getLastMementoIndex());
    }
}
