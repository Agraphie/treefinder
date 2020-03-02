package com.example.treeservice.infrastructure.repository;

import com.example.treeservice.domain.PlantedTree;
import com.example.treeservice.domain.distance.BoundingBox;
import com.example.treeservice.domain.distance.EarthDistanceCalculator;
import com.example.treeservice.domain.distance.PlaneDistanceCalculator;
import com.example.treeservice.domain.distance.SimplePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Repository
public class NewYorkTreeRepository {

  // 1.024 Megabytes
  private final static int ALLOWED_MAX_BYTE_COUNT = 1024 * 1000;
  private final static String NYC_TREE_CENSUS_2015_API = "https://data.cityofnewyork.us/resource/uvpi-gqnh.json";
  private final WebClient newYorkTreeApiClient;
  private final PlaneDistanceCalculator planeDistanceCalculator;
  private final EarthDistanceCalculator earthDistanceCalculator;

  @Autowired
  public NewYorkTreeRepository(PlaneDistanceCalculator planeDistanceCalculator,
      EarthDistanceCalculator earthDistanceCalculator) {
    this.planeDistanceCalculator = planeDistanceCalculator;
    this.earthDistanceCalculator = earthDistanceCalculator;
    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(ALLOWED_MAX_BYTE_COUNT))
        .build();

    newYorkTreeApiClient = WebClient.builder()
        .exchangeStrategies(exchangeStrategies)
        .baseUrl(NYC_TREE_CENSUS_2015_API)
        .build();
  }

  private Flux<NewYorkTreeDTO> findAll() {
    return newYorkTreeApiClient.get()
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(r -> r.bodyToMono(NewYorkTreeDTO[].class))
        .flatMapMany(Flux::fromArray);
  }

  public Flux<PlantedTree> findByYXInRadius(double centerX, double centerY, double radiusInMeters) {
    final SimplePoint circleCenter = new SimplePoint(centerX, centerY);
    final double radiusInKm = radiusInMeters / 1000;

    return this.findAll()
        .filter(p -> planeDistanceCalculator.calculateDistance(circleCenter, p) <= radiusInMeters)
        .map(NewYorkTreeDTO::transformToDomain);
  }

  public Flux<PlantedTree> findByLatLonInRadius(double latitude, double longitude,
      double radiusInMeters) {
    final SimplePoint circleCenter = new SimplePoint(latitude, longitude);
    final double radiusInKm = radiusInMeters / 1000;
    BoundingBox boundingBox = earthDistanceCalculator
        .createBoundingBox(latitude, longitude, radiusInKm);

    return this.findAll()
        .map(NewYorkTreeDTO::transformToDomain)
        .filter(boundingBox::contains)
        .filter(p -> earthDistanceCalculator.calculateDistance(circleCenter, p) <= radiusInKm);
  }
}
