package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

/**
 * Command to insert text into the editor at the current cursor position.
 * This command interacts with the {@link EngineImpl} to perform the insertion operation.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class Insertion extends CommandImpl implements Command {

    /**
     * Constructs a new Insertion command with the specified engine.
     *
     * @param engine the engine on which this command operates. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public Insertion(EngineImpl engine) {
        super(engine);
    }

    /**
     * Executes the insertion operation using the provided parameters.
     *
     * @param params a map of parameters for the command execution.
     *               This map must contain a key "text" with the value of the text to insert.
     * @throws IllegalArgumentException if the "text" parameter is missing in the provided map.
     * @throws NullPointerException if the engine or its dependencies are improperly initialized.
     */
    @Override
    public void execute(Map<String, Object> params) {
        if (!params.containsKey("text")) {
            throw new IllegalArgumentException("Cannot execute insertion command due to missing text");
        }
        String text = String.valueOf(params.get("text"));
        this.engine.insert(text);
    }
}
