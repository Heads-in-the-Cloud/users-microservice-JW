pipeline {
    agent any
    environment {
        registry = "902316339693.dkr.ecr.us-east-2.amazonaws.com/jw-users"
    }
    parameters{
        string(name:'sonarqubekey', defaultValue: 'NULL', description: 'sonarqube key')
    }
    tools { 
        maven 'mvn' 
        jdk 'java' 
    }
    
    stages {
        stage('Sonarqube check'){
            steps{
                withSonarQubeEnv('sonarqube'){
                    sh"mvn verify sonar:sonar -Dsonar.projectKey=users-microservice -Dsonar.host.url=http://jenkins.hitec.link:9000 -Dsonar.login=${params.sonarqubekey}"
                }
            }
        }
        stage('Quality gate'){
            steps{
                waitForQualityGate abortPipeline: false
            }
        }
        stage('Docker build') {
            steps {
                echo 'Maven packaging:'
                sh 'mvn package'
                echo 'Building image:'
                script{
                    userimage = docker.build registry + ":userimage"
                }  
            }
        }
        stage('Push Image'){
            steps{
                echo 'Pushing image:'
                sh 'docker context use default'
                script{
                    docker.withRegistry('https://902316339693.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:jw-aws-cred'){
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