const PostCSS = require('postcss');
const TailwindCSS = require('tailwindcss');
const PostCSSDiscardComments = require('postcss-discard-comments');
const CSSNano = require('cssnano');
const PostCSSPresetEnv = require('postcss-preset-env');
const AutoPrefixer = require('autoprefixer');
const PostCSSCSSVariables = require('postcss-css-variables');

const {writeFile} = require('./util.js');
const baseConfig = require('./baseConfig.js');

(async () => {
  await writeFile('./assets/tailwind-reset.css', (await PostCSS([
    TailwindCSS({
      corePlugins: ['preflight']
    }),
    PostCSSDiscardComments({ removeAll: true }),
    CSSNano({ preset: 'advanced' }),
    PostCSSPresetEnv({
      browsers: ['ie 10', '> 2%', 'last 2 versions'],
      features: { 'nesting-rules': false },
    }),
    AutoPrefixer,
    PostCSSCSSVariables,
  ]).process('@tailwind base;\n')).css);
  const cssContent = await PostCSS([
    TailwindCSS({
      ...baseConfig,
      prefix: 'xe-',
    }),
    CSSNano({
      preset: 'advanced',
    }),
    PostCSSPresetEnv({
      browsers: ['ie 10', '> 2%', 'last 2 versions'],
      features: { 'nesting-rules': false },
    }),
    AutoPrefixer,
    PostCSSCSSVariables,
  ]).process('@tailwind utilities;\n');
  await writeFile('./assets/xetailwind.css', cssContent.css);
})();
