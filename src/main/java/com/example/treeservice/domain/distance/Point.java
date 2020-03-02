package com.example.treeservice.domain.distance;

public interface Point {

  double getX();

  double getY();

  default double getXAsRad() {
    return Math.toRadians(getX());
  }

  default double getYAsRad() {
    return Math.toRadians(getY());
  }
}
