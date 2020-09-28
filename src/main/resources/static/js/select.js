let selectionStart = editor.selectionStart;
let selectionEnd = editor.selectionEnd;

async function checkSelection() {
    const currentSelectionStart = editor.selectionStart;
    const currentSelectionEnd = editor.selectionEnd;

    if (currentSelectionStart !== selectionStart || currentSelectionEnd !== selectionEnd) {
        if(currentSelectionStart !== selectionStart) {
            selectionStart = currentSelectionStart;
        }
        if(currentSelectionEnd !== selectionEnd) {
            selectionEnd = currentSelectionEnd;
        }
        await select();
    }
}

async function select() {
    const index = {
        beginIndex: selectionStart,
        endIndex: selectionEnd,
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
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      appendToLog("Selection updated", data)
    })
    .catch(error => {
        console.error('Error selecting:', error);
    });
}