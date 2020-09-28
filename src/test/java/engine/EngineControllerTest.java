package engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.engine.EngineInvoker;
import fr.istic.aco.editor.engine.EngineSerializer;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.MementoImpl;
import fr.istic.aco.editor.memento.OriginatorImpl;
import fr.istic.aco.editor.selection.SelectionImpl;
import fr.istic.aco.editor.selection.dto.SelectionDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EngineControllerTest {

    @Mock
    private EngineInvoker engineInvoker;

    @Mock
    private EngineImpl engine;

    @Mock
    private SelectionImpl selection;

    @Mock
    private OriginatorImpl originator;

    @Mock
    private CaretakerImpl<EngineImpl> caretaker;

    @Mock
    private MementoImpl memento;

    @InjectMocks
    private EngineController engineController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        String buffer = "This is the given buffer content.";

        when(memento.state()).thenReturn(engine);
        when(caretaker.getMemento(0)).thenReturn(memento);

        when(engine.getBufferContents()).thenReturn(buffer);
        when(engine.getSelection()).thenReturn(selection);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Get the engine state")
    void testGetBufferContents() {
        ResponseEntity<String> response = engineController.getEngineState();

        assertEquals(200, response.getStatusCode().value());
        verify(caretaker, times(2)).getLastMementoIndex();
    }

    @Nested
    @DisplayName("Update a selection")
    class PasteIntoClipboard {
        @Test
        @DisplayName("Update a selection in a valid range")
        void testValidUpdateSelection() {
            SelectionDto selectionDto = new SelectionDto(10, 15);

            ResponseEntity<String> response = engineController.updateSelection(selectionDto);

            assertEquals(200, response.getStatusCode().value());
            verify(engineInvoker, times(1)).setCommand(any());
            verify(engineInvoker, times(1)).execute(any());
            verify(originator, times(2)).setState(any());
            verify(caretaker, times(1)).getNextMementoIndex();
            verify(caretaker, times(2)).addMemento(any());
        }

        @ParameterizedTest
        @CsvSource({"-1,2", "-3,-1", "3,1"})
        @DisplayName("Update a selection in an invalid range")
        void testInvalidUpdateSelection(int beginIndex, int endIndex) {
            SelectionDto selectionDto = new SelectionDto(beginIndex, endIndex);

            doThrow(new IllegalArgumentException())
                    .when(engineInvoker).execute(any());

            ResponseEntity<String> response = engineController.updateSelection(selectionDto);

            assertEquals(400, response.getStatusCode().value());
            verify(engineInvoker, times(1)).setCommand(any());
            verify(engineInvoker, times(1)).execute(any());
            verify(caretaker, times(1)).getNextMementoIndex();
        }
    }

    @Test
    @DisplayName("Cut a part of the buffer into the clipboard")
    void testCutIntoClipboard() {
        ResponseEntity<String> response = engineController.cutSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).setCommand(any());
        verify(engineInvoker, times(1)).execute(any());
        verify(originator, times(2)).setState(any());
        verify(caretaker, times(1)).getNextMementoIndex();
        verify(caretaker, times(2)).addMemento(any());
    }

    @Test
    @DisplayName("Copy a part of the buffer into the clipboard")
    void testCopyIntoClipboard() {
        ResponseEntity<String> response = engineController.copySelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).setCommand(any());
        verify(engineInvoker, times(1)).execute(any());
        verify(originator, times(2)).setState(any());
        verify(caretaker, times(1)).getNextMementoIndex();
        verify(caretaker, times(2)).addMemento(any());
    }

    @Test
    @DisplayName("Paste the content of the clipboard into the buffer")
    void testPasteIntoBuffer() {
        ResponseEntity<String> response = engineController.pasteClipboard();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).setCommand(any());
        verify(engineInvoker, times(1)).execute(null);
        verify(originator, times(2)).setState(any());
        verify(caretaker, times(1)).getNextMementoIndex();
        verify(caretaker, times(2)).addMemento(any());
    }

    @Test
    @DisplayName("Insert text into the buffer")
    void testInsertTextIntoBuffer() {
        String text = "..";

        ResponseEntity<String> response = engineController.insertText(text);

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).setCommand(any());
        verify(engineInvoker, times(1)).execute(any());
        verify(originator, times(2)).setState(any());
        verify(caretaker, times(1)).getNextMementoIndex();
        verify(caretaker, times(2)).addMemento(any());
    }

    @Test
    @DisplayName("Delete text from the buffer")
    void testDeleteSelectedTextFromBuffer() {
        ResponseEntity<String> response = engineController.deleteSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).setCommand(any());
        verify(engineInvoker, times(1)).execute(null);
        verify(originator, times(2)).setState(any());
        verify(caretaker, times(1)).getNextMementoIndex();
        verify(caretaker, times(2)).addMemento(any());
    }

    @Test
    @DisplayName("Replay a memento")
    void testReplayMemento() {
        int mementoIndex = 0;

        ResponseEntity<String> response = engineController.replay(mementoIndex);

        assertEquals(200, response.getStatusCode().value());
        verify(originator, times(1)).restoreState(any());
        verify(caretaker, times(2)).getMemento(mementoIndex);
    }
}