async function paste() {
    const url = "http://localhost:8080/api/engine/paste";

    await fetch(url, {
      method: 'POST'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error: ${response.status} - ${response}`);
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