package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

public class Insertion extends CommandImpl implements Command {
    public Insertion(EngineImpl engine) {
        super(engine);
    }

    @Override
    public void execute(Map<String, Object> params) {
        if (!params.containsKey("text")) {
            throw new IllegalArgumentException("Cannot execute insertion command due to missing text");
        }
        String text = String.valueOf(params.get("text"));
        this.engine.insert(text);
    }
}