let recordedMementoIndex = 0;

function record() {
    recordButton.disabled = true;
    replayButton.disabled = false;

    recording = true;
    recordedMementoIndex = engine.mementoIndex + 1;

    appendToLog("Recording started:");
}
