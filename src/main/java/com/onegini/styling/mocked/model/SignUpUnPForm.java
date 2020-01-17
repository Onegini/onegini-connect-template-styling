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

package com.onegini.styling.mocked.model;

import lombok.Data;
import java.util.*;
import com.onegini.styling.mocked.model.AddressField;

@Data
public class SignUpUnPForm {
  private Map<String, String> customAttributes;
  private String firstName;
  private String lastName;
  private String birthDateDay;
  private String birthDateMonth;
  private String birthDateYear;
  private String gender;
  private String postalCode;
  private String houseNumber;
  private boolean billingIsShippingAddress;
  private String bankNo;
  private String email;
  private String emailConfirmation;
  private String password;
  private String confirmPassword;
  private String pin;
  private String confirmPin;
  private String mobileNumber;
  private String mobileNumberConfirmation;
  private boolean termsOfUse;
  private boolean newsletter;
  private AddressField address;
  private AddressField addressAlt1;
}
