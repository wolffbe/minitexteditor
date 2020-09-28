package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.OriginatorImpl;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OriginatorImplTest {

    @Mock
    private EngineImpl engine;

    @Mock
    private SelectionImpl selection;

    @InjectMocks
    private OriginatorImpl originator;

    private AutoCloseable autoCloseable;

    String buffer = "This is the given buffer content.";
    String clipboard = "given";
    int beginIndex = 0;
    int endIndex = 5;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        originator = new OriginatorImpl();

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
    @DisplayName("Save engine state as memento")
    void testSaveState() {
        originator.setState(engine);

        Memento<EngineImpl> memento = originator.saveState();
        assertNotNull(memento);

        EngineImpl state = memento.state();
        assertNotNull(state);

        assertEquals(buffer, state.getBufferContents());
        assertEquals(clipboard, state.getClipboardContents());
        assertEquals(beginIndex, state.getSelection().getBeginIndex());
        assertEquals(endIndex, state.getSelection().getEndIndex());
    }

    @Test
    @DisplayName("Restore engine state from memento")
    void testRestoreState() {
        String newBuffer = "This is the updated buffer content.";
        String newClipboard = "updated given";

        originator.setState(engine);
        Memento<EngineImpl> memento = originator.saveState();

        when(engine.getBufferContents()).thenReturn(newBuffer);
        when(engine.getClipboardContents()).thenReturn(newClipboard);

        originator.setState(engine);
        assertEquals(newBuffer, originator.saveState().state().getBufferContents());
        assertEquals(newClipboard, originator.saveState().state().getClipboardContents());

        originator.restoreState(memento);
        EngineImpl restoredState = originator.saveState().state();

        assertEquals(buffer, restoredState.getBufferContents());
        assertEquals(clipboard, restoredState.getClipboardContents());
        assertEquals(beginIndex, restoredState.getSelection().getBeginIndex());
        assertEquals(endIndex, restoredState.getSelection().getEndIndex());
    }
}
