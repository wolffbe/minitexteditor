package selection;

import fr.istic.aco.editor.selection.SelectionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectionDtoTest {

    @Test
    @DisplayName("Initialize selection data transfer object")
    void testInitializeSelectionDto() {
        int expectedBeginIndex = 5;
        int expectedEndIndex = 15;

        SelectionDto selectionDto = new SelectionDto(expectedBeginIndex, expectedEndIndex);

        assertEquals(expectedBeginIndex, selectionDto.beginIndex());
        assertEquals(expectedEndIndex, selectionDto.endIndex());
    }
}
