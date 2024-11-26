async function deleteText() {
    const url = "http://localhost:8080/api/engine/delete";

    await fetch(url, {
      method: 'DELETE'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error: ${response.status} - ${response}`);
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