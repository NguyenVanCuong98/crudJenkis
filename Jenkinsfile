pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven'
    }

    environment {
        JAVA_HOME = "${tool 'jdk17'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"

        // Biến kết nối DB
        DB_HOST = '0.0.0.0'         // ví dụ: localhost, 127.0.0.1, mysql-server
        DB_PORT = '3306'
        DB_NAME = 'studentdb'
        DB_USER = 'root'
        DB_PASSWORD = '123456' // lưu trong Jenkins Credentials
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/NguyenVanCuong98/crudJenkis'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Database Connection Test') {
            steps {
                script {
                    sh '''
                        echo "Checking MySQL connection..."
                        mysql -h $DB_HOST -P $DB_PORT -u $DB_USER -p$DB_PASSWORD -e "SHOW DATABASES;"
                    '''
                }
            }
        }

        stage('Package & Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy (optional)') {
            when {
                branch 'main'
            }
            steps {
                echo "Deploying to production server..."
            }
        }
    }

    post {
        success {
            echo '🎉 Build, DB check và test thành công!'
        }
        failure {
            echo '❌ Build hoặc DB check thất bại. Kiểm tra log.'
        }
    }
}
