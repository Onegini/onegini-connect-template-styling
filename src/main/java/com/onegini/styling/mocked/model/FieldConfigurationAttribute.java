package com.onegini.styling.mocked.model;

import lombok.Data;

@Data
public class FieldConfigurationAttribute {

  private String label;

  public boolean isEditable() {
    return true;
  }
}
