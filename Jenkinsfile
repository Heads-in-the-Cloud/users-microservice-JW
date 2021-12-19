pipeline {
    // agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
    agent any
    stages {
        stage('Docker build') {
            steps {
                echo 'Building image:'
                sh 'sudo docker build -t userimage .'
            }
        }
        stage('Tag Image'){
            steps{
                echo 'Tagging image: '
                sh 'sudo docker tag userimage jswen19109814/user:user'
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image: '
                sh 'sudo docker push jswen19109814/user:user'
            }
        }
    }
}