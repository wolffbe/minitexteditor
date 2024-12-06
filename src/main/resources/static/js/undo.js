/**
 * Performs an undo operation by making an API request to the text editing engine.
 * Sends a POST request to revert the last action and logs the result.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function undo() {
    const url = "http://localhost:8080/api/engine/undo";

    await fetch(url, {
      method: 'POST'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      if(data === null) {
        appendToLog("Nothing to undo!");
      } else {
        appendToLog("Undo successful", data)
      }
    })
    .catch(error => {
        let message = "Error undoing";
        appendToLog(message);
        console.error(message, error);
    });
}