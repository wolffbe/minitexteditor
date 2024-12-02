package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Objects;

public record MementoImpl(EngineImpl state) implements Memento<EngineImpl> {
    public MementoImpl(EngineImpl state) {
        this.state = new EngineImpl(Objects.requireNonNull(state, "A memento requires an engine state."));
    }
}