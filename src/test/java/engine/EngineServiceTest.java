package engine;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.engine.EngineService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import fr.istic.aco.editor.selection.SelectionImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    @DisplayName("Get the state of the engine")
    void testGetStateOfEngine() {
        String buffer = "This is the given buffer content.";
        String clipboard = "given";
        int beginIndex = 0;
        int endIndex = clipboard.length();
        int bufferEndIndex = buffer.length();

        String mockState =
                "{" +
                        "\"buffer\": \"" + buffer + "\"," +
                        "\"clipboard\": \"" + clipboard + "\"," +
                        "\"beginIndex\": " + beginIndex + "," +
                        "\"endIndex\": " + endIndex + "," +
                        "\"bufferEndIndex\": " + buffer.length() +
                "}";

        SelectionImpl mockSelection = mock(SelectionImpl.class);

        when(engine.getSelection()).thenReturn(mockSelection);
        when(mockSelection.getBeginIndex()).thenReturn(beginIndex);
        when(mockSelection.getEndIndex()).thenReturn(endIndex);
        when(mockSelection.getBufferEndIndex()).thenReturn(bufferEndIndex);
        when(engine.getBufferContents()).thenReturn(buffer);
        when(engine.getClipboardContents()).thenReturn(clipboard);

        String result = engineService.getEngineState();

        assertEquals(mockState, result);
        verify(engine, times(1)).getBufferContents();
        verify(engine, times(1)).getClipboardContents();
        verify(engine, times(3)).getSelection();
        verify(mockSelection, times(1)).getBeginIndex();
        verify(mockSelection, times(1)).getEndIndex();
        verify(mockSelection, times(1)).getBufferEndIndex();
    }


    @Test
    @DisplayName("Get the content of the buffer")
    void testGetBufferContents() {
        String mockBuffer = "This is the given buffer content.";
        when(engine.getBufferContents()).thenReturn(mockBuffer);

        String result = engineService.getBufferContents();

        assertEquals(mockBuffer, result);
        verify(engine, times(1)).getBufferContents();
    }

    @Test
    @DisplayName("Get the content of the clipboard")
    void testGetClipboardContents() {
        String mockClipboard = "This is the given clipboard content.";
        when(engine.getClipboardContents()).thenReturn(mockClipboard);

        String result = engineService.getClipboardContents();

        assertEquals(mockClipboard, result);
        verify(engine, times(1)).getClipboardContents();
    }

    @Nested
    @DisplayName("Update the selection")
    class UpdateSelection {
        @Test
        @DisplayName("Update the selection using valid indexes")
        void testValidUpdateIndexes() {
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
        @DisplayName("Update the selection by moving the caret to the right")
        void testMoveCaretToTheRight() {
            String buffer = "This is the given buffer content.";
            int initialBeginIndex = 0;
            int initialEndIndex = 0;

            int updatedBeginIndex = 1;
            int updatedEndIndex = 1;

            SelectionImpl mockSelection = mock(SelectionImpl.class);
            when(engine.getSelection()).thenReturn(mockSelection);
            when(engine.getSelection().getBeginIndex()).thenReturn(initialBeginIndex);
            when(engine.getSelection().getEndIndex()).thenReturn(initialEndIndex);
            when(engine.getBufferContents()).thenReturn(buffer);

            assertDoesNotThrow(() -> {
                engineService.updateSelection(updatedBeginIndex, updatedEndIndex);
            });
        }

        @Test
        @DisplayName("Update the selection by moving the caret to the left")
        void testMoveCaretToTheLeft() {
            String buffer = "This is the given buffer content.";
            int initialBeginIndex = 1;
            int initialEndIndex = 1;

            int updatedBeginIndex = 0;
            int updatedEndIndex = 0;

            SelectionImpl mockSelection = mock(SelectionImpl.class);
            when(engine.getSelection()).thenReturn(mockSelection);
            when(engine.getSelection().getBeginIndex()).thenReturn(initialBeginIndex);
            when(engine.getSelection().getEndIndex()).thenReturn(initialEndIndex);
            when(engine.getBufferContents()).thenReturn(buffer);

            assertDoesNotThrow(() -> {
                engineService.updateSelection(updatedBeginIndex, updatedEndIndex);
            });
        }
    }

    @Test
    @DisplayName("Cut text from the buffer")
    void testCutSelectedText() {
        engineService.cutSelectedText();

        verify(engine, times(1)).cutSelectedText();
    }

    @Nested
    @DisplayName("Copy text into the clipboard")
    class CopyText {
        @Test
        @DisplayName("Copy a text selection into the clipboard")
        void testCopySelectedText() {
            int beginIndex = 0;
            int endIndex = 1;

            SelectionImpl mockSelection = mock(SelectionImpl.class);
            when(engine.getSelection()).thenReturn(mockSelection);
            when(engine.getSelection().getBeginIndex()).thenReturn(beginIndex);
            when(engine.getSelection().getBeginIndex()).thenReturn(endIndex);

            engineService.copySelectedText();

            verify(engine, times(1)).copySelectedText();
        }

        @Test
        @DisplayName("Skip copying when the selection is empty")
        void testDoesNotCopyWhenSelectionIsEmpty() {
            int beginIndex = 1;
            int endIndex = 1;

            SelectionImpl mockSelection = mock(SelectionImpl.class);
            when(engine.getSelection()).thenReturn(mockSelection);
            when(engine.getSelection().getBeginIndex()).thenReturn(beginIndex);
            when(engine.getSelection().getBeginIndex()).thenReturn(endIndex);

            engineService.updateSelection(beginIndex, endIndex);

            verify(engine, times(0)).copySelectedText();
        }
    }

    @Test
    @DisplayName("Paste text into the clipboard")
    void testPasteClipboard() {
        engineService.pasteClipboard();

        verify(engine, times(1)).pasteClipboard();
    }

    @Test
    @DisplayName("Insert text into the buffer")
    void testInsertText() {
        String text = "This is the buffer.";
        engineService.insertText(text);

        verify(engine, times(1)).insert(text);
    }

    @Nested
    @DisplayName("Delete text from the buffer")
    class DeleteText {
        @Test
        @DisplayName("Delete a selection from the buffer")
        void testDeleteSelectedText() {
            engineService.deleteSelectedText();

            verify(engine, times(1)).delete();
        }

        @Test
        @DisplayName("Delete individual characters from the buffer")
        void testDeleteZeroIndex() {
            int beginIndex = 0;
            int endIndex = 0;

            SelectionImpl mockSelection = mock(SelectionImpl.class);
            when(engine.getSelection()).thenReturn(mockSelection);
            when(mockSelection.getBeginIndex()).thenReturn(beginIndex);
            when(mockSelection.getEndIndex()).thenReturn(endIndex);

            engineService.deleteSelectedText();

            verify(engine, times(1)).delete();
        }
    }
}
