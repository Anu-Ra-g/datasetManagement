# WeatherApp

A Spring Boot application for dataset Management.

## Features

- Connects to PostgreSQL database
- Uses Spring Data JPA with Hibernate
- Uses Spring Web for REST API
- Externalized configuration for secure secrets management

## Getting Started

### Prerequisites

- Java 17
- SpringBoot
- PostgreSQL database up and running
- Docker for development

---

### Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/Anu-Ra-g/datasetManagement.git
cd datasetManagement
```

2. **Configure the application properties**

Copy contents of `application-dev.properties` in the `application.properties`,
```bash
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

Update it with your database and server configurations:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/weatherdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. **Build the project**

```bash
./mvnw clean install
```

4. **Run the application**

```bash
./mvnw spring-boot:run
```

