package fr.istic.aco.editor.memento;

/**
 * Represents the Originator in the Memento design pattern.
 * The originator is responsible for creating mementos to capture its state and restoring its state from a memento.
 *
 * @param <T> the type of the object whose state is managed by the originator.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public interface Originator<T> {

    /**
     * Creates a memento capturing the current state of the originator.
     *
     * @return a {@link Memento} containing the current state of the originator.
     */
    Memento<T> saveState();

    /**
     * Restores the originator's state from the given memento.
     *
     * @param memento the memento containing the state to restore. Must not be null.
     * @throws NullPointerException if the provided memento is null.
     */
    void restoreState(Memento<T> memento);
}
