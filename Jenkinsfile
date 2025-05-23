pipeline {
    agent any

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

        stage('Run MySQL container') {
            steps {
                sh '''
                    docker rm -f mysql-dev || true
                    docker run -d --name mysql-dev -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=studentdb -p 3306:3306 mysql:8.0
                '''
            }
        }

        stage('Wait for MySQL') {
            steps {
                echo 'Chờ MySQL container sẵn sàng...'
                sh '''
                    for i in {1..10}; do
                      if docker exec mysql-dev mysqladmin ping -h"127.0.0.1" -uroot -p123456 --silent; then
                        echo "MySQL đã sẵn sàng!"
                        break
                      fi
                      echo "Đợi MySQL... ($i)"
                      sleep 5
                    done
                '''
            }
        }

        stage('Build') {
            steps {
                echo 'Build ứng dụng với Maven'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                echo 'Chạy test'
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            echo 'Dọn dẹp MySQL container'
            sh 'docker stop mysql-dev || true'
        }
        success {
            echo 'Build thành công!'
        }
        failure {
            echo 'Build thất bại!'
        }
    }
}
