package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

/**
 * Command to set or modify the selection in the editor.
 * This command interacts with the {@link EngineImpl} to update the selection range.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public class Selection extends CommandImpl implements Command {

    /**
     * Constructs a new Selection command with the specified engine.
     *
     * @param engine the engine on which this command operates. Must not be null.
     * @throws NullPointerException if the provided engine is null.
     */
    public Selection(EngineImpl engine) {
        super(engine);
    }

    /**
     * Executes the selection operation by updating the selection range in the editor.
     *
     * @param params a map of parameters for the command execution.
     *               The map must contain:
     *               <ul>
     *                   <li><strong>beginIndex</strong>: an integer indicating the start index of the selection.</li>
     *                   <li><strong>endIndex</strong>: an integer indicating the end index of the selection.</li>
     *               </ul>
     *               If the begin and end indices are the same, the behavior adjusts the selection range.
     * @throws IllegalArgumentException if either "beginIndex" or "endIndex" is missing from the parameter map.
     * @throws NullPointerException if the engine or its dependencies are improperly initialized.
     */
    @Override
    public void execute(Map<String, Object> params) {
        if (!params.containsKey("beginIndex")) {
            throw new IllegalArgumentException("Cannot execute selection command due to missing beginIndex");
        }
        if (!params.containsKey("endIndex")) {
            throw new IllegalArgumentException("Cannot execute selection command due to missing endIndex");
        }

        int beginIndex = (int) params.get("beginIndex");
        int endIndex = (int) params.get("endIndex");

        if (beginIndex == endIndex && beginIndex > engine.getSelection().getBeginIndex()) {
            engine.getSelection().setEndIndex(endIndex);
            engine.getSelection().setBeginIndex(beginIndex);
        } else {
            engine.getSelection().setBeginIndex(beginIndex);
            engine.getSelection().setEndIndex(endIndex);
        }
    }
}
