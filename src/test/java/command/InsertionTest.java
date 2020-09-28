package command;

import fr.istic.aco.editor.command.Deletion;
import fr.istic.aco.editor.command.Insertion;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class InsertionTest {

    @Mock
    private EngineImpl engine;

    private Insertion insertion;

    private AutoCloseable autoCloseable;

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        insertion = new Insertion(engine);
    }

    @Test
    @DisplayName("Execute insert command")
    void testInsertingText() {
        String text = "text";
        Map<String, Object> params = new HashMap<>();
        params.put("text", text);

        insertion.execute(params);

        verify(engine, times(1)).insert(text);
    }

    @Test
    @DisplayName("Execute insert command with a missing text parameter")
    void testInsertMissingTextParameter() {
        Map<String, Object> params = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> insertion.execute(params));

        verify(engine, never()).insert(anyString());
    }
}
