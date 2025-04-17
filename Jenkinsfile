pipeline {
    agent any
    tools {
        maven 'Maven3'
    }
    environment {
        SONARQUBE_SERVER = 'SonarQubeServer'  // The name of the SonarQube server configured in Jenkins
        SONAR_TOKEN = 'squ_4a02fbf0dea224ae6b7d27f986c481cba858fbb8' // Store the token securely
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKERHUB_USERNAME = 'sakuheinonen'
        DOCKERHUB_REPO = 'week-5-inclass'
        DOCKER_IMAGE_TAG_ARM64 = 'arm64'
        DOCKER_IMAGE_TAG_AMD64 = 'amd64'
        FULL_IMAGE_NAME_AMD64 = "${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG_AMD64}"
        FULL_IMAGE_NAME_ARM64 = "${DOCKERHUB_USERNAME}/${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG_ARM64}"

    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'week-5-inclass', url: 'https://github.com/SakuJuuH/software-engineering-project-2.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh """
                        sonar-scanner \
                        -Dsonar.projectKey=devops-demo \
                        -Dsonar.sources=src \
                        -Dsonar.projectName=DevOps-Demo \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.token=${env.SONAR_TOKEN} \
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }
        stage('Build Docker images') {
            parallel {
                stage('Build ARM64 Docker Image') {
                    steps {
                        // Build Docker image
                        script {
                            sh "docker build --platform linux/arm64 -t ${FULL_IMAGE_NAME_ARM64} ."
                        }
                    }
                }

                stage('Build AMD64 Docker Image') {
                    steps {
                        // Build Docker image
                        script {
                            sh "docker build --platform linux/amd64 -t ${FULL_IMAGE_NAME_AMD64} ."
                        }
                    }
                }
            }
        }
        
        // --- Docker Push Stages (Sequential) ---
        stage('Push Docker Images') {
            // Run push steps sequentially to avoid credential conflicts
            steps {
                // Push AMD64
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        echo "Pushing AMD64 image: ${env.FULL_IMAGE_NAME_AMD64}"
                        docker.image(env.FULL_IMAGE_NAME_AMD64).push()
                    }
                }
                // Push ARM64
                script {
                     docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        echo "Pushing ARM64 image: ${env.FULL_IMAGE_NAME_ARM64}"
                        docker.image(env.FULL_IMAGE_NAME_ARM64).push()
                    }
                }
            }
        }
    }
}
