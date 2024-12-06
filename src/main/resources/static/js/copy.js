/**
 * Performs a copy operation by making an API request to the text editing engine.
 * The function sends a POST request to copy the selected text and logs the outcome.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function copy() {
    const url = "http://localhost:8080/api/engine/copy";

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
        appendToLog("Nothing to copy!");
      } else {
        appendToLog("Copy successful", data)
      }
    })
    .catch(error => {
        let message = "Error copying";
        appendToLog(message);
        console.error(message, error);
    });
}