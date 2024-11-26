async function insert(key) {
    if(key.length > 1) return;

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
        throw new Error(`Error: ${response.status} - ${response}`);
      }
      return response.json();
    })
    .then(data => {
      appendToLog('Insertion successful', data)
    })
    .catch(error => {
        let message = "Error inserting";
        appendToLog(message);
        console.error(message, error);
    });
}