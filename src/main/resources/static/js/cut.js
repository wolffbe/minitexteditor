async function cut() {
    const url = "http://localhost:8080/api/engine/cut";

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