pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.1'
    }

    environment {
        DB_HOST = 'mysql'
        DB_PORT = '3306'
        DB_NAME = 'jenkinsdb'
        DB_USER = 'root'
        DB_PASSWORD = '123456'
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
                    echo "Tạo Docker network nếu chưa có"
                    docker network create jenkins-net || true

                    echo "Chạy MySQL container"
                    docker run -d --name mysql --network jenkins-net \
                      -e MYSQL_ROOT_PASSWORD=123456 \
                      -e MYSQL_DATABASE=jenkinsdb \
                      -p 3306:3306 \
                      mysql:8.0

                    echo "Chờ MySQL khởi động (tối đa 30s)..."
                    for i in {1..6}; do
                      if docker exec mysql mysqladmin ping -uroot -p123456 --silent; then
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
                sh 'chmod +x ./mvnw'
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw'
                sh '''
                    ./mvnw test \
                    -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} \
                    -Dspring.datasource.username=${DB_USER} \
                    -Dspring.datasource.password=${DB_PASSWORD}
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying..."
                // Thêm bước deploy nếu cần
            }
        }
    }

    post {
        always {
            sh 'docker stop mysql || true'
            sh 'docker rm mysql || true'
        }
    }
}
