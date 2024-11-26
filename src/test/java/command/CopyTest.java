package command;

import fr.istic.aco.editor.command.Copy;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CopyTest {

    private EngineImpl engine;
    private Copy copy;

    @BeforeEach
    void setUp() {
        engine = Mockito.mock(EngineImpl.class);
        copy = new Copy(engine);
    }

    @Test
    @DisplayName("Copy text")
    void testCopyText() {
        Map<String, Object> params = new HashMap<>();

        copy.execute(params);

        verify(engine, times(1)).copySelectedText();
    }
}
