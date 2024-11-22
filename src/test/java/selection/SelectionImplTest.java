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
    class SetBeginSelectionDto {
        @Test
        @DisplayName("A begin index can be set to a value equal to a buffer begin index.")
        public void testBeginIndexSameBufferBeginIndex() {
            int beginIndex = selection.getBufferBeginIndex();

            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to a value larger than a buffer begin index.")
        public void testBeginIndexLargerThanBufferBeginIndex() {
            int endIndex = selection.getBufferBeginIndex() + 2;
            int beginIndex = selection.getBufferBeginIndex() + 1;

            selection.setEndIndex(endIndex);
            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to a value smaller than a buffer end index.")
        public void testEndIndexSmallerThanBufferEndIndex() {
            int endIndex = selection.getBufferEndIndex();
            int beginIndex = selection.getBufferEndIndex() - 1;

            selection.setEndIndex(endIndex);
            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to a value equal to a buffer end index.")
        public void testBeginIndexSameBufferEndIndex() {
            int endIndex = selection.getBufferEndIndex();
            int beginIndex = selection.getBufferEndIndex();

            selection.setEndIndex(endIndex);
            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index cannot be set to a value larger than a buffer end index.")
        public void testBeginIndexLargerThanBufferEndIndex() {
            int beginIndex = selection.getBufferEndIndex() + 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setBeginIndex(beginIndex);
            });

            String expectedErrorMessage =
                    "A begin index of " + beginIndex +
                            " cannot be set to a value larger than a buffer end index of " +
                            selection.getBufferEndIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("A begin index can be set to a value smaller than an end index.")
        public void testBeginIndexSmallerThanEndIndex() {
            int beginIndex = 0;
            int endIndex = 1;

            selection.setEndIndex(endIndex);
            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index can be set to a value equal to an end index index.")
        public void testBeginIndexEqualToBeginIndex() {
            int beginIndex = selection.getEndIndex();

            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index cannot be set to a value larger than an end index.")
        public void testBeginIndexLargerThanEndIndex() {
            int beginIndex = selection.getEndIndex() + 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setBeginIndex(beginIndex);
            });

            String expectedErrorMessage =
                    "A begin index of " + beginIndex +
                            " cannot be set to a value larger than an end index of " +
                            selection.getEndIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("A begin index can be set to a value larger than zero.")
        public void testEndIndexLargerThanZero() {
            int endIndex = 2;
            int beginIndex = 1;

            selection.setEndIndex(endIndex);
            selection.setBeginIndex(beginIndex);

            assertEquals(beginIndex, selection.getBeginIndex());
        }

        @Test
        @DisplayName("A begin index cannot be set to a value smaller than zero.")
        public void testEndIndexSmallerThanZero() {
            int beginIndex = -1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setBeginIndex(beginIndex);
            });

            String expectedErrorMessage = "A begin index cannot be set to a value smaller than zero.";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }
    }

    @Nested
    @DisplayName("Set the end index of a selection control object")
    class SetEndSelectionDto {
        @Test
        @DisplayName("An end index can be set to a value equal to a buffer begin index.")
        public void testEndIndexSameBufferBeginIndex() {
            int endIndex = selection.getBufferBeginIndex();

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value larger than a buffer begin index.")
        public void testEndIndexLargerThanBufferBeginIndex() {
            int endIndex = selection.getBufferBeginIndex() + 1;

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value smaller than a buffer end index.")
        public void testEndIndexSmallerThanBufferEndIndex() {
            int index = selection.getBufferEndIndex() - 1;

            selection.setEndIndex(index);

            assertEquals(index, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value equal to a buffer end index.")
        public void testEndIndexSameBufferEndIndex() {
            int endIndex = selection.getBufferEndIndex();

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index cannot be set to a value larger than a buffer end index.")
        public void testEndIndexLargerThanBufferEndIndex() {
            int endIndex = selection.getBufferEndIndex() + 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setEndIndex(endIndex);
            });

            String expectedErrorMessage =
                    "An end index of " + endIndex +
                            " cannot be set to a value larger than a buffer end index of " +
                            selection.getBufferEndIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("An end index cannot be set to a value smaller than a begin index.")
        public void testEndIndexSmallerThanBeginIndex() {
            selection.setEndIndex(6);
            selection.setBeginIndex(5);

            int endIndex = selection.getBeginIndex() - 1;

            Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
                selection.setEndIndex(endIndex);
            });

            String expectedErrorMessage =
                    "An end index of " + endIndex +
                            " cannot be set to a value smaller than a begin index of " +
                            selection.getBeginIndex() + ".";
            String errorMessage = exception.getMessage();
            assertEquals(errorMessage, expectedErrorMessage);
        }

        @Test
        @DisplayName("An end index can be set to a value equal to a begin index.")
        public void testEndIndexEqualToBeginIndex() {
            int endIndex = selection.getBeginIndex();

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value larger than a begin index.")
        public void testEndIndexLargerThanBeginIndex() {
            int endIndex = selection.getBeginIndex() + 1;

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

        @Test
        @DisplayName("An end index can be set to a value larger than zero.")
        public void testEndIndexLargerThanZero() {
            int endIndex = 1;

            selection.setEndIndex(endIndex);

            assertEquals(endIndex, selection.getEndIndex());
        }

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
    }
}