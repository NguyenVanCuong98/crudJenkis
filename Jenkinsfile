pipeline {
    agent {
        docker {
            image 'docker/compose:1.29.2'  // image có sẵn docker + docker-compose
            args '-v /var/run/docker.sock:/var/run/docker.sock'  // gắn docker socket
        }
    }
    stages {
        stage('Checkout') {
            steps {
                     git branch: 'main', url: 'https://github.com/NguyenVanCuong98/crudJenkis.git'
                  }
        }
        stage('Build and Run') {
            steps {
                sh 'docker-compose down || true' // tắt container nếu đang chạy
                sh 'docker-compose up -d --build'
            }
        }
    }
}
