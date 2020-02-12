/*
 * Copyright 2019 Onegini B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

const gulp = require('gulp');
const watch = require('gulp-watch');
const bs = require('browser-sync').create();
const sass = require('gulp-sass');
const babel = require('gulp-babel');
const uglify = require('gulp-uglify');
const cleanCSS = require('gulp-clean-css');

const compileSass = () => gulp.src(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/scss/**/*.scss`)
    .pipe(sass().on('error', sass.logError))
    .pipe(cleanCSS())
    .pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/static/css`));

const copyHtml = () => gulp.src(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/templates/**/*.html`)
    .pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/templates`));

const compileJs = () => 




gulp.src(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/js/**/*.js`)
    .pipe(babel({presets: ['@babel/preset-env']}))
    .pipe(uglify())
    .pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/static/js`));

const copyMessages = () => gulp.src(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/messages/*.properties`)
.pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/messages`));

const copyAssets = () => gulp.src([`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/**/*.*`, `!${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/JS/**/*.js`])
.pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/static`));

const watchSass = () =>  watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/scss/**/*.scss`, gulp.series(compileSass, bs.reload));	
const watchJs = () =>  watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/js/**/*.js`, gulp.series(compileJs, bs.reload));
const watchHtml = () => watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/templates/**/*.html`, gulp.series(copyHtml, bs.reload));
const watchAssets = () => watch([`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/**/*.*`, `!${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/JS/**/*.js`], gulp.series([copyAssets, bs.reload]));
const watchMessages = () => watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/messages/*.properties`, gulp.series([copyMessages, bs.reload]));

const serve = () => bs.init({
    proxy: `http://localhost:${process.env.SERVER_PORT}/`
    // port: config.port,
    // files: `${STYLING_EXTENSIONRESOURCESLOCATION}.`,
});

const build = gulp.parallel(compileSass, compileJs, copyHtml, copyAssets, copyMessages);
const watcher = gulp.parallel(serve, watchSass, watchJs, watchHtml, watchAssets, watchMessages);
exports.build = build;
exports.watcher = watcher;
exports.default = gulp.series(build, watch);
