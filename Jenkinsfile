pipeline {
    agent any

    triggers {
        pollSCM('*/5 * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Compile the project and execute SQLite test suite on the host
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Deploy via Ansible') {
            steps {
                // Execute the Ansible Playbook to deploy the built JAR to the web container
                sh 'ansible-playbook -i inventory.ini playbook.yml'
            }
        }
    }

    post {
        failure {
            // Send email alerts using Jenkins emailext plugin on build or test failure
            emailext (
                subject: "Build Failure in Jenkins: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """Hello,

The Jenkins build #${env.BUILD_NUMBER} for job '${env.JOB_NAME}' has FAILED.

Details:
- Build URL: ${env.BUILD_URL}
- Console Output: ${env.BUILD_URL}console
- Git Branch: ${env.GIT_BRANCH}

Please investigate and resolve the issue.

Regards,
Jenkins CI/CD System""",
                to: 'srengty@gmail.com',
                recipientProviders: [
                    [$class: 'CulpritsRecipientProvider'],
                    [$class: 'DevelopersRecipientProvider']
                ]
            )
        }
    }
}
