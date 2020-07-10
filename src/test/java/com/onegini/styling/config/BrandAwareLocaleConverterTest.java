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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.Test;

public class BrandAwareLocaleConverterTest {

  private BrandAwareLocaleConverter converter = new BrandAwareLocaleConverter();

  @Test
  public void shouldReturnLocaleWithLanguageWhenProvidingStringWithOnlyLanguage() {
    final Locale locale = converter.convert("nl");

    assertThat(locale.toLanguageTag()).isEqualTo("nl");
  }

  @Test
  public void shouldReturnLocaleWithLanguageAndCountryWhenProvidingStringWithLanguageAndCountry() {
    final Locale locale = converter.convert("nl_NL");

    assertThat(locale.toLanguageTag()).isEqualTo("nl-NL");
  }

  @Test
  public void shouldReturnLocaleWithLanguageAndCountryAndVariantCodeWhenProvidingStringWithLanguageAndCountryAndVariantCode() {
    final Locale locale = converter.convert("nl_NL_variant");

    assertThat(locale.toLanguageTag()).isEqualTo("nl-NL-variant");
  }

  @Test
  public void shouldReturnLocaleWithLanguageAndVariantCodeWhenProvidingStringWithLanguageAndVariantCode() {
    final Locale locale = converter.convert("nl__variant");

    assertThat(locale.toLanguageTag()).isEqualTo("nl-variant");
  }
}