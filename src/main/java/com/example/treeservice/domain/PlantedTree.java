package com.example.treeservice.domain;

import com.example.treeservice.domain.distance.Point;
import lombok.Data;

@Data
public class PlantedTree implements Point {

  private final double latitude;
  private final double longitude;
  private final String commonName;
  /**
   * x Coordinate in an arbitrary plane, for definitive comparability use latitude and longitude.
   */
  private double xCoordinate;
  /**
   * y Coordinate in an arbitrary plane, for definitive comparability use latitude and longitude.
   */
  private double yCoordinate;

  public PlantedTree(double latitude, double longitude, double xCoordinate, double yCoordinate,
      String commonName) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.commonName = commonName;
    this.yCoordinate = yCoordinate;
    this.xCoordinate = xCoordinate;
  }

  @Override public double getX() {
    return latitude;
  }

  @Override public double getY() {
    return longitude;
  }
}
