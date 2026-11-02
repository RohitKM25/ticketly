pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/RohitKM25/ticketly', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Dockerize & Push') {
            steps {
                script {
                    def appVersion = sh(
                        script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", 
                        returnStdout: true
                    ).trim()
                    
                    def app = docker.build("rohitkm25/ticketly:${appVersion}")
                    app.tag("latest")
                    
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-credentials') {
                        app.push("${appVersion}")
                        app.push("latest")
                    }
                }
            }
        }
        
        stage('Deploy to AKS') {
            steps {
                script {
                  def appVersion = sh(
                            script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", 
                            returnStdout: true
                        ).trim()

                  sh "sed -i 's|image:.*|image: rohitkm25/ticketly:${appVersion}|g' k8s/deployment.yaml"
                  sh 'kubectl apply -f k8s/ --validate=false'
                }
            }
        }
    }
}
