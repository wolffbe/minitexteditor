package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.Command;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EngineInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute(Map<String, Object> params) {
        command.execute(params);
    }
}
