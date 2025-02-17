# Rewards Program

This project calculates reward points for customers based on their transactions over a three-month period.

## Prerequisites

- Java 11+
- Maven
- Spring Boot

## Getting Started

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/PoojaRaniF/rewardsCalculator/
   cd rewardsCalculator

2. **Build the project:**
mvn clean install

3. **Run the application:**
mvn spring-boot:run

**API Endpoints**
GET /rewards: Returns the reward points for each customer per month.
**Example Data**
The application uses a sample dataset to demonstrate the functionality. You can modify the dataset in the data.sql file located in the src/main/resources directory.

**Project Structure**
Controller: Handles RESTful endpoints.
Service: Contains business logic for calculating reward points.
Repository: Manages data storage and retrieval.
Tests: Includes unit and integration tests.

**Reward Calculation Logic**
2 points for every dollar spent over $100 in each transaction.
1 point for every dollar spent between $50 and $100 in each transaction.

**Example Calculation**
For a $120 purchase:
Points = 2 * ($120 - $100) + 1 * ($100 - $50) = 2 * 20 + 1 * 50 = 90 points

**Testing**
Run the following command to execute unit and integration tests:
mvn test
