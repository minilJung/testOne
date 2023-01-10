function processPureRule(rule) {
  const declarations = rule.declarations.filter((declaration) => declaration.type === 'declaration');
  const values = new Map();
  declarations.filter((declaration) => declaration.property.startsWith('--')).forEach((declaration) => {
    values.set(declaration.property, declaration.value);
  });
  return {
    ...rule,
    declarations: declarations.filter((declaration) => !declaration.property.startsWith('--')).map((declaration) => {
      return {
        ...declaration,
        value: declaration.value.replace(/var\s*\(\s*(--[\w\-]*)\)/, (match, name) => {
          if (!values.has(name)) {
            console.log('Error: couldn\'t find variable ' + name);
          }
          return values.get(name);
        }),
      };
    }),
  };
}

function processRule(rule) {
  switch (rule.type) {
    case 'rule':
      return processPureRule(rule);
    case 'comment':
      return null;
    case 'media':
    case 'supports':
      return {
        ...rule,
        rules: rule.rules.map(processRules),
      };
    default:
      // TODO: process other cases too
      return rule;
  }
}

function processRules(rules) {
  return rules.map(processRule).filter((l) => !!l);
}

module.exports = function (css) {
  return {
    ...css,
    stylesheet: {
      ...css.stylesheet,
      rules: processRules(css.stylesheet.rules),
    },
  };
};
