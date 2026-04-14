📄 API Documentation (Swagger/OpenAPI)
======================================

The application provides an interactive **Swagger UI** (the same as we used initially) documentation, allowing us to visualize and test all endpoints directly from the browser.

1\. Accessing the Documentation
-------------------------------

As mentioned before, once the application is running via Docker, access the documentation at:

* * * * *

2\. 🛠️ API Structure & Implementation
--------------------------------------

The API is organized into functional controllers. The business logic for these endpoints is located in the **Services** directory.

Each correspondent service is linked next to the mentioned controller, there it is possible to further visualize how each method was

structured.

If you would wish to see a detailed list of each one of the endpoints,

you can check this file:

-   🔐

### 2.1. Authentication Controller (`/api/auth`)

Handles user registration and login.

-   **Implementation:** and

### 2.2. User Controller (`/api/user`)

Handles user account management, including self-service and administrative actions (RBAC).

-   **Implementation:**

### 2.3. User Role Controller (`/api/roles`)

Handles promotion and demotion of administrative privileges for users.

-   **Implementation:**

* * * * *

3\. Testing Protected Endpoints
-------------------------------

To test restricted endpoints, don't forget to authorize your session in Swagger:

1.  **Generate a Token:** Use `POST /api/auth/login`.

2.  **Authorize:** Click the **Authorize** (lock icon) button.

3.  **Input Token:** Enter `<your_token>`.

Locker icon:

Token screen:🧪 Test Suite Configuration
===========================

This document outlines the test implementation for the user management service layer, focusing on business logic validation and security context mocking.

* * * * *

1\. User Service Tests (`UserServiceTest`)
------------------------------------------

Validates the `UserService` operations using the **AAA (Arrange, Act, Assert)** pattern to ensure high-quality code and reliable user management.

-   **Key Features:**

    -   **Mocking Strategy:** Utilizes `@Mock` for `UserRepository` and `BCryptPasswordEncoder` to isolate service logic from external dependencies.

    -   **Context Simulation:** Mocks Spring Security's `SecurityContext` and `Authentication` to test "My User" operations without a running security filter.

    -   **Data Integrity:** Ensures that unique constraints (Username, CPF, Email) are respected before any persistence operation.

    -   **Exception Handling:** Validates that the system correctly throws `DataIntegrityViolationException` and `UsernameNotFoundException` in failure scenarios.

* * * * *

2\. Test Scenarios & Nesting
----------------------------

The test suite is organized into `@Nested` classes to maintain a clean hierarchy and improve readability.

-   **User Creation:** * Verifies successful user registration with password encryption.

    -   Ensures no duplicate data is saved to the database.

-   **User Retrieval:** * Tests fetching user data by ID.

    -   Validates error handling when a user is not found.

-   **Self-Service Operations (`MyUser`):** * Simulates an authenticated session to test `updateMyUser` and `deleteMyUser`.

    -   Confirms that changes only affect the currently logged-in user.

-   **Administrative Actions:** * Validates administrative deletion of users via ID.

* * * * *

3\. Tooling & Environment
-------------------------

Technical stack used to maintain the integrity of the service layer.

-   **JUnit 5:** Core testing framework using nested structures and display names.

-   **Mockito:** Used for behavior verification (`verify`) and stubbing (`doReturn`).

-   **Assertions:** Combines standard JUnit assertions (`assertEquals`, `assertNotNull`) to validate object states post-execution.

* * * * *

#### Return to main documentation:

[🔙📖 Go back to README](https://www.google.com/search?q=https://github.com/ramosprodev-krs/mongodb-user-controller)
