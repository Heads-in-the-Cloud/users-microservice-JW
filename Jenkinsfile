pipeline {
    // agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
    agent any
    // tools { 
    //     maven 'Maven 3.3.9' 
    //     jdk 'jdk8' 
    // }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }
        stage('build') {
            steps {
                echo "hello"
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn package' 
            }
        }
    }
}