package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import org.springframework.stereotype.Component;

@Component
public class OriginatorImpl implements Originator<EngineImpl> {
    private EngineImpl state;

    public void setState(EngineImpl state) {
        this.state = state;
    }

    @Override
    public Memento<EngineImpl> saveState() {
        return new MementoImpl(state);
    }

    @Override
    public void restoreState(Memento<EngineImpl> memento) {
        this.state = memento.state();
    }
}