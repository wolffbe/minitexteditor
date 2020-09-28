package command;

import fr.istic.aco.editor.command.CommandImpl;
import fr.istic.aco.editor.engine.EngineImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommandImplTest {

    @Test
    @DisplayName("Test initializing CommandImpl")
    void testCommandImplInitialization() {
        EngineImpl mockEngine = Mockito.mock(EngineImpl.class);

        CommandImpl command = new CommandImpl(mockEngine);

        assertNotNull(command);
    }
}
