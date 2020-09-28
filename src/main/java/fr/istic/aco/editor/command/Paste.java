package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

public class Paste extends CommandImpl implements Command {
    public Paste(EngineImpl engine) {
        super(engine);
    }

    @Override
    public void execute(Map<String, Object> params) {
        this.engine.pasteClipboard();
    }
}