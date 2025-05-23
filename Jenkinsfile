pipeline {
    agent any

    tools {
        jdk 'JDK 17'               // Sử dụng JDK 17 đã được cấu hình trên Jenkins
        maven 'Maven 3.8.1'        // Sử dụng Maven 3.8.1 đã được cấu hình trên Jenkins
    }

    environment {
        // Khai báo biến môi trường để dùng cho kết nối database
        DB_HOST = 'mysql'           // Tên host MySQL (có thể là tên container hoặc service trong Docker network)
        DB_PORT = '3306'            // Cổng MySQL mặc định
        DB_NAME = 'jenkinsdb'       // Tên database bạn dùng
        DB_USER = 'root'            // Username MySQL
        DB_PASSWORD = '123456'      // Password MySQL
    }

    stages {
        stage('Checkout') {
            steps {
                // Lấy code từ repo Git (nhánh main)
                git branch: 'main', url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }

        stage('Build') {
            steps {
                // Biên dịch và đóng gói dự án
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                // Chạy test với các tham số kết nối MySQL được truyền vào qua biến môi trường
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
                // Thêm các bước deploy nếu cần, ví dụ: chạy container, copy file, gọi script deploy...
            }
        }
    }
}
