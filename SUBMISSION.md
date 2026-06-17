# Final Exam - DevOps Submission (Task 2)

## Student Information
* **Name**: PHE RITHIKA
* **ID**: e20220245
* **Class**: GIC-I4-A
* **Group**: A

---

## Submission Summary
* **Repository URL**: https://github.com/Rithika11-ui/I4A-FirstProjectJenkins.git
* **Branch**: `finalexam`
* **Choice Selected**: Choice A (Docker Compose Deployment)

---

## 1. Container Architecture Setup

### Web Server Container (`web`):
* **Base Image**: Eclipse Temurin JDK 25 (`eclipse-temurin:25-jdk`)
* **Installed Services**: Nginx (Web Server) & OpenSSH-Server (SSH daemon)
* **Reverse Proxy**: Nginx listens on port `8443` inside the container and forwards all incoming requests to the Spring Boot application running internally on port `8080`.
* **SSH Configuration**: Configured to run on port `2222` inside the container, allowing `root` user password login.
* **Exposed Ports**: Port `8443` (website) and Port `2222` (SSH) are exposed to the host machine.

### Database Container (`db`):
* **Base Image**: MySQL 8.0 (`mysql:8.0`)
* **Database Name**: `A-PHE_Rithika-db` (Group: `A`, Name: `PHE Rithika`)
* **Root Password**: `Hello@123`
* **Volumes**: Persisted database files are stored in `db_data` volume to prevent data loss on container restart.

---

## 2. Configuration Files

### `docker-compose.yml`
```yaml
version: '3.8'

services:
  db:
    image: mysql:8.0
    container_name: db
    restart: always
    environment:
      MYSQL_DATABASE: "A-PHE_Rithika-db"
      MYSQL_ROOT_PASSWORD: "Hello@123"
    volumes:
      - db_data:/var/lib/mysql

  web:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: web
    restart: always
    environment:
      SPRING_DATASOURCE_URL: "jdbc:mysql://db:3306/A-PHE_Rithika-db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
      SPRING_DATASOURCE_USERNAME: "root"
      SPRING_DATASOURCE_PASSWORD: "Hello@123"
    ports:
      - "8443:8443"
      - "2222:2222"
    depends_on:
      - db

volumes:
  db_data:
```

### `Dockerfile`
```dockerfile
FROM eclipse-temurin:25-jdk

# Install NGINX, OpenSSH Server, Git, and curl
RUN apt-get update && apt-get install -y nginx openssh-server git curl && rm -rf /var/lib/apt/lists/*

# Configure SSH
RUN mkdir -p /var/run/sshd
RUN echo 'root:Hello@123' | chpasswd
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
# Run SSH on port 2222 inside the container
RUN sed -i 's/#Port 22/Port 2222/' /etc/ssh/sshd_config

# Configure NGINX to proxy port 8443 to port 8080 (Spring Boot)
RUN rm -f /etc/nginx/sites-enabled/default
COPY nginx.conf /etc/nginx/sites-enabled/default

# Set working directory
WORKDIR /app

# Copy the Spring Boot files from context (demo directory)
COPY . /app

# Build the Spring Boot application (using local Gradle wrapper inside container)
RUN chmod +x gradlew && ./gradlew clean bootJar -x test

# Copy entrypoint script and make it executable
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Expose Nginx proxy port 8443 and SSH port 2222
EXPOSE 8443 2222

ENTRYPOINT ["/entrypoint.sh"]
```

---

## 3. Verification & Validation Outputs

### A. Verify Container Status (`docker compose ps`)
```powershell
CONTAINER ID   IMAGE       COMMAND                  CREATED          STATUS         PORTS                                                                                      NAMES
48d1f04d188a   mysql:8.0   "docker-entrypoint.s…"   9 minutes ago    Up 9 minutes   3306/tcp, 33060/tcp                                                                        db
254207c3b4bd   demo-web    "/entrypoint.sh"         9 minutes ago    Up 9 minutes   0.0.0.0:2222->2222/tcp, [::]:2222->2222/tcp, 0.0.0.0:8443->8443/tcp, [::]:8443->8443/tcp   web
```

### B. Verify Web App Proxy Access via Nginx (`http://localhost:8443/profiles`)
```powershell
> curl.exe -i http://localhost:8443/profiles
HTTP/1.1 200 
Server: nginx/1.28.3 (Ubuntu)
Date: Wed, 17 Jun 2026 11:02:00 GMT
Content-Type: text/html;charset=UTF-8
Transfer-Encoding: chunked
Connection: keep-alive
...
```

### C. Verify SSH Access on Port 2222
```powershell
> ssh root@localhost -p 2222
root@localhost's password: Hello@123
Welcome to Ubuntu 26.04 LTS (GNU/Linux 6.6.87-microsoft-standard-WSL2 x86_64)
...
root@254207c3b4bd:~#
```

### D. Verify MySQL Database Name and Tables
```powershell
> docker exec -it db mysql -u root -pHello@123
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| A-PHE_Rithika-db   |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.01 sec)

mysql> use `A-PHE_Rithika-db`;
Database changed

mysql> show tables;
+------------------------------+
| Tables_in_A-PHE_Rithika-db   |
+------------------------------+
| profiles                     |
| templates                    |
+------------------------------+
2 rows in set (0.01 sec)
```
