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
        success {
            echo 'Build thành công!'
        }
        failure {
            echo 'Build thất bại!'
        }
    }
}
