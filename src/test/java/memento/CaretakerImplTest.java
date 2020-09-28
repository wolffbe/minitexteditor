package memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.MementoImpl;
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

        memento = new MementoImpl(engine);
        caretaker = new CaretakerImpl<>();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Add memento")
    void testAddMemento() {
        caretaker.addMemento(memento);

        assertEquals(1, caretaker.getNextMementoIndex());
    }

    @Nested
    @DisplayName("Get memento")
    class PasteIntoClipboard {
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
    }

    @Test
    @DisplayName("Get the next memento index")
    void testGetNextMementoIndex() {
        caretaker.addMemento(memento);

        assertEquals(1, caretaker.getNextMementoIndex());
    }

    @Test
    @DisplayName("Get the last memento index")
    void testGetLastMementoIndex() {
        caretaker.addMemento(memento);

        assertEquals(0, caretaker.getLastMementoIndex());
    }
}
