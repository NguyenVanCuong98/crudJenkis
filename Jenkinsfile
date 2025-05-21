pipeline {
    agent any

    tools {
        jdk 'JDK 17'       // Tên phải trùng với cấu hình ở Global Tool Configuration
        maven 'Maven 3'    // Tên Maven cũng trùng với cấu hình trong Jenkins
    }

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
                // Ví dụ deploy
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
