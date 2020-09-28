/**
 * Functions for managing the state of the text editing engine.
 * Includes operations for fetching the engine state from the server and updating the local state.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */

/**
 * Updates the local engine state by fetching the current state from the server.
 * Calls `getEngineState` to retrieve the state and `setEngineState` to update the local engine object.
 */
async function updateEngineState() {
    const state = await getEngineState();
    setEngineState(state);
}

/**
 * Fetches the current state of the engine from the server.
 * Sends a GET request to the `/api/engine` endpoint.
 *
 * @returns {Promise<Object|undefined>} The current engine state as an object, or undefined if an error occurs.
 */
async function getEngineState() {
    const url = "http://localhost:8080/api/engine";

    try {
        const response = await fetch(url);

        if (!response.ok) {
            const errorMessage = await response.json();
            throw new Error(response);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        const message = "Error fetching engine state";
        appendToLog(message);
        console.error(message, error);
    }
}

/**
 * Updates the local engine state and the editor UI based on the given data object.
 * Verifies that the data contains the required fields before updating.
 *
 * @param {Object} data - The new engine state to set. Should contain `mementoIndex`, `buffer`, `clipboard`,
 *                        `beginIndex`, `endIndex`, `bufferEndIndex`, and `lastMementoIndex`.
 */
function setEngineState(data) {
    if (data &&
        'mementoIndex' in data &&
        'buffer' in data &&
        'clipboard' in data &&
        'beginIndex' in data &&
        'endIndex' in data &&
        'bufferEndIndex' in data &&
        'lastMementoIndex' in data) {
            editor.value = data.buffer;

            engine.mementoIndex = data.mementoIndex;
            engine.buffer = data.buffer;
            engine.clipboard = data.clipboard;
            engine.beginIndex = data.beginIndex;
            engine.endIndex = data.endIndex;
            engine.bufferEndIndex = data.bufferEndIndex;
            engine.lastMementoIndex = data.lastMementoIndex;

            editor.selectionStart = data.beginIndex;
            editor.selectionEnd = data.endIndex;
    } else {
        let message = "Error fetching engine state";
        appendToLog(message);
        console.error(message);
    }
}