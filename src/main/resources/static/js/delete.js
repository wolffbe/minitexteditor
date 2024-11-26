async function deleteText() {
    if(engine.buffer.length === 0) return;

    const url = "http://localhost:8080/api/engine/delete";

    await fetch(url, {
      method: 'DELETE'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      appendToLog("Deletion successful", data)
    })
    .catch(error => {
        let message = "Error deleting";
        appendToLog(message);
        console.error(message, error);
    });
}