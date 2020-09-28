package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.selection.dto.SelectionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/engine")
public class EngineController {

    private final EngineImpl engine;
    private final EngineInvoker engineInvoker;

    public EngineController(EngineImpl engine, EngineInvoker engineInvoker) {
        this.engine = engine;
        this.engineInvoker = engineInvoker;
    }

    @GetMapping()
    public ResponseEntity<String> getEngineState() {
        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestBody SelectionDto selection) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", selection.beginIndex());
        params.put("endIndex", selection.endIndex());

        Command select = new Selection(engine);

        engineInvoker.setCommand(select);

        try {
            engineInvoker.execute(params);
            return ResponseEntity.ok(EngineSerializer.toString(engine));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        Command cut = new Cut(engine);

        engineInvoker.setCommand(cut);
        engineInvoker.execute(null);

        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        Command copy = new Copy(engine);

        engineInvoker.setCommand(copy);
        engineInvoker.execute(null);

        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        Command paste = new Paste(engine);

        engineInvoker.setCommand(paste);
        engineInvoker.execute(null);

        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestBody String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        Command delete = new Deletion(engine);

        engineInvoker.setCommand(delete);
        engineInvoker.execute(null);

        return ResponseEntity.ok(EngineSerializer.toString(engine));
    }
}
