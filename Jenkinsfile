pipeline {
    agent any

    environment {
        COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout source code') {
            steps {
                echo '==> Cloning source code from GitHub...'
                git url: 'https://github.com/NguyenVanCuong98/crudJenkis.git'
            }
        }

        stage('Stop & clean old containers') {
            steps {
                echo '==> Stopping and removing old containers (if any)...'
                sh 'docker-compose -f $COMPOSE_FILE down || true'
            }
        }

        stage('Build Docker images') {
            steps {
                echo '==> Building Docker images...'
                sh 'docker-compose -f $COMPOSE_FILE build'
            }
        }

        stage('Run application') {
            steps {
                echo '==> Running application containers...'
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }

        stage('Check running containers') {
            steps {
                echo '==> Listing running containers...'
                sh 'docker ps'
            }
        }
    }

    post {
        failure {
            echo '❌ Build failed. Check log for details.'
        }
        success {
            echo '✅ Build and deployment completed successfully!'
        }
    }
}
