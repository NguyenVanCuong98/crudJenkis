pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package & Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        stage('Deploy (optional)') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploying to production server..."
                // Thêm các bước deploy nếu có
            }
        }
    }
    post {
        success {
            echo '🎉 Build and tests successful!'
        }
        failure {
            echo '❌ Build failed. Check logs.'
        }
    }
}
