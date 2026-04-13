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

🚀 Passo a Passo para Execução
------------------------------

### 1. Clonar o Repositório

Bash

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   git clone [https://github.com/your-username/mongodb-user-application.git](https://github.com/your-username/mongodb-user-application.git)  cd mongodb-user-application   `

### 2\. Configuração do Spring Boot (application.properties)

Para que a aplicação comunique com o container do MongoDB, as propriedades devem estar alinhadas com as credenciais do Docker:

Properties

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   spring.data.mongodb.host=localhost  spring.data.mongodb.port=27017  spring.data.mongodb.database=mongodb  spring.data.mongodb.username=user  spring.data.mongodb.password=12345  spring.data.mongodb.authentication-database=admin  # Segurança  jwt.secret=super-secret-phrase-that-no-one-should-know-32-chars  jwt.expiration=3600000   `

### 3\. Subir o Banco de Dados

Executa o comando abaixo na raiz do projeto para iniciar o MongoDB:

Bash

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   docker-compose up -d   `

### 4\. Executar a Aplicação

Com o banco de dados ativo, inicia a aplicação Spring Boot:

Bash

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML`   ./mvnw spring-boot:run   `

🔍 Verificação
--------------

*   **MongoDB:** Acessível em localhost:27017 via DBeaver ou MongoDB Compass.
    
*   **Swagger UI:** Acessível em http://localhost:8080/swagger-ui.html (assim que a app iniciar)."""
    

with open("docker-setup-guide.txt", "w", encoding="utf-8") as f:f.write(content)

Plain textANTLR4BashCC#CSSCoffeeScriptCMakeDartDjangoDockerEJSErlangGitGoGraphQLGroovyHTMLJavaJavaScriptJSONJSXKotlinLaTeXLessLuaMakefileMarkdownMATLABMarkupObjective-CPerlPHPPowerShell.propertiesProtocol BuffersPythonRRubySass (Sass)Sass (Scss)SchemeSQLShellSwiftSVGTSXTypeScriptWebAssemblyYAMLXML``   Aqui está o ficheiro `.txt` com o guia estruturado com base nas tuas configurações de `properties` e `compose`.  O ficheiro contém as instruções de clonagem, configuração das variáveis de ambiente e os comandos para subir o serviço.  Seu arquivo TXT está pronto  [file-tag: code-generated-file-0-1776085454999888343]  Quando estiveres pronto, podes mandar as classes de **Security/JWT** para seguirmos para o próximo tópico da navegação!   ``
