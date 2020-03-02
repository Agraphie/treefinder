package com.example.treeservice.infrastructure.controller;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TreeDTO {

  private final String commonName;
  private final double latitude;
  private final double longitude;
}
