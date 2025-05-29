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

        stage('Start Services') {
            steps {
                script {
                    sh '''
                        echo "Tạo Docker network nếu chưa có"
                        docker network inspect jenkins-net > /dev/null 2>&1 || docker network create jenkins-net

                        # Dừng & xóa container nếu đang chạy
                        for container in mysql zookeeper kafka; do
                          if [ $(docker ps -q -f name=$container) ]; then
                            echo "Dừng và xóa container $container"
                            docker stop $container
                            docker rm $container
                          fi
                        done

                        echo "Chạy MySQL container"
                        docker run -d --name mysql --network jenkins-net \
                            -e MYSQL_ROOT_PASSWORD=123456 \
                            -e MYSQL_DATABASE=jenkinsdb \
                            -p 3306:3306 \
                            mysql:8.0

                        echo "Chạy Zookeeper container"
                        docker run -d --name zookeeper --network jenkins-net \
                            -e ALLOW_ANONYMOUS_LOGIN=yes \
                            -p 2181:2181 \
                            zookeeper:3.8

                        echo "Chạy Kafka container"
                        docker run -d --name kafka --network jenkins-net \
                            -e KAFKA_BROKER_ID=1 \
                            -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
                            -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
                            -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
                            -p 9092:9092 \
                            confluentinc/cp-kafka:latest

                        echo "Chờ MySQL khởi động (tối đa 30s)..."
                        for i in $(seq 1 6); do
                            if docker exec mysql mysqladmin ping -uroot -p123456 --silent; then
                                echo "✅ MySQL đã sẵn sàng!"
                                break
                            fi
                            echo "⏳ Chưa sẵn sàng, thử lại sau 5s..."
                            sleep 5
                        done

                        echo "Chờ Zookeeper khởi động (tối đa 30s)..."
                        for i in $(seq 1 6); do
                            if echo ruok | nc localhost 2181 | grep imok; then
                                echo "✅ Zookeeper đã sẵn sàng!"
                                break
                            fi
                            echo "⏳ Zookeeper chưa sẵn sàng, thử lại sau 5s..."
                            sleep 5
                        done

                        echo "Chờ Kafka khởi động (tối đa 60s)..."
                        for i in $(seq 1 12); do
                            if docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list > /dev/null 2>&1; then
                                echo "✅ Kafka đã sẵn sàng!"
                                break
                            fi
                            echo "⏳ Kafka chưa sẵn sàng, thử lại sau 5s..."
                            sleep 5
                        done
                    '''
                }
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
