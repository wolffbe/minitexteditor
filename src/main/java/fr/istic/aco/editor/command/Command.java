package fr.istic.aco.editor.command;

import java.util.Map;

/**
 * Represents a command in the text editing engine.
 * Commands encapsulate an action and provide a mechanism for executing it with optional parameters.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public interface Command {

    /**
     * Executes the command with the given parameters.
     *
     * @param params a map of parameters required for the command's execution.
     */
    void execute(Map<String, Object> params);
}
