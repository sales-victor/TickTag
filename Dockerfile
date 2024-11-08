# Use uma imagem base do Java
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR do seu projeto para a imagem
COPY target/TickTag.jar app.jar

# Comando para executar o aplicativo
CMD ["java", "-jar", "app.jar"]
