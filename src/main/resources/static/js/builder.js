const { dirname, join, relative } = require('path');

const AutoPrefixer = require('autoprefixer');
const JSON = require('comment-json');
const CSSNano = require('cssnano');
const findUpGlob = require('find-up-glob');
const PostCSS = require('postcss');
const PostCSSDiscardComments = require('postcss-discard-comments');
const PostCSSImport = require('postcss-import');
const PostCSSPresetEnv = require('postcss-preset-env');
const TailwindCSS = require('tailwindcss');
const PostCSSNesting = require('tailwindcss/nesting/index.js');

const {mkdir, readFile, writeFile} = require('./util.js');
const postProcess = require('./postProcess.js');

const BUILD_SOURCE = '**/*.{css,html,blade.php}';

// 임시: 항상 프로덕션 모드로 빌드
process.env.NODE_ENV = 'production';

const PROJECT_IDENTIFIER = 'tailwind.xe.json';

const pluginsBefore = [PostCSSImport, PostCSSNesting];

const pluginsAfter = [
  PostCSSDiscardComments({ removeAll: true }),
  CSSNano({
    preset: 'advanced',
  }),
  PostCSSPresetEnv({
    browsers: ['ie 10', '> 2%', 'last 2 versions'],
    features: { 'nesting-rules': false },
  }),
  AutoPrefixer,
];

function plugin(projectPath, tailwindConfig) {
  return [
    ...pluginsBefore,
    TailwindCSS({
      prefix: 'xe-',
      ...tailwindConfig,
      mode: 'jit',
      purge: [projectPath + '/**/*.{pug,html,blade.php,jsx,tsx,md,mdx}'],
    }),
    ...(process.env.NODE_ENV === 'production' ? pluginsAfter : []),
  ];
}

async function getProject(filePath) {
  const configPath = await findUpGlob(PROJECT_IDENTIFIER, { cwd: dirname(filePath) });
  if (!configPath || relative(process.cwd(), configPath[0]).startsWith('../')) {
    return undefined;
  }
  return dirname(configPath[0]);
}

function dedupAsync(func) {
  const running = new Map();
  const next = new Map();
  return async (arg) => {
    if (running.get(arg)) {
      next.set(arg, true);
      return await running.get(arg);
    } else {
      let result;
      let err;
      do {
        next.delete(arg);
        result = undefined;
        err = undefined;
        const promise = func(arg);
        running.set(arg, promise);
        try {
          result = await promise;
        } catch (e) {
          err = e;
        }
        running.delete(arg);
      } while (next.get(arg));
      if (err) {
        throw err;
      } else {
        return result;
      }
    }
  };
}

const buildProject = dedupAsync(async (project) => {
  const projectConfig = await readFile(join(project, PROJECT_IDENTIFIER));
  const postcssContent = await readFile(join(project, 'tailwind.xe.css'));
  const cssContent = await PostCSS(plugin(project, JSON.parse(projectConfig))).process(
    postcssContent || '@tailwind utilities;\n',
    {
      from: join(project, postcssContent ? 'tailwind.xe.css' : PROJECT_IDENTIFIER),
      to: join(project, 'assets/css/backup-dist.css'),
    }
  );
  await mkdir(join(project, 'assets/css'));
  await writeFile(join(project, 'assets/css/backup-dist.css'), await postProcess(cssContent.css));
});

module.exports = dedupAsync(async (filePath) => {
  const project = await getProject(filePath);
  if (project === undefined) {
    return;
  }
  if (filePath == join(project, 'assets/css/backup-dist.css')) {
    return;
  }
  console.log('processing ' + filePath);
  await buildProject(project);
});

module.exports.BUILD_SOURCE = BUILD_SOURCE;
