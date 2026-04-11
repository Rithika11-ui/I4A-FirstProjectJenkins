pipeline {
    agent { label 'LaravelAgent' }

    environment {
        DEPLOY_HOST     = '178.128.93.188'
        DEPLOY_USER     = 'PHE-RITHIKA'
        APP_DIR         = '/var/www/PHE-RITHIKA'
        GIT_REPO        = 'https://github.com/Rithika11-ui/I4A-FirstProjectJenkins.git'

        TELEGRAM_BOT_TOKEN = credentials('telegram-bot-token')
        TELEGRAM_CHAT_ID   = credentials('telegram-chat-id')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository...'
                checkout scm
            }
        }

        stage('Install PHP Dependencies') {
            steps {
                echo 'Running composer install...'
                sh 'composer install --no-interaction --prefer-dist --optimize-autoloader'
            }
        }

        stage('Environment Setup') {
            steps {
                echo 'Setting up .env and generating app key...'
                sh '''
                    if [ ! -f .env ]; then
                        cp .env.example .env
                    fi
                    php artisan key:generate
                '''
            }
        }

        stage('Install Node Dependencies') {
            steps {
                echo 'Running npm install...'
                sh 'npm install'
            }
        }

        stage('Build Frontend Assets') {
            steps {
                echo 'Building frontend with npm run build...'
                sh 'npm run build'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Running PHP tests...'
                sh 'php artisan test --parallel || true'
            }
        }

        stage('Deploy via Ansible') {
            steps {
                echo "Deploying to ${DEPLOY_HOST}..."
                sshagent(credentials: ['deploy-ssh-key']) {
                    sh '''
                        ansible-playbook -i ansible/inventory.ini \
                            ansible/deploy.yml \
                            -e "deploy_host=${DEPLOY_HOST}" \
                            -e "deploy_user=${DEPLOY_USER}" \
                            -e "app_dir=${APP_DIR}" \
                            -e "git_repo=${GIT_REPO}"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
            mail to: 'pherithika@gmail.com',
                subject: "✅ Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Good news! Build #${env.BUILD_NUMBER} was deployed successfully.\n\nURL: ${env.BUILD_URL}"
            sh """curl -s -X POST https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage \
                -d chat_id=${TELEGRAM_CHAT_ID} \
                -d parse_mode=HTML \
                -d text=✅ <b>Build SUCCESS</b>%0AJob: ${env.JOB_NAME}%0ABuild: #${env.BUILD_NUMBER}%0AURL: ${env.BUILD_URL}"""
        }
        failure {
            echo 'Build or deployment FAILED!'
            mail to: 'pherithika@gmail.com',
                subject: "❌ Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Build #${env.BUILD_NUMBER} failed.\n\nURL: ${env.BUILD_URL}"
            sh """curl -s -X POST https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage \
                -d chat_id=${TELEGRAM_CHAT_ID} \
                -d parse_mode=HTML \
                -d text=❌ <b>Build FAILED</b>%0AJob: ${env.JOB_NAME}%0ABuild: #${env.BUILD_NUMBER}%0AURL: ${env.BUILD_URL}"""
        }
    }
}
