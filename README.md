# **Reservoir Info API**
A Spring Boot REST API that collects water volume data from reservoirs in Bulgaria, processes the data, and sends it to [CarpMap](https://carpmap.online).

CarpMap Repository -> [CarpMap](https://github.com/niki-evgeniev/CarpMap)

## **Technologies & Dependencies**
The project is built with:
- **Java 21**
- **Spring Boot 3**
- **Gradle**
- **Spring Web (REST API)**
- **Spring Data JPA (MySQL database)**
- **Spring Boot Validation**
- **Apache PDFBox** (PDF processing)
- **ModelMapper** (DTO conversion)
- **Spring WebClient** (for communication with CarpMap)

## **How to Run the Project?**
If using IntelliJ IDEA, simply run the main class (usually `Application.java`).

To run from the terminal using Gradle:
```sh
./gradlew bootRun  # For Linux/Mac
gradlew bootRun    # For Windows
```

To build and run the JAR file:
```sh
./gradlew build
java -jar build/libs/your-jar-file-name.jar
```

## **API Endpoints**
| Method | URL        | Description |
|--------|-----------|-------------|
| GET    | `/api/info` | Returns water volume data for reservoirs. |

## **Configuration**
- The project uses **MySQL database**.
- Configuration settings are defined in **`application.yml`** or **`application.properties`**.

## **Communication with CarpMap**
The API uses **Spring WebClient** to send processed data to **CarpMap**.

