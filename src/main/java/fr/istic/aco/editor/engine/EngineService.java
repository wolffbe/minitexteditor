package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.Memento;
import fr.istic.aco.editor.memento.OriginatorImpl;
import fr.istic.aco.editor.selection.SelectionDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class EngineService {

    private EngineImpl engine;
    private final EngineInvoker engineInvoker;
    private final OriginatorImpl originator;
    private final CaretakerImpl<EngineImpl> caretaker;

    public EngineService(EngineImpl engine, EngineInvoker engineInvoker, OriginatorImpl originator, CaretakerImpl<EngineImpl> caretaker) {
        this.engine = Objects.requireNonNull(engine, "An engine service requires an engine.");
        this.engineInvoker = Objects.requireNonNull(engineInvoker, "An engine service requires an engine invoker.");
        this.originator = Objects.requireNonNull(originator, "An engine service requires an originator.");
        this.caretaker = Objects.requireNonNull(caretaker, "An engine service requires a caretaker.");
        initializeEngine();
    }

    private void initializeEngine() {
        caretaker.addMemento(originator.saveState());
    }

    public EngineDto getEngineState() {
        return new EngineDto(caretaker.getCurrentMementoIndex(), engine, caretaker.getLastMementoIndex());
    }

    public Optional<EngineDto> updateSelection(SelectionDto requestedSelection) {
        int currentBeginIndex = engine.getSelection().getBeginIndex();
        int currentEndIndex = engine.getSelection().getEndIndex();

        if (currentBeginIndex == requestedSelection.beginIndex() &&
                currentEndIndex == requestedSelection.endIndex()) {
            return Optional.empty();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", requestedSelection.beginIndex());
        params.put("endIndex", requestedSelection.endIndex());

        Command select = new Selection(engine);
        engineInvoker.setCommand(select);
        engineInvoker.execute(params);

        caretaker.addMemento(originator.saveState());

        return Optional.of(getEngineState());
    }

    public Optional<EngineDto> cutSelection() {
        if (engine.getSelection().getBeginIndex() == engine.getSelection().getEndIndex()) {
            return Optional.empty();
        }

        Command cut = new Cut(engine);

        engineInvoker.setCommand(cut);
        engineInvoker.execute(null);

        caretaker.addMemento(originator.saveState());

        return Optional.of(getEngineState());
    }

    public Optional<EngineDto> copySelection() {
        if (engine.getSelection().getBeginIndex() == engine.getSelection().getEndIndex()) {
            return Optional.empty();
        }

        Command copy = new Copy(engine);

        engineInvoker.setCommand(copy);
        engineInvoker.execute(null);

        caretaker.addMemento(originator.saveState());

        return Optional.of(getEngineState());
    }

    public Optional<EngineDto> pasteClipboard() {
        if (engine.getClipboardContents().isEmpty()) {
            return Optional.empty();
        }

        Command paste = new Paste(engine);

        engineInvoker.setCommand(paste);
        engineInvoker.execute(null);

        caretaker.addMemento(originator.saveState());

        return Optional.of(getEngineState());
    }

    public EngineDto insertText(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        caretaker.addMemento(originator.saveState());

        return getEngineState();
    }

    public Optional<EngineDto> deleteText() {
        if (engine.getBufferContents().isEmpty()) {
            return Optional.empty();
        }

        Command delete = new Deletion(engine);

        engineInvoker.setCommand(delete);
        engineInvoker.execute(null);

        caretaker.addMemento(originator.saveState());

        return Optional.of(getEngineState());
    }

    public Optional<EngineDto> replay(int fromMementoIndex) {
        if (caretaker.getCurrentMementoIndex() == fromMementoIndex) {
            return Optional.empty();
        }

        caretaker.setMementoIndex(fromMementoIndex);

        for (int i = fromMementoIndex; i <= caretaker.getLastMementoIndex(); i++) {
            Memento<EngineImpl> memento = caretaker.getMemento(i);
            originator.restoreState(memento);
        }

        return Optional.of(getEngineState());
    }


    public Optional<EngineDto> undo() {
        if (caretaker.isFirstMemento()) {
            return Optional.empty();
        }

        caretaker.decrementMementoIndex();

        Memento<EngineImpl> previousMemento = caretaker.getMemento(caretaker.getCurrentMementoIndex());
        originator.restoreState(previousMemento);

        return Optional.of(getEngineState());
    }

    public Optional<EngineDto> redo() {
        if (caretaker.isLastMemento()) {
            return Optional.empty();
        }

        caretaker.incrementMementoIndex();

        Memento<EngineImpl> nextMemento = caretaker.getMemento(caretaker.getCurrentMementoIndex());
        originator.restoreState(nextMemento);

        return Optional.of(getEngineState());
    }
}
