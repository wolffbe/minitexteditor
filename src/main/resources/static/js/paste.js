/**
 * Performs a paste operation by making an API request to the text editing engine.
 * Sends a POST request to insert the clipboard content at the current cursor position.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function paste() {
    const url = "http://localhost:8080/api/engine/paste";

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
        appendToLog("Nothing to paste!");
      } else {
        appendToLog("Paste successful", data)
      }
    })
    .catch(error => {
        let message = "Error pasting";
        appendToLog(message);
        console.error(message, error);
    });
}