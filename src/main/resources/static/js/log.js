/**
 * Appends a message to the log display in the editor.
 * If an optional object is provided, it will be serialized and included in the log entry.
 *
 * Author: Benedict Wolff
 * @version 1.0
 *
 * @param {string} message - The main message to append to the log.
 * @param {Object} [obj] - An optional object to include in the log entry, serialized as JSON.
 */
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