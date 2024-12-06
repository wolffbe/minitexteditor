package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.selection.SelectionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for handling editor engine-related API endpoints.
 * Provides endpoints for managing text editing operations such as selection, insertion, deletion, cut, copy, paste,
 * and undo/redo functionalities.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@RestController
@RequestMapping("/api/engine")
public class EngineController {

    /**
     * Service layer for handling engine operations.
     */
    private final EngineService engineService;

    /**
     * Constructs an {@code EngineController} with the specified engine service.
     *
     * @param engineService the service used to handle engine operations. Must not be null.
     * @throws NullPointerException if {@code engineService} is null.
     */
    public EngineController(EngineService engineService) {
        this.engineService = Objects.requireNonNull(engineService,
                "An engine controller cannot be initialized without an engine service.");
    }

    /**
     * Retrieves the current state of the editor engine.
     *
     * @return the current engine state wrapped in a {@link ResponseEntity}.
     */
    @GetMapping()
    public ResponseEntity<EngineDto> getEngineState() {
        EngineDto engineDto = engineService.getEngineState();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Updates the current selection in the editor.
     *
     * @param requestedSelection the selection details sent in the request body.
     * @return the updated engine state wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/select")
    public ResponseEntity<Optional<EngineDto>> updateSelection(@RequestBody SelectionDto requestedSelection) {
        Optional<EngineDto> engineDto = engineService.updateSelection(requestedSelection);
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Executes a cut operation on the currently selected text.
     *
     * @return the updated engine state after the cut operation, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/cut")
    public ResponseEntity<Optional<EngineDto>> cutSelection() {
        Optional<EngineDto> engineDto = engineService.cutSelection();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Executes a copy operation on the currently selected text.
     *
     * @return the engine state after the copy operation, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/copy")
    public ResponseEntity<Optional<EngineDto>> copySelection() {
        Optional<EngineDto> engineDto = engineService.copySelection();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Pastes the content from the clipboard into the editor at the current cursor position.
     *
     * @return the updated engine state after the paste operation, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/paste")
    public ResponseEntity<Optional<EngineDto>> pasteClipboard() {
        Optional<EngineDto> engineDto = engineService.pasteClipboard();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Inserts the given text at the current cursor position.
     *
     * @param text the text to insert, provided in the request body.
     * @return the updated engine state after the insertion, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/insert")
    public ResponseEntity<EngineDto> insertText(@RequestBody String text) {
        EngineDto engineDto = engineService.insertText(text);
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Deletes the currently selected text from the editor.
     *
     * @return the updated engine state after the delete operation, wrapped in a {@link ResponseEntity}.
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Optional<EngineDto>> deleteSelectedText() {
        Optional<EngineDto> engineDto = engineService.deleteText();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Replays actions from a specific memento index.
     *
     * @param fromMementoIndex the index from which to replay actions.
     * @return the updated engine state after replaying, wrapped in a {@link ResponseEntity}.
     */
    @GetMapping(value = "/replay")
    public ResponseEntity<Optional<EngineDto>> replay(int fromMementoIndex) {
        Optional<EngineDto> engineDto = engineService.replay(fromMementoIndex);
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Undoes the last action performed in the editor.
     *
     * @return the updated engine state after the undo operation, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/undo")
    public ResponseEntity<Optional<EngineDto>> undo() {
        Optional<EngineDto> engineDto = engineService.undo();
        return ResponseEntity.ok(engineDto);
    }

    /**
     * Redoes the last undone action in the editor.
     *
     * @return the updated engine state after the redo operation, wrapped in a {@link ResponseEntity}.
     */
    @PostMapping(value = "/redo")
    public ResponseEntity<Optional<EngineDto>> redo() {
        Optional<EngineDto> engineDto = engineService.redo();
        return ResponseEntity.ok(engineDto);
    }
}
