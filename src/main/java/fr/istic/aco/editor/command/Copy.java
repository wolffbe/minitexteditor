package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

public class Copy extends CommandImpl implements Command {
    public Copy(EngineImpl engine) {
        super(engine);
    }

    @Override
    public void execute(Map<String, Object> params) {
        this.engine.copySelectedText();
    }
}
