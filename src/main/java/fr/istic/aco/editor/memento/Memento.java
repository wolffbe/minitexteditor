package fr.istic.aco.editor.memento;

/**
 * Represents a Memento in the Memento design pattern.
 * A memento captures and stores the state of an object, allowing it to be restored later.
 *
 * @param <T> the type of the object whose state is encapsulated by the memento.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public interface Memento<T> {

    /**
     * Retrieves the state encapsulated by this memento.
     *
     * @return the state of the object as an instance of {@code T}.
     */
    T state();
}
