const { readFileSync } = require('fs');

const CSS = require('css');

// css rules to exclude (already exists)
const commonCSS = (() => {
  return (CSS.parse(
    readFileSync('./assets/xetailwind.css').toString()
  ).stylesheet.rules || []).map((
    /** @type {(this: string, rule: CSS.Rule | CSS.Comment | CSS.AtRule) => [string, string[]][]} */
    function toArray(rule) {
      switch (rule.type) {
        case 'stylesheet':
          return rule.rules.map(toArray, this).flat(1);
        case 'rule':
          const declarations = (rule.declarations || [])
            .filter((declaration) => declaration.type === 'declaration')
            .map(({ property, value }) => property.trim() + ': ' + value.trim());
          if (declarations.length === 0) { return []; }
          return (rule.selectors || []).map((selector) => [this + selector, declarations]);
        case 'custom-media':
          throw new Error('Custom media not supported');
        case 'import':
          throw new Error('Import not supported. bundle it;');
        case 'comment':
        case 'charset':
          return [];
        case 'supports':
          return rule.supports ? (rule.rules || []).map(toArray, this + '@supports ' + rule.supports.trim() + '\n').flat(1) : [];
        case 'media':
          return rule.media ? (rule.rules || []).map(toArray, this + '@media ' + rule.media.trim() + '\n').flat(1) : [];
        default:
          console.warn('TODO: process others...');
          return [];
      }
    }
  ), '').flat(1).reduce((acc, [selector, rules]) => {
    if (acc.has(selector)) {
      acc.set(selector, acc.get(selector).concat(rules));
    } else {
      acc.set(selector, rules);
    }
    return acc;
  }, new Map());
})();

function filterStylesheet(stylesheet) {
  return { rules: stylesheet.rules.map(filterStyleRule, this).filter((l) => l) };
}

function filterStyleRule(styleRule) {
  switch (styleRule.type) {
    case 'stylesheet':
      return { type: 'stylesheet', rules: styleRule.rules.map(filterStyleRule, this).filter((l) => l) };
    case 'rule':
      return filterRule.call(this, styleRule);
    case 'custom-media':
      throw new Error('Custom media not supported');
    case 'import':
      throw new Error('Import not supported. bundle it;');
    case 'comment':
    case 'charset':
      return undefined;
    case 'supports':
      return rule.supports ? { rules: (rule.rules || []).map(filterStyleRule, this + '@supports ' + rule.supports.trim() + '\n').filter((l) => l) } : undefined;
    case 'media':
      return rule.media ? { rules: (rule.rules || []).map(filterStyleRule, this + '@media ' + rule.media.trim() + '\n').filter((l) => l) } : undefined;
    default:
      console.warn('TODO: process others...');
      return styleRule;
  }
}

function filterRule(rule) {
  const selectors = rule.selectors.filter((selector) => {
    if (commonCSS.has(this + selector)) {
      return rule.declarations.some(
        (declaration) => !commonCSS.get(this + selector).includes(
          declaration.property.trim() + ': ' + declaration.value.trim()
        )
      );
    }
    return true;
  });
  const declarations = rule.declarations.filter((declaration) => !selectors.every((selector) => commonCSS.has(this + selector) && commonCSS.get(this + selector).includes(declaration.property.trim() + ': ' + declaration.value.trim())));
  return selectors.length && declarations.length ? { type: 'rule', selectors, declarations } : undefined;
}

function filterDuplicate(css) {
  return { type: 'stylesheet', stylesheet: filterStylesheet.call('', css.stylesheet) };
}

module.exports = filterDuplicate;
