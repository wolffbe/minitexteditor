package command;

import fr.istic.aco.editor.command.Insertion;
import fr.istic.aco.editor.command.Paste;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PasteTest {

    @Mock
    private EngineImpl engine;

    private Paste paste;

    private AutoCloseable autoCloseable;

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        paste = new Paste(engine);
    }

    @Test
    @DisplayName("Execute paste command")
    void testPasteSelectedText() {
        Map<String, Object> params = new HashMap<>();

        paste.execute(params);

        verify(engine, times(1)).pasteClipboard();
    }
}
