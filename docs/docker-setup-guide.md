# 🐳 Running the application with Docker (Guide)

In this guide, you will learn the first steps to get this application running, including:
- Locally running the app with Docker
- Registering your first user
- Logging in with your user
- Authorizing endpoints with your JWT Token

### Setting things first
Before proceeding, ensure you have the following tools properly installed on your system:  
- [Java Development Kit (JDK 21)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)  
- [Docker Engine / Docker Desktop](https://www.docker.com/products/docker-desktop/)  
- [Insomnia](https://insomnia.rest/download) or [Postman](https://www.postman.com/downloads/) (use if you judge necessary, as Swagger is already included).

---

## The use of "docker-compose.yml"
This project includes a **docker-compose.yml** file designed to orchestrate the **MongoDB** database and the **Spring Boot** application containers seamlessly.

### 1. **Clone the repository** 
```bash
git clone [https://github.com/ramosprodev-krs/mongodb-user-application.git](https://github.com/ramosprodev-krs/mongodb-user-application.git)
cd mongodb-user-application
```
---

### 2. **Start the containers**

Use Docker Compose to start both the Application and the Database containers:

```
docker-compose up --build -d
```
---

### 3. **Available ports**

After successfully starting the containers, the following ports will be available:

```
Port 8080 -> Runs the application (Spring Boot API)
Port 27017 -> Runs the MongoDB Database
```
---

### 4. **Accessing the endpoints**
We will interact with the authentication and protected endpoints locally using Swagger.

#### 4.1. Registering with Swagger

To register your first account, access: `http://localhost:8080/swagger-ui.html`

1.  Open the **"Authentication Controller"** tab.

2.  Select the **/auth/register** endpoint.

3.  Provide the user data in the following JSON pattern:

```
{
  "username": "your_user",
  "password": "your_password",
  "email": "example@email.com"
}
```

#### 4.2. Logging in with Swagger
After registering, you need to authenticate to receive your access token.

1.  Access the **/auth/login** endpoint.

2.  Provide your credentials:
```
{
  "username": "your_user",
  "password": "your_password"
}
```

Upon success, the API will return a **JWT Token**. Copy this token.

#### 4.3. Authorizing Protected Endpoints

To access routes that require authentication, you must use the generated token:

1.  Click the **"Authorize"** (locker icon) at the top of the Swagger page.
2.  Paste your token in the field (some configurations require the 'Bearer ' prefix).
3.  Once authorized, you can interact with all protected resources based on your user's permissions.

* * * * *

Now that you've finished this guide, you can return to the main documentation:

🔙📖 Go back to README
