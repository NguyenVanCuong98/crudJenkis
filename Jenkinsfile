pipeline {
    agent any

    environment {
        // Đặt biến môi trường nếu cần
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                // Lấy code từ nhánh main
                git branch: 'main', url: 'https://github.com/your/repo.git'
            }
        }

        stage('Build & Deploy') {
            steps {
                // Dừng các container đang chạy nếu có
                sh 'docker-compose -f $DOCKER_COMPOSE_FILE down'

                // Build và chạy container mới
                sh 'docker-compose -f $DOCKER_COMPOSE_FILE up -d --build'
            }
        }
    }

    post {
        success {
            echo 'Build và deploy thành công!'
        }
        failure {
            echo 'Build hoặc deploy thất bại!'
        }
    }
}
