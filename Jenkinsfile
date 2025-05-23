pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven 3.8.1'
    }

    environment {
        DB_HOST = 'mysql'           // Tên container/service MySQL trong mạng Docker/Jenkins
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

        stage('Build') {
            steps {
                sh 'chmod +x ./mvnw'           // Cấp quyền thực thi cho mvnw
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'chmod +x ./mvnw'           // Cấp quyền thực thi cho mvnw
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
                // Thêm các bước deploy nếu cần
            }
        }
    }
}
