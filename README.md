# 🚀 Générateur de Code Spring Boot

Ce projet est un **générateur de code Java** conçu pour accélérer le développement d'applications Spring Boot. Il permet de créer automatiquement la structure de base (entités, repositories, services, contrôleurs) d'un module métier à partir d'un simple fichier de configuration YAML.

## ✨ Fonctionnalités

* **Définition par YAML :** Décrivez vos entités, leurs champs et leurs relations (OneToMany, ManyToOne) dans un fichier YAML intuitif. Ce fichier sert de "plan" pour votre backend, rendant la modélisation des données simple et rapide.

* **Génération automatisée :** Génère les classes Java correspondantes (modèles JPA, interfaces de repository Spring Data, services de base, contrôleurs REST). Fini le codage répétitif, le générateur s'en charge pour vous.

* **Architecture en couches :** Produit un code structuré en modules (ex: `ecommerce`, `elearning`) respectant les bonnes pratiques de Spring Boot (modèle, persistance, logique métier, API). Cela garantit un code propre et facile à maintenir.

* **Relations bidirectionnelles :** Gère la génération des annotations JPA pour les relations complexes (`@OneToMany`, `@ManyToOne`, `mappedBy`, `joinColumn`, `cascade`). Le générateur s'assure que vos entités sont correctement liées et gérées par Hibernate.

* **Gestion des ID :** Les entités générées héritent d'une `BaseEntity` pour une gestion automatique des IDs. Vous n'avez pas à vous soucier de la configuration de la clé primaire pour chaque nouvelle entité.

---

## 🎯 Utilité et Valeur Ajoutée du Projet

Ce générateur offre une **facilité sans précédent** pour le développement de la couche backend de vos applications. Imaginez pouvoir générer une API REST complète, prête à l'emploi, en quelques secondes ! C'est exactement ce que ce projet vous permet de faire.

### ⚡ La Vitesse de Développement Accélérée

Traditionnellement, la mise en place d'un backend pour une nouvelle entité métier (comme `Produit` ou `Commande`) implique des tâches répétitives :
1.  Créer la classe de l'entité (`Product.java`).
2.  Définir ses champs et ses relations (`name`, `stock`, `orders`).
3.  Créer l'interface de repository pour l'accès aux données (`ProductRepository.java`).
4.  Créer la classe de service avec les opérations CRUD de base (`ProductService.java`).
5.  Créer le contrôleur REST pour exposer les endpoints (`ProductController.java`).

Ce processus, bien que simple, devient fastidieux et source d'erreurs lorsqu'il est répété pour des dizaines d'entités. **Notre générateur automatise 100% de ces tâches de base.** Vous définissez une fois votre modèle dans un fichier YAML clair et concis, et le générateur s'occupe du reste. Cela vous permet de **gagner un temps précieux** et d'accélérer drastiquement votre cycle de développement.

### 🏗️ Cohérence et Robustesse de l'Architecture

En utilisant des templates standardisés, le code généré est toujours **cohérent** et suit les meilleures pratiques de Spring Boot. Fini les différences de style ou d'implémentation entre les développeurs ou les modules. Chaque entité aura une structure identique, facilitant la lecture, la maintenance et l'évolution du code. Cela réduit considérablement les erreurs humaines et garantit une base de code **robuste** dès le départ, minimisant les bugs liés à des incohérences architecturales.

### 🔄 Flexibilité et Adaptabilité

Le cœur de ce projet est sa **flexibilité**. Vous pouvez :
* **Changer de domaine métier :** Passez d'un projet e-commerce à un projet e-learning en changeant simplement le fichier YAML d'entrée. L'outil s'adapte à vos besoins.
* **Évoluer facilement :** Ajoutez de nouvelles entités ou modifiez des entités existantes dans le YAML, et régénérez le code en un clin d'œil. La base de code s'adapte à vos évolutions sans effort manuel.
* **Personnaliser :** Les templates peuvent être ajustés pour répondre à des besoins spécifiques sans modifier la logique du générateur principal. Vous avez le contrôle total sur le code final.

### 🖱️ La Magie du "Single-Click Backend"

L'utilité principale pour l'utilisateur est la capacité de générer une **couche backend complète et fonctionnelle en une seule commande**. Après avoir défini votre modèle dans un fichier YAML, il suffit d'exécuter :

```bash
java -jar target/code-generator-1.0-SNAPSHOT-jar-with-dependencies.jar \
     src/main/resources/config/votre_modele.yaml \
     /chemin/vers/votre/projet-spring-boot/src/main/java
```

Et voilà ! Votre backend est prêt. Cela vous permet de vous concentrer sur la **logique métier complexe** et les fonctionnalités uniques de votre application, plutôt que sur le code "boilerplate" répétitif. C'est un **gain de temps et d'efficacité considérable** pour tout projet Spring Boot.

---

## 🛠️ Technologies Utilisées

* **Java 17+**
* **Maven** pour la gestion de projet et la compilation.
* **SnakeYAML** pour parser les fichiers de configuration YAML.
* **Handlebars.java** comme moteur de template pour générer le code source.

---

## 📁 Structure du Projet

Le projet est divisé en deux parties : le générateur lui-même et les templates qu'il utilise.

```
code-generator/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── CodeGenerator.java     # Logique principale du générateur
        └── resources/
            ├── templates/                   # Contient tous les templates Handlebars (.hbs)
            │   ├── ControllerTemplate.hbs
            │   ├── EntityTemplate.hbs
            │   ├── RepositoryTemplate.hbs
            │   └── ServiceTemplate.hbs
            └── config/                      # Fichiers de configuration YAML
                ├── ecommerce.yaml
                └── elearning.yaml
```

---

## 🚀 Démarrage Rapide

Suivez ces étapes pour configurer et utiliser le générateur.

### 1. Cloner le dépôt (ou créer le projet)

Si vous n'avez pas encore le projet, clonez-le ou créez une structure Maven similaire.

### 2. Configurer le `pom.xml` du générateur

Assurez-vous que votre `pom.xml` inclut les dépendances nécessaires et le `maven-assembly-plugin` pour créer un JAR exécutable.

```xml
<project>
    <!-- ... -->
    <dependencies>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>com.github.jknack</groupId>
            <artifactId>handlebars</artifactId>
            <version>4.3.1</version>
        </dependency>
        <!-- ... autres dépendances ... -->
    </dependencies>

    <build>
        <plugins>
            <!-- ... plugin de compilation ... -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.6.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.example.CodeGenerator</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. Compiler le générateur

Ouvrez votre terminal à la racine du projet `code-generator` et exécutez la commande Maven :

```bash
mvn clean install
```

Cela créera un fichier JAR exécutable (`code-generator-1.0-SNAPSHOT-jar-with-dependencies.jar`) dans le dossier `target/`.

### 4. Préparer l'application Spring Boot cible

Créez une nouvelle application Spring Boot (via Spring Initializr ou manuellement) avec les dépendances suivantes : `Spring Web`, `Spring Data JPA`, et un pilote de base de données (ex: `H2 Database` pour le développement).

Assurez-vous que les classes de base (`BaseEntity.java`, `BaseRepository.java`, `BaseService.java`) sont présentes dans votre projet Spring Boot sous `src/main/java/com/example/core/`.

### 5. Exécuter le générateur de code

Exécutez le JAR du générateur en spécifiant le chemin vers votre fichier YAML de configuration et le répertoire de sortie de votre application Spring Boot.

```bash
java -jar target/code-generator-1.0-SNAPSHOT-jar-with-dependencies.jar \
     src/main/resources/config/ecommerce.yaml \
     /chemin/vers/votre/projet-spring-boot/src/main/java
```

Remplacez `ecommerce.yaml` par `elearning.yaml` si vous souhaitez générer le module e-learning.

## 6. Tester l'API générée (avec Postman ou curl)

Après avoir généré le code et démarré votre application Spring Boot (`mvn spring-boot:run`), vous pouvez tester les endpoints.

### Exemple de création d'un Course (E-learning)

Pour créer un `Course` avec un `Instructor` et des `Lessons` associés en une seule requête :

* **Méthode:** `POST`

* **URL:** `http://localhost:8080/api/courses`

* **Headers:** `Content-Type: application/json`

* **Body (raw, JSON):**

    ```json
    {
      "title": "ReactJS Fundamentals",
      "description": "Apprenez les bases de la création d'interfaces utilisateur avec React et JSX.",
      "instructor": {
        "firstName": "Jane",
        "lastName": "Doe",
        "email": "jane.doe@example.com"
      },
      "lessons": [
        {
          "title": "Mise en place de l'environnement",
          "content": "Installation de Node.js, npm, et de l'outil create-react-app."
        },
        {
          "title": "Composants et JSX",
          "content": "Découvrez les bases des composants fonctionnels et de l'utilisation du JSX."
        }
      ]
    }
    ```

### Exemple de création d'un Produit avec une Commande (E-commerce)

Pour créer un `Product` avec une `Order`, un `OrderItem` et un `User` associés en une seule requête :

* **Méthode:** `POST`

* **URL:** `http://localhost:8080/api/products`

* **Headers:** `Content-Type: application/json`

* **Body (raw, JSON):**

    ```json
    {
      "name": "Ordinateur portable",
      "stock": 50,
      "price": 1200.50,
      "orders": [
        {
          "orderDate": "2025-08-04",
          "status": "PENDING",
          "user": {
            "username": "user123",
            "email": "user123@example.com"
          },
          "items": [
            {
              "quantity": 1,
              "price": 1200.50
            }
          ]
        }
      ]
    }
    ```

## 💡 Améliorations Possibles

* **Génération de DTOs :** Introduire la génération de Data Transfer Objects pour découpler les entités de l'API REST et gérer plus finement la sérialisation/désérialisation JSON.

* **Validation :** Ajouter des annotations de validation (ex: `@NotNull`, `@Size`) aux champs des entités et DTOs via le fichier YAML.

* **Tests Unitaires/d'Intégration :** Générer des squelettes de tests pour les services et contrôleurs.

* **Personnalisation des Templates :** Permettre des options plus granulaires dans le YAML pour personnaliser les templates (ex: ajouter des méthodes spécifiques aux services).

* **Génération de Frontend :** Étendre le générateur pour créer des interfaces utilisateur (ex: React, Vue.js) basées sur les mêmes définitions YAML.

* **Gestion des Enums :** Ajouter la prise en charge des types `Enum` dans les champs des entités.

* **Optimistic Locking :** Intégrer automatiquement le champ `@Version` pour la gestion du verrouillage optimiste dans les entit
