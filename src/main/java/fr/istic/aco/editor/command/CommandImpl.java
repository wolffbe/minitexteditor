package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

public class CommandImpl {
    protected EngineImpl engine;

    public CommandImpl(EngineImpl engine) {
        this.engine = engine;
    }
}

