package fr.istic.aco.editor.selection;

/**
 * Data Transfer Object (DTO) representing a text selection in the editor.
 * This class encapsulates the start and end indices of the selection.
 *
 * @param beginIndex the starting index of the selection.
 * @param endIndex   the ending index of the selection.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
public record SelectionDto(int beginIndex, int endIndex) {
}
