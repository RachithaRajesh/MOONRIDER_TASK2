pipeline {
  agent any
  environment {
    DOCKER_IMAGE = "rachitharajesh/product-catalog:${env.BUILD_NUMBER}"
    KUBECONFIG_CREDENTIAL_ID = 'gke-kubeconfig'
    // Fix Windows PATH issue
    PATH = "${env.PATH};C:\\Windows\\System32;C:\\Windows"
  }
  stages {
    stage('Build with Maven') {
      steps {
        bat 'mvn clean package -DskipTests'
      }
    }
    stage('Build Docker Image') {
      steps {
        bat 'docker build -t %DOCKER_IMAGE% .'
      }
    }
    stage('Push Docker Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          bat '''
            echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
            docker push %DOCKER_IMAGE%
          '''
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        withCredentials([file(credentialsId: env.KUBECONFIG_CREDENTIAL_ID, variable: 'KUBECONFIG_FILE')]) {
          bat '''
            set KUBECONFIG=%KUBECONFIG_FILE%
            kubectl set image deployment/product-catalog-v1 product-catalog=%DOCKER_IMAGE% -n v1-namespace
            kubectl apply -f kubernetes/v1.0/
          '''
        }
      }
    }
    stage('Integration Tests') {
      steps {
        bat '''
          kubectl rollout status deployment/product-catalog-v1 -n v1-namespace
          curl -f http://34.49.197.94/v1 || exit 1
        '''
      }
    }
  }
}
