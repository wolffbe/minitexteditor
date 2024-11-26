package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.*;
import fr.istic.aco.editor.memento.CaretakerImpl;
import fr.istic.aco.editor.memento.OriginatorImpl;
import fr.istic.aco.editor.selection.SelectionDto;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<EngineDto> getEngineState() {
        int mementoIndex = caretaker.getLastMementoIndex();
        return ResponseEntity.ok(new EngineDto(mementoIndex, caretaker));
    }

    @PostMapping(value = "/select", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> updateSelection(@RequestBody SelectionDto selection) {
        SelectionImpl lastSelection = (SelectionImpl) originator.saveState().state().getSelection();

        if (lastSelection.getBeginIndex() == selection.beginIndex() && lastSelection.getEndIndex() == selection.endIndex()) {
            return ResponseEntity.ok(Optional.empty());
        } else {
            try {
                int nextMementoIndex = caretaker.getNextMementoIndex();

                Map<String, Object> params = new HashMap<>();
                params.put("beginIndex", selection.beginIndex());
                params.put("endIndex", selection.endIndex());

                Command select = new Selection(engine);

                engineInvoker.setCommand(select);

                engineInvoker.execute(params);

                originator.setState(engine);
                caretaker.addMemento(originator.saveState());

                return ResponseEntity.ok(Optional.of(new EngineDto(nextMementoIndex, caretaker)));
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
            }
        }
    }

    @PostMapping(value = "/cut", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> cutSelectedText() {
        SelectionImpl selection = (SelectionImpl) originator.saveState().state().getSelection();

        if (selection.getBeginIndex() == selection.getEndIndex()) {
            return ResponseEntity.ok(Optional.empty());
        } else {
            int nextMementoIndex = caretaker.getNextMementoIndex();

            Command cut = new Cut(engine);

            engineInvoker.setCommand(cut);
            engineInvoker.execute(null);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(Optional.of(new EngineDto(nextMementoIndex, caretaker)));
        }
    }

    @PostMapping(value = "/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> copySelectedText() {
        SelectionImpl selection = (SelectionImpl) originator.saveState().state().getSelection();

        if (selection.getBeginIndex() == selection.getEndIndex()) {
            return ResponseEntity.ok(Optional.empty());
        } else {
            int nextMementoIndex = caretaker.getNextMementoIndex();

            Command copy = new Copy(engine);

            engineInvoker.setCommand(copy);
            engineInvoker.execute(null);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(Optional.of(new EngineDto(nextMementoIndex, caretaker)));
        }
    }

    @PostMapping(value = "/paste", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> pasteClipboard() {
        if(originator.saveState().state().getClipboardContents().isEmpty()) {
            return ResponseEntity.ok(Optional.empty());
        } else {
            int nextMementoIndex = caretaker.getNextMementoIndex();

            Command paste = new Paste(engine);

            engineInvoker.setCommand(paste);
            engineInvoker.execute(null);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(Optional.of(new EngineDto(nextMementoIndex, caretaker)));
        }
    }

    @PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EngineDto> insertText(@RequestBody String text) {
        int nextMementoIndex = caretaker.getNextMementoIndex();

        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        Command insertion = new Insertion(engine);

        engineInvoker.setCommand(insertion);
        engineInvoker.execute(params);

        originator.setState(engine);
        caretaker.addMemento(originator.saveState());

        return ResponseEntity.ok(new EngineDto(nextMementoIndex, caretaker));
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> deleteSelectedText() {
        if(originator.saveState().state().getBufferContents().isEmpty()) {
            return ResponseEntity.ok(Optional.empty());
        } else {
            int nextMementoIndex = caretaker.getNextMementoIndex();

            Command delete = new Deletion(engine);

            engineInvoker.setCommand(delete);
            engineInvoker.execute(null);

            originator.setState(engine);
            caretaker.addMemento(originator.saveState());

            return ResponseEntity.ok(Optional.of(new EngineDto(nextMementoIndex, caretaker)));
        }
    }

    @GetMapping(value = "/replay", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> replay(@RequestParam Integer mementoIndex) {
        int lastMementoIndex = caretaker.getLastMementoIndex();

        if(mementoIndex <= lastMementoIndex) {
            originator.restoreState(caretaker.getMemento(mementoIndex));
            return ResponseEntity.ok(Optional.of(new EngineDto(mementoIndex, caretaker)));
        } else {
            return ResponseEntity.ok(Optional.empty());
        }
    }

    @PostMapping(value = "/redo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<EngineDto>> redo(@RequestBody Integer mementoIndex) {
        int lastMementoIndex = caretaker.getLastMementoIndex();

        if(mementoIndex <= lastMementoIndex) {
            originator.restoreState(caretaker.getMemento(mementoIndex));
            return ResponseEntity.ok(Optional.of(new EngineDto(mementoIndex, caretaker)));
        } else {
            return ResponseEntity.ok(Optional.empty());
        }
    }
}
