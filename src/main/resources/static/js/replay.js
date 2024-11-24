async function replay() {
    appendToLog("Recording stopped.");
    appendToLog("Replay started:");

    editor.disabled = true;
    recordButton.disabled = true;
    replayButton.disabled = true;

    const url = "http://localhost:8080/api/engine/replay";

    let record = recordedMementoIndex;

    try {
        for (; record <= engine.mementoCount; record++) {
            const response = await fetch(`${url}?index=${record}`, {
                method: 'GET',
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();

            setEngineState(data)
            appendToLog("Successfully restored memento", data);

            await new Promise(resolve => setTimeout(resolve, 100));
        }

    } catch (error) {
        console.error('Error during replay:', error);
    } finally {
        recordedMementoIndex = 0;
        editor.disabled = false;
        recordButton.disabled = false;
        appendToLog("Replay stopped.");
    }
}