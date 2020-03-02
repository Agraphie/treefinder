package com.example.treeservice.infrastructure.controller;

import com.example.treeservice.service.TreeService;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TreeController {

  private final TreeService treeService;

  @Autowired
  public TreeController(TreeService treeService) {
    this.treeService = treeService;
  }

  @GetMapping("/group/sphere")
  public Mono<GroupedTreesResponseDTO> groupTreesInRadiusByLatLon(
      @RequestParam("lat") double lat,
      @RequestParam("lon") double lon,
      @RequestParam("radius") double radius) {
    return treeService.findTreesInRadiusByLatLon(lat, lon, radius)
        .map(p -> p.getCommonName() == null ? "" : p.getCommonName())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .map(GroupedTreesResponseDTO::new);
  }

  @GetMapping("/group/plane")
  public Mono<GroupedTreesResponseDTO> groupTreesInRadiusByStatePlane(
      @RequestParam("centerX") double centerX,
      @RequestParam("centerY") double centerY,
      @RequestParam("radius") double radius) {

    return treeService.findTreesInRadiusByXY(centerX, centerY, radius)
        .map(p -> p.getCommonName() == null ? "" : p.getCommonName())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .map(GroupedTreesResponseDTO::new);
  }

  // Introduce a local response wrapper to not give out simple data structures in case of response changes in the future
  @AllArgsConstructor
  @Value
  static class GroupedTreesResponseDTO {

    final Map<String, Long> groupedTrees;
  }
}
