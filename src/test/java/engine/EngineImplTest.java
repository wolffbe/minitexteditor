package engine;

import fr.istic.aco.editor.engine.Engine;
import fr.istic.aco.editor.selection.Selection;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineImplTest {

    private Engine engine;

    @BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    @Test
    @DisplayName("Get the empty selection")
    void getSelection() {
        String buffer = "";
        int beginIndex = 0;
        int endIndex = 0;

        Selection selection = engine.getSelection();

        assertEquals(beginIndex, selection.getBufferBeginIndex());
        assertEquals(endIndex, selection.getEndIndex());
        assertEquals(buffer, engine.getBufferContents());
    }

    @Test
    @DisplayName("Get the content of the buffer")
    void getBufferContents() {
        String buffer = "This is the given buffer content.";
        engine.insert(buffer);

        String bufferContents = engine.getBufferContents();

        assertEquals(buffer, bufferContents);
    }

    @Test
    @DisplayName("Get the content of clipboard")
    void getClipboardContents() {
        assertTrue(engine.getClipboardContents().isEmpty());
    }

    @Test
    @DisplayName("Cut a selection from the buffer into the clipboard")
    void testCutSelectedText() {
        String buffer = "This is the given buffer content.";
        String clipboard = "the";
        String cut = buffer.replace("the", "");
        int beginIndex = buffer.indexOf("the");
        int endIndex = beginIndex + clipboard.length();

        engine.insert(buffer);
        engine.getSelection().setBeginIndex(beginIndex);
        engine.getSelection().setEndIndex(endIndex);
        engine.cutSelectedText();

        assertEquals(cut, engine.getBufferContents());
        assertEquals(beginIndex, engine.getSelection().getBeginIndex());
        assertEquals(endIndex - clipboard.length(), engine.getSelection().getBeginIndex());
        assertEquals(clipboard, engine.getClipboardContents());
    }

    @Test
    @DisplayName("Insert text into the buffer")
    void testInsertTextIntoBuffer() {
        String buffer = "This is the given buffer content.";

        engine.insert(buffer);

        assertEquals(buffer, engine.getBufferContents());
    }

    @Test
    @DisplayName("Delete text from the buffer")
    void testDeleteTextFromBuffer() {
        StringBuilder buffer = new StringBuilder("This is the given buffer content.");
        String given = "given";
        int beginIndex = buffer.indexOf(given);
        int endIndex = beginIndex + given.length();

        engine.insert(buffer.toString());
        engine.getSelection().setBeginIndex(beginIndex);
        engine.getSelection().setEndIndex(endIndex);
        engine.delete();

        assertEquals(buffer.delete(beginIndex, endIndex).toString(), engine.getBufferContents());
    }

    @Nested
    @DisplayName("Copy a selection from the buffer into the clipboard")
    class CopyIntoClipboard {
        @Test
        @DisplayName("Copy a selection with a valid range")
        void testCopySelectedText() {
            String buffer = "This is the given buffer content.";
            String clipboard = "the";
            int beginIndex = buffer.indexOf("the");
            int endIndex = buffer.indexOf("the") + clipboard.length();

            engine.insert(buffer);
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
            engine.copySelectedText();

            assertEquals(buffer, engine.getBufferContents());
            assertEquals(beginIndex, engine.getSelection().getBeginIndex());
            assertEquals(endIndex, engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Copy a selection with a larger end index than a begin index")
        void testCopySelectedTextWithInvalidRange() {
            String buffer = "This is the given buffer content.";
            int beginIndex = 10;
            int endIndex = 5;
            String expectedErrorMessage = "Range [" + beginIndex + ", " + endIndex + ") " +
                    "out of bounds for length " + buffer.length();

            engine.insert(buffer);
            engine.getSelection().setBeginIndex(10);
            engine.getSelection().setEndIndex(5);

            Exception exception = assertThrows(StringIndexOutOfBoundsException.class, () -> {
                engine.copySelectedText();
            });
            String errorMessage = exception.getMessage();

            assertEquals(errorMessage, expectedErrorMessage);
        }
    }

    @Nested
    @DisplayName("Paste the content of the clipboard into the buffer")
    class PasteIntoClipboard {
        @Test
        @DisplayName("Paste the content using a smaller-sized selection")
        void testPasteClipboardSmallerSelection() {
            StringBuilder buffer = new StringBuilder("This is the given buffer content.");
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int endIndex = buffer.indexOf(clipboard) + clipboard.length();

            engine.insert(buffer.toString());
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
            engine.copySelectedText();

            engine.getSelection().setEndIndex(beginIndex);
            engine.pasteClipboard();

            assertEquals(buffer.insert(beginIndex, clipboard).toString(), engine.getBufferContents());
            assertEquals(beginIndex, engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste the content using an equally-sized selection")
        void testPasteClipboardEqualSelection() {
            String buffer = "This is the given buffer content.";
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int endIndex = buffer.indexOf(clipboard) + clipboard.length();

            engine.insert(buffer);
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
            engine.copySelectedText();
            engine.pasteClipboard();

            assertEquals(buffer, engine.getBufferContents());
            assertEquals(beginIndex, engine.getSelection().getBeginIndex());
            assertEquals(endIndex, engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste the content using a larger-sized selection")
        void testPasteClipboardLargerSelection() {
            StringBuilder buffer = new StringBuilder("This is the given buffer content.");
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int endIndex = beginIndex + clipboard.length();
            String bufferAfterPaste = buffer.delete(endIndex, buffer.length()).toString();

            engine.insert(buffer.toString());
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
            engine.copySelectedText();

            engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
            engine.pasteClipboard();

            assertEquals(bufferAfterPaste, engine.getBufferContents());
            assertEquals(beginIndex, engine.getSelection().getBeginIndex());
            assertEquals(endIndex, engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }
    }
}