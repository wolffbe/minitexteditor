/**
 * Starts recording actions in the text editing engine.
 * Disables the record button, enables the replay button, and marks the starting point for the recording.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */

let recordedMementoIndex = 0;

function record() {
    recordButton.disabled = true;
    replayButton.disabled = false;

    recording = true;
    recordedMementoIndex = engine.mementoIndex + 1;

    appendToLog("Recording started:");
}
