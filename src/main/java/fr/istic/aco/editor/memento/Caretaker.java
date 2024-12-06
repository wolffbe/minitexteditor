package fr.istic.aco.editor.memento;

/**
 * The `Caretaker` interface defines the contract for managing mementos in the Memento design pattern.
 * A caretaker is responsible for storing and retrieving mementos without modifying their content.
 *
 * @param <T> the type of the object whose state is saved in the memento.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public interface Caretaker<T> {

    /**
     * Adds a new memento to the caretaker's collection.
     *
     * @param memento the memento to be added. Must not be null.
     */
    void addMemento(Memento<T> memento);

    /**
     * Retrieves the memento stored at the specified index.
     *
     * @param index the index of the memento to retrieve. Must be a valid index within the caretaker's collection.
     * @return the memento stored at the specified index.
     * @throws IndexOutOfBoundsException if the index is invalid.
     */
    Memento<T> getMemento(int index);
}
