package engine;

import fr.istic.aco.editor.engine.EngineDto;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class EngineDtoTest {

    @Mock
    private EngineImpl mockEngine;

    @Mock
    private SelectionImpl mockSelection;

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
    @DisplayName("Initialize engine data transfer object with engine")
    void testInitializeEngineDto() {
        String buffer = "This is the given buffer content.";
        String clipboard = "given";

        int beginIndex = 5;
        int endIndex = 10;
        int bufferEndIndex = 50;

        int mementoIndex = 1;
        int lastMementoIndex = 10;

        when(mockEngine.getBufferContents()).thenReturn(buffer);
        when(mockEngine.getClipboardContents()).thenReturn(clipboard);
        when(mockEngine.getSelection()).thenReturn(mockSelection);

        when(mockSelection.getBeginIndex()).thenReturn(beginIndex);
        when(mockSelection.getEndIndex()).thenReturn(endIndex);
        when(mockSelection.getBufferEndIndex()).thenReturn(bufferEndIndex);

        EngineDto engineDto = new EngineDto(mementoIndex, mockEngine, lastMementoIndex);

        assertEquals(mementoIndex, engineDto.getMementoIndex());
        assertEquals(buffer, engineDto.getBuffer());
        assertEquals(clipboard, engineDto.getClipboard());
        assertEquals(beginIndex, engineDto.getBeginIndex());
        assertEquals(endIndex, engineDto.getEndIndex());
        assertEquals(bufferEndIndex, engineDto.getBufferEndIndex());
        assertEquals(lastMementoIndex, engineDto.getLastMementoIndex());
    }

    @Test
    @DisplayName("Initialize engine data transfer object without engine")
    void testInitializeEngineDtoWithoutEngine() {
        String expectedErrorMessage = "An engine data transfer object requires an engine.";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            new EngineDto(0, null, 0);
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }
}
