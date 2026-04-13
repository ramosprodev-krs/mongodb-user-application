# 📄 API Documentation (Swagger/OpenAPI)  
The application provides an interactive **Swagger UI** (the same as we used initially) documentation, allowing us to visualize and test all endpoints directly from the browser.

## 1. Accessing the Documentation

As mentioned before, once the application is running via Docker, access the documentation at:  
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

If you would wish to see a detailed list of each one of the endpoints,  
you can check this file:
- 🔐 [Endpoint Permissions(RBAC)](endpoint.md)

* * * * *

## 2. 🛠️ API Structure & Implementation
The API is organized into functional controllers. The business logic for these endpoints is located in the **Services** directory.  

Each correspondent service is linked next to the mentioned controller, there it is possible to further visualize how each method was  
structured.

### 2.1. Authentication Controller (`/api/auth`)
Handles user registration and login.

* **Implementation:** and [**TokenService**]("https://github.com/ramosprodev-krs/mongodb-user-application/blob/master/src/main/java/krs/mongodb_user_controller/services/TokenService.java") and [**UserService**]("https://github.com/ramosprodev-krs/mongodb-user-application/blob/master/src/main/java/krs/mongodb_user_controller/services/UserService.java).  
<img src="https://i.imgur.com/XwDBGrR.png">

### 2.2. User Controller (`/api/user`)
Handles user account management, including self-service and administrative actions (RBAC).

* **Implementation:** [**UserService**]("https://github.com/ramosprodev-krs/mongodb-user-application/blob/master/src/main/java/krs/mongodb_user_controller/services/TokenService.java").  
<img src="https://i.imgur.com/iwjC3Ob.png">

### 2.3. User Role Controller (`/api/roles`)
Handles promotion and demotion of administrative privileges for users.

* **Implementation:** [**UserRoleService**]("https://github.com/ramosprodev-krs/mongodb-user-application/blob/master/src/main/java/krs/mongodb_user_controller/services/UserRoleService.java").  
<img src="https://i.imgur.com/wwowaWi.png">

* * * * *

## 3. Testing Protected Endpoints
To test restricted endpoints, don't forget to authorize your session in Swagger:

1.  **Generate a Token:** Use `POST /api/auth/login`.
2.  **Authorize:** Click the **Authorize** (lock icon) button.
3.  **Input Token:** Enter `<your_token>`.

Locker icon:  
<img src="https://i.imgur.com/Gwe24gL.png">

Token screen:  
<img src="https://i.imgur.com/W7FuZEQ.png">

#### Return to main documentation:
[🔙📖 Go back to README](https://github.com/ramosprodev-krs/mongodb-user-application)
