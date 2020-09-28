package fr.istic.aco.editor.command;

import java.util.Map;

public interface Command {
    void execute(Map<String, Object> params);
}
