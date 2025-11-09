📊 Automação de API Pet Store


<img align="center" alt="-Java" height="48" width="58" src="https://github.com/devicons/devicon/blob/v2.16.0/icons/java/java-original-wordmark.svg">


Projeto de automação de testes de API desenvolvido com RestAssured para validar endpoints e funcionalidades do sistema PetStore.

🚀 Tecnologias Utilizadas

    Java 17+

    RestAssured 5.3.0

    TestNG 7.4.0

    Maven 3.8+

    Allure Reports (para relatórios)

📁 Estrutura do Projeto

src/test/java/
├── config/
│   ├── TestConfig.java
│   └── Environment.java
├── models/
│   ├── request/
│   │   ├── UserRequest.java
│   │   └── LoginRequest.java
│   └── response/
│       ├── UserResponse.java
│       └── ErrorResponse.java
├── services/
│   ├── UserService.java
│   └── AuthService.java
├── tests/
│   ├── UserTests.java
│   └── AuthTests.java
└── utils/
    ├── TestDataGenerator.java
    └── FileReader.java

src/test/resources/
├── test-data/
│   ├── users/
│   └── products/
├── config/
│   └── environment.properties
└── suite/
    └── testng.xml

⚙️ Pré-requisitos

Java JDK 17 ou superior
Maven 3.8 ou superior
Git
