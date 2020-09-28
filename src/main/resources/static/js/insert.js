/**
 * Performs an insertion operation by making an API request to the text editing engine.
 * Sends a POST request with a single character to insert it at the current cursor position.
 *
 * Author: Benedict Wolff
 * @version 1.0
 *
 * @param {string} key - The character to insert. Must be a single character.
 */
async function insert(key) {
    if (key.length > 1) return;

    const url = "http://localhost:8080/api/engine/insert";

    await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: key,
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      appendToLog('Insertion successful', data);
    })
    .catch(error => {
        let message = "Error inserting";
        appendToLog(message);
        console.error(message, error);
    });
}