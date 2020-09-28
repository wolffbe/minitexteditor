package engine;

import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.engine.EngineInvoker;
import fr.istic.aco.editor.selection.SelectionImpl;
import fr.istic.aco.editor.selection.dto.SelectionDto;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EngineControllerTest {

    @Mock
    private EngineInvoker engineInvoker;

    @Mock
    private EngineImpl engine;

    @InjectMocks
    private EngineController engineController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        StringBuilder buffer = new StringBuilder("This is the given buffer content.");
        SelectionImpl selectionImpl = new SelectionImpl(buffer);
        when(engine.getSelection()).thenReturn(selectionImpl);
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
    }

    @Test
    @DisplayName("Update a selection in a valid range")
    void testValidUpdateSelection() {
        SelectionDto selectionDto = new SelectionDto(10, 15);

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", selectionDto.beginIndex());
        params.put("endIndex", selectionDto.endIndex());

        ResponseEntity<String> response = engineController.updateSelection(selectionDto);

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(params);
    }

    @ParameterizedTest
    @CsvSource({"-1,2", "-3,-1", "3,1"})
    @DisplayName("Update a selection in an invalid range")
    void testInvalidUpdateSelection(int beginIndex, int endIndex) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", beginIndex);
        params.put("endIndex", endIndex);

        SelectionDto selectionDto = new SelectionDto(beginIndex, endIndex);

        doThrow(new IllegalArgumentException())
                .when(engineInvoker).execute(params);

        ResponseEntity<String> response = engineController.updateSelection(selectionDto);

        assertEquals(400, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(params);
    }

    @Test
    @DisplayName("Cut a part of the buffer into the clipboard")
    void testCutIntoClipboard() {
        ResponseEntity<String> response = engineController.cutSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(null);
    }

    @Test
    @DisplayName("Copy a part of the buffer into the clipboard")
    void testCopyIntoClipboard() {
        ResponseEntity<String> response = engineController.copySelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(null);
    }

    @Test
    @DisplayName("Paste the content of the clipboard into the buffer")
    void testPasteIntoBuffer() {
        ResponseEntity<String> response = engineController.pasteClipboard();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(null);
    }

    @Test
    @DisplayName("Insert text into the buffer")
    void testInsertTextIntoBuffer() {
        String text = "..";
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        ResponseEntity<String> response = engineController.insertText(text);

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(params);
    }

    @Test
    @DisplayName("Delete text from the buffer")
    void testDeleteSelectedTextFromBuffer() {
        StringBuilder buffer = new StringBuilder("This is the given buffer");
        SelectionImpl selectionImpl = new SelectionImpl(buffer);
        when(engine.getSelection()).thenReturn(selectionImpl);

        ResponseEntity<String> response = engineController.deleteSelectedText();

        assertEquals(200, response.getStatusCode().value());
        verify(engineInvoker, times(1)).execute(null);
    }
}