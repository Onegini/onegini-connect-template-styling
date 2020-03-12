/*
 * Copyright 2020 Onegini B.V.
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
const sourceLocation = process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION;
const targetLocation = process.env.STYLING_EXTENSIONRESOURCESLOCATION;

const compileScss = () =>
    gulp.src(`${sourceLocation}/scss/**/*.scss`)
    .pipe(sass().on('error', sass.logError))
    .pipe(cleanCSS())
    .pipe(gulp.dest(`${targetLocation}/static/css`));

const copyHtml = () =>
    gulp.src(`${sourceLocation}/templates/**/*.html`)
    .pipe(gulp.dest(`${targetLocation}/templates`));

const copyEmails = () =>
    gulp.src(`${sourceLocation}/email-templates/**/*.html`)
    .pipe(gulp.dest(`${targetLocation}/email-templates`));

const compileJs = () =>
    gulp.src(`${sourceLocation}/js/**/*.js`)
    .pipe(babel({presets: ['@babel/preset-env']}))
    .pipe(uglify())
    .pipe(gulp.dest(`${targetLocation}/static/js`));

const copyMessages = () =>
    gulp.src(`${sourceLocation}/messages/*.properties`)
    .pipe(gulp.dest(`${targetLocation}/messages`));

const copyAssets = () =>
    gulp.src(`${sourceLocation}/static/**/*.*`)
    .pipe(gulp.dest(`${targetLocation}/static`));

const watchSass = () =>  watch(`${sourceLocation}/scss/**/*.scss`, gulp.series(compileScss, bs.reload));	
const watchJs = () =>  watch(`${sourceLocation}/js/**/*.js`, gulp.series(compileJs, bs.reload));
const watchHtml = () => watch(`${sourceLocation}/templates/**/*.html`, gulp.series(copyHtml, bs.reload));
const watchEmails = () => watch(`${sourceLocation}/email-templates/**/*.html`, gulp.series(copyEmails, bs.reload));
const watchAssets = () => watch([`${sourceLocation}/static/**/*.*`, `!${sourceLocation}/static/JS/**/*.js`], gulp.series(copyAssets, bs.reload));
const watchMessages = () => watch(`${sourceLocation}/messages/*.properties`, gulp.series(copyMessages, bs.reload));

const serve = () => bs.init({
    proxy: `http://localhost:${process.env.SERVER_PORT}/`
});

const build = gulp.parallel(compileScss, compileJs, copyHtml, copyEmails, copyAssets, copyMessages);
const watcher = gulp.parallel(serve, watchSass, watchJs, watchHtml, watchEmails, watchAssets, watchMessages);
exports.build = build;
exports.watcher = watcher;
exports.default = gulp.series(build, watch);
