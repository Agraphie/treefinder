package com.example.treeservice.domain.distance;

import org.springframework.stereotype.Component;

@Component
public class PlaneDistanceCalculator implements DistanceCalculator {


  /**
   * Calculate the distance based on the x and y unit. E.g. in meters or kilometers.
   *
   * @param p1 Point to calculate from.
   * @param p2 Point to calculate to.
   * @return The distance in the x and y unit.
   */
  @Override public double calculateDistance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math
        .pow(p2.getY() - p1.getY(), 2));
  }

  @Override public BoundingBox createBoundingBox(double latitude, double longitude, double radius) {
    return BoundingBox.builder()
        .latitudeMinAsRad(Math.toRadians(latitude - radius))
        .longitudeMaxAsRad(Math.toRadians(latitude + radius))
        .longitudeMinAsRad(Math.toRadians(longitude - radius))
        .longitudeMaxAsRad(Math.toRadians(longitude + radius))
        .build();
  }
}
