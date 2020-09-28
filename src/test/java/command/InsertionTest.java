package command;

import fr.istic.aco.editor.command.Insertion;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InsertionTest {

    private EngineImpl engine;
    private Insertion insertion;

    @BeforeEach
    void setUp() {
        engine = Mockito.mock(EngineImpl.class);
        insertion = new Insertion(engine);
    }

    @Test
    @DisplayName("Insert text")
    void testInsertingText() {
        String text = "text";
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        insertion.execute(params);

        verify(engine, times(1)).insert(text);
    }

    @Test
    @DisplayName("Insert text with a missing text parameter")
    void testInsertMissingTextParameter() {
        Map<String, Object> params = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> insertion.execute(params));

        verify(engine, never()).insert(anyString());
    }
}
