package command;

import fr.istic.aco.editor.command.Copy;
import fr.istic.aco.editor.command.Deletion;
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

class DeletionTest {

    @Mock
    private EngineImpl engine;

    private Deletion deletion;

    private AutoCloseable autoCloseable;

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        deletion = new Deletion(engine);
    }

    @Test
    @DisplayName("Execute delete command")
    void testDeleteText() {
        Map<String, Object> params = new HashMap<>();

        deletion.execute(params);

        verify(engine, times(1)).delete();
    }
}
