const {resolve} = require('path');

const glob = require('glob');

const builder = require('./builder.js');

glob(builder.BUILD_SOURCE, (err, files) => {
  if (err) {
    console.error('Error: ' + err);
  } else {
    files.forEach((filePath) => {
      builder(resolve(filePath)).catch((e) => {
        console.log('Error building ' + filePath + ': ' + e);
      });
    });
  }
});
