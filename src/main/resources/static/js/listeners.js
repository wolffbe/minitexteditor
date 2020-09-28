/**
 * Event listeners for managing user interactions with the text editor.
 * Handles keyboard input, mouse events, and button clicks to interact with the text editing engine.
 *
 * Author:  Benedict Wolff
 * @version 1.0
 */

document.addEventListener('DOMContentLoaded', updateEngineState);

editor.addEventListener('keydown', async (event) => {
    if(event.ctrlKey && event.key === "c") {
        event.preventDefault();
    }
    if(event.ctrlKey && event.key === "x") {
        event.preventDefault();
    }
    if(event.ctrlKey && event.key === "v") {
        event.preventDefault();
    }
    if(event.ctrlKey && event.key === "y") {
        event.preventDefault();
    }
    if(event.ctrlKey && event.key === "z") {
        event.preventDefault();
    }

    if(event.key === "Backspace") {
        event.preventDefault();
    }

    if(event.key === "Delete" || event.key === "Enter" || event.key === 'Alt' || event.key === 'AltGraph') {
        event.preventDefault();
        alert("The " + event.key + " key is currently not supported!");
    }

    if(!event.ctrlKey && event.key.length === 1) {
        await insert(event.key).then(async () => {
            await updateEngineState();
        });
    }
});

editor.addEventListener('keyup', async (event) => {
    if(event.key === "ArrowUp" ||
       event.key === "ArrowDown" ||
       event.key === "ArrowLeft" ||
       event.key === "ArrowRight") {
        await checkSelection().then(async () => {
            await updateEngineState();
        });
    }

    if(event.ctrlKey && event.key === "a") {
        await checkSelection().then(async () => {
            await updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "c") {
        await copy().then(async () => {
            await updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "x") {
        await cut().then(async () => {
            await updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "v") {
        await paste().then(async () => {
            await updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "y") {
        await redo().then(async () => {
            await updateEngineState();
        });
    }
    if(event.ctrlKey && event.key === "z") {
        await undo().then(async () => {
            await updateEngineState();
        });
    }

    if(event.key === "Backspace") {
        await deleteText().then(async () => {
            await updateEngineState();
        });
    }
});

editor.addEventListener('contextmenu', function(event) {
    event.preventDefault();
    alert("The context menu is currently not supported!");
});

editor.addEventListener('click', async (event) => {
    await checkSelection().then(async () => {
        await updateEngineState();
    });
});

recordButton.addEventListener('click', () => {
    record();
});
replayButton.addEventListener('click', async () => {
    await replay();
});