package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.OriginatorImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OriginatorImplTest {

    OriginatorImpl originator;

    @Test
    @DisplayName("Save state with engine")
    void testSaveStateWithEngine() {
        EngineImpl engine = new EngineImpl();
        OriginatorImpl originator = new OriginatorImpl(engine);

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
        EngineImpl engine = new EngineImpl();
        OriginatorImpl originator = new OriginatorImpl(engine);

        String firstInsert = "Hello, ";
        String secondInsert = "World!";

        engine.insert(firstInsert);
        Memento<EngineImpl> initialState = originator.saveState();

        engine.insert(secondInsert);
        Memento<EngineImpl> currentState = originator.saveState();

        originator.restoreState(initialState);

        assertEquals(firstInsert, initialState.state().getBufferContents());
        assertEquals(firstInsert + secondInsert, currentState.state().getBufferContents());
        assertEquals(firstInsert, engine.getBufferContents());
    }

    @Test
    @DisplayName("Restore non-existent memento")
    void testRestoreStateWithNullMemento() {
        EngineImpl engine = new EngineImpl();
        OriginatorImpl originator = new OriginatorImpl(engine);

        String expectedErrorMessage = "A memento needs to exist to be restored.";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            originator.restoreState(null);
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }

    @Test
    @DisplayName("Get engine")
    void testGetEngine() {
        EngineImpl engine = new EngineImpl();
        OriginatorImpl originator = new OriginatorImpl(engine);

        assertEquals(engine, originator.getEngine());
    }

    @Test
    @DisplayName("Set engine")
    void testSetEngine() {
        EngineImpl engine = new EngineImpl();

        OriginatorImpl originator = new OriginatorImpl();
        originator.setEngine(engine);

        assertEquals(engine, originator.getEngine());
    }
}
