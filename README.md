# Two-Phase Commit (2PC) Distributed Transaction Demo

This project demonstrates a **Two-Phase Commit (2PC)** distributed transaction system using a **Coordinator Service** and two **Participant Services**, all interacting with PostgreSQL databases.

## **Prerequisites**
- **Java 17+**
- **Maven**
- **Docker & Docker Compose**
- **PostgreSQL Client (optional, for checking DB)**

---

## **Setup Instructions**

### **1. Start PostgreSQL Databases**
Run the following command to start the databases using Docker:

```sh
docker-compose up -d
```

This will start two PostgreSQL instances:
- Participant 1 Database (db1) on port 5432
```sh
psql -h localhost -p 5432 -U user -d db1
then,
db1=# SELECT * FROM transaction_log; 
```
- Participant 2 Database (db2) on port 5433
```sh
psql -h localhost -p 5433 -U user -d db2
```

### **2. Build and Run the Coordinator Service**
```sh
cd coordinator-service
mvn clean package
mvn spring-boot:run
```
### **3. Similarly, build and run Participant Services**
```sh
cd ../participant-service-[1/2]
mvn clean package
mvn spring-boot:run  # the port number is applied based on application.properties settings
```

## **Testing the 2PC Transaction**
### **Start a New Transaction**
Run the following curl command to trigger a transaction:
```sh
curl -X POST "http://localhost:8080/transaction/start" \
     -H "Content-Type: application/json" \
     -d '{
          "transactionId": "txn-1001",
          "participants": ["http://localhost:8081", "http://localhost:8082"]
         }'
```

### Expected Behavior
- The Coordinator sends a prepare request to all participants.
- If both Participants confirm, the Coordinator sends a commit request.
- If any Participant fails, the Coordinator sends a rollback request.

## Troubleshooting
1. "Relation 'transaction_log' does not exist"
If you get this error, the required table may not be created. Run this SQL command manually:

```sh
> CREATE TABLE transaction_log (
    transaction_id VARCHAR(255) PRIMARY KEY,
    status VARCHAR(50)
);
```
2. "Duplicate key value violates unique constraint"
This means a transaction ID is being reused. Try using a unique transactionId in the request.
