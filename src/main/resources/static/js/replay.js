async function replay() {
    appendToLog("Recording stopped.");

    editor.disabled = true;
    recordButton.disabled = true;
    replayButton.disabled = true;

    const url = "http://localhost:8080/api/engine/replay";

    let record = recordedMementoIndex;

    appendToLog("Replay started:");

    try {
        for (; record <= engine.lastMementoIndex; record++) {
            const response = await fetch(`${url}?mementoIndex=${record}`, {
                method: 'GET',
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.status} - ${response}`);
            }

            const data = await response.json();

            if(data !== null) {
                setEngineState(data)
                appendToLog("Successfully restored memento", data);

                await new Promise(resolve => setTimeout(resolve, 100));
            }
        }
    } catch (error) {
        let message = "Error during replay";
        appendToLog(message);
        console.error(message, error);
    } finally {
        recordedMementoIndex = 0;

        editor.disabled = false;
        recordButton.disabled = false;

        appendToLog("Replay stopped.");
    }
}