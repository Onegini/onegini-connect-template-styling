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

const compileJs = () =>  gulp.src(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/js/**/*.js`)
	.pipe(babel({presets: ['@babel/preset-env']}))
	.pipe(uglify())
	.pipe(gulp.dest(`${process.env.STYLING_EXTENSIONRESOURCESLOCATION}/static/js`));

const watchSass = () =>  watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/scss/**/*.scss`, gulp.series([compileSass]));	
const watchJs = () =>  watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/static/js/**/*.js`, gulp.series([compileJs]));
const watchHtml = () => watch(`${process.env.STYLING_EXTENSIONUNCOMPILEDLOCATION}/templates`, gulp.series([copyHtml, bs.reload]));

const serve = () => bs.init({
	// proxy: `http://127.0.0.1:${config.proxyPort}/`,
	// port: config.port,
	// files: `${STYLING_EXTENSIONRESOURCESLOCATION}.`,
});

const build = gulp.parallel(compileSass, compileJs, copyHtml);
const watch = gulp.parallel(watchSass, watchJs, watchHtml, serve);
exports.build = build;
exports.watch = watch;
exports.default = gulp.series(build, watch);
