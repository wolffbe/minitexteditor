package engine;

import fr.istic.aco.editor.command.Command;
import fr.istic.aco.editor.engine.EngineController;
import fr.istic.aco.editor.engine.EngineInvoker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EngineInvokerTest {

    @Mock
    private Command mockCommand;

    @InjectMocks
    private EngineInvoker engineInvoker;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Execute invoker command")
    void testExecuteInvoker() {
        engineInvoker.execute(null);

        verify(mockCommand, times(1)).execute(any());
    }

    @Test
    @DisplayName("Execute invoker command without command")
    void testExecuteInvokerWithoutCommand() {
        String expectedErrorMessage = "An invoker requires a command.";

        engineInvoker.setCommand(null);
        Exception exception = assertThrows(NullPointerException.class, () -> {
            engineInvoker.execute(null);
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }
}
