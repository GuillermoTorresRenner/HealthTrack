#!/bin/bash

# Script para gestionar la aplicación Docker HealthTrack

set -e

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuración
IMAGE_NAME="healthtrack"
CONTAINER_NAME="healthtrack-app"
PORT="8080"

function print_usage() {
    echo "Uso: $0 [COMANDO]"
    echo ""
    echo "Comandos disponibles:"
    echo "  build     - Construir la imagen Docker"
    echo "  run       - Ejecutar el contenedor"
    echo "  stop      - Detener el contenedor"
    echo "  restart   - Reiniciar el contenedor"
    echo "  logs      - Mostrar logs del contenedor"
    echo "  clean     - Limpiar imágenes y contenedores no utilizados"
    echo "  compose   - Usar docker-compose (up/down/logs)"
    echo "  test      - Ejecutar tests antes de construir"
    echo "  help      - Mostrar esta ayuda"
}

function build_image() {
    echo -e "${GREEN}🔨 Construyendo imagen Docker...${NC}"
    docker build -t $IMAGE_NAME .
    echo -e "${GREEN}✅ Imagen construida exitosamente${NC}"
}

function run_container() {
    echo -e "${GREEN}🚀 Ejecutando contenedor...${NC}"
    
    # Detener contenedor existente si está corriendo
    if docker ps -q -f name=$CONTAINER_NAME | grep -q .; then
        echo -e "${YELLOW}⚠️  Deteniendo contenedor existente...${NC}"
        docker stop $CONTAINER_NAME
        docker rm $CONTAINER_NAME
    fi
    
    docker run -d \
        --name $CONTAINER_NAME \
        -p $PORT:8080 \
        -e SPRING_PROFILES_ACTIVE=docker \
        $IMAGE_NAME
    
    echo -e "${GREEN}✅ Contenedor ejecutándose en http://localhost:$PORT${NC}"
}

function stop_container() {
    echo -e "${YELLOW}🛑 Deteniendo contenedor...${NC}"
    docker stop $CONTAINER_NAME || true
    docker rm $CONTAINER_NAME || true
    echo -e "${GREEN}✅ Contenedor detenido${NC}"
}

function restart_container() {
    stop_container
    run_container
}

function show_logs() {
    echo -e "${GREEN}📋 Mostrando logs del contenedor...${NC}"
    docker logs -f $CONTAINER_NAME
}

function clean_docker() {
    echo -e "${YELLOW}🧹 Limpiando imágenes y contenedores no utilizados...${NC}"
    docker system prune -f
    docker image prune -f
    echo -e "${GREEN}✅ Limpieza completada${NC}"
}

function docker_compose_cmd() {
    case $2 in
        "up")
            echo -e "${GREEN}🚀 Iniciando servicios con docker-compose...${NC}"
            docker-compose up -d
            ;;
        "down")
            echo -e "${YELLOW}🛑 Deteniendo servicios...${NC}"
            docker-compose down
            ;;
        "logs")
            echo -e "${GREEN}📋 Mostrando logs...${NC}"
            docker-compose logs -f
            ;;
        *)
            echo -e "${RED}❌ Comando de compose no válido. Use: up, down, logs${NC}"
            exit 1
            ;;
    esac
}

function run_tests() {
    echo -e "${GREEN}🧪 Ejecutando tests...${NC}"
    ./mvnw test
    echo -e "${GREEN}✅ Tests completados${NC}"
}

# Verificar que Docker está instalado
if ! command -v docker &> /dev/null; then
    echo -e "${RED}❌ Docker no está instalado${NC}"
    exit 1
fi

# Manejar argumentos
case $1 in
    "build")
        build_image
        ;;
    "run")
        run_container
        ;;
    "stop")
        stop_container
        ;;
    "restart")
        restart_container
        ;;
    "logs")
        show_logs
        ;;
    "clean")
        clean_docker
        ;;
    "compose")
        docker_compose_cmd $@
        ;;
    "test")
        run_tests
        ;;
    "help"|"--help"|"-h")
        print_usage
        ;;
    "")
        print_usage
        ;;
    *)
        echo -e "${RED}❌ Comando no reconocido: $1${NC}"
        print_usage
        exit 1
        ;;
esac
