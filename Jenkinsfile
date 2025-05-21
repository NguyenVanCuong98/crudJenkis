pipeline {
    agent any

    tools {
        maven 'Maven 3'   // Tên cấu hình Maven trong Jenkins
        jdk 'JDK 17'      // Tên cấu hình JDK trong Jenkins
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
                script {
                    def mvnHome = tool 'Maven 3'
                    def javaHome = tool 'JDK 17'
                    sh """
                        export JAVA_HOME=${javaHome}
                        export PATH=${mvnHome}/bin:${javaHome}/bin:\$PATH
                        mvn clean package -DskipTests
                    """
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def mvnHome = tool 'Maven 3'
                    def javaHome = tool 'JDK 17'
                    sh """
                        export JAVA_HOME=${javaHome}
                        export PATH=${mvnHome}/bin:${javaHome}/bin:\$PATH
                        mvn test
                    """
                }
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
