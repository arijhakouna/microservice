# Guide de Configuration - Application Micro-services

## Prérequis

### 1. Installation des outils
```bash
# Java 17+
sudo apt update
sudo apt install openjdk-17-jdk

# Maven 3.8+
sudo apt install maven

# Docker et Docker Compose
sudo apt install docker.io docker-compose
sudo usermod -aG docker $USER
```

### 2. Vérification des installations
```bash
java -version
mvn -version
docker --version
docker-compose --version
```

## Configuration du Repository GitHub

### 1. Créer un repository privé
1. Allez sur GitHub.com
2. Créez un nouveau repository privé nommé `microservice-config`
3. Clonez le repository localement

### 2. Ajouter les fichiers de configuration
```bash
git clone https://github.com/your-username/microservice-config.git
cd microservice-config

# Copier les fichiers de configuration
cp -r ../microservice_subject/config-server/src/main/resources/config/* .

# Commiter et pousser
git add .
git commit -m "Add microservice configurations"
git push origin main
```

### 3. Créer un token GitHub
1. Allez dans Settings > Developer settings > Personal access tokens
2. Créez un nouveau token avec les permissions `repo`
3. Copiez le token

## Configuration de l'application

### 1. Mettre à jour docker-compose.yml
Remplacez les valeurs suivantes dans `docker-compose.yml` :
```yaml
SPRING_CLOUD_CONFIG_SERVER_GIT_URI: https://github.com/VOTRE_USERNAME/microservice-config.git
SPRING_CLOUD_CONFIG_SERVER_GIT_USERNAME: VOTRE_USERNAME
SPRING_CLOUD_CONFIG_SERVER_GIT_PASSWORD: VOTRE_TOKEN
```

### 2. Compiler les services
```bash
# Rendre le script exécutable
chmod +x build.sh

# Compiler tous les services
./build.sh
```

## Démarrage de l'application

### 1. Démarrer tous les services
```bash
docker-compose up -d
```

### 2. Vérifier le statut
```bash
docker-compose ps
```

### 3. Vérifier les logs
```bash
# Logs de tous les services
docker-compose logs -f

# Logs d'un service spécifique
docker-compose logs -f professeur-service
```

## Accès aux services

### URLs d'accès
- **Eureka Dashboard** : http://localhost:8761
- **API Gateway** : http://localhost:8081
- **Keycloak Admin** : http://localhost:8080 (admin/admin)
- **Professeur Service** : http://localhost:8082
- **Classe Service** : http://localhost:8083

### Configuration Keycloak
1. Accédez à http://localhost:8080
2. Connectez-vous avec admin/admin
3. Créez un nouveau realm
4. Créez un client pour l'application
5. Configurez les utilisateurs

## Tests des API

### Service Professeur
```bash
# Créer un professeur
curl -X POST http://localhost:8081/api/professeurs \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@example.com",
    "specialite": "Mathématiques"
  }'

# Lister les professeurs
curl http://localhost:8081/api/professeurs

# Récupérer un professeur
curl http://localhost:8081/api/professeurs/{id}
```

### Service Classe
```bash
# Créer une classe
curl -X POST http://localhost:8081/api/classes \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "6ème A",
    "niveau": "6ème",
    "nombreEtudiants": 25,
    "professeurId": "professeur_id_ici"
  }'

# Lister les classes
curl http://localhost:8081/api/classes

# Récupérer le professeur d'une classe (OpenFeign)
curl http://localhost:8081/api/classes/{id}/professeur
```

## Monitoring

### Eureka
- Dashboard : http://localhost:8761
- Vérifiez que tous les services sont enregistrés

### Actuator
- Health checks : http://localhost:8082/actuator/health
- Metrics : http://localhost:8082/actuator/metrics

### Kafka
```bash
# Vérifier les topics
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092

# Consulter les messages
docker exec -it kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic professeur-events --from-beginning
```

## Dépannage

### Problèmes courants

1. **Services ne démarrent pas**
   ```bash
   docker-compose logs [service-name]
   ```

2. **Problème de connexion à la base de données**
   ```bash
   docker-compose restart mongodb mysql
   ```

3. **Problème de configuration**
   - Vérifiez les variables d'environnement dans docker-compose.yml
   - Vérifiez que le repository GitHub est accessible

4. **Problème de réseau**
   ```bash
   docker network ls
   docker network inspect microservice_subject_microservices-network
   ```

### Arrêt et nettoyage
```bash
# Arrêter les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v

# Nettoyer complètement
docker system prune -f
```

## Développement

### Ajouter un nouveau service
1. Créer le dossier du service
2. Ajouter le pom.xml
3. Créer les classes Java
4. Ajouter la configuration dans config-server
5. Mettre à jour docker-compose.yml
6. Recompiler avec `./build.sh`

### Modifier la configuration
1. Modifier les fichiers dans `config-server/src/main/resources/config/`
2. Pousser les changements vers GitHub
3. Redémarrer le config-server : `docker-compose restart config-server` 