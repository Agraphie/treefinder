package com.example.treeservice.domain.distance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EarthDistanceCalculatorTest {

  @Test public void testBerlinTokyo() {
    SimplePoint berlin = new SimplePoint(52.516666666666667, 13.400);
    SimplePoint tokyo = new SimplePoint(35.700, 139.766666666666667);

    double distance = new EarthDistanceCalculator().calculateDistance(berlin, tokyo);
    // Allow 50 meters difference as the formula is only accurate to 50 meters
    assertEquals(distance, 8941.2025, 0.05);
  }

  /**
   * Tree1 id: 200540 Tree2 id: 200628, location and distance manually verified.
   */
  @Test public void testTree1ToTree2() {
    SimplePoint tree1 = new SimplePoint(40.79411067, -73.81867946);
    SimplePoint tree2 = new SimplePoint(40.793138, -73.81946649);

    double distance = new EarthDistanceCalculator().calculateDistance(tree1, tree2);
    // Allow 50 meters difference as the formula is only accurate to 50 meters
    assertEquals(distance, 0.1268, 0.05);
  }
}
