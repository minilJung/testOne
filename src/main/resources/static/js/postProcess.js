const CSS = require('css');

const removeVariables = require('./removeVariables');
// const filterDuplicate = require('./filterDuplicate');

module.exports = async function (string) {
  const styles = CSS.parse(string);
  return CSS.stringify(
    await removeVariables(
      // await filterDuplicate(
        styles,
      // ),
    ),
    {
      compress: true,
    },
  );
};
