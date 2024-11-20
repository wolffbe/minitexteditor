package fr.istic.aco.editor.selection;

public class SelectionImpl implements Selection {
    private final StringBuilder buffer;
    private final int BUFFER_BEGIN_INDEX;
    private int beginIndex;
    private int endIndex;

    /**
     * Constructs a fr.istic.aco.editor.selection.SelectionImpl object with the specified StringBuilder buffer.
     * Initializes the selection with default indices:
     * - `beginIndex` and `endIndex` are set to 0, indicating no selection initially.
     * - `BUFFER_BEGIN_INDEX` is also set to 0, typically representing the start of the buffer.
     *
     * @param buffer the StringBuilder instance used as the source text for selection operations
     */
    public SelectionImpl(StringBuilder buffer) {
        this.buffer = buffer;
        this.beginIndex = 0;
        this.endIndex = 0;
        this.BUFFER_BEGIN_INDEX = 0;
    }

    /**
     * Provides the index of the first character designated
     * by the selection.
     *
     * @return the beginning index
     */
    @Override
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * Changes the value of the begin index of the selection
     *
     * @param beginIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    public void setBeginIndex(int beginIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException("A begin index cannot be set to a value smaller than zero.");
        } else if (beginIndex > this.getBufferEndIndex()) {
            throw new IndexOutOfBoundsException(
                    "A begin index of " + beginIndex +
                            " cannot be set to a value larger than a buffer end index of " +
                            this.getBufferEndIndex() + ".");
        } else if (beginIndex > this.getEndIndex()) {
            throw new IndexOutOfBoundsException(
                    "A begin index of " + beginIndex +
                            " cannot be set to a value larger than an end index of " + this.getEndIndex() + ".");
        } else {
            this.beginIndex = beginIndex;
        }
    }

    /**
     * Provides the index of the first character
     * after the last character designated
     * by the selection.
     *
     * @return the end index
     */
    @Override
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Changes the value of the end index of the selection
     *
     * @param endIndex, must be within the buffer index range
     * @throws IndexOutOfBoundsException if the beginIndex is out of bounds
     */
    public void setEndIndex(int endIndex) {

        if (endIndex < 0) {
            throw new IndexOutOfBoundsException("An end index cannot be set to a value smaller than zero.");
        } else if (endIndex < this.getBeginIndex()) {
            throw new IndexOutOfBoundsException(
                    "An end index of " + endIndex +
                            " cannot be set to a value smaller than a begin index of " + this.getBeginIndex() + ".");
        } else if (endIndex > this.getBufferEndIndex()) {
            throw new IndexOutOfBoundsException(
                    "An end index of " + endIndex +
                            " cannot be set to a value larger than a buffer end index of " +
                            this.getBufferEndIndex() + ".");
        } else {
            this.endIndex = endIndex;
        }
    }

    /**
     * Provides the index of the first character in the buffer
     *
     * @return the buffer's begin index
     */
    @Override
    public int getBufferBeginIndex() {
        return BUFFER_BEGIN_INDEX;
    }

    /**
     * Provides the index of the first "virtual" character
     * after the end of the buffer
     *
     * @return the post end buffer index
     */
    @Override
    public int getBufferEndIndex() {
        return this.buffer.length();
    }
}
