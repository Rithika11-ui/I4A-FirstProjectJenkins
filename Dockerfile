FROM eclipse-temurin:25-jdk

# Install NGINX, OpenSSH Server, Git, curl, and MySQL client
RUN apt-get update && apt-get install -y nginx openssh-server git curl default-mysql-client && rm -rf /var/lib/apt/lists/*

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
