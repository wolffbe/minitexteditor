package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CaretakerImplTest {

    @Mock
    private EngineImpl engine;

    @Mock
    private SelectionImpl selection;

    @Mock
    private Memento<EngineImpl> memento;

    @InjectMocks
    private CaretakerImpl<EngineImpl> caretaker;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        when(engine.getBufferContents()).thenReturn("This is the given buffer content.");
        when(engine.getSelection()).thenReturn(selection);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Add memento")
    void testAddMemento() {
        caretaker.addMemento(memento);

        assertEquals(0, caretaker.getCurrentMementoIndex());
    }

    @Test
    @DisplayName("Add memento before removing future mementos")
    void testAddMementoBeforeRemovingFuture() {
        caretaker.addMemento(memento);
        caretaker.addMemento(memento);

        //simulate undo
        caretaker.decrementMementoIndex();

        caretaker.addMemento(memento);

        assertEquals(1, caretaker.getCurrentMementoIndex());
    }

    @Test
    @DisplayName("Get memento by index")
    void testGetMemento() {
        caretaker.addMemento(memento);

        assertEquals(memento, caretaker.getMemento(0));
    }

    @ParameterizedTest
    @CsvSource({"-1", "2"})
    @DisplayName("Get memento with an invalid index")
    void testGetMementoInvalidIndex(int index) {
        caretaker.addMemento(memento);

        assertThrows(IndexOutOfBoundsException.class, () -> caretaker.getMemento(index));
    }

    @Test
    @DisplayName("Check if memento is first memento in list")
    void testIsFirstMemento() {
        caretaker.addMemento(memento);

        assertTrue(caretaker.isFirstMemento());
    }

    @Test
    @DisplayName("Check if memento is not first memento in list")
    void testIsNotFirstMemento() {
        caretaker.addMemento(memento);
        caretaker.addMemento(memento);

        caretaker.setMementoIndex(1);

        assertFalse(caretaker.isFirstMemento());
    }

    @Test
    @DisplayName("Check if memento is last memento in list")
    void testIsLastMemento() {
        caretaker.addMemento(memento);

        assertTrue(caretaker.isLastMemento());
    }

    @Test
    @DisplayName("Check if memento is not last memento in list")
    void testIsNotLastMemento() {
        caretaker.addMemento(memento);
        caretaker.addMemento(memento);

        caretaker.setMementoIndex(0);

        assertFalse(caretaker.isLastMemento());
    }

    @Test
    @DisplayName("Get the last memento index")
    void testGetLastMementoIndex() {
        caretaker.addMemento(memento);

        assertEquals(0, caretaker.getLastMementoIndex());
    }

    @Test
    @DisplayName("Get the current memento index")
    void testGetCurrentMementoIndex() {
        caretaker.addMemento(memento);

        assertEquals(0, caretaker.getCurrentMementoIndex());
    }

    @Nested
    @DisplayName("Set memento index")
    class SetMementoIndex {
        @Test
        @DisplayName("Set valid memento index")
        public void testSetMementoIndex() {
            caretaker.addMemento(memento);
            caretaker.addMemento(memento);

            caretaker.setMementoIndex(0);

            assertEquals(0, caretaker.getCurrentMementoIndex());
        }

        @Test
        @DisplayName("Set invalid negative memento index")
        public void testSetNegativeMementoIndex() {
            String expectedErrorMessage = "A memento index cannot be set to a value less than zero.";
            caretaker.addMemento(memento);

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                caretaker.setMementoIndex(-1);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }

        @Test
        @DisplayName("Set invalid memento index larger than largest memento index")
        public void testSetLargeMementoIndex() {
            String expectedErrorMessage = "A memento index cannot be larger than the last memento index.";
            caretaker.addMemento(memento);

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                caretaker.setMementoIndex(2);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }
    }

    @Test
    @DisplayName("Increment memento index")
    public void testIncrementMementoIndex() {
        caretaker.addMemento(memento);
        caretaker.addMemento(memento);
        caretaker.decrementMementoIndex();
        caretaker.incrementMementoIndex();

        assertEquals(1, caretaker.getCurrentMementoIndex());
    }

    @Test
    @DisplayName("Increment memento index further than highest memento index")
    public void testDecrementMementoIndexFurtherThanHighest() {
        String expectedErrorMessage =
                "A memento index cannot be incremented to a value larger than the maximum number of mementos.";

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            caretaker.incrementMementoIndex();
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }

    @Test
    @DisplayName("Decrement memento index")
    public void testDecrementMementoIndex() {
        caretaker.addMemento(memento);
        caretaker.addMemento(memento);
        caretaker.decrementMementoIndex();

        assertEquals(0, caretaker.getCurrentMementoIndex());
    }

    @Test
    @DisplayName("Decrement memento index further than 0")
    public void testDecrementMementoIndexFurtherThanLowest() {
        String expectedErrorMessage = "A memento index cannot be set to a value lower than 0.";

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            caretaker.decrementMementoIndex();
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }
}
