package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

/**
 * Command to copy the currently selected text in the editor.
 * This command interacts with the {@link EngineImpl} to perform the copy operation.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class Copy extends CommandImpl implements Command {

    /**
     * Constructs a new Copy command with the specified engine.
     *
     * @param engine the engine on which this command operates. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public Copy(EngineImpl engine) {
        super(engine);
    }

    /**
     * Executes the copy operation using the provided parameters.
     *
     * @param params a map of parameters for the command execution. This command does not require any parameters.
     * @throws NullPointerException if the engine or its dependencies are improperly initialized.
     */
    @Override
    public void execute(Map<String, Object> params) {
        this.engine.copySelectedText();
    }
}