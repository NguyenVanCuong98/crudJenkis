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

        stages {
                stage('Run MySQL container') {
                    steps {
                        sh 'docker run -d --name mysql-dev -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=mydb -p 3306:3306 mysql:8.0'
                    }
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
