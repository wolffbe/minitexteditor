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
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      appendToLog("Insert successful", data)
    })
    .catch(error => {
        console.error('Error inserting:', error);
    });
}