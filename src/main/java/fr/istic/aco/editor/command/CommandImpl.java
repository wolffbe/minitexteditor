package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;
import java.util.Objects;

public class CommandImpl {
    protected EngineImpl engine;

    public CommandImpl(EngineImpl engine) {
        this.engine = Objects.requireNonNull(engine, "A command cannot be initialized without an engine.");
    }
}