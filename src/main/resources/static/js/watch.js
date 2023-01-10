const {resolve} = require('path');

const {watch} = require('chokidar');

const builder = require('./builder.js');

const watcher = watch(builder.BUILD_SOURCE);

watcher.on('change', (filePath) => {
  builder(resolve(filePath)).catch((e) => {
    console.log('Error building ' + filePath + ': ' + e);
  });
});

watcher.on('ready', () =>
  console.log('Initial scan complete. Ready for changes')
);
