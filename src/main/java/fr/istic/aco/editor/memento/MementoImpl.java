package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import org.springframework.stereotype.Component;

@Component
public class MementoImpl implements Memento {
    private final EngineImpl state;

    public MementoImpl(EngineImpl state) {
        this.state = new EngineImpl(state);
    }

    public EngineImpl getState() {
        return this.state;
    }

    @Override
    public String toString() {
        return this.state.getBufferContents();
    }
}