async function replay() {
    appendToLog("Recording stopped.");

    editor.disabled = true;
    recordButton.disabled = true;
    replayButton.disabled = true;

    let record = recordedMementoIndex;

    appendToLog("Replay started:");

    for (; record <= engine.lastMementoIndex; record++) {
        try {
            const data = await fetchRedo(record);

            if (data !== null) {
                setEngineState(data);
                appendToLog("Successfully restored memento", data);

                await new Promise(resolve => setTimeout(resolve, 100));
            }
        } catch (error) {
            let message = `Error during replay for memento index ${record}`;
            appendToLog(message);
            console.error(message, error);
        }
    }

    recordedMementoIndex = 0;

    editor.disabled = false;
    recordButton.disabled = false;

    appendToLog("Replay stopped.");
}
