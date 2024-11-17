package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.engine.dto.Selection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/engine")
public class EngineController {

    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping()
    public ResponseEntity<String> getEngineState() {
        return ResponseEntity.ok(engineService.getEngineState());
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestBody Selection selection) {
        try {
            engineService.updateSelection(selection.getBeginIndex(), selection.getEndIndex());
            return ResponseEntity.ok(engineService.getEngineState());
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        engineService.cutSelectedText();
        return ResponseEntity.ok(engineService.getEngineState());
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        engineService.copySelectedText();
        return ResponseEntity.ok(engineService.getEngineState());
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        engineService.pasteClipboard();
        return ResponseEntity.ok(engineService.getEngineState());
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestBody String text) {
        engineService.insertText(text);
        return ResponseEntity.ok(engineService.getEngineState());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        engineService.deleteSelectedText();
        return ResponseEntity.ok(engineService.getEngineState());
    }
}
