pipeline {
    agent any

    environment {
        MVN_HOME = '/usr/local/maven'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }

        stage('Build') {
            steps {
                echo 'Build ứng dụng với Maven trên Linux agent'
                sh '''
                    echo "Đang chạy mvn clean install"
                    mvn clean install
                '''
            }
        }

        stage('Test') {
            steps {
                echo 'Chạy test'
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Triển khai ứng dụng'
                // Thêm lệnh deploy shell script, ví dụ:
                sh './deploy.sh'
            }
        }
    }

    post {
        success {
            echo 'Build và deploy thành công!'
        }
        failure {
            echo 'Build thất bại, kiểm tra lại.'
        }
    }
}
