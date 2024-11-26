async function redo() {
    const url = "http://localhost:8080/api/engine/redo";

    await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: engine.mementoIndex + 1,
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error: ${response.status} - ${response}`);
      }
      return response.json();
    })
    .then(data => {
      if(data === null) {
        appendToLog("Nothing to redo!");
      } else {
        appendToLog("Redo successful", data)
      }
    })
    .catch(error => {
        let message = "Error redoing";
        appendToLog(message);
        console.error(message, error);
    });
}