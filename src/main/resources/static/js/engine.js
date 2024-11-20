async function getEngineState() {
  const url = "http://localhost:8080/api/engine";

  await fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      if (data &&
          'buffer' in data &&
          'clipboard' in data &&
          'beginIndex' in data &&
          'endIndex' in data &&
          'bufferEndIndex' in data) {

        editor.value = data.buffer;

        engine.buffer = data.buffer;
        engine.clipboard = data.clipboard;
        engine.beginIndex = data.beginIndex;
        engine.endIndex = data.endIndex;
        engine.bufferEndIndex = data.bufferEndIndex;
      } else {
        console.warn('No data received from API');
        editor.value = 'Error';
      }
    })
    .catch(error => {
      console.error('Error fetching data:', error);
      editor.value = 'Error fetching data';
    });
}