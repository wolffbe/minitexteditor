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
    private final CaretakerImpl caretaker;

    public EngineController(EngineImpl engine, EngineInvoker engineInvoker, OriginatorImpl originator, CaretakerImpl caretaker) {
        this.engine = engine;
        this.engineInvoker = engineInvoker;
        this.originator = originator;
        this.caretaker = caretaker;

        this.originator.setState(this.engine);
        this.caretaker.addMemento(this.originator.saveState());
    }

    @GetMapping()
    public ResponseEntity<String> getEngineState() {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();
        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex, caretaker));
    }

    @PostMapping("/select")
    public ResponseEntity<String> updateSelection(@RequestBody SelectionDto selection) {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", selection.beginIndex());
        params.put("endIndex", selection.endIndex());

        Command select = new Selection(engine);

        engineInvoker.setCommand(select);

        try {
            engineInvoker.execute(params);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/cut")
    public ResponseEntity<String> cutSelectedText() {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Command cut = new Cut(engine);

        engineInvoker.setCommand(cut);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
    }

    @PostMapping("/copy")
    public ResponseEntity<String> copySelectedText() {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Command copy = new Copy(engine);

        engineInvoker.setCommand(copy);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
    }

    @PostMapping("/paste")
    public ResponseEntity<String> pasteClipboard() {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Command paste = new Paste(engine);

        engineInvoker.setCommand(paste);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertText(@RequestBody String text) {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSelectedText() {
        int mementoLastItemIndex = caretaker.getMementoLastItemIndex();

        Command delete = new Deletion(engine);

        engineInvoker.setCommand(delete);
        engineInvoker.execute(null);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(mementoLastItemIndex + 1, caretaker));
    }

    @GetMapping("/replay")
    public ResponseEntity<String> replay(@RequestParam Integer index) {
        originator.restoreState(caretaker.getMemento(index));

        return ResponseEntity.ok(EngineSerializer.toResponseEntityBody(index, caretaker));
    }
}
