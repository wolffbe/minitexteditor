package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import org.springframework.stereotype.Component;

@Component
public class OriginatorImpl implements Originator {
    private EngineImpl state;

    public void setState(EngineImpl state) {
        this.state = state;
    }

    @Override
    public MementoImpl saveState() {
        return new MementoImpl(state);
    }

    @Override
    public void restoreState(MementoImpl memento) {
        this.state = memento.getState();
    }
}