package engine;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.engine.EngineService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fr.istic.aco.editor.selection.SelectionImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EngineServiceTest {

    @Mock
    private EngineImpl engine;

    @InjectMocks
    private EngineService engineService;

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
    void testGetBufferContents() {
        String mockBuffer = "This is the buffer content.";
        when(engine.getBufferContents()).thenReturn(mockBuffer);

        String result = engineService.getBufferContents();

        assertEquals(mockBuffer, result);
        verify(engine, times(1)).getBufferContents();
    }

    @Test
    void testGetClipboardContents() {
        String mockClipboard = "Clipboard content";
        when(engine.getClipboardContents()).thenReturn(mockClipboard);

        String result = engineService.getClipboardContents();

        assertEquals(mockClipboard, result);
        verify(engine, times(1)).getClipboardContents();
    }

    @Test
    void testValidUpdateSelection() {
        int beginIndex = 5;
        int endIndex = 10;

        SelectionImpl mockSelection = mock(SelectionImpl.class);
        when(engine.getSelection()).thenReturn(mockSelection);

        engineService.updateSelection(beginIndex, endIndex);

        verify(mockSelection, times(1)).setBeginIndex(beginIndex);
        verify(mockSelection, times(1)).setEndIndex(endIndex);
        verify(engine, times(2)).getSelection();
    }

    @Test
    void testCutSelectedText() {
        engineService.cutSelectedText();

        verify(engine, times(1)).cutSelectedText();
    }

    @Test
    void testCopySelectedText() {
        engineService.copySelectedText();

        verify(engine, times(1)).copySelectedText();
    }

    @Test
    void testPasteClipboard() {
        engineService.pasteClipboard();

        verify(engine, times(1)).pasteClipboard();
    }

    @Test
    void testInsertText() {
        String text = "Hello, World!";
        engineService.insertText(text);

        verify(engine, times(1)).insert(text);
    }

    @Test
    void testDeleteSelectedText() {
        engineService.deleteSelectedText();

        verify(engine, times(1)).delete();
    }
}
