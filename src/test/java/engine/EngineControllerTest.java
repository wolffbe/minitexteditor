package engine;

import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineDto;
import fr.istic.aco.editor.engine.EngineService;
import fr.istic.aco.editor.selection.SelectionDto;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EngineControllerTest {

    @Mock
    SelectionDto selectionDto;

    @Mock
    EngineDto mockEngineDto;

    @Mock
    private EngineService engineService;

    @InjectMocks
    private EngineController engineController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    @DisplayName("Initialize controller")
    class InitializeController {
        @Test
        @DisplayName("Initialize controller with service")
        void testInitializeControllerWithService() {
            EngineController controller = new EngineController(engineService);
            assertNotNull(controller);
        }

        @Test
        @DisplayName("Initialize controller without service")
        void testInitializeControllerWithoutService() {
            String expectedErrorMessage = "An engine controller cannot be initialized without an engine service.";

            Exception exception = assertThrows(NullPointerException.class, () -> {
                new EngineController(null);
            });
            String errorMessage = exception.getMessage();

            assertEquals(expectedErrorMessage, errorMessage);
        }
    }

    @Test
    @DisplayName("Get engine state")
    void testGetEngineState() {
        when(engineService.getEngineState()).thenReturn(mockEngineDto);

        ResponseEntity<EngineDto> response = engineController.getEngineState();

        assertEquals(mockEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).getEngineState();
    }

    @Test
    @DisplayName("Update selection")
    void testUpdateSelection() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.updateSelection(selectionDto)).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.updateSelection(selectionDto);

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).updateSelection(selectionDto);
    }

    @Test
    @DisplayName("Cut selection")
    void testCutSelection() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.cutSelection()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.cutSelection();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).cutSelection();
    }

    @Test
    @DisplayName("Copy selection")
    void testCopySelection() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.copySelection()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.copySelection();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).copySelection();
    }

    @Test
    @DisplayName("Paste clipboard")
    void testPasteClipboard() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.pasteClipboard()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.pasteClipboard();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).pasteClipboard();
    }

    @Test
    @DisplayName("Insert text")
    void testInsertText() {
        String text = "This is the given buffer content.";

        when(engineService.insertText(anyString())).thenReturn(mockEngineDto);

        ResponseEntity<EngineDto> response = engineController.insertText(text);

        assertEquals(mockEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).insertText(text);
    }

    @Test
    @DisplayName("Delete selected text")
    void testDeleteSelectedText() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.deleteText()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.deleteSelectedText();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).deleteText();
    }

    @Test
    @DisplayName("Replay previous actions")
    void testReplay() {
        int fromMementoIndex = 1;
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.replay(fromMementoIndex)).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.replay(fromMementoIndex);

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).replay(fromMementoIndex);
    }

    @Test
    @DisplayName("Undo previous action")
    void testUndo() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.undo()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.undo();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).undo();
    }

    @Test
    @DisplayName("Redo previous action")
    void testRedo() {
        Optional<EngineDto> mockOptionalEngineDto = Optional.of(mockEngineDto);
        when(engineService.redo()).thenReturn(mockOptionalEngineDto);

        ResponseEntity<Optional<EngineDto>> response = engineController.redo();

        assertEquals(mockOptionalEngineDto, response.getBody());
        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).redo();
    }
}