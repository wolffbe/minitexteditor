/**
 * Replays recorded actions in the text editing engine.
 * Temporarily disables the editor and control buttons while replaying actions from the recorded memento index.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
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

/**
 * Fetches replay data for the recorded memento index from the text editing engine.
 * Sends a GET request with the memento index as a query parameter.
 *
 * @param {number} recordedMementoIndex - The starting index for replaying actions.
 * @returns {Promise<Object|undefined>} The replayed engine state, or undefined if an error occurs.
 */
async function fetchReplay(recordedMementoIndex) {
    const url = new URL("http://localhost:8080/api/engine/replay");
    url.searchParams.append('fromMementoIndex', recordedMementoIndex);

    return fetch(url.toString(), {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(response);
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