# 📄 API Documentation (Swagger/OpenAPI)  
The application provides an interactive **Swagger UI** documentation, allowing us to visualize and test all endpoints directly from the browser.

## 1. Accessing the Documentation

Once the application is running, access the documentation at:  
**[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

For a detailed list of roles and permissions, check:
- 🔐 [Endpoint Permissions (RBAC)](endpoint.md)

---

## 2. 🛠️ API Structure & Implementation
The API is organized into functional controllers. The business logic is located in the **Services** directory.

### 2.1. Authentication Controller (`/api/auth`)
Handles user registration and login.

* **Implementation:** [**TokenService**](../src/main/java/krs/mongodb_user_controller/services/TokenService.java) and [**UserService**](../src/main/java/krs/mongodb_user_controller/services/UserService.java).  
<img src="https://i.imgur.com/XwDBGrR.png">

### 2.2. User Controller (`/api/user`)
Handles user account management, including self-service and administrative actions.

* **Implementation:** [**UserService**](../src/main/java/krs/mongodb_user_controller/services/UserService.java).  
<img src="https://i.imgur.com/iwjC3Ob.png">

### 2.3. User Role Controller (`/api/roles`)
Handles promotion and demotion of administrative privileges.

* **Implementation:** [**UserRoleService**](../src/main/java/krs/mongodb_user_controller/services/UserRoleService.java).  
<img src="https://i.imgur.com/wwowaWi.png">

---

## 3. Testing Protected Endpoints
To test restricted endpoints, authorize your session in Swagger:

1. **Generate a Token:** Use `POST /api/auth/login`.
2. **Authorize:** Click the **Authorize** (lock icon) button.
3. **Input Token:** Enter your JWT string.

Locker icon:  
<img src="https://i.imgur.com/Gwe24gL.png">

Token screen:  
<img src="https://i.imgur.com/W7FuZEQ.png">

#### Return to main documentation:
[🔙📖 Go back to README](../README.md)
