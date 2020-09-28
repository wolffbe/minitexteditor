package fr.istic.aco.editor.command;

import fr.istic.aco.editor.engine.EngineImpl;

import java.util.Map;

public class Selection extends CommandImpl implements Command {
    public Selection(EngineImpl engine) {
        super(engine);
    }

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
