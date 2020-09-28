function appendToLog(message, obj) {
    const logMessage = message + ":\n" + JSON.stringify(obj) + "\n\n";
    log.value += logMessage;
    log.scrollTop = log.scrollHeight;
}