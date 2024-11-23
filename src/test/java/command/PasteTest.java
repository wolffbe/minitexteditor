package command;

import fr.istic.aco.editor.command.Paste;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PasteTest {

    private EngineImpl engine;
    private Paste paste;

    @BeforeEach
    void setUp() {
        engine = Mockito.mock(EngineImpl.class);
        paste = new Paste(engine);
    }

    @Test
    @DisplayName("Test pasting a selected text")
    void testPasteSelectedText() {
        Map<String, Object> params = new HashMap<>();

        paste.execute(params);

        verify(engine, times(1)).pasteClipboard();
    }
}
