package command;

import fr.istic.aco.editor.command.CommandImpl;
import fr.istic.aco.editor.command.Copy;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CopyTest {

    @Mock
    private EngineImpl engine;

    private Copy copy;

    private AutoCloseable autoCloseable;

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        copy = new Copy(engine);
    }

    @Test
    @DisplayName("Execute copy command")
    void testCopyText() {
        Map<String, Object> params = new HashMap<>();

        copy.execute(params);

        verify(engine, times(1)).copySelectedText();
    }
}
