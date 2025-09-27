pipeline{
    agent any
    environment{
        PATH = "${PATH}:${tool name: 'maven3.x', type: 'maven'}/bin"
     }
     stages{
        stage('maven'){
            steps{
                sh "mvn clean package"
            }
        }
        stage('deploy to dev'){
            when {
                branch 'devops'
            }
            steps{
                echo "deployed to dev"
            }
        }
            stage('deploy to prod'){
            when {
                branch 'master'
            }
            steps{
                echo "deployed to production"
            }
        }
     }
  }
