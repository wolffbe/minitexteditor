package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Objects;

/**
 * Implementation of the {@link Memento} interface for the editor engine state.
 * This class captures and stores a deep clone of the {@link EngineImpl} state to ensure
 * immutability and encapsulation of the saved state.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public record MementoImpl(EngineImpl state) implements Memento<EngineImpl> {

    /**
     * Constructs a new {@code MementoImpl} with a deep-cloned state of the given engine.
     *
     * @param state the engine state to be encapsulated in the memento. Must not be null.
     * @throws NullPointerException if {@code state} is null.
     */
    public MementoImpl(EngineImpl state) {
        Objects.requireNonNull(state, "A memento requires an engine state.");

        // Deeply clone the engine
        EngineImpl clonedEngine = new EngineImpl();
        clonedEngine.insert(state.getBufferContents());
        clonedEngine.getSelection().setBeginIndex(state.getSelection().getBeginIndex());
        clonedEngine.getSelection().setEndIndex(state.getSelection().getEndIndex());

        this.state = clonedEngine;
    }
}
