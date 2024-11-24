async function getEngineState() {

  const url = "http://localhost:8080/api/engine";

  try {
      const response = await fetch(url);
      if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setEngineState(data);
  } catch (error) {
      console.error('Error fetching engine state:', error);
      editor.value = 'Error fetching engine state';
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
        'mementoLastItemIndex' in data) {
            editor.value = data.buffer;

            engine.mementoIndex = data.mementoIndex;
            engine.buffer = data.buffer;
            engine.clipboard = data.clipboard;
            engine.beginIndex = data.beginIndex;
            engine.endIndex = data.endIndex;
            engine.bufferEndIndex = data.bufferEndIndex;
            engine.mementoLastItemIndex = data.mementoLastItemIndex;

            editor.selectionStart = data.beginIndex;
            editor.selectionEnd = data.endIndex;
    } else {
        console.warn('Error parsing engine state');
        editor.value = 'Error';
    }
}