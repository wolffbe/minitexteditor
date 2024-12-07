/**
 * Checks if the current text selection in the editor matches the engine's recorded selection.
 * If there is a mismatch, it triggers the `select` function to update the engine's selection state.
 *
 * Author: Benedict Wolff
 * @version 1.0
 */
async function checkSelection() {
    if (editor.selectionStart !== engine.beginIndex || editor.selectionEnd !== engine.endIndex) {
        await select();
    }
}

/**
 * Updates the selection in the text editing engine based on the editor's current selection.
 * Sends a POST request with the updated selection indices.
 */
async function select() {
    const index = {
        beginIndex: editor.selectionStart,
        endIndex: editor.selectionEnd,
    }

    const url = "http://localhost:8080/api/engine/select";

    await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(index),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      if(data != null) {
        appendToLog("Selection updated", data)
      }
    })
    .catch(error => {
        let message = "Error updating selection";
        appendToLog(message);
        console.error(message, error);
    });
}