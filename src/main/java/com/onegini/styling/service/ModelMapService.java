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

package com.onegini.styling.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.yaml.snakeyaml.Yaml;

import com.onegini.styling.config.StylingProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ModelMapService {

  @Resource
  private StylingProperties stylingProperties;
  @Resource
  private TemplatePathService templatePathService;

  public ModelMap loadTemplateModelMap(final String templateName) {
    final String yamlFile = determineYamlFileName(templateName);

    final ModelMap modelMap = new ModelMap();
    modelMap.addAllAttributes(loadYamlFile(yamlFile));
    log.info("Loading template '{}' with model '{}'", templateName, modelMap);
    return modelMap;
  }

  private Map<String, Object> loadYamlFile(final String fileName) {
    log.debug("Start looking up YAML file {}", fileName);

    final Yaml yaml = new Yaml();
    final Path path = Paths.get(fileName).toAbsolutePath();

    if (Files.exists(path)) {
      log.debug("A template configuration file exists {}", fileName);
      try (final InputStream inputStream = Files.newInputStream(path)) {
        return yaml.load(inputStream);
      } catch (final IOException e) {
        log.warn("Failed to load YAML file {} with error {}", fileName, e.getMessage());
      }
    }

    return new ModelMap();
  }

  private String determineYamlFileName(final String templateName) {
    return stylingProperties.getTemplateConfigLocation() + "/" +
        templateName.replace("/", "_") +
        ".yaml";
  }

  public ModelMap loadAllTemplatePaths() {
    final ModelMap modelMap = new ModelMap();
    modelMap.addAttribute("templatePaths", templatePathService.getTemplatePaths());
    modelMap.addAttribute("templatePathsLocation", templatePathService.getTemplatePathsLocation());
    return modelMap;
  }
}
