pipeline {
    agent any

    environment {
        IMAGE_NAME = "springboot-app"
        DOCKER_COMPOSE_FILE = "docker-compose-app.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/yourusername/yourrepo.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_NAME} ."
                }
            }
        }

        stage('Run with docker-compose') {
            steps {
                script {
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }

    post {
        success {
            echo 'Build và deploy thành công!'
            // Gửi thông báo Slack ở đây (mình có ví dụ bên dưới)
        }
        failure {
            echo 'Build hoặc deploy thất bại!'
            // Gửi thông báo Slack ở đây (mình có ví dụ bên dưới)
        }
    }
}
