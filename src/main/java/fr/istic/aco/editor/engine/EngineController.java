package fr.istic.aco.editor.engine;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/engine")
public class EngineController {

    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/buffer")
    public ResponseEntity<String> getBufferContents() {
        return ResponseEntity.ok(engineService.getBufferContents());
    }

    @GetMapping("/clipboard")
    public ResponseEntity<String> getClipboardContents() {
        return ResponseEntity.ok(engineService.getClipboardContents());
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestParam int beginIndex, @RequestParam int endIndex) {
        try {
            engineService.updateSelection(beginIndex, endIndex);
            return ResponseEntity.ok("Selection updated successfully.");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body("Invalid selection range");
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        engineService.cutSelectedText();
        return ResponseEntity.ok("Text cut to clipboard.");
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        engineService.copySelectedText();
        return ResponseEntity.ok("Text copied to clipboard.");
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        engineService.pasteClipboard();
        return ResponseEntity.ok("Clipboard contents pasted.");
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestParam String text) {
        engineService.insertText(text);
        return ResponseEntity.ok("Text inserted into buffer.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        engineService.deleteSelectedText();
        return ResponseEntity.ok("Selected text deleted.");
    }
}
