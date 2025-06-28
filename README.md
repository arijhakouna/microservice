# Application Micro-services - Professeur & Classe

## Description
Application micro-services développée avec Spring Boot, implémentant une architecture distribuée avec deux micro-services principaux :
- **Service Professeur** (MongoDB)
- **Service Classe** (MySQL)

## Architecture

### Services
1. **Eureka Server** (Port 8761) - Service Discovery
2. **Config Server** (Port 8888) - Configuration centralisée
3. **API Gateway** (Port 8081) - Gateway avec routage statique et dynamique
4. **Professeur Service** (Port 8082) - Gestion des professeurs
5. **Classe Service** (Port 8083) - Gestion des classes
6. **Keycloak** (Port 8080) - Authentification et autorisation

### Communication
- **Synchrone** : OpenFeign entre les services
- **Asynchrone** : Kafka pour les événements

### Bases de données
- **MongoDB** : Service Professeur
- **MySQL** : Service Classe
- **PostgreSQL** : Keycloak

## Prérequis
- Docker et Docker Compose
- Java 17+
- Maven 3.8+

## Démarrage rapide

### 1. Configuration du repository de configuration
Avant de démarrer, vous devez :
1. Créer un repository GitHub privé pour les configurations
2. Modifier les variables dans `docker-compose.yml` :
   - `SPRING_CLOUD_CONFIG_SERVER_GIT_URI`
   - `SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME`
   - `SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD`

### 2. Lancement de l'application
```bash
# Cloner le repository
git clone <your-repo-url>
cd microservice_subject

# Démarrer tous les services
docker-compose up -d

# Vérifier que tous les services sont démarrés
docker-compose ps
```

### 3. Accès aux services
- **Eureka Dashboard** : http://localhost:8761
- **API Gateway** : http://localhost:8081
- **Keycloak Admin** : http://localhost:8080 (admin/admin)
- **Professeur Service** : http://localhost:8082
- **Classe Service** : http://localhost:8083

## API Endpoints

### Service Professeur (via Gateway)
- `GET /api/professeurs` - Liste des professeurs
- `GET /api/professeurs/{id}` - Professeur par ID
- `POST /api/professeurs` - Créer un professeur
- `PUT /api/professeurs/{id}` - Modifier un professeur
- `DELETE /api/professeurs/{id}` - Supprimer un professeur

### Service Classe (via Gateway)
- `GET /api/classes` - Liste des classes
- `GET /api/classes/{id}` - Classe par ID
- `POST /api/classes` - Créer une classe
- `PUT /api/classes/{id}` - Modifier une classe
- `DELETE /api/classes/{id}` - Supprimer une classe
- `GET /api/classes/{id}/professeur` - Professeur d'une classe (OpenFeign)

## Structure du projet
```
├── docker-compose.yml
├── eureka-server/
├── config-server/
├── api-gateway/
├── professeur-service/
├── classe-service/
└── README.md
```

## Configuration des profils
- **dev** : Configuration de développement
- **prod** : Configuration de production
- **docker** : Configuration pour Docker

## Sécurité
L'application est sécurisée avec Keycloak. Tous les endpoints nécessitent une authentification JWT.

## Monitoring
- **Eureka** : Service discovery et monitoring
- **Actuator** : Endpoints de monitoring dans chaque service

## Arrêt de l'application
```bash
docker-compose down
```

## Nettoyage complet
```bash
docker-compose down -v
docker system prune -f
``` 