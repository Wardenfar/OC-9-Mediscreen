# Mediscreen (Micro-services Sprint Boot)

Application en micro-services pour stocker des patients et des notes
de médecins à propos de ces derniers.

Une fois les données saisies : vous pouvez lancer une analyse automatique
qui va calculer un niveau de risque de développement du diabète pour chaque patient.

# Pré-requis

Java (JDK) : version 17
Gradle : version 7.2

# Lancement de l'application

Cette application est déployée grâce à Docker : donc il vous faudra l'installer
pour pouvoir utiliser cette application.

```bash
docker-compose build
docker-compose up -d

open http://localhost:8081
```

# Lancement des tests

TODO