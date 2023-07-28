pipeline {
  agent any
  stages {
    stage('test') {
      steps {
        sh './mvnw test'
      }
    }

    stage('build') {
      steps {
        sh './mvnw clean install'
      }
    }

  }
}