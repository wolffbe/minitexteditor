package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

/**
 * Command to paste the content of the clipboard into the editor at the current cursor position.
 * This command interacts with the {@link EngineImpl} to perform the paste operation.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class Paste extends CommandImpl implements Command {

    /**
     * Constructs a new Paste command with the specified engine.
     *
     * @param engine the engine on which this command operates. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public Paste(EngineImpl engine) {
        super(engine);
    }

    /**
     * Executes the paste operation using the content from the clipboard.
     *
     * @param params a map of parameters for the command execution. This command does not require any parameters.
     * @throws NullPointerException if the engine or its dependencies are improperly initialized.
     */
    @Override
    public void execute(Map<String, Object> params) {
        this.engine.pasteClipboard();
    }
}
