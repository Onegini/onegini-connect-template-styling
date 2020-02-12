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

package com.onegini.styling.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ThymeleafConfig {

  private static final String PATH_WEB_TEMPLATES = "/templates/"; // NOSONAR
  private static final String PATH_EMAIL_TEMPLATES = "/email-templates/"; // NOSONAR
  private static final String PATTERN_WEB_TEMPLATES = "personal/*";
  private static final String PATTERN_EMAIL_TEMPLATES = "*";

  @Resource
  private StylingProperties stylingProperties;

  @Bean
  public FileTemplateResolver fileTemplateResolver() {
    return createTemplateResolver(0, stylingProperties.getExtensionResourcesLocation() + PATH_WEB_TEMPLATES, PATTERN_WEB_TEMPLATES);
  }

  @Bean
  public FileTemplateResolver coreFileTemplateResolver() {
    return createTemplateResolver(1, stylingProperties.getCoreResourcesLocation() + PATH_WEB_TEMPLATES, PATTERN_WEB_TEMPLATES);
  }

  @Bean
  public FileTemplateResolver tokenServerCustomFileTemplateResolver() {
    return createTemplateResolver(2, stylingProperties.getTokenServerCustomResourcesLocation() + PATH_WEB_TEMPLATES, "*");
  }

  @Bean
  public FileTemplateResolver tokenServerDefaultFileTemplateResolver() {
    return createTemplateResolver(3, stylingProperties.getTokenServerDefaultResourcesLocation() + PATH_WEB_TEMPLATES, "*");
  }

  @Bean
  public FileTemplateResolver extensionEmailFileTemplateResolver() {
    return createTemplateResolver(0, stylingProperties.getExtensionResourcesLocation() + PATH_EMAIL_TEMPLATES, PATTERN_EMAIL_TEMPLATES);
  }

  @Bean
  public FileTemplateResolver coreEmailFileTemplateResolver() {
    return createTemplateResolver(1, stylingProperties.getCoreResourcesLocation() + PATH_EMAIL_TEMPLATES, PATTERN_EMAIL_TEMPLATES);
  }

  private FileTemplateResolver createTemplateResolver(final int order, final String prefix, final String resolvablePattern) {
    final FileTemplateResolver templateResolver = new FileTemplateResolver();

    templateResolver.setPrefix(prefix);
    templateResolver.setCacheable(false);
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    final Set<String> resolvablePatterns = new HashSet<>();
    resolvablePatterns.add(resolvablePattern);
    templateResolver.setCheckExistence(true);
    templateResolver.setResolvablePatterns(resolvablePatterns);
    templateResolver.setOrder(order);

    log.info("Creating TemplateResolver. Path prefix: '{}', resolves pattern: '{}'", prefix, resolvablePattern);
    return templateResolver;
  }

}
