pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.1'
    }

    environment {
        DB_HOST = 'localhost'
        DB_PORT = '3306'
        DB_NAME = 'studentdb'
        DB_USER = 'root'
        DB_PASS = '123456'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }

        stage('Start MySQL') {
            steps {
                sh '''
                    docker run -d --name mysql-dev \
                        -e MYSQL_ROOT_PASSWORD=${DB_PASS} \
                        -e MYSQL_DATABASE=${DB_NAME} \
                        -p ${DB_PORT}:3306 mysql:8.0
                '''
                echo 'Đợi MySQL khởi động...'
                sh 'sleep 40'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            echo 'Stopping MySQL container'
            sh 'docker stop mysql-dev || true'
            sh 'docker rm mysql-dev || true'
        }
    }
}
