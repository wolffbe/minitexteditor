package command;

import fr.istic.aco.editor.command.Deletion;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeletionTest {

    private EngineImpl engine;
    private Deletion deletion;

    @BeforeEach
    void setUp() {
        engine = Mockito.mock(EngineImpl.class);
        deletion = new Deletion(engine);
    }

    @Test
    @DisplayName("Test deleting text")
    void testDeleteText() {
        Map<String, Object> params = new HashMap<>();

        deletion.execute(params);

        verify(engine, times(1)).delete();
    }
}
