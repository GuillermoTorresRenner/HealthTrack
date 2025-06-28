# Imagen base con JDK para compilar y ejecutar
FROM eclipse-temurin:17-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer puerto
EXPOSE 8080

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=docker

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/healthtrack-0.0.1-SNAPSHOT.jar"]