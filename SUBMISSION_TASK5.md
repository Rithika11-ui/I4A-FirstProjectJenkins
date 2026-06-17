# Final Exam - DevOps Submission (Task 5)

## Student Information
* **Name**: PHE RITHIKA
* **ID**: e20220245
* **Class**: GIC-I4-A
* **Group**: A

---

## Task 5: GitHub Actions CI/CD Pipeline Automation

I have successfully designed, configured, and integrated a complete GitHub Actions CI/CD pipeline that automates the build, test, email notification on failure, and Ansible deployment processes.

Below are the details and configuration files demonstrating the successful implementation of this task.

---

### 1. GitHub Actions Workflow Configuration
The workflow file is stored at `.github/workflows/ci-cd.yml`. It triggers on every push to the repository and performs the following jobs:
1. **Build & Test:** Compiles the Spring Boot project and runs all JUnit tests using Gradle against SQLite.
2. **Failure Notification:** If the build or test fails, it dynamically retrieves the committer's email address and sends a failure notification email to `srengty@gmail.com` and the developer who committed the error using SMTP.
3. **Deployment via Ansible Playbook:** If the build and test succeed, it runs the Ansible playbook to copy the built JAR into the `web` container, restarts the application, and backs up the database.

```yaml
name: CI/CD Pipeline

on:
  push:
    branches:
      - '**'

jobs:
  build-test-deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Start Docker Compose Stack
        run: |
          docker compose up -d --build
          echo "Waiting for containers to start..."
          sleep 10

      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          java-version: '25'
          distribution: 'temurin'
          cache: 'gradle'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build and Test with Gradle
        run: ./gradlew clean build

      - name: Install Ansible & SSHPass
        if: success()
        run: |
          sudo apt-get update
          sudo apt-get install -y ansible sshpass

      - name: Run Ansible Playbook to Deploy
        if: success()
        run: |
          ansible-playbook -i inventory.ini playbook.yml

      - name: Get Committer Email
        if: failure()
        run: |
          COMMITTER_EMAIL=$(git log -1 --format='%ae')
          echo "COMMITTER_EMAIL=$COMMITTER_EMAIL" >> $GITHUB_ENV

      - name: Send Mail on Failure
        if: failure()
        uses: dawidd6/action-send-mail@v3
        with:
          server_address: smtp.gmail.com
          server_port: 465
          username: ${{ secrets.MAIL_USERNAME }}
          password: ${{ secrets.MAIL_PASSWORD }}
          subject: "GitHub Actions Build Failure: ${{ github.repository }} - #${{ github.run_number }}"
          body: |
            Hello,

            The GitHub Actions workflow has failed on commit ${{ github.sha }} by ${{ github.actor }}.

            Details:
            - Commit Message: ${{ github.event.head_commit.message }}
            - Workflow Run Log: ${{ github.server_url }}/${{ github.repository }}/actions/runs/${{ github.run_id }}

            Please review the log and resolve the issue.

            Regards,
            CI/CD Pipeline System
          to: srengty@gmail.com,${{ env.COMMITTER_EMAIL }}
          from: GitHub Actions CI/CD <noreply@github.com>
```

---

### 2. Ansible Playbook Configuration
The updated `playbook.yml` is configured to deploy the newly built artifact to the target server via SSH and initiate database backup inside the container:

```yaml
---
- name: Automate Deployment and Database Backup
  hosts: web_servers
  gather_facts: no
  tasks:
    - name: Copy built JAR into web container
      ansible.builtin.copy:
        src: build/libs/demo-0.0.1-SNAPSHOT.jar
        dest: /app/build/libs/demo-0.0.1-SNAPSHOT.jar
        mode: '0755'

    - name: Restart application by stopping Java (Docker auto-restarts container)
      shell: pkill -f java || true

    - name: Backup the MySQL database inside the container
      shell: mysqldump -h db -u root -pHello@123 A-PHE_Rithika-db > /app/backup.sql
      no_log: true
```

---

## Submission Details
* **Repository URL**: https://github.com/Rithika11-ui/I4A-FirstProjectJenkins.git
* **Branch**: `finalexam`
