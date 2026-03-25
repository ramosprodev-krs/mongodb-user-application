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

### 1. **Clone the repository** ```bash
git clone [https://github.com/your-username/mongodb-user-application.git](https://github.com/your-username/mongodb-user-application.git)
cd mongodb-user-application
