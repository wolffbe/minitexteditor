package command;

import fr.istic.aco.editor.command.Cut;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CutTest {

    private EngineImpl engine;
    private Cut cut;

    @BeforeEach
    void setUp() {
        engine = Mockito.mock(EngineImpl.class);
        cut = new Cut(engine);
    }

    @Test
    @DisplayName("Test cutting text")
    void testCutSelectedText() {
        Map<String, Object> params = new HashMap<>();

        cut.execute(params);

        verify(engine, times(1)).cutSelectedText();
    }
}
