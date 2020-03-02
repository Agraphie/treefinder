package com.example.treeservice.domain.distance;

public interface DistanceCalculator {

  /**
   * Returns the calculated distance in kilometres.
   *
   * @param p1 Point to calculate from.
   * @param p2 Point to calculate to.
   * @return The distance in kilometres.
   */
  double calculateDistance(Point p1, Point p2);

  /**
   * Creates a bounding box around a given point.
   *
   * @param latitude  The latitude or x coordinate of the point.
   * @param longitude The longitude or y coordinate of the point.
   * @param radius    The radius to draw the box from.
   * @return The created bounding box.
   */
  BoundingBox createBoundingBox(double latitude, double longitude, double radius);
}
