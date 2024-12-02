package engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.engine.EngineDto;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.engine.EngineInvoker;
import fr.istic.aco.editor.engine.EngineService;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.OriginatorImpl;
import fr.istic.aco.editor.selection.SelectionDto;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EngineServiceTest {

    @Mock
    SelectionImpl selection;

    @Mock
    EngineImpl engine;

    @Mock
    EngineInvoker engineInvoker;

    @Mock
    OriginatorImpl originator;

    @Mock
    CaretakerImpl<EngineImpl> caretaker;

    @InjectMocks
    EngineService engineService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        when(selection.getBeginIndex()).thenReturn(0);
        when(selection.getEndIndex()).thenReturn(0);

        when(engine.getBufferContents()).thenReturn("");
        when(engine.getClipboardContents()).thenReturn("");
        when(engine.getSelection()).thenReturn(selection);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    @DisplayName("Initialize engine service")
    class initializeEngineService {
        @Test
        @DisplayName("Initialize engine service")
        void testInitializeEngineService() {
            EngineService service = new EngineService(engine, engineInvoker, originator, caretaker);

            assertNotNull(service);
            verify(caretaker, times(2)).addMemento(any());
        }

        @Test
        @DisplayName("Initialize engine service without engine")
        void testInitializeEngineServiceWithoutEngine() {
            String expectedErrorMessage = "An engine service requires an engine.";

            Exception exception = assertThrows(NullPointerException.class, () -> {
                new EngineService(null, engineInvoker, originator, caretaker);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }

        @Test
        @DisplayName("Initialize engine service without invoker")
        void testInitializeEngineServiceWithoutInvoker() {
            String expectedErrorMessage = "An engine service requires an engine invoker.";

            Exception exception = assertThrows(NullPointerException.class, () -> {
                new EngineService(engine, null, originator, caretaker);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }

        @Test
        @DisplayName("Initialize engine service without originator")
        void testInitializeEngineServiceWithoutOriginator() {
            String expectedErrorMessage = "An engine service requires an originator.";

            Exception exception = assertThrows(NullPointerException.class, () -> {
                new EngineService(engine, engineInvoker, null, caretaker);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }

        @Test
        @DisplayName("Initialize engine service without caretaker")
        void testInitializeEngineControllerWithoutCaretaker() {
            String expectedErrorMessage = "An engine service requires a caretaker.";

            Exception exception = assertThrows(NullPointerException.class, () -> {
                new EngineService(engine, engineInvoker, originator, null);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }
    }

    @Test
    @DisplayName("Get engine state")
    void testGetEngineState() {
        EngineDto engineDto = engineService.getEngineState();

        assertNotNull(engineDto);
        assertInstanceOf(EngineDto.class, engineDto);
        assertEquals(engineDto.getBuffer(), "");

        verify(caretaker, times(1)).getCurrentMementoIndex();
        verify(caretaker, times(1)).getLastMementoIndex();
    }

    @Test
    @DisplayName("Update selection")
    void testUpdateSelection() {
        when(engine.getSelection().getBeginIndex()).thenReturn(0);
        when(engine.getSelection().getEndIndex()).thenReturn(3);

        Optional<EngineDto> engineDto = engineService.updateSelection(new SelectionDto(1,2));

        assertTrue(engineDto.isPresent());

        verify(engineInvoker, times(1)).setCommand(any(Selection.class));
        verify(engineInvoker, times(1)).execute(any());
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Update selection with same selection")
    void testUpdateSelectionWithSameSelection() {
        Optional<EngineDto> engineDto = engineService.updateSelection(new SelectionDto(0, 0));

        assertTrue(engineDto.isEmpty());
        verifyNoInteractions(engineInvoker);
    }

    @Test
    @DisplayName("Cut selection")
    void testCutSelection() {
        when(selection.getBeginIndex()).thenReturn(0);
        when(selection.getEndIndex()).thenReturn(2);

        Optional<EngineDto> engineDto = engineService.cutSelection();

        assertTrue(engineDto.isPresent());

        verify(engineInvoker, times(1)).setCommand(any(Cut.class));
        verify(engineInvoker, times(1)).execute(null);
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Cut selection with same selection")
    void testCutSelectionWithSameSelection() {
        Optional<EngineDto> engineDto = engineService.cutSelection();

        assertTrue(engineDto.isEmpty());
        verifyNoInteractions(engineInvoker);
    }

    @Test
    @DisplayName("Copy selection")
    void testCopySelection() {
        when(engine.getSelection().getBeginIndex()).thenReturn(0);
        when(engine.getSelection().getEndIndex()).thenReturn(1);

        Optional<EngineDto> engineDto = engineService.copySelection();

        assertTrue(engineDto.isPresent());

        verify(engineInvoker, times(1)).setCommand(any(Copy.class));
        verify(engineInvoker, times(1)).execute(null);
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Copy selection with same selection")
    void testCopySelectionWithSameSelection() {
        Optional<EngineDto> engineDto = engineService.copySelection();

        assertTrue(engineDto.isEmpty());
        verifyNoInteractions(engineInvoker);
    }

    @Test
    @DisplayName("Paste clipboard")
    void testPasteClipboard() {
        when(engine.getClipboardContents()).thenReturn("This is the current clipboard content.");

        Optional<EngineDto> engineDto = engineService.pasteClipboard();

        assertTrue(engineDto.isPresent());

        verify(engineInvoker, times(1)).setCommand(any(Paste.class));
        verify(engineInvoker, times(1)).execute(null);
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Paste empty clipboard")
    void testPasteEmptyClipboard() {
        Optional<EngineDto> engineDto = engineService.pasteClipboard();

        assertTrue(engineDto.isEmpty());
        verifyNoInteractions(engineInvoker);
    }

    @Test
    @DisplayName("Insert text")
    void testInsertText() {
        EngineDto engineDto = engineService.insertText(anyString());

        assertNotNull(engineDto);

        verify(engineInvoker, times(1)).setCommand(any(Insertion.class));
        verify(engineInvoker, times(1)).execute(any());
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Delete text")
    void testDeleteText() {
        when(engine.getBufferContents()).thenReturn("This is the current buffer content.");

        Optional<EngineDto> engineDto = engineService.deleteText();

        assertTrue(engineDto.isPresent());

        verify(engineInvoker, times(1)).setCommand(any(Deletion.class));
        verify(engineInvoker, times(1)).execute(null);
        verify(caretaker, times(2)).addMemento(any());
        verify(originator, times(2)).saveState();
    }

    @Test
    @DisplayName("Delete empty buffer")
    void testDeleteEmptyBuffer() {
        Optional<EngineDto> engineDto = engineService.deleteText();

        assertTrue(engineDto.isEmpty());
        verifyNoInteractions(engineInvoker);
    }

    @Test
    @DisplayName("Replay previous actions")
    void testReplay() {
        when(caretaker.getCurrentMementoIndex()).thenReturn(5);

        Optional<EngineDto> engineDto = engineService.replay(0);

        assertTrue(engineDto.isPresent());

        verify(caretaker, times(2)).getCurrentMementoIndex();
        verify(caretaker, times(1)).setMementoIndex(0);
        verify(caretaker, times(3)).getLastMementoIndex();
        verify(caretaker, times(1)).getMemento(anyInt());
        verify(originator, times(1)).restoreState(any());
    }

    @Test
    @DisplayName("Replay actions from same memento index as current memento index")
    void testReplayFromSameMementoIndexAsCurrentMementoIndex() {
        when(caretaker.getCurrentMementoIndex()).thenReturn(0);

        Optional<EngineDto> engineDto = engineService.replay(0);

        assertTrue(engineDto.isEmpty());
    }

    @Test
    @DisplayName("Undo previous action")
    void testUndo() {
        when(caretaker.isFirstMemento()).thenReturn(false);

        Optional<EngineDto> engineDto = engineService.undo();

        assertTrue(engineDto.isPresent());

        verify(caretaker, times(1)).isFirstMemento();
        verify(caretaker, times(1)).decrementMementoIndex();
        verify(caretaker, times(1)).getMemento(anyInt());
        verify(caretaker, times(2)).getCurrentMementoIndex();
        verify(originator, times(1)).saveState();
    }

    @Test
    @DisplayName("Undo previous action to before the first memento")
    void testUndoToBeforeFirstMemento() {
        when(caretaker.isFirstMemento()).thenReturn(true);

        Optional<EngineDto> engineDto = engineService.undo();

        assertTrue(engineDto.isEmpty());
    }

    @Test
    @DisplayName("Redo previous action")
    void testRedo() {
        when(caretaker.isLastMemento()).thenReturn(false);

        Optional<EngineDto> engineDto = engineService.redo();

        assertNotNull(engineDto);
        assertInstanceOf(Optional.class, engineDto);
        assertTrue(engineDto.isPresent());
        assertEquals("", engineDto.get().getBuffer());

        verify(caretaker, times(1)).isLastMemento();
        verify(caretaker, times(1)).incrementMementoIndex();
        verify(caretaker, times(1)).getMemento(anyInt());
        verify(caretaker, times(2)).getCurrentMementoIndex();
        verify(originator, times(1)).saveState();
    }

    @Test
    @DisplayName("Redo previous action to after the last memento")
    void testRedoToAfterLastMemento() {
        when(caretaker.isLastMemento()).thenReturn(true);

        Optional<EngineDto> engineDto = engineService.redo();

        assertTrue(engineDto.isEmpty());
    }
}