package com.example.treeservice.infrastructure.repository;

import com.example.treeservice.domain.PlantedTree;
import com.example.treeservice.domain.distance.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class NewYorkTreeDTO implements Point {

  @JsonProperty("tree_id")
  private String treeId;
  private double latitude;
  private double longitude;
  @JsonProperty("spc_common")
  private String commonName;
  /*
  The x coordinate in the NYC state plane, originally in feet.
   */
  @JsonProperty("x_sp")
  private double xSp;
  /*
  The y coordinate in the NYC state plane, originally in feet.
 */
  @JsonProperty("y_sp")
  private double ySp;

  /**
   * Construct PlantedTree and convert feet coordinates to metres.
   *
   * @return The constructed planted tree.
   */
  public PlantedTree transformToDomain() {
    return new PlantedTree(this.latitude, this.longitude, this.xSp * 0.3048, this.ySp * 0.3048,
        this.commonName);
  }

  @Override public double getX() {
    return xSp;
  }

  @Override public double getY() {
    return ySp;
  }
}
