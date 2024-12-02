package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.MementoImpl;
import fr.istic.aco.editor.memento.OriginatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class OriginatorImplTest {

    @Mock
    private EngineImpl engine;

    private OriginatorImpl originator;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        originator = new OriginatorImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Save state with engine")
    void testSaveStateWithEngine() {
        Memento<EngineImpl> memento = originator.saveState();

        EngineImpl savedState = memento.state();

        assertEquals("", savedState.getBufferContents());
        assertEquals("", savedState.getClipboardContents());
        assertEquals(0, savedState.getSelection().getBeginIndex());
        assertEquals(0, savedState.getSelection().getEndIndex());
    }

    @Test
    @DisplayName("Save state without engine")
    void testSaveStateWithoutEngine() {
        engine = null;

        Memento<EngineImpl> memento = originator.saveState();

        EngineImpl savedState = memento.state();

        assertEquals("", savedState.getBufferContents());
        assertEquals("", savedState.getClipboardContents());
        assertEquals(0, savedState.getSelection().getBeginIndex());
        assertEquals(0, savedState.getSelection().getEndIndex());
    }

    @Test
    @DisplayName("Restore state")
    void testRestoreState() {
        engine = new EngineImpl();

        String buffer = "This is the given buffer content.";
        String updatedBuffer = "This is the updated buffer content.";
        engine.insert(buffer);

        Memento<EngineImpl> initialMemento = originator.saveState();

        EngineImpl updatedState = new EngineImpl();
        updatedState.insert(updatedBuffer);
        originator.restoreState(new MementoImpl(updatedState));

        Memento<EngineImpl> modifiedMemento = originator.saveState();
        assertEquals(updatedBuffer, modifiedMemento.state().getBufferContents());

        originator.restoreState(initialMemento);

        Memento<EngineImpl> restoredMemento = originator.saveState();
        assertEquals("", restoredMemento.state().getBufferContents());
    }
}
