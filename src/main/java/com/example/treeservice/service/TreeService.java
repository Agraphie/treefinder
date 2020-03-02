package com.example.treeservice.service;

import com.example.treeservice.domain.PlantedTree;
import com.example.treeservice.infrastructure.repository.NewYorkTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TreeService {

  private final NewYorkTreeRepository newYorkTreeRepository;

  @Autowired public TreeService(NewYorkTreeRepository newYorkTreeRepository) {
    this.newYorkTreeRepository = newYorkTreeRepository;
  }

  public Flux<PlantedTree> findTreesInRadiusByLatLon(double latitude, double longitude,
      double radiusInMeters) {
    return newYorkTreeRepository.findByLatLonInRadius(latitude, longitude, radiusInMeters);
  }

  public Flux<PlantedTree> findTreesInRadiusByXY(double x, double y, double radiusInMeters) {
    return newYorkTreeRepository.findByYXInRadius(x, y, radiusInMeters);
  }
}
