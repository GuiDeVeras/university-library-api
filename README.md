# university-library-api
REST API for a university library system built with pure Java and PostgreSQL.

Structure

src/main/java/api
│
├── Server.java
│
├── handler/
│   ├── StudentHandler.java
│   ├── AuthorHandler.java
│   ├── BookHandler.java
│   └── LoanHandler.java
│
├── model/
│   ├── Student.java
│   ├── Author.java
│   ├── Book.java
│   └── Loan.java
│
├── repository/
│   ├── StudentRepository.java
│   ├── AuthorRepository.java
│   ├── BookRepository.java
│   └── LoanRepository.java
│
└── util/
    ├── JsonUtil.java
    └── ResponseUtil.java
