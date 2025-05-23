pipeline {
    agent any

    environment {
        DB_NAME = 'studentdb'
        DB_USER = 'root'
        DB_PASS = '123456'
    }

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.1'
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
                    docker network create jenkins-net || true
                    docker run -d --rm --name mysql-dev --network jenkins-net \
                        -e MYSQL_ROOT_PASSWORD=${DB_PASS} \
                        -e MYSQL_DATABASE=${DB_NAME} \
                        -p 3306:3306 mysql:8.0

                    echo "Chờ MySQL khởi động..."
                    for i in {1..6}; do
                        if docker exec mysql-dev mysqladmin ping -h"localhost" --silent; then
                            echo "✅ MySQL đã sẵn sàng!"
                            break
                        fi
                        echo "⏳ Chưa sẵn sàng, thử lại sau 5s..."
                        sleep 5
                    done
                '''
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
            sh 'docker stop mysql-dev || true'
        }
    }
}
