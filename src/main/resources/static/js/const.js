const editor = document.getElementById('editor');

const engine = {
    buffer: "",
    clipboard: "",
    bufferBeginIndex: 0,
    beginIndex: 0,
    endIndex: 0,
    bufferEndIndex: 0,
}

const log = document.getElementById('log');