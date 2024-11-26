async function getEngineState() {

  const url = "http://localhost:8080/api/engine";

  try {
      const response = await fetch(url);
      if (!response.ok) {
        const errorMessage = await response.json();
        throw new Error(`Error: ${response.status} - ${errorMessage}`);
      }
      const data = await response.json();
      setEngineState(data);
  } catch (error) {
      let message = "Error fetching engine state";
      appendToLog(message);
      console.error(message, error);
  }
}

function setEngineState(data) {
    if (data &&
        'mementoIndex' in data &&
        'buffer' in data &&
        'clipboard' in data &&
        'beginIndex' in data &&
        'endIndex' in data &&
        'bufferEndIndex' in data &&
        'lastMementoIndex' in data) {
            editor.value = data.buffer;

            engine.mementoIndex = data.mementoIndex;
            engine.buffer = data.buffer;
            engine.clipboard = data.clipboard;
            engine.beginIndex = data.beginIndex;
            engine.endIndex = data.endIndex;
            engine.bufferEndIndex = data.bufferEndIndex;
            engine.lastMementoIndex = data.lastMementoIndex;

            editor.selectionStart = data.beginIndex;
            editor.selectionEnd = data.endIndex;
    } else {
        let message = "Error fetching engine state";
        appendToLog(message);
        console.error(message);
    }
}