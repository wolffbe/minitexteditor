package selection;

import fr.istic.aco.editor.selection.Selection;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionImplTest {

    String randomBuffer;
    private Selection selection;

    @BeforeEach
    void setUp() {
        randomBuffer = "This is the buffer content.";

        StringBuilder buffer = new StringBuilder(randomBuffer);
        selection = new SelectionImpl(buffer);

        assertInstanceOf(SelectionImpl.class, selection);
    }

    @Test
    @DisplayName("Get a begin index of a selected control object")
    public void testGetBeginIndex() {
        int beginIndex = 0;

        selection.setBeginIndex(beginIndex);

        assertEquals(beginIndex, selection.getBeginIndex());
    }

    @Test
    @DisplayName("Get an end index of a selected control object")
    public void testGetEndIndex() {
        int endIndex = 0;

        selection.setEndIndex(endIndex);

        assertEquals(endIndex, selection.getEndIndex());
    }

    @Test
    @DisplayName("Get a buffer begin index of a selected control object")
    public void getBufferBeginIndex() {
        int bufferBeginIndex = 0;

        assertEquals(bufferBeginIndex, selection.getBufferBeginIndex());
    }

    @Test
    @DisplayName("Get a buffer end index of a selected control object")
    public void getBufferEndIndex() {
        assertEquals(randomBuffer.length(), selection.getBufferEndIndex());
    }

    @Nested
    @DisplayName("Set a begin index of a selection control object")
    class SetBeginIndex {
        @Test
        @DisplayName("A begin index cannot be set to a value smaller than zero.")
        public void testBeginIndexSmallerThanZero() {
            int beginIndex = -1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setBeginIndex(beginIndex);
            });

            String expectedErrorMessage = "A begin index cannot be set to a value smaller than zero.";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("A begin index can be set to a value smaller than an end index.")
        public void testBeginIndexSmallerThanEndIndex() {
            int beginIndex = 1;
            int endIndex = 2;

            selection.setBeginIndex(beginIndex);
            selection.setEndIndex(endIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to a value equal to an end index.")
        public void testBeginIndexEqualEndIndex() {
            int index = 1;

            selection.setBeginIndex(index);
            selection.setEndIndex(index);

            assertEquals(index, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to the same value as a buffer end index.")
        public void testEndIndexSameValueAsBufferEndIndex() {
            int beginIndex = selection.getBufferEndIndex();

            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBufferEndIndex());
        }

        @Test
        @DisplayName("A begin index cannot be set to a value larger than a buffer end index.")
        public void beginIndexLargerThanBufferEndIndex() {
            int beginIndex = selection.getBufferEndIndex() + 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setBeginIndex(beginIndex);
            });

            String expectedErrorMessage =
                    "A begin index of " + beginIndex +
                            " cannot be larger than a buffer end index of " + selection.getBufferEndIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }
    }

    @Nested
    @DisplayName("Set the end index of a selection control object")
    class SetEndIndex {
        @Test
        @DisplayName("An end index cannot be set to a value smaller than zero.")
        public void testEndIndexSmallerThanZero() {
            int endIndex = -1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setEndIndex(endIndex);
            });

            String expectedErrorMessage = "An end index cannot be set to a value smaller than zero.";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("An end index can be set to a value larger than a begin index.")
        public void testBeginIndexSmallerThanEndIndex() {
            int beginIndex = 1;
            int endIndex = 2;

            selection.setBeginIndex(beginIndex);
            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value equal to a begin index.")
        public void testBeginIndexEqualEndIndex() {
            int index = 1;

            selection.setBeginIndex(index);
            selection.setEndIndex(index);

            assertEquals(index, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to the same value as a buffer end index.")
        public void testEndIndexSameValueAsBufferEndIndex() {
            int index = randomBuffer.length();

            selection.setEndIndex(index);

            assertEquals(index, selection.getBufferEndIndex());
        }

        @Test
        @DisplayName("An end index cannot be set to a value larger than a buffer end index.")
        public void beginIndexLargerThanBufferEndIndex() {
            int endIndex = selection.getBufferEndIndex() + 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setEndIndex(endIndex);
            });

            String expectedErrorMessage =
                    "An end index of " + endIndex +
                            " cannot be larger than a buffer end index of " + selection.getBufferEndIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }
    }
}