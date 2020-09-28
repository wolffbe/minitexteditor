package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.Command;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * The `EngineInvoker` class is responsible for executing commands on the editor engine.
 * It follows the Command design pattern, acting as the invoker that triggers execution of a given command.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@Component
public class EngineInvoker {

    /**
     * The command to be executed.
     */
    private Command command;

    /**
     * Sets the command to be executed by the invoker.
     *
     * @param command the command to execute. Must not be null.
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently set command with the provided parameters.
     *
     * @param params a map of parameters required for the command execution.
     * @throws NullPointerException if no command is set.
     */
    public void execute(Map<String, Object> params) {
        Objects.requireNonNull(command, "An invoker requires a command.");
        command.execute(params);
    }
}