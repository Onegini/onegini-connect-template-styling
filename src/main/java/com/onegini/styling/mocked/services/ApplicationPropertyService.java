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

package com.onegini.styling.mocked.services;

import org.springframework.stereotype.Service;

import com.onegini.web.access.Features;
import lombok.Data;

@Service
@Data
public class ApplicationPropertyService {

  private boolean customEmailValidationEnabled;

  public boolean isFeatureEnabled(final Features features) {
    return true;
  }
}
