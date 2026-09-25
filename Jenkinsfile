pipeline {
    agent any

    environment {
        DOCKERHUB_USER        = 'emnablel'
        DOCKER_IMAGE_BACKEND  = "${DOCKERHUB_USER}/emna-5arctic8-appgestion-backend"
        DOCKER_IMAGE_FRONTEND = "${DOCKERHUB_USER}/emna-5arctic8-appgestion-frontend"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/emnablel/5Arctic8-emna.git'
            }
        }

        stage('Maven Compile') {
            steps {
                dir('backend') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Maven Test') {
            steps {
                dir('backend') {
                    sh 'mvn test -Dtest=CoutCalculatorTest'
                }
            }
        }

        stage('Maven Package') {
            steps {
                dir('backend') {
                    sh 'mvn package -DskipTests'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE_BACKEND:latest ./backend'
                sh 'docker build -t $DOCKER_IMAGE_FRONTEND:latest ./frontend'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push $DOCKER_IMAGE_BACKEND:latest'
                    sh 'docker push $DOCKER_IMAGE_FRONTEND:latest'
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker compose down || true'
                sh 'docker compose up -d --build'
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès !'
        }
        failure {
            echo 'Le pipeline a échoué. Vérifie les logs.'
        }
    }
}
