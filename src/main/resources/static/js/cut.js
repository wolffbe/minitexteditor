/**
 * Performs a cut operation by making an API request to the text editing engine.
 * The function sends a POST request to cut the selected text and logs the outcome.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function cut() {
    const url = "http://localhost:8080/api/engine/cut";

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
        appendToLog("Nothing to cut!");
      } else {
        appendToLog("Cut successful", data)
      }
    })
    .catch(error => {
        let message = "Error cutting";
        appendToLog(message);
        console.error(message, error);
    });
}