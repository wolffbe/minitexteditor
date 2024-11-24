package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.OriginatorImpl;
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
    private final OriginatorImpl originator;
    private final CaretakerImpl<EngineImpl> caretaker;

    public EngineController(EngineImpl engine, EngineInvoker engineInvoker, OriginatorImpl originator, CaretakerImpl<EngineImpl> caretaker) {
        this.engine = engine;
        this.engineInvoker = engineInvoker;
        this.originator = originator;
        this.caretaker = caretaker;

        this.originator.setState(this.engine);
        this.caretaker.addMemento(this.originator.saveState());
    }

    @GetMapping()
    public ResponseEntity<String> getEngineState() {
        int mementoIndex = caretaker.getMementoCount();
        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestBody SelectionDto selection) {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", selection.beginIndex());
        params.put("endIndex", selection.endIndex());

        Command select = new Selection(engine);

        engineInvoker.setCommand(select);

        try {
            engineInvoker.execute(params);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Command cut = new Cut(engine);

        engineInvoker.setCommand(cut);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Command copy = new Copy(engine);

        engineInvoker.setCommand(copy);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Command paste = new Paste(engine);

        engineInvoker.setCommand(paste);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestBody String text) {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        int mementoIndex = caretaker.getMementoCount() + 1;

        Command delete = new Deletion(engine);

        engineInvoker.setCommand(delete);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoIndex, caretaker));
    }

    @GetMapping("/replay")
    public ResponseEntity<String> replay(@RequestParam Integer index) {
        originator.restoreState(caretaker.getMemento(index));

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(index, caretaker));
    }
}
