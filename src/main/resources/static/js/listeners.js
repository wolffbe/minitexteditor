document.addEventListener('DOMContentLoaded', getEngineState);

editor.addEventListener('keydown', async (event) => {
    if(event.key === "ArrowUp" ||
       event.key === "ArrowDown" ||
       event.key === "ArrowLeft" ||
       event.key === "ArrowRight") {
        await checkSelection();
    }
    if(event.ctrlKey && event.key === "c") {
        await copy().then(() => {
            getEngineState();
        });
    }
    if(event.ctrlKey && event.key === "x") {
        await cut().then(() => {
            getEngineState();
        });
    }
    if(event.ctrlKey && event.key === "v") {
        await paste().then(() => {
            getEngineState();
        });
    }
    if(event.key === "Backspace") {
        await deleteText().then(() => {
            getEngineState();
        });
    }
    if(event.key === "Delete" || event.key === "Enter" || event.key === 'Alt' || event.key === 'AltGraph') {
        alert("The " + event.key + " key is currently not supported!");
        getEngineState();
    }
    if(!event.ctrlKey && event.key.length === 1) {
        await insert(event.key).then(() => {
            getEngineState();
        });
    }
});

editor.addEventListener('contextmenu', function(event) {
    event.preventDefault();
    alert("The context menu is currently not supported!");
});

editor.addEventListener("click", async (event) => {
    await checkSelection();
});

recordButton.addEventListener('click', () => {
    record();
});
replayButton.addEventListener('click', async () => {
    await replay();
});