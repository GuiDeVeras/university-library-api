# university-library-api
REST API for a university library system built with pure Java and PostgreSQL.

Structure

api/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── api/
│               ├── Server.java          // start the server
│               ├── config/
│               │     ConnectionFactory.java
│               ├── handler/
│               │     StudentHandler.java
│               │     AuthorHandler.java
│               │     BookHandler.java
│               │     LoanHandler.java
│               ├── dao/
│               │     StudentDAO.java
│               │     AuthorDAO.java
│               │     BookDAO.java
│               │     LoanDAO.java
│               ├── model/
│               │     Student.java
│               │     Author.java
│               │     Book.java
│               │     Loan.java
│               ├── util/
│               │     JsonUtil.java
│               │     ResponseUtil.java
│               └── dto/
│                     (optional)
