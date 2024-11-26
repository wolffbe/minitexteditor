package engine;

import fr.istic.aco.editor.engine.Engine;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.selection.Selection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        engine.getSelection().setBeginIndex(beginIndex);
        engine.getSelection().setEndIndex(endIndex);
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
        engine.getSelection().setBeginIndex(beginIndex);
        engine.getSelection().setEndIndex(endIndex);
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
        @DisplayName("Paste text using a smaller-sized selection")
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
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste text using an equally-sized selection")
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
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + clipboard.length(), engine.getSelection().getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }

        @Test
        @DisplayName("Paste text using a larger-sized selection")
        void testPasteClipboardLargerSelection() {
            String buffer = "This is the given buffer content.";
            String bufferAfterPaste = "This is the given";
            String clipboard = "given";
            int beginIndex = buffer.indexOf(clipboard);
            int copyEndIndex = beginIndex + clipboard.length();
            int pasteEndIndex = buffer.length();
            int finalEndIndex = bufferAfterPaste.length();
            Selection selection = engine.getSelection();

            engine.insert(buffer);
            selection.setBeginIndex(beginIndex);
            selection.setEndIndex(copyEndIndex);
            engine.copySelectedText();

            selection.setEndIndex(pasteEndIndex);
            engine.pasteClipboard();

            assertEquals(bufferAfterPaste, engine.getBufferContents());
            assertEquals(beginIndex + clipboard.length(), selection.getBeginIndex());
            assertEquals(finalEndIndex, selection.getEndIndex());
            assertEquals(clipboard, engine.getClipboardContents());
        }
    }

    @Nested
    @DisplayName("Insert text into the buffer")
    class InsertionIntoTheBuffer {
        @Test
        @DisplayName("Insert text into an empty buffer")
        void testInsertIntoBuffer() {
            String buffer = "This is the given buffer content.";

            engine.insert(buffer);

            assertEquals(buffer, engine.getBufferContents());
        }

        @Test
        @DisplayName("Insert characters into an empty buffer")
        void testInsertCharacters() {
            String buffer = "test";
            int beginIndex = 0;
            int endIndex = 0;
            String[] characters = {"t", "e", "s", "t"};

            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);

            for (String s : characters) {
                engine.insert(s);
            }

            assertEquals(beginIndex + characters.length, engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + characters.length, engine.getSelection().getEndIndex());
            assertEquals(buffer, engine.getBufferContents());
        }

        @Test
        @DisplayName("Insert characters overwriting a selection")
        void testInsertOverwritingSelection() {
            String buffer = "This is the given buffer content.";
            String select = "the given";
            String finalBuffer = "This is the buffer content.";
            int beginIndex = buffer.indexOf(select);
            int endIndex = beginIndex + select.length();
            String[] characters = {"t", "h", "e"};

            engine.insert(buffer);
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
            for (String s : characters) {
                engine.insert(s);
            }

            assertEquals(finalBuffer, engine.getBufferContents());
            assertEquals(beginIndex + characters.length, engine.getSelection().getBeginIndex());
            assertEquals(beginIndex + characters.length, engine.getSelection().getEndIndex());
        }
    }

    @Nested
    @DisplayName("Delete text from the buffer")
    class DeletionTextFromBuffer {
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

        @Test
        @DisplayName("Delete individual characters from the buffer")
        void testIndividualCharactersFromBuffer() {
            StringBuilder buffer = new StringBuilder("This is the given buffer content.");
            String delete = " content";
            String finalBuffer = "This is the given buffer.";
            int index = buffer.indexOf(delete) + delete.length();

            engine.insert(buffer.toString());
            engine.getSelection().setBeginIndex(index);
            engine.getSelection().setEndIndex(index);
            for (int i=0; i < delete.length(); i++) {
                engine.delete();
            }

            assertEquals(finalBuffer, engine.getBufferContents());
        }

        @Test
        @DisplayName("Delete individual characters from the beginning of buffer")
        void testIndividualCharactersFromTheBeginningOfTheBuffer() {
            String buffer = "This is the given buffer content.";
            int beginIndex = 0;
            int endIndex = 0;

            engine.insert(buffer);
            engine.getSelection().setBeginIndex(0);
            engine.getSelection().setEndIndex(0);
            engine.delete();

            assertEquals(buffer, engine.getBufferContents());
            assertEquals(beginIndex, engine.getSelection().getBeginIndex());
            assertEquals(endIndex, engine.getSelection().getEndIndex());
        }
    }


}