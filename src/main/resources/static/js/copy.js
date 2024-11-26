async function copy() {
    const url = "http://localhost:8080/api/engine/copy";

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