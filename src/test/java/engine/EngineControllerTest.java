package engine;

import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineService;
import fr.istic.aco.editor.engine.dto.Selection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EngineControllerTest {

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

    @Test
    @DisplayName("Get the engine state")
    void testGetBufferContents() {
        String mockBuffer = "This is the engine state.";
        when(engineService.getEngineState()).thenReturn(mockBuffer);

        ResponseEntity<String> response = engineController.getEngineState();

        assertEquals(200, response.getStatusCode().value());
        System.out.println(response);
        assertEquals(mockBuffer, response.getBody());
        verify(engineService, times(1)).getEngineState();
    }

    @Test
    @DisplayName("Update a selection in a valid range")
    void testValidUpdateSelection() {
        Selection selection = new Selection(10, 15);
        int beginIndex = selection.getBeginIndex();
        int endIndex = selection.getEndIndex();

        ResponseEntity<String> response = engineController.updateSelection(selection);

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).updateSelection(
                beginIndex,
                endIndex);
    }

    @ParameterizedTest
    @CsvSource({"-1,2", "-3,-1", "3,1"})
    @DisplayName("Update a selection in an invalid range")
    void testInvalidUpdateSelection(int beginIndex, int endIndex) {
        Selection selection = new Selection(beginIndex, endIndex);

        doThrow(new IndexOutOfBoundsException("Invalid selection range"))
                .when(engineService).updateSelection(beginIndex, endIndex);

        ResponseEntity<String> response = engineController.updateSelection(selection);

        assertEquals(400, response.getStatusCode().value());
        verify(engineService, times(1)).updateSelection(beginIndex, endIndex);
    }

    @Test
    @DisplayName("Cut a part of the buffer into the clipboard")
    void testCutIntoClipboard() {
        ResponseEntity<String> response = engineController.cutSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).cutSelectedText();
    }

    @Test
    @DisplayName("Copy a part of the buffer into the clipboard")
    void testCopyIntoClipboard() {
        ResponseEntity<String> response = engineController.copySelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).copySelectedText();
    }

    @Test
    @DisplayName("Paste the content of the clipboard into the buffer")
    void testPasteIntoBuffer() {
        ResponseEntity<String> response = engineController.pasteClipboard();

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).pasteClipboard();
    }

    @Test
    @DisplayName("Insert text into the buffer")
    void testInsertTextIntoBuffer() {
        String text = "Hello, World!";
        ResponseEntity<String> response = engineController.insertText(text);

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).insertText(text);
    }

    @Test
    @DisplayName("Delete text from the buffer")
    void testDeleteSelectedTextFromBuffer() {
        ResponseEntity<String> response = engineController.deleteSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).deleteSelectedText();
    }
}