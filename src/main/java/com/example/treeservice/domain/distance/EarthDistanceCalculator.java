package com.example.treeservice.domain.distance;

import org.springframework.stereotype.Component;

@Component
public class EarthDistanceCalculator implements DistanceCalculator {

  private static final double EARTH_FLATTENING = 1 / 298.257_223_563;
  private static final double EARTH_EQUATOR_RADIUS_IN_KM = 6378.137;

  /**
   * Calculate a bounding box based on the idea from http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates.
   *
   * @param latitude  The latitude or x coordinate of the point.
   * @param longitude The longitude or y coordinate of the point.
   * @param radius    The radius to draw the box from.
   * @return A bounding box corresponding to the coordinates.
   */
  @Override
  public BoundingBox createBoundingBox(double latitude, double longitude,
      double radius) {
    final double latRad = Math.toRadians(latitude);
    final double lonRad = Math.toRadians(longitude);
    final double angularRadius = radius / EARTH_EQUATOR_RADIUS_IN_KM;
    final double latMin = latRad - angularRadius;
    final double latMax = latRad + angularRadius;
    final double deltaLon = Math.asin(Math.sin(angularRadius) / Math.cos(latRad));
    final double lonMin = lonRad - deltaLon;
    final double lonMax = lonRad + deltaLon;

    return BoundingBox.builder().latitudeMinAsRad(latMin).latitudeMaxAsRad(latMax)
        .longitudeMinAsRad(lonMin).longitudeMaxAsRad(lonMax).build();
  }

  /**
   * Calculating the distance between two given points using ThaddeusVincent algorithm. Is accurate
   * to 50 metres.
   *
   * @param p1 Point to calculate the distance from.
   * @param p2 Point to calculate the distance to.
   * @return The calculated distance in kilometres.
   */
  @Override
  public double calculateDistance(Point p1, Point p2) {
    // Check if they are the same points, then we can shortcut the calculations and void dividing by zeros.
    if (p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
      return 0;
    }
    final double f = (p1.getX() + p2.getX()) / 2;
    final double g = (p1.getX() - p2.getX()) / 2;
    final double l = (p1.getY() - p2.getY()) / 2;
    final double fRad = Math.toRadians(f);
    final double gRad = Math.toRadians(g);
    final double lRad = Math.toRadians(l);

    final double s =
        Math.pow(Math.sin(gRad), 2.0) * Math.pow(Math.cos(lRad), 2.0)
            + Math.pow(Math.cos(fRad), 2.0) * Math.pow(Math.sin(lRad), 2.0);
    final double gCosPow2 = Math.pow(Math.cos(gRad), 2);
    final double fSinPow2 = Math.pow(Math.sin(fRad), 2);
    final double c =
        gCosPow2 * Math.pow(Math.cos(lRad), 2) + fSinPow2 * Math.pow(Math.sin(lRad), 2);
    final double w = Math.atan(Math.sqrt(s / c));
    final double t = Math.sqrt(s * c) / w;
    final double h1 = (3 * t - 1) / (2 * c);
    final double h2 = (3 * t + 1) / (2 * s);
    final double approxDistance = 2 * w * EARTH_EQUATOR_RADIUS_IN_KM;
    return approxDistance * (
        1 + EARTH_FLATTENING * h1 * fSinPow2 * gCosPow2
            - EARTH_FLATTENING * h2 * Math.pow(Math.cos(fRad), 2) * Math.pow(Math.sin(gRad), 2));
  }
}
