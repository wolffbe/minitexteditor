document.addEventListener('DOMContentLoaded', getEngineState);

editor.addEventListener('keydown', async (event) => {
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

editor.addEventListener("input", async (event) => {
    await checkSelection();
});
editor.addEventListener("click", async (event) => {
    await checkSelection();
});
editor.addEventListener("keyup", async (event) => {
    await checkSelection();
});