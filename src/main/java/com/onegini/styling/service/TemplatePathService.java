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

package com.onegini.styling.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.onegini.styling.config.StylingProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
class TemplatePathService {
  @Resource
  private StylingProperties stylingProperties;

  List<String> getTemplatePaths() {
    log.debug("Getting all configured templates");
    final String templateConfigLocation = getTemplatePathsLocation();
    final List<String> templatePaths = new ArrayList<>();

    try (final Stream<Path> stream = Files.walk(Paths.get(templateConfigLocation).toAbsolutePath(), 3)) {
      templatePaths.addAll(stream
          .filter(file -> !file.toFile().isDirectory())
          .map(Path::getFileName)
          .map(Path::toString)
          .filter(fileName -> fileName.endsWith(".yaml"))
          .map(fileName -> StringUtils.substringBeforeLast(fileName, ".yaml"))
          .map(fileName -> fileName.replace("__", "/"))
          .collect(Collectors.toSet()));
    } catch (final IOException e) {
      log.error(String.format("Cannot read files from %s", templateConfigLocation), e);
    }

    templatePaths.sort(Comparator.naturalOrder());
    return templatePaths;
  }

  String getTemplatePathsLocation() {
    return stylingProperties.getTemplateConfigLocation();
  }
}
