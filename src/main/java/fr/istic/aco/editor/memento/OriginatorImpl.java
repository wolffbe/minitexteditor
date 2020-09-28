package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Implementation of the {@link Originator} interface for managing the state of an {@link EngineImpl}.
 * This class can save the current state of the engine to a memento and restore the engine's state from a memento.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@Component
public class OriginatorImpl implements Originator<EngineImpl> {

    /**
     * The editor engine whose state is managed by this originator.
     */
    private EngineImpl engine;

    /**
     * Constructs an {@code OriginatorImpl} with a new {@link EngineImpl}.
     */
    public OriginatorImpl() {
        this.engine = new EngineImpl();
    }

    /**
     * Constructs an {@code OriginatorImpl} with the specified engine.
     *
     * @param engine the engine to be managed by this originator. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public OriginatorImpl(EngineImpl engine) {
        this.engine = Objects.requireNonNull(engine, "An originator requires an engine.");
    }

    /**
     * Saves the current state of the engine to a memento.
     * The state is deeply cloned to ensure immutability.
     *
     * @return a {@link Memento} containing the deep-cloned state of the engine.
     */
    @Override
    public Memento<EngineImpl> saveState() {
        // Deeply clone the engine
        EngineImpl clonedEngine = new EngineImpl();
        clonedEngine.insert(engine.getBufferContents());
        clonedEngine.getSelection().setBeginIndex(engine.getSelection().getBeginIndex());
        clonedEngine.getSelection().setEndIndex(engine.getSelection().getEndIndex());

        return new MementoImpl(clonedEngine);
    }

    /**
     * Restores the engine's state from the specified memento.
     * The current buffer is cleared, and the buffer and selection are restored from the memento's state.
     *
     * @param memento the memento containing the state to restore. Must not be null, and the memento state must not be null.
     * @throws NullPointerException if the memento or its state is null.
     */
    @Override
    public void restoreState(Memento<EngineImpl> memento) {
        Objects.requireNonNull(memento, "A memento needs to exist to be restored.");
        Objects.requireNonNull(memento.state(), "A memento state needs to exist to be restored.");

        // Clear the entire buffer
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(engine.getSelection().getBufferEndIndex());
        engine.delete();

        // Restore buffer and selection from memento
        engine.insert(memento.state().getBufferContents());
        engine.getSelection().setBeginIndex(memento.state().getSelection().getBeginIndex());
        engine.getSelection().setEndIndex(memento.state().getSelection().getEndIndex());
    }

    /**
     * Returns the engine managed by this originator.
     *
     * @return the current {@link EngineImpl}.
     */
    public EngineImpl getEngine() {
        return engine;
    }

    /**
     * Sets the engine to be managed by this originator.
     *
     * @param engine the new engine. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public void setEngine(EngineImpl engine) {
        this.engine = Objects.requireNonNull(engine, "An originator requires an engine.");
    }
}
