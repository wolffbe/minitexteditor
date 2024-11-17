package engine;

import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineService;
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
    @DisplayName("Get the content of the buffer")
    void testGetBufferContents() {
        String mockBuffer = "This is the buffer content.";
        when(engineService.getBufferContents()).thenReturn(mockBuffer);

        ResponseEntity<String> response = engineController.getBufferContents();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockBuffer, response.getBody());
        verify(engineService, times(1)).getBufferContents();
    }

    @Test
    @DisplayName("Get the content of the clipboard")
    void testGetClipboardContents() {
        String mockClipboard = "This is the clipboard content.";
        when(engineService.getClipboardContents()).thenReturn(mockClipboard);

        ResponseEntity<String> response = engineController.getClipboardContents();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockClipboard, response.getBody());
        verify(engineService, times(1)).getClipboardContents();
    }

    @Test
    @DisplayName("Update a selection in a valid range")
    void testValidUpdateSelection() {
        int beginIndex = 5;
        int endIndex = 10;

        ResponseEntity<String> response = engineController.updateSelection(beginIndex, endIndex);

        assertEquals(200, response.getStatusCode().value());
        verify(engineService, times(1)).updateSelection(beginIndex, endIndex);
    }

    @ParameterizedTest
    @CsvSource({"-1,2", "-3,-1", "3,1"})
    @DisplayName("Update a selection in an invalid range")
    void testInvalidUpdateSelection(int beginIndex, int endIndex) {
        doThrow(new IndexOutOfBoundsException("Invalid selection range"))
                .when(engineService).updateSelection(beginIndex, endIndex);

        ResponseEntity<String> response = engineController.updateSelection(beginIndex, endIndex);

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