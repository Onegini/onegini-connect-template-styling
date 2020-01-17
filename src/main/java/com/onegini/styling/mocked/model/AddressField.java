package com.onegini.styling.mocked.model;

import lombok.Data;

@Data
public class AddressField {
  private String companyName;
  private String attention;
  private String streetName;
  private int houseNumber;
  private String houseNumberAddition;
  private String postalCode;
  private String city;
  private String region;
  private String country;
}
