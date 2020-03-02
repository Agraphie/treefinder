package com.example.treeservice.domain.distance;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BoundingBox {

  private final double latitudeMinAsRad;
  private final double latitudeMaxAsRad;
  private final double longitudeMinAsRad;
  private final double longitudeMaxAsRad;

  public boolean contains(Point point) {
    return point.getXAsRad() >= this.latitudeMinAsRad
        && point.getXAsRad() <= this.latitudeMaxAsRad
        && point.getYAsRad() >= this.longitudeMinAsRad
        && point.getYAsRad() <= this.longitudeMaxAsRad;
  }
}
