pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven'
    }

    environment {
        JAVA_HOME = "${tool 'jdk17'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"

        DB_HOST = 'localhost'     // Hoặc IP MySQL trong Docker network
        DB_PORT = '3306'
        DB_NAME = 'studentdb'
        DB_CREDENTIALS = credentials('mysql-root')
        DOCKER_IMAGE = "your-repo/your-app:latest"  // tùy chỉnh nếu cần
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }

        stage('Build Java') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('DB Connection Test') {
            steps {
                sh '''
                    echo "Testing connection to MySQL..."
                    mysql -h $DB_HOST -P $DB_PORT -u $DB_CREDENTIALS_USR -p$DB_CREDENTIALS_PSW -e "SHOW DATABASES;"
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            when {
                expression { return env.DOCKER_IMAGE.contains("your-repo") }
            }
            steps {
                // Chỉ push nếu bạn có login docker registry ở trước đó
                // sh 'docker login -u $DOCKER_USER -p $DOCKER_PASSWORD registry-url'
                sh "docker push ${DOCKER_IMAGE}"
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "🚀 Deploying to production server..."
            }
        }
    }

    post {
        success {
            echo '🎉 Build + Test + DB check + Docker image OK!'
        }
        failure {
            echo '❌ Something went wrong, check logs.'
        }
    }
}
