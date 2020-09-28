/**
 * Fetches the result of a redo operation by making an API request to the text editing engine.
 * Sends a POST request to perform the redo operation and handles the response.
 *
 * Author: Benedict Wolff
 * @version 1.0
 *
 * @param {number} mementoIndex - The index of the memento to redo.
 * @returns {Promise<Object|undefined>} The updated engine state after the redo, or undefined if an error occurs.
 */
async function fetchRedo(mementoIndex) {
    const url = "http://localhost:8080/api/engine/redo";

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: mementoIndex,
        });

        if (!response.ok) {
            throw new Error(response);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        let message = "Error redoing";
        appendToLog(message);
        console.error(message, error);
    }
}

/**
 * Performs a redo operation by fetching the redo result from the server.
 * Logs the result of the operation or a message if there is nothing to redo.
 */
async function redo() {
    const data = await fetchRedo();
    if(data === null) {
        appendToLog("Nothing to redo!");
    } else {
        appendToLog("Redo successful", data)
    }
}