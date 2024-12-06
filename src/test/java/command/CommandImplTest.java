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

import static org.junit.jupiter.api.Assertions.*;

class CommandImplTest {

    @Mock
    private EngineImpl engine;

    private AutoCloseable autoCloseable;

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Initialize command")
    void testInitializeCommand() {
        CommandImpl command = new CommandImpl(engine);

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
