package com.example.treeservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@ExtendWith(SpringExtension.class)
@AutoConfigureWebFlux
public class TreeControllerTest {


  WebTestClient webTestClient;

  public TreeControllerTest() {
    webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
  }

  @Test public void testTreeGroupingByLatLon() {
    String expectedOutput = "\n"
        + "{\n"
        + "\n"
        + "    \"groupedTrees\": {\n"
        + "        \"\": 1,\n"
        + "        \"Sophora\": 6,\n"
        + "        \"Japanese zelkova\": 1,\n"
        + "        \"Chinese fringetree\": 1,\n"
        + "        \"eastern redcedar\": 1,\n"
        + "        \"sweetgum\": 2,\n"
        + "        \"silver maple\": 4,\n"
        + "        \"Norway maple\": 58,\n"
        + "        \"white oak\": 2,\n"
        + "        \"pin oak\": 11,\n"
        + "        \"crepe myrtle\": 1,\n"
        + "        \"silver linden\": 1,\n"
        + "        \"red maple\": 1,\n"
        + "        \"willow oak\": 1,\n"
        + "        \"American elm\": 1,\n"
        + "        \"honeylocust\": 11,\n"
        + "        \"Callery pear\": 5,\n"
        + "        \"ginkgo\": 7,\n"
        + "        \"tulip-poplar\": 1,\n"
        + "        \"swamp white oak\": 1,\n"
        + "        \"London planetree\": 18,\n"
        + "        \"black cherry\": 1,\n"
        + "        \"American linden\": 1,\n"
        + "        \"Amur maple\": 3\n"
        + "    }\n"
        + "\n"
        + "}\n";

    webTestClient.get()
        .uri("/group/sphere?lat=40.79411067&lon=-73.81867946&radius=8000")
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(expectedOutput);
  }

  @Test public void testTreeGroupingByXY() {

    String expectedOutput = "{\n"
        + "\n"
        + "    \"groupedTrees\": {\n"
        + "        \"eastern redcedar\": 1,\n"
        + "        \"honeylocust\": 1,\n"
        + "        \"Norway maple\": 2,\n"
        + "        \"pin oak\": 1,\n"
        + "        \"London planetree\": 1\n"
        + "    }\n"
        + "\n"
        + "}";

    webTestClient.get()
        .uri("/group/plane?centerX=1034455.701&centerY=228644.8374&radius=8000")
        .exchange()
        .expectStatus().isOk()
        .expectBody().json(expectedOutput);
  }
}
