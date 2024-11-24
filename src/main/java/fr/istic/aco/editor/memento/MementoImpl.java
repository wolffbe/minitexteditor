package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;

public record MementoImpl(EngineImpl state) implements Memento<EngineImpl> {
    public MementoImpl(EngineImpl state) {
        this.state = new EngineImpl(state.getBufferContents(), state.getClipboardContents(), state.getSelection());
    }
}