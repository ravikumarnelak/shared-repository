// deployMavenApp.groovy
 
def call(Map config) {
    pipeline {
        agent any
 
        environment {
            MAVEN_HOME = tool 'Maven'
            PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        }
 
        stages {
            stage('Checkout') {
                steps {
                    checkout scm
                }
            }
 
            stage('Build') {
                steps {
                    script {
                        def mavenHome = tool 'Maven'
                        def mavenCMD = "${mavenHome}/bin/mvn"
 
                        sh "${mavenCMD} clean install"
                    }
                }
            }
 
            stage('Deploy') {
                steps {
                    script {
                        def mavenHome = tool 'Maven'
                        def mavenCMD = "${mavenHome}/bin/mvn"
                        def settingsFile = "${WORKSPACE}/jenkins_home/workspace/${JOB_NAME}/maven-settings.xml"
 
                        sh "${mavenCMD} deploy -s ${settingsFile}"
                    }
                }
            }
        }
    }
}
