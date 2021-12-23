pipeline {
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
                script{
                    userimage = docker.build registry + ":user"
                }  
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image:'
                script{
                    docker.withRegistry('', 'jwdockerhub'){
                        userimage.push()
                    }
                }
            }
        }
        stage('Call docker-compose'){
            steps{
                echo 'Calling docker-compose job'
                build job: 'JW-Docker-Compose'
            }
        }
    }
}