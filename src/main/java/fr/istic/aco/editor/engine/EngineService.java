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

/**
 * Service layer for managing operations on the editor engine.
 * This class acts as a bridge between the API controller and the core engine logic, handling
 * commands, memento management, and engine state updates.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@Service
public class EngineService {

    /**
     * The core editor engine that performs operations.
     */
    private final EngineImpl engine;

    /**
     * The command invoker responsible for executing commands.
     */
    private final EngineInvoker engineInvoker;

    /**
     * The originator for creating and restoring mementos of the engine state.
     */
    private final OriginatorImpl originator;

    /**
     * The caretaker responsible for managing the memento history.
     */
    private final CaretakerImpl<EngineImpl> caretaker;

    /**
     * Constructs an {@code EngineService} with the specified dependencies.
     *
     * @param engine         the core editor engine. Must not be null.
     * @param engineInvoker  the invoker for executing commands. Must not be null.
     * @param originator     the originator for managing engine mementos. Must not be null.
     * @param caretaker      the caretaker for storing and retrieving mementos. Must not be null.
     * @throws NullPointerException if any dependency is null.
     */
    public EngineService(EngineImpl engine, EngineInvoker engineInvoker, OriginatorImpl originator, CaretakerImpl<EngineImpl> caretaker) {
        this.engine = Objects.requireNonNull(engine, "An engine service requires an engine invoker.");
        this.engineInvoker = Objects.requireNonNull(engineInvoker, "An engine service requires an engine invoker.");
        this.originator = Objects.requireNonNull(originator, "An engine service requires an originator.");
        this.caretaker = Objects.requireNonNull(caretaker, "An engine service requires a caretaker.");
        initializeEngineService();
    }

    /**
     * Initializes the engine service by associating the originator with the engine
     * and saving the initial engine state in the caretaker.
     */
    private void initializeEngineService() {
        originator.setEngine(engine);
        caretaker.addMemento(originator.saveState());
    }

    /**
     * Retrieves the current state of the editor engine.
     *
     * @return the current engine state as an {@code EngineDto}.
     */
    public EngineDto getEngineState() {
        return new EngineDto(caretaker.getCurrentMementoIndex(), engine, caretaker.getLastMementoIndex());
    }

    /**
     * Updates the selection in the editor.
     *
     * @param requestedSelection the requested selection details.
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if the selection remains unchanged.
     */
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

    /**
     * Executes a cut operation on the currently selected text.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if there is no selection to cut.
     */
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

    /**
     * Executes a copy operation on the currently selected text.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if there is no selection to copy.
     */
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

    /**
     * Pastes the content of the clipboard at the current cursor position.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if the clipboard is empty.
     */
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

    /**
     * Inserts text at the current cursor position.
     *
     * @param text the text to insert.
     * @return the updated engine state as an {@code EngineDto}.
     */
    public EngineDto insertText(String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);
        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        caretaker.addMemento(originator.saveState());

        return getEngineState();
    }

    /**
     * Deletes the currently selected text.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if there is no content to delete.
     */
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

    /**
     * Replays actions from a specified memento index.
     *
     * @param fromMementoIndex the starting index for replaying actions.
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if the memento index matches the current state.
     */
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

    /**
     * Undoes the last operation performed on the editor.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if no further undo actions are possible.
     */
    public Optional<EngineDto> undo() {
        if (caretaker.isFirstMemento()) {
            return Optional.empty();
        }

        caretaker.decrementMementoIndex();
        Memento<EngineImpl> previousMemento = caretaker.getMemento(caretaker.getCurrentMementoIndex());
        originator.restoreState(previousMemento);

        return Optional.of(getEngineState());
    }

    /**
     * Redoes the last undone operation on the editor.
     *
     * @return an {@code Optional} containing the updated engine state, or an empty {@code Optional}
     * if no further redo actions are possible.
     */
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