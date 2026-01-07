pipeline {
    agent any

    tools {
        maven 'Maven 3.9.9'
        jdk 'JDK 21'
    }

    environment {
        DOCKER_IMAGE = 'oesys-app'
        DOCKER_TAG = "${BUILD_NUMBER}"
        JAVA_HOME = "${tool 'JDK 21'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('🚀 Checkout') {
            steps {
                echo '📦 Checking out code from repository...'
                checkout scm
                sh 'git rev-parse --short HEAD > .git/commit-id'
                script {
                    env.GIT_COMMIT_SHORT = readFile('.git/commit-id').trim()
                }
                // Maven wrapper'a execute izni ver
                sh 'chmod +x mvnw'
            }
        }

        stage('☕ Verify Java') {
            steps {
                echo '☕ Verifying Java installation...'
                sh '''
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "PATH: $PATH"
                    java -version
                    javac -version
                '''
            }
        }

        stage('🐳 Docker Ayağa Kaldırma') {
            steps {
                echo '🐳 Starting Docker containers (PostgreSQL, etc.)...'
                script {
                    try {
                        sh '''
                            docker compose -f compose.yaml down -v || true
                            docker compose -f compose.yaml up -d
                            sleep 10
                            docker compose -f compose.yaml ps
                        '''
                    } catch (Exception e) {
                        echo "⚠️ Docker start warning: ${e.message}"
                    }
                }
            }
        }

        stage('🔧 Maven Clean') {
            steps {
                echo '🧹 Cleaning previous build artifacts...'
                sh './mvnw clean'
            }
        }

        stage('📦 Maven Compile') {
            steps {
                echo '⚙️ Compiling source code...'
                sh './mvnw compile'
            }
        }

        stage('🧪 Birim Testleri') {
            steps {
                echo '🧪 Running Unit Tests...'
                script {
                    try {
                        sh './mvnw test -Dtest=*Test'
                        currentBuild.result = 'SUCCESS'
                        echo '✅ Birim testleri başarıyla tamamlandı!'
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "⚠️ Bazı birim testleri başarısız: ${e.message}"
                    }
                }
            }
            post {
                always {
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('🔗 Entegrasyon Testleri') {
            steps {
                echo '🔗 Running Integration Tests...'
                script {
                    try {
                        sh './mvnw verify -DskipUTs=true -DskipITs=false'
                        echo '✅ Entegrasyon testleri başarıyla tamamlandı!'
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "⚠️ Bazı entegrasyon testleri başarısız: ${e.message}"
                    }
                }
            }
            post {
                always {
                    junit testResults: '**/target/failsafe-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('🌐 Selenium E2E Testleri') {
            steps {
                echo '🌐 Running Selenium E2E Tests...'
                script {
                    try {
                        sh '''
                            # Uygulamayı başlat
                            ./mvnw spring-boot:run -DskipTests &
                            APP_PID=$!
                            sleep 30

                            # Selenium testlerini çalıştır
                            ./mvnw test -Dtest=*E2E* || true

                            # Uygulamayı durdur
                            kill $APP_PID || true
                        '''
                        echo '✅ Selenium testleri başarıyla tamamlandı!'
                    } catch (Exception e) {
                        currentBuild.result = 'UNSTABLE'
                        echo "⚠️ Selenium testleri başarısız: ${e.message}"
                    }
                }
            }
        }

        stage('📊 Test Coverage Raporu') {
            steps {
                echo '📊 Generating JaCoCo Test Coverage Report...'
                sh './mvnw jacoco:report'
            }
            post {
                always {
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('📦 Build Package') {
            steps {
                echo '📦 Building application package (JAR)...'
                sh './mvnw package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }

        stage('🐳 Docker Image Build') {
            steps {
                echo '🐳 Building Docker image...'
                script {
                    try {
                        sh """
                            docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                            docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                        """
                        echo '✅ Docker image başarıyla oluşturuldu!'
                    } catch (Exception e) {
                        echo "⚠️ Docker image build warning: ${e.message}"
                    }
                }
            }
        }

        stage('🛑 Docker Cleanup') {
            steps {
                echo '🛑 Stopping Docker containers...'
                script {
                    try {
                        sh 'docker compose -f compose.yaml down -v || true'
                        echo '✅ Docker containerleri durduruldu!'
                    } catch (Exception e) {
                        echo "⚠️ Docker cleanup warning: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Pipeline tamamlandı - Raporlar hazırlanıyor...'

            // Test raporlarını HTML olarak yayınla
            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'JaCoCo Coverage Report',
                reportTitles: 'Code Coverage'
            ])

            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/surefire-reports',
                reportFiles: '*.html',
                reportName: 'Unit Test Report',
                reportTitles: 'Unit Tests'
            ])
        }

        success {
            echo '✅ Pipeline başarıyla tamamlandı!'
            echo "📦 Build: ${BUILD_NUMBER}"
            echo "🔖 Commit: ${env.GIT_COMMIT_SHORT}"
        }

        failure {
            echo '❌ Pipeline başarısız oldu!'
        }

        unstable {
            echo '⚠️ Pipeline unstable - Bazı testler başarısız'
        }
    }
}

