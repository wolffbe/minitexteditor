async function replay() {
    appendToLog("Recording stopped.");

    editor.disabled = true;
    recordButton.disabled = true;
    replayButton.disabled = true;

    const data = await fetchReplay(recordedMementoIndex)
    if(data !== null) {
        setEngineState(data);
    }

    recordedMementoIndex = 0;

    editor.disabled = false;
    recordButton.disabled = false;
}

async function fetchReplay(recordedMementoIndex) {
    const url = new URL("http://localhost:8080/api/engine/replay");
    url.searchParams.append('fromMementoIndex', recordedMementoIndex);

    return fetch(url.toString(), {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error: ${response.status} - ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        if (data === null) {
            appendToLog("Nothing to replay!");
        } else {
            appendToLog("Replay successful", data);
        }
        return data;
    })
    .catch(error => {
        const message = "Error replaying";
        appendToLog(message);
        console.error(message, error);
    });
}