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

package com.onegini.styling.config;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  private static final String FILE = "file:";
  @Resource
  private StylingProperties stylingProperties;


  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/static/**")
        .addResourceLocations(FILE + stylingProperties.getExtensionResourcesLocation() + "/static/",
            FILE + stylingProperties.getCoreResourcesLocation() + "/static/")
        .resourceChain(false);
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean
  public MessageSource messageSource() {
    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        FILE + stylingProperties.getExtensionResourcesLocation() + "/messages/messages-personal-branding",
        FILE + stylingProperties.getCoreResourcesLocation() + "/messages/messages-personal",
        FILE + stylingProperties.getTokenServerCustomResourcesLocation() + "/messages/messages",
        FILE + stylingProperties.getTokenServerDefaultResourcesLocation() + "/messages/messages");
    messageSource.setCacheSeconds(1);

    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    final SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(stylingProperties.getDefaultLocale());
    return slr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    final LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("language");
    return lci;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

}
