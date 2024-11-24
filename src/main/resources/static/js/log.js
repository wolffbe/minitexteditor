function appendToLog(message, obj) {
    let logMessage = "";

    if (arguments.length === 1) {
        logMessage = message + "\n\n";
    } else if (arguments.length === 2) {
        logMessage = message + ":\n" + JSON.stringify(obj) + "\n\n";
    }
    log.value += logMessage;
    log.scrollTop = log.scrollHeight;
}