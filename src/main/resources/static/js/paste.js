async function paste() {
    if(engine.beginIndex === 0 && engine.endIndex === 0) return;

    const url = "http://localhost:8080/api/engine/paste";

    await fetch(url, {
      method: 'POST'
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      appendToLog("Paste successful", data)
    })
    .catch(error => {
        console.error('Error pasting:', error);
    });
}