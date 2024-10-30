# Gestionnaire de Bibliothèque

Une application Java de gestion de bibliothèque avec interface graphique permettant de gérer les livres, les membres et les emprunts.

## Fonctionnalités

- ✨ Ajout de livres
- 👥 Ajout de membres
- 📚 Emprunt de livres
- 🔄 Retour de livres
- 📋 Consultation des livres disponibles
- 👥 Consultation des membres

## Structure du Projet

src/
├── model/
│ ├── Book.java (Gestion des livres)
│ └── Member.java (Gestion des membres)
├── service/
│ ├── ILibraryService.java (Interface des services)
│ └── LibraryService.java (Implémentation des services)
├── ui/
│ └── BibliothequeGUI.java (Interface graphique)
└── Main.java (Point d'entrée)

## Technologies Utilisées

- Java 11+
- Swing (Interface graphique)
- Collections Framework
- Streams API

## Installation

1. Clonez le repository :

git clone https://github.com/CedricHaegele/gestionnaire-bibliotheque.git
  
2. Ouvrez le projet dans votre IDE préféré (IntelliJ IDEA recommandé)
3. Compilez et exécutez la classe `Main.java`

## Utilisation

1. Lancez l'application
2. Utilisez les boutons pour :
   - Ajouter des livres (ISBN, titre, auteur)
   - Ajouter des membres (ID, nom, email)
   - Emprunter des livres (avec l'ISBN et l'ID membre)
   - Rendre des livres
   - Afficher l'état de la bibliothèque

## Concepts Java Utilisés

- Programmation Orientée Objet
- Interfaces et implémentations
- Collections (List, ArrayList)
- Encapsulation
- Streams API
- Gestion des exceptions
- Interface graphique Swing
- Logger pour le suivi des opérations

## Structure des Classes

### Model
- `Book` : Gestion des livres (ISBN, titre, auteur, statut d'emprunt)
- `Member` : Gestion des membres (ID, nom, email, livres empruntés)

### Service
- `ILibraryService` : Interface définissant les opérations de la bibliothèque
- `LibraryService` : Implémentation des opérations (ajout, emprunt, retour)

### UI
- `BibliothequeGUI` : Interface graphique avec Swing
- Boutons pour toutes les opérations
- Affichage des livres et membres

## Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créez votre branche (`git checkout -b feature/NouvelleFeature`)
3. Committez vos changements (`git commit -m 'Ajout d'une nouvelle feature'`)
4. Push vers la branche (`git push origin feature/NouvelleFeature`)
5. Ouvrez une Pull Request

## Auteur

Cédric HAEGELE
