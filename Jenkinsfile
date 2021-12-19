pipeline {
    // agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
    agent any
    // tools { 
    //     maven 'Maven 3.3.9' 
    //     jdk 'jdk8' 
    // }
    stages {
        stage('Docker build') {
            steps {
                echo 'Building image:'
                sh 'docker build -t userimage .'
            }
        }
        stage('Tag Image'){
            steps{
                echo 'Tagging image: '
                sh 'docker tag userimage jswen19109814/user:user'
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image: '
                sh 'docker push jswen19109814/user:user'
            }
        }
    }
}