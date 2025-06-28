#!/bin/bash

echo "🚀 Building Microservices Application..."

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to build a service
build_service() {
    local service_name=$1
    local service_path=$2
    
    echo -e "${YELLOW}📦 Building $service_name...${NC}"
    cd "$service_path"
    
    if mvn clean package -DskipTests; then
        echo -e "${GREEN}✅ $service_name built successfully!${NC}"
    else
        echo -e "${RED}❌ Failed to build $service_name${NC}"
        exit 1
    fi
    
    cd - > /dev/null
}

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}❌ Maven is not installed. Please install Maven first.${NC}"
    exit 1
fi

# Build all services
echo -e "${YELLOW}🔨 Starting build process...${NC}"

# Build Eureka Server
build_service "Eureka Server" "eureka-server"

# Build Config Server
build_service "Config Server" "config-server"

# Build Professeur Service
build_service "Professeur Service" "professeur-service"

# Build Classe Service
build_service "Classe Service" "classe-service"

# Build API Gateway
build_service "API Gateway" "api-gateway"

echo -e "${GREEN}🎉 All services built successfully!${NC}"
echo -e "${YELLOW}📋 Next steps:${NC}"
echo -e "1. Create a GitHub repository for configurations"
echo -e "2. Update docker-compose.yml with your GitHub credentials"
echo -e "3. Run: docker-compose up -d"
echo -e "4. Access services at:"
echo -e "   - Eureka: http://localhost:8761"
echo -e "   - API Gateway: http://localhost:8081"
echo -e "   - Keycloak: http://localhost:8080" 