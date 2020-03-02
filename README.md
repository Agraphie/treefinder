# treefinder
A simple application for grouping the trees from the New York City Tree Census 2015. The original API is reachable under https://data.cityofnewyork.us/Environment/2015-Street-Tree-Census-Tree-Data/uvpi-gqnh

## Run the application
To run the application execute `./gradlew spring-boot:run` with Java 11 set as JDK.

## Endpoints
The API exposes 2 endpoints
- `/group/sphere` with the parameters `lat`, `lon` and `radius`. This endpoint accepts a latitude, a longitude and a radius in meters. It will then perform contains calculations based on the Great Circle Distance
- `/group/plane` with the parameters `centerX`, `centerY` and `radius`. This endpoint accepts a x coordinate, a y coordinate and a radius. It will then perform contains calculations based on the simple plane distance
