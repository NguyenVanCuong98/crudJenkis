pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock' // để có thể chạy docker trong container nếu cần
        }
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
                echo 'Waiting for MySQL container to be ready...'
                sh '''
                    for i in $(seq 1 10); do
                      if docker exec mysql-dev mysqladmin ping -h"127.0.0.1" -uroot -p123456 --silent; then
                        echo "MySQL is ready!"
                        break
                      fi
                      echo "Waiting MySQL... ($i)"
                      sleep 5
                    done
                '''
            }
        }
        stage('Build and Test') {
            steps {
                withEnv([
                    'SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezone=UTC',
                    'SPRING_DATASOURCE_USERNAME=root',
                    'SPRING_DATASOURCE_PASSWORD=123456',
                    'SPRING_JPA_HIBERNATE_DDL_AUTO=update',
                    'SPRING_JPA_SHOW_SQL=true',
                    'SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect'
                ]) {
                    sh 'mvn clean install'
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up MySQL container'
            sh 'docker stop mysql-dev || true'
            sh 'docker rm mysql-dev || true'
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
