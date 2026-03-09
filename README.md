# auth-user-api

**Project Start Date:** September 27, 2025  
**Completion Date:** October 17, 2025   

This project was developed in **Java** using the **Spring Framework**, following a layered architecture centered on four main pillars: **validation**, **registration**, **authorization**, and **authentication**.

Completed in three weeks, the development process gave me the opportunity to explore each layer in depth, strengthening my understanding of backend systems and improving my overall programming skills.

### Technologies Used
- Java (Spring Framework)
- Spring Security
- Java Validation
- JWT (JSON Web Token)
- Docker
- MongoDB
- Insomnia

---

## How to Run the Project  

### Setting things first
Before proceeding, you will need the following tools properly installed:  
∘ [Java Development Kit (JDK 17 or later)](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html)  
∘ [Docker Desktop](https://www.docker.com/products/docker-desktop/)  
∘ [MongoDB](https://www.mongodb.com/try/download/shell)  
∘ [Insomnia](https://insomnia.rest/download)  
∘ [Postman](https://www.postman.com/downloads/)  

You can use any **API client** of your choice.  
Insomnia and Postman are listed here merely as examples for testing the endpoints. 
  

## Option 1 — Run in your localhost

This project also includes a **`docker-compose.yml`** file that starts both the **Spring Boot API** and a **MongoDB** database container.

### 1. **Clone the repository**  
   ```bash
   git clone https://github.com/KalRSilva/auth-user-api.git
   cd auth-user-api
   ```  
  
### 2. **Create your .env file**  
   Create a `.env` file in the root directory of the project with the following variables:

   ```env
   MONGO_USER=yourMongoUsername
   MONGO_PASS=yourMongoPassword
   ```
  
### 3. **Start the containers**  
   Use Docker Compose to start both the API and the MongoDB container:

   ```bash
   docker-compose up --build
   ```

   The API will be available at http://localhost:4040  
   and MongoDB will be running on port 27017.  
     

### 4. **Accessing endpoints**  
  These are the endpoints that you can access to perform operations such as user registration, authentication, and administrative management.  
   
   #### **Setup** (`/setup`)
   | Method | Endpoint | Description |
   |:--------|:----------|:-------------|
   | `POST` | `/setup/admin` | Registers the first admin (works only for the first admin created). |  
   
   First admin register (**`/setup/admin`**) example:  
     
   {  
         "fullName" : "Lucas Andrade Silva",  
         "username" : "lucas_andrade",  
         "password" : "SecurePassword123!",  
         "cpf" : "123.456.789-10",  
         "email" : "lucas.andrade@example.com",  
         "age" : 28,  
         "userRole" : "ADMIN"  
      }    
     
   > Note: This endpoint exists solely to create the first admin in the system.  
     Once it has been successfully used, it becomes **permanently disabled** to prevent additional and undue admins registrations.
      
   ---  
     
         
   #### **Authentication** (`/auth`)
   | Method | Endpoint | Description |
   |:--------|:----------|:-------------|
   | `POST` | `/auth/register` | Registers a new user (non-admin). |
   | `POST` | `/auth/login` | Authenticates an existing user and returns a **JWT token**. |  

   Register (**`/auth/register`**) example:
     
   {  
         "fullName" : "Lucas Andrade Silva",  
         "username" : "lucas_andrade",  
         "password" : "SecurePassword123!",  
         "cpf" : "123.456.789-10",  
         "email" : "lucas.andrade@example.com",  
         "age" : 28  
   }  

   > Note: This format also applies to the **PATCH** method located at the `/user` endpoint.  

   Login (**`/auth/login`**) example:  
     
   {  
      "username" : "lucas_andrade"  
      "password" : "SecurePassword123!"  
   }  
     
   After successfully logging in, you will receive your **JWT token**.  
   Further when accessing other endpoints, you will need to include it as a **Bearer Token**.  
      
   > Note: The `/auth/register` endpoint **does not** allow admin account creation — only regular users can register here.
     > To create an admin account, you must either use the `/setup/admin` endpoint (if no admins exist yet) or already possess the admin role to access admin-specific endpoints.
      
   ---
  
   #### **Admin** (`/admin`)
   | Method | Endpoint | Description |
   |:--------|:----------|:-------------|
   | `POST` | `/admin/users` | Creates a new user. |
   | `GET` | `/admin/users/read` | Reads all users from the database. |
   | `GET` | `/admin/users/read/{id}` | Reads a specific user by ID. |
   | `PATCH` | `/admin/users/patch/{id}` | Updates user information. |
   | `DELETE` | `/admin/users/delete/{id}` | Deletes a user by ID. |

   Register (**`/admin/users`**) example:  
     
   {  
      "fullName" : "Lucas Andrade Silva",  
      "username" : "lucas_andrade",  
      "password" : "SecurePassword123!",  
      "cpf" : "123.456.789-10",  
      "email" : "lucas.andrade@example.com",  
      "age" : 28,  
      "userRole" : "ADMIN/USER"  
   }  

   This format also applies to the PATCH (**`/admin/users/patch/{id}`**) method.  
      
   > Note: These endpoints are **restricted** to admin users.  
     > Attempting to access them without a valid admin token will return **403 Forbidden**.
      
   ---
      
   #### **User** (`/user`)
   | Method | Endpoint | Description |
   |:--------|:----------|:-------------|
   | `GET` | `/user/read/{id}` | Returns the current user's information. |
   | `PATCH` | `/user/update/{id}` | Allows the user to update their own data. |
   | `DELETE` | `/user/delete/{id}` | Deletes the current user's account. |
      
   > Note: Each `/user/...` endpoint uses Spring Security's `@PreAuthorize` annotation to ensure that users can only act on their **own** account.
      
   --- 

   
