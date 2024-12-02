package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OriginatorImpl implements Originator<EngineImpl> {
    private EngineImpl state;

    @Override
    public Memento<EngineImpl> saveState() {
        return new MementoImpl(Objects.isNull(state) ? new EngineImpl() : new EngineImpl(state));
    }

    @Override
    public void restoreState(Memento<EngineImpl> memento) {
        this.state = memento.state();
    }
}