package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;
import java.util.Objects;

/**
 * Base class for all command implementations in the editor.
 * Commands encapsulate actions to be executed on the engine.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class CommandImpl {
    /**
     * The engine instance associated with this command.
     */
    protected EngineImpl engine;

    /**
     * Constructs a new CommandImpl with the specified engine.
     *
     * @param engine the engine on which this command operates. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public CommandImpl(EngineImpl engine) {
        this.engine = Objects.requireNonNull(engine, "A command cannot be initialized without an engine.");
    }
}
