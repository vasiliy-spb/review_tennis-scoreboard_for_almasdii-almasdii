# 🎾 Tennis Scoreboard

A web application for tracking tennis match scores in real time.

## 📋 Overview

Tennis Scoreboard allows users to create matches, track scores point by point,
and view the history of finished matches with filtering and pagination.

## ✨ Features

- Create a new match between two players
- Track score in real time (points → games → sets)
- Finished matches are saved to the database
- Browse match history with pagination
- Filter match history by player name

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Web | Java Servlets, JSP, JSTL |
| ORM | Hibernate 6 |
| Database | H2 (file mode) |
| Logging | Logback + SLF4J |
| Testing | JUnit 5 |
| Build | Maven |
| Server | Apache Tomcat 10 |

## 🏗 Project Structure
src/
├── main/
│   ├── java/TableTennis/
│   │   ├── dao/          # Data access layer (BaseDao + implementations)
│   │   ├── dto/          # Data transfer objects
│   │   ├── entity/       # Hibernate entities (MatchEntity, Player)
│   │   ├── Exception/    # Custom exceptions
│   │   ├── filter/       # Servlet filters (ExceptionHandler, Logging)
│   │   ├── listener/     # ServletContext listener
│   │   ├── mapper/       # Entity ↔ DTO mappers
│   │   ├── model/        # Domain model (Match, Score logic)
│   │   ├── service/      # Business logic
│   │   ├── servlet/      # HTTP request handlers
│   │   ├── utils/        # TransactionManager, HibernateUtil
│   │   └── validator/    # Input validation
│   └── resources/
│       ├── hibernate.cfg.xml
│       └── logback.xml
└── test/
└── java/             # JUnit 5 tests for domain model

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Apache Tomcat 10.1

### Build

```bash
mvn clean package
```

### Deploy

Copy the generated WAR file to Tomcat:

```bash
cp target/tennis.war $CATALINA_HOME/webapps/ROOT.war
```

Open in browser:
http://localhost:8080

## 🎮 How to Use

1. Go to the home page and click **New Match**
2. Enter names for Player 1 and Player 2
3. Click the player's button each time they win a point
4. When the match ends the result is saved automatically
5. View all finished matches at **/matches**
6. Use the search field to filter by player name

## 🧪 Testing

Domain model is covered by unit tests using JUnit 5.
Tests verify scoring logic: points, games, sets and match completion.

```bash
mvn test
```

## 📐 Domain Model
Match
├── Player firstPlayer
├── Player secondPlayer
├── Player winner (null until match ends)
└── Score
├── points  (0, 15, 30, 40, deuce, advantage)
├── games   (first to 6, must lead by 2)
└── sets    (first to 2)

## 🗄 Database

H2 embedded database in file mode — data persists between restarts.
Schema:
players (id, name)
matches (id, player1 → players.id, player2 → players.id, winner → players.id)
