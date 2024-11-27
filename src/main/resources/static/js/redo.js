async function fetchRedo(mementoIndex) {
    const url = "http://localhost:8080/api/engine/redo";

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: mementoIndex,
        });

        if (!response.ok) {
            throw new Error(`Error: ${response.status} - ${response}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        let message = "Error redoing";
        appendToLog(message);
        console.error(message, error);
    }
}

async function redo() {
    const data = await fetchRedo(engine.mementoIndex)
    if(data === null) {
        appendToLog("Nothing to redo!");
    } else {
        appendToLog("Redo successful", data)
    }
}