package com.example.treeservice.domain.distance;

import lombok.Data;

@Data
public class SimplePoint implements Point {

  private final double x;
  private final double y;

  public SimplePoint(double x, double y) {
    this.x = x;
    this.y = y;
  }
}
