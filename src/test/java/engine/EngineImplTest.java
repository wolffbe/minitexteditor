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
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        String buffer = "";

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

        assertEquals(buffer, engine.getBufferContents());
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
        int beginIndex = buffer.indexOf(clipboard);
        int endIndex = beginIndex + clipboard.length();
        String cut = buffer.replace("the", "");

        engine.insert(buffer);
        engine.getSelection().setEndIndex(endIndex);
        engine.getSelection().setBeginIndex(beginIndex);
        engine.cutSelectedText();

        assertEquals(cut, engine.getBufferContents());
        assertEquals(beginIndex, engine.getSelection().getBeginIndex());
        assertEquals(endIndex - clipboard.length(), engine.getSelection().getEndIndex());
        assertEquals(clipboard, engine.getClipboardContents());
    }

    @Test
    @DisplayName("Copy a selection from the buffer into the clipboard")
    void testCopySelectedText() {
        String buffer = "This is the given buffer content.";
        String clipboard = "the";
        int beginIndex = buffer.indexOf("the");
        int endIndex = buffer.indexOf("the") + clipboard.length();

        engine.insert(buffer);
        engine.getSelection().setEndIndex(endIndex);
        engine.getSelection().setBeginIndex(beginIndex);
        engine.copySelectedText();

        assertEquals(buffer, engine.getBufferContents());
        assertEquals(beginIndex, engine.getSelection().getBeginIndex());
        assertEquals(endIndex, engine.getSelection().getEndIndex());
        assertEquals(clipboard, engine.getClipboardContents());
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
            engine.getSelection().setEndIndex(endIndex);
            engine.getSelection().setBeginIndex(beginIndex);
            engine.copySelectedText();

            engine.getSelection().setEndIndex(beginIndex);
            engine.pasteClipboard();

            assertEquals(buffer.insert(beginIndex, clipboard).toString(), engine.getBufferContents());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste the content using an equally-sized selection")
        void testPasteClipboardEqualSelection() {
            StringBuilder buffer = new StringBuilder("This is the given buffer content.");
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int endIndex = buffer.indexOf(clipboard) + clipboard.length();

            engine.insert(buffer.toString());
            engine.getSelection().setEndIndex(endIndex);
            engine.getSelection().setBeginIndex(beginIndex);
            engine.copySelectedText();
            engine.pasteClipboard();

            assertEquals(buffer.insert(beginIndex, clipboard).toString(), engine.getBufferContents());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste the content using a larger-sized selection")
        void testPasteClipboardLargerSelection() {
            String buffer = "This is the given buffer content.";
            String bufferAfterPaste = "This is the given content.";
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int copyEndIndex = beginIndex + clipboard.length();
            int pasteEndIndex = buffer.indexOf("content") - 1;
            int finalEndIndex = bufferAfterPaste.indexOf("content") - 1;
            Selection selection = engine.getSelection();

            engine.insert(buffer);
            selection.setEndIndex(copyEndIndex);
            selection.setBeginIndex(beginIndex);
            engine.copySelectedText();

            selection.setEndIndex(pasteEndIndex);
            engine.pasteClipboard();

            assertEquals(bufferAfterPaste, engine.getBufferContents());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getBeginIndex());
            assertEquals(finalEndIndex, engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }
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
        engine.getSelection().setEndIndex(endIndex);
        engine.getSelection().setBeginIndex(beginIndex);
        engine.delete();

        assertEquals(buffer.delete(beginIndex, endIndex).toString(), engine.getBufferContents());
    }
}