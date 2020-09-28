package fr.istic.aco.editor.engine;

import fr.istic.aco.editor.command.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EngineInvokerTest {

    private EngineInvoker engineInvoker;
    private Command mockCommand;

    @BeforeEach
    void setUp() {
        engineInvoker = new EngineInvoker();
        mockCommand = Mockito.mock(Command.class);
        engineInvoker.setCommand(mockCommand);
    }

    @Test
    @DisplayName("Test execute invoker command")
    void testExecute() {
        Map<String, Object> params = new HashMap<>();

        engineInvoker.execute(params);

        verify(mockCommand, times(1)).execute(params);
    }
}
