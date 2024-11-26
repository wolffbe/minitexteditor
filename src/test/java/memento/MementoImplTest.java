package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.MementoImpl;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MementoImplTest {

    @Mock
    private EngineImpl engine;

    @Mock
    private SelectionImpl selection;

    private AutoCloseable autoCloseable;

    String buffer = "This is the given buffer content.";
    String clipboard = "given";
    int beginIndex = 5;
    int endIndex = 10;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        when(engine.getBufferContents()).thenReturn(buffer);
        when(engine.getClipboardContents()).thenReturn(clipboard);
        when(engine.getSelection()).thenReturn(selection);

        when(selection.getBeginIndex()).thenReturn(beginIndex);
        when(selection.getEndIndex()).thenReturn(endIndex);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Capture the engine state")
    void testMementoCapturesState() {
        MementoImpl memento = new MementoImpl(engine);

        EngineImpl state = memento.state();

        assertNotNull(state);
        assertEquals(buffer, state.getBufferContents());
        assertEquals(clipboard, state.getClipboardContents());
        assertEquals(beginIndex, state.getSelection().getBeginIndex());
        assertEquals(endIndex, state.getSelection().getEndIndex());
    }

    @Test
    @DisplayName("Create an independent copy of the engine state")
    void testMementoCreatesIndependentCopy() {
        MementoImpl memento = new MementoImpl(engine);
        EngineImpl state = memento.state();

        when(engine.getBufferContents()).thenReturn("This is NOT the given buffer content.");
        when(selection.getBeginIndex()).thenReturn(beginIndex - 1);
        when(selection.getEndIndex()).thenReturn(endIndex - 1);

        assertEquals(buffer, state.getBufferContents());
        assertEquals(beginIndex, state.getSelection().getBeginIndex());
        assertEquals(endIndex, state.getSelection().getEndIndex());
    }
}
