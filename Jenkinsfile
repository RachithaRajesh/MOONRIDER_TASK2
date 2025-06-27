pipeline {
  agent any
  environment {
    DOCKER_IMAGE = "rachitharajesh/product-catalog:${env.BUILD_NUMBER}"
    KUBECONFIG_CREDENTIAL_ID = 'gke-kubeconfig'
  }
  stages {
    // DELETE THIS STAGE - IT'S CAUSING THE ERROR
    // stage('Checkout') {
    //   steps {
    //     git 'https://github.com/RachithaRajesh/MOONRIDER_TASK2.git'
    //   }
    // }
    stage('Build with Maven') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $DOCKER_IMAGE .'
      }
    }
    stage('Push Docker Image') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
          sh 'docker push $DOCKER_IMAGE'
        }
      }
    }
    stage('Deploy to Kubernetes') {
      steps {
        withCredentials([file(credentialsId: env.KUBECONFIG_CREDENTIAL_ID, variable: 'KUBECONFIG_FILE')]) {
          sh 'export KUBECONFIG=$KUBECONFIG_FILE'
          sh 'kubectl set image deployment/product-catalog-v1 product-catalog=$DOCKER_IMAGE -n v1-namespace'
          sh 'kubectl apply -f kubernetes/v1.0/'
        }
      }
    }
    stage('Integration Tests') {
      steps {
        sh 'kubectl rollout status deployment/product-catalog-v1 -n v1-namespace'
        sh 'curl -f http://34.49.197.94/v1 || exit 1'
      }
    }
  }
}
