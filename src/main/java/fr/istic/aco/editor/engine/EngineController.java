package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.selection.dto.SelectionDto;
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
        return ResponseEntity.ok(this.toString());
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestBody SelectionDto selection) {
        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", selection.beginIndex());
        params.put("endIndex", selection.endIndex());

        Command select = new Selection(engine);

        try {
            engineInvoker.setCommand(select);
            engineInvoker.execute(params);
            return ResponseEntity.ok(this.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        Command cut = new Cut(engine);

        engineInvoker.setCommand(cut);
        engineInvoker.execute(null);

        return ResponseEntity.ok(this.toString());
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        Command copy = new Copy(engine);

        engineInvoker.setCommand(copy);
        engineInvoker.execute(null);

        return ResponseEntity.ok(this.toString());
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        Command paste = new Paste(engine);

        engineInvoker.setCommand(paste);
        engineInvoker.execute(null);

        return ResponseEntity.ok(this.toString());
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestBody String text) {
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        return ResponseEntity.ok(this.toString());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        Command delete = new Deletion(engine);

        engineInvoker.setCommand(delete);
        engineInvoker.execute(null);

        return ResponseEntity.ok(this.toString());
    }

    @Override
    public String toString() {
        String buffer = engine.getBufferContents();
        String clipboard = engine.getClipboardContents();
        int beginIndex = engine.getSelection().getBeginIndex();
        int endIndex = engine.getSelection().getEndIndex();
        int bufferEndIndex = engine.getSelection().getBufferEndIndex();

        return String.format(
                "{" +
                        "\"buffer\": \"%s\"," +
                        "\"clipboard\": \"%s\"," +
                        "\"beginIndex\": %d," +
                        "\"endIndex\": %d," +
                        "\"bufferEndIndex\": %d" +
                        "}",
                buffer, clipboard, beginIndex, endIndex, bufferEndIndex
        );
    }
}
