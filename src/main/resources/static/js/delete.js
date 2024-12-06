/**
 * Performs a delete operation by making an API request to the text editing engine.
 * The function sends a DELETE request to remove the selected text and logs the outcome.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function deleteText() {
    const url = "http://localhost:8080/api/engine/delete";

    await fetch(url, {
      method: 'DELETE'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      if(data === null) {
        appendToLog("Nothing to delete!");
      } else {
        appendToLog("Deletion successful", data)
      }
    })
    .catch(error => {
        let message = "Error deleting";
        appendToLog(message);
        console.error(message, error);
    });
}