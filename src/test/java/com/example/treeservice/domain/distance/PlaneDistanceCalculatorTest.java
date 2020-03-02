package com.example.treeservice.domain.distance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PlaneDistanceCalculatorTest {

  /**
   * Tree1 id: 200540 Tree2 id: 200628, location and distance manually verified, using x_sp and y_sp
   * which need to be converted to meters first.
   */
  @Test public void testTree1ToTree2() {
    SimplePoint tree1 = new SimplePoint(1034455.701 * 0.3048, 228644.8374 * 0.3048);
    SimplePoint tree2 = new SimplePoint(1034238.511 * 0.3048, 228290.0133 * 0.3048);

    double distance = new PlaneDistanceCalculator().calculateDistance(tree1, tree2);
    assertEquals(126.8, distance, 0.01);
  }
}
