const editor = document.getElementById('editor');

const engine = {
    mementoIndex: 0,
    buffer: "",
    clipboard: "",
    bufferBeginIndex: 0,
    beginIndex: 0,
    endIndex: 0,
    bufferEndIndex: 0,
    mementoListLength: 0,
}

const log = document.getElementById('log');

const recordButton = document.getElementById('recordButton');
const replayButton = document.getElementById('replayButton');