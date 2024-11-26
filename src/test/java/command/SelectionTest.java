package command;

import fr.istic.aco.editor.command.Selection;
import fr.istic.aco.editor.engine.EngineImpl;
import fr.istic.aco.editor.selection.SelectionImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SelectionTest {

    @Mock
    private EngineImpl engine;

    @InjectMocks
    private Selection selection;

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
    @DisplayName("Execute select command")
    void testSelectValidSelection() {
        int beginIndex = 0;
        int endIndex = 1;

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", beginIndex);
        params.put("endIndex", endIndex);

        SelectionImpl selectionImpl = mock(SelectionImpl.class);

        when(engine.getSelection()).thenReturn(selectionImpl);
        when(selectionImpl.getBeginIndex()).thenReturn(beginIndex);
        when(selectionImpl.getEndIndex()).thenReturn(endIndex);

        selection.execute(params);

        verify(selectionImpl, times(1)).setBeginIndex(beginIndex);
        verify(selectionImpl, times(1)).setEndIndex(endIndex);
    }

    @Test
    @DisplayName("Move the carel to the left")
    void testMoveCarelToLeft() {
        int carelBeginIndex = 1;
        int carelEndIndex = 1;

        int beginIndex = 0;
        int endIndex = 0;

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", beginIndex);
        params.put("endIndex", endIndex);

        SelectionImpl selectionImpl = mock(SelectionImpl.class);

        when(engine.getSelection()).thenReturn(selectionImpl);
        when(selectionImpl.getBeginIndex()).thenReturn(carelBeginIndex);
        when(selectionImpl.getEndIndex()).thenReturn(carelEndIndex);

        selection.execute(params);

        verify(selectionImpl, times(1)).setBeginIndex(beginIndex);
        verify(selectionImpl, times(1)).setEndIndex(endIndex);
    }

    @Test
    @DisplayName("Move the carel to the right")
    void testMoveCarelToRight() {
        int carelBeginIndex = 0;
        int carelEndIndex = 0;

        int beginIndex = 1;
        int endIndex = 1;

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", beginIndex);
        params.put("endIndex", endIndex);

        SelectionImpl selectionImpl = mock(SelectionImpl.class);

        when(engine.getSelection()).thenReturn(selectionImpl);
        when(selectionImpl.getBeginIndex()).thenReturn(carelBeginIndex);
        when(selectionImpl.getEndIndex()).thenReturn(carelEndIndex);

        selection.execute(params);

        verify(selectionImpl, times(1)).setBeginIndex(beginIndex);
        verify(selectionImpl, times(1)).setEndIndex(endIndex);
    }

    @Test
    @DisplayName("Select with missing begin index")
    void testSelectMissingBeginIndex() {
        int beginIndex = 0;
        int endIndex = 1;

        Map<String, Object> params = new HashMap<>();
        params.put("endIndex", endIndex);

        SelectionImpl selectionImpl = mock(SelectionImpl.class);
        when(engine.getSelection()).thenReturn(selectionImpl);

        when(selectionImpl.getBeginIndex()).thenReturn(beginIndex);
        when(selectionImpl.getEndIndex()).thenReturn(endIndex);

        assertThrows(IllegalArgumentException.class, () -> selection.execute(params));

        verify(selectionImpl, never()).setBeginIndex(anyInt());
        verify(selectionImpl, never()).setEndIndex(anyInt());
    }


    @Test
    @DisplayName("Select with missing end index")
    void testSelectMissingEndIndex() {
        int beginIndex = 0;
        int endIndex = 1;

        Map<String, Object> params = new HashMap<>();
        params.put("beginIndex", beginIndex);

        SelectionImpl selectionImpl = mock(SelectionImpl.class);
        when(engine.getSelection()).thenReturn(selectionImpl);

        when(selectionImpl.getBeginIndex()).thenReturn(beginIndex);
        when(selectionImpl.getEndIndex()).thenReturn(endIndex);

        assertThrows(IllegalArgumentException.class, () -> selection.execute(params));

        verify(selectionImpl, never()).setBeginIndex(anyInt());
        verify(selectionImpl, never()).setEndIndex(anyInt());
    }
}
