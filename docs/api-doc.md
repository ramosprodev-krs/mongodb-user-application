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

Token screen:
