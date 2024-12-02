document.addEventListener('DOMContentLoaded', updateEngineState);

editor.addEventListener('keyup', async (event) => {
    if(event.key === "ArrowUp" ||
       event.key === "ArrowDown" ||
       event.key === "ArrowLeft" ||
       event.key === "ArrowRight") {
        await checkSelection();
    }

    if(event.ctrlKey && event.key === "c") {
        await copy().then(() => {
            updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "x") {
        await cut().then(() => {
            updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "v") {
        if(engine.clipboard === "") {
            event.preventDefault();
        } else {
            await paste().then(() => {
                updateEngineState();
            });
        }
    }

    if(event.ctrlKey && event.key === "y") {
        event.preventDefault();
        await redo().then(() => {
            updateEngineState();
        });
    }

    if(event.ctrlKey && event.key === "z") {
        event.preventDefault();
        await undo().then(() => {
            updateEngineState();
        });
    }

    if(event.key === "Backspace") {
        await deleteText().then(() => {
            updateEngineState();
        });
    }

    if(event.key === "Delete" || event.key === "Enter" || event.key === 'Alt' || event.key === 'AltGraph') {
        event.preventDefault();
        alert("The " + event.key + " key is currently not supported!");
    }

    if(!event.ctrlKey && event.key.length === 1) {
        await insert(event.key).then(() => {
            updateEngineState();
        });
    }
});

editor.addEventListener('contextmenu', function(event) {
    event.preventDefault();
    alert("The context menu is currently not supported!");
});

editor.addEventListener('click', async (event) => {
    await checkSelection();
});

recordButton.addEventListener('click', () => {
    record();
});
replayButton.addEventListener('click', async () => {
    await replay();
});