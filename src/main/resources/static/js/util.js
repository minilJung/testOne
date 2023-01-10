const { promisify } = require('static/js/util');
const fs = require('fs');

const writeFile = promisify(fs.writeFile);
const readFileOriginal = promisify(fs.readFile);
const mkdirOriginal = promisify(fs.mkdir);

async function readFile(filePath) {
  try {
    return (await readFileOriginal(filePath)).toString();
  } catch (err) {
    if (err.code === 'ENOENT') {
      return undefined;
    } else {
      throw err;
    }
  }
}

function mkdir(filePath) {
  return mkdirOriginal(filePath, { recursive: true });
}

module.exports = {
  writeFile,
  readFile,
  mkdir,
};
