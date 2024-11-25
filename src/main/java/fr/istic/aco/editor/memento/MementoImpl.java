package fr.istic.aco.editor.memento;

import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.selection.SelectionImpl;

public record MementoImpl(EngineImpl state) implements Memento<EngineImpl> {
    public MementoImpl(EngineImpl state) {

        SelectionImpl selection = new SelectionImpl(new StringBuilder(state.getBufferContents()));
        selection.setEndIndex(state.getSelection().getEndIndex());
        selection.setBeginIndex(state.getSelection().getBeginIndex());

        this.state = new EngineImpl(
                state.getBufferContents(),
                state.getClipboardContents(),
                selection);
    }
}