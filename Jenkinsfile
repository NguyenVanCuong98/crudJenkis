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

        stage('Start MySQL') {
            steps {
                echo 'Khởi động MySQL 8.0 bằng Docker'
                sh '''
                    docker run -d --rm --name mysql-student \
                        -e MYSQL_ROOT_PASSWORD=123456 \
                        -e MYSQL_DATABASE=studentdb \
                        -p 3306:3306 \
                        mysql:8.0
                    sleep 20
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
            sh 'docker stop mysql-test || true'
        }
        success {
            echo 'Build thành công!'
        }
        failure {
            echo 'Build thất bại!'
        }
    }
}
