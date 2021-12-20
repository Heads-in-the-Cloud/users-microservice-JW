pipeline {
    // agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
    environment {
        registry = "jswen19109814/user"
        dockerImage = ''
    }
        agent any
    tools { 
        maven 'mvn' 
        jdk 'java' 
    }
    stages {
        stage('Docker build') {
            steps {
                echo 'Maven packaging:'
                sh 'mvn package'
                echo 'Building image:'
                // sh 'docker build -t userimage .'
                script{
                    userimage = docker.build registry + ":user"
                }  
            }
        }
        // stage('Tag Image'){
        //     steps{
        //         echo 'Tagging image: '
        //         sh 'docker tag userimage jswen19109814/user:user'
        //     }
        // }
        stage('Push Image'){
            docker.withRegistry('https://hub.docker.com/repository/docker/jswen19109814/user', 'jwdockerhub'){
                dockerimage.push()
            }
        }
    }
}