let isSelecting = false;

async function checkSelection() {
    if (isSelecting) return;

    if (editor.selectionStart !== engine.beginIndex || editor.selectionEnd !== engine.endIndex) {
        isSelecting = true;
        await select();
        isSelecting = false;
    }
}

async function select() {
    const index = {
        beginIndex: editor.selectionStart,
        endIndex: editor.selectionEnd,
    }

    const url = "http://localhost:8080/api/engine/select";

    await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(index),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`Error: ${response.status} - ${response}`);
      }
      return response.json();
    })
    .then(data => {
      if(data != null) {
        appendToLog("Selection updated", data)
      }
    })
    .catch(error => {
        let message = "Error updating selection";
        appendToLog(message);
        console.error(message, error);
    });
}