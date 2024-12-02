package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.selection.SelectionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/engine")
public class EngineController {

    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = Objects.requireNonNull(engineService,
                "An engine controller cannot be initialized without an engine service.");
    }

    @GetMapping()
    public ResponseEntity<EngineDto> getEngineState() {
        EngineDto engineDto = engineService.getEngineState();
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/select")
    public ResponseEntity<Optional<EngineDto>> updateSelection(@RequestBody SelectionDto requestedSelection) {
        Optional<EngineDto> engineDto = engineService.updateSelection(requestedSelection);
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/cut")
    public ResponseEntity<Optional<EngineDto>> cutSelection() {
        Optional<EngineDto> engineDto = engineService.cutSelection();
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/copy")
    public ResponseEntity<Optional<EngineDto>> copySelection() {
        Optional<EngineDto> engineDto = engineService.copySelection();
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/paste")
    public ResponseEntity<Optional<EngineDto>> pasteClipboard() {
        Optional<EngineDto> engineDto = engineService.pasteClipboard();
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<EngineDto> insertText(@RequestBody String text) {
        EngineDto engineDto = engineService.insertText(text);
        return ResponseEntity.ok(engineDto);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Optional<EngineDto>> deleteSelectedText() {
        Optional<EngineDto> engineDto = engineService.deleteText();
        return ResponseEntity.ok(engineDto);
    }

    @GetMapping(value = "/replay")
    public ResponseEntity<Optional<EngineDto>> replay(int fromMementoIndex) {
        Optional<EngineDto> engineDto = engineService.replay(fromMementoIndex);
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/undo")
    public ResponseEntity<Optional<EngineDto>> undo() {
        Optional<EngineDto> engineDto = engineService.undo();
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping(value = "/redo")
    public ResponseEntity<Optional<EngineDto>> redo() {
        Optional<EngineDto> engineDto = engineService.redo();
        return ResponseEntity.ok(engineDto);
    }
}
