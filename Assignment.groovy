pipeline{
    agent any
    environment{
        PATH = "${PATH}:${tool name: 'maven3.x', type: 'maven'}/bin"
     }
     parameters {
        string defaultValue: 'https://github.com/SangramVicky/2025devopswebapp1', description: 'Your url is ', name: 'url'
        string defaultValue: 'master', description: 'choose your parameter', name: 'BranchName'
                }
    stages{
        stage('git') {
            steps{
                git url: "${params['url']}" ,
                 branch: "${params['BranchName']}" ,
                 credentialsId: 'GIT' 
            }
        }
        stage('maven') {
            steps{
                sh "mvn clean package"
            }
        }
          stage('Deploy') {
            steps{
                sshagent(['Tom']) {
                  //Shutdown tomcat server
                     sh "ssh -o StrictHostKeyChecking=no ec2-user@172.31.32.5 /opt/tomcat/bin/shutdown.sh"
    
                  //copy war file to remote tomact server
                     sh "scp target/2025devopswebapp1.war ec2-user@172.31.32.5:/opt/tomcat/webapps/"
    
                  //start tomcat server
                     sh "ssh ec2-user@172.31.32.5 /opt/tomcat/bin/startup.sh"
                }
            }
        }
    }
                   post {
                   failure {
                   mail body: '''hi its from jenkins
                                your deployment Failed.

                                team-Devops
                                status - ok''', subject: 'deployment failed', to: 'ashutoshpattanaik78@gmail.com'
                           }
                   success {
                   mail body: '''hi its from jenkins
                   your deployment successful.

                   team-Devops
                   status - ok''', subject: 'deploy succeded', to: 'ashutoshpattanaik78@gmail.com'
                     }
           }
}