package fr.istic.aco.editor.memento;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link Caretaker} interface for managing a list of mementos in the Memento design pattern.
 * This class stores a list of mementos, maintains the current memento index, and provides methods for adding,
 * retrieving, and navigating mementos.
 *
 * @param <T> the type of the object whose state is saved in the memento.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@Component
public class CaretakerImpl<T> implements Caretaker<T> {

    /**
     * List of stored mementos.
     */
    private final List<Memento<T>> mementoList = new ArrayList<>();

    /**
     * The index of the current memento in the list.
     */
    private int mementoIndex = -1;

    /**
     * Adds a new memento to the list. If there are future mementos (after an undo), they are cleared
     * before adding the new memento.
     *
     * @param memento the memento to be added. Must not be null.
     */
    @Override
    public void addMemento(Memento<T> memento) {
        // Delete future mementos in case of action after undo
        if (!mementoList.isEmpty() && mementoIndex < mementoList.size() - 1) {
            mementoList.subList(mementoIndex + 1, mementoList.size()).clear();
        }
        mementoList.add(memento);
        mementoIndex = mementoList.size() - 1;
    }

    /**
     * Retrieves the memento stored at the specified index.
     *
     * @param index the index of the memento to retrieve.
     * @return the memento at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    @Override
    public Memento<T> getMemento(int index) {
        if (index <= -1 || index >= mementoList.size()) {
            throw new IndexOutOfBoundsException("Invalid list index: " + index);
        }
        return mementoList.get(index);
    }

    /**
     * Checks if the current memento is the first in the list.
     *
     * @return true if the current memento is the first; false otherwise.
     */
    public boolean isFirstMemento() {
        return mementoIndex == 0;
    }

    /**
     * Checks if the current memento is the last in the list.
     *
     * @return true if the current memento is the last; false otherwise.
     */
    public boolean isLastMemento() {
        return mementoIndex == mementoList.size() - 1;
    }

    /**
     * Returns the index of the last memento in the list.
     *
     * @return the index of the last memento.
     */
    public int getLastMementoIndex() {
        return mementoList.size() - 1;
    }

    /**
     * Returns the index of the current memento.
     *
     * @return the current memento index.
     */
    public int getCurrentMementoIndex() {
        return mementoIndex;
    }

    /**
     * Sets the current memento index to a specified value.
     *
     * @param mementoIndex the index to set.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    public void setMementoIndex(int mementoIndex) {
        if (mementoIndex < 0) {
            throw new IndexOutOfBoundsException("A memento index cannot be set to a value less than zero.");
        } else if (mementoIndex > this.getLastMementoIndex()) {
            throw new IndexOutOfBoundsException("A memento index cannot be larger than the last memento index.");
        } else {
            this.mementoIndex = mementoIndex;
        }
    }

    /**
     * Increments the current memento index to the next value.
     *
     * @throws IndexOutOfBoundsException if incrementing exceeds the maximum index.
     */
    public void incrementMementoIndex() {
        if (mementoIndex + 1 >= mementoList.size()) {
            throw new IndexOutOfBoundsException(
                    "A memento index cannot be incremented to a value larger than the maximum number of mementos.");
        }
        mementoIndex++;
    }

    /**
     * Decrements the current memento index to the previous value.
     *
     * @throws IndexOutOfBoundsException if decrementing results in an index less than zero.
     */
    public void decrementMementoIndex() {
        if (mementoIndex - 1 < 0) {
            throw new IndexOutOfBoundsException("A memento index cannot be set to a value lower than 0.");
        }
        mementoIndex--;
    }
}