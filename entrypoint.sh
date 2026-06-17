#!/bin/bash

# Start SSH daemon
echo "Starting SSH server..."
/usr/sbin/sshd

# Start Nginx
echo "Starting Nginx web server..."
service nginx start

# Wait for MySQL database container to be ready
echo "Waiting for MySQL database container (db:3306) to be ready..."
while ! timeout 1 bash -c "echo > /dev/tcp/db/3306" 2>/dev/null; do
  echo "Database not ready yet, sleeping 2s..."
  sleep 2
done
echo "Database is ready!"

# Launch the Spring Boot application
echo "Starting Spring Boot application..."
exec java -jar /app/build/libs/demo-0.0.1-SNAPSHOT.jar
