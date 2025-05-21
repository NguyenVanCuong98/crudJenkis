pipeline {
    agent any

    environment {
        MAVEN_HOME = '/usr/share/maven' // hoặc đường dẫn maven trong container
    }

    tools {
        maven 'Maven 3' // tên cấu hình Maven đã add trong Jenkins (Manage Jenkins > Global Tool Configuration)
        jdk 'JDK 17'    // tương tự với JDK
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
                // Ví dụ: copy file jar qua SSH hoặc chạy docker container
                // sh 'scp target/*.jar user@server:/app/'
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
