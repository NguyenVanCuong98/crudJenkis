pipeline {
    agent any

    tools {
        jdk 'JDK 17'       // Tên cấu hình JDK trong Global Tool Configuration
        maven 'Maven 3'    // Tên cấu hình Maven trong Global Tool Configuration
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
                    def javaHome = tool 'JDK 17'
                    def mavenHome = tool 'Maven 3'
                    withEnv([
                        "JAVA_HOME=${javaHome}",
                        "PATH+JAVA=${javaHome}/bin",
                        "PATH+MAVEN=${mavenHome}/bin"
                    ]) {
                        sh 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    def javaHome = tool 'JDK 17'
                    def mavenHome = tool 'Maven 3'
                    withEnv([
                        "JAVA_HOME=${javaHome}",
                        "PATH+JAVA=${javaHome}/bin",
                        "PATH+MAVEN=${mavenHome}/bin"
                    ]) {
                        sh 'mvn test'
                    }
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
