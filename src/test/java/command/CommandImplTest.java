package command;

import fr.istic.aco.editor.command.CommandImpl;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CommandImplTest {

    @Test
    @DisplayName("Initialize command")
    void testInitializeCommand() {
        EngineImpl mockEngine = Mockito.mock(EngineImpl.class);

        CommandImpl command = new CommandImpl(mockEngine);

        assertNotNull(command);
    }

    @Test
    @DisplayName("Initialize command without engine")
    void testInitializeCommandWithoutEngine() {
        String expectedErrorMessage = "A command cannot be initialized without an engine.";

        Exception exception = assertThrows(NullPointerException.class, () -> {
            new CommandImpl(null);
        });
        String errorMessage = exception.getMessage();

        assertEquals(expectedErrorMessage, errorMessage);
    }
}
