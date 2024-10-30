# 📚 Gestionnaire de Bibliothèque

Une application Java moderne de gestion de bibliothèque avec interface graphique permettant de gérer les livres, les membres et les emprunts.

## ✨ Fonctionnalités

### 📊 Tableau de Bord
- Vue d'ensemble des statistiques
- Nombre total de livres
- Nombre de livres empruntés
- Nombre de livres disponibles
- Nombre total de membres

### 📚 Gestion des Livres
- Ajout de nouveaux livres
- Modification des informations des livres
- Suppression des livres (si non empruntés)
- Visualisation de l'état des livres (disponible/emprunté)
- Gestion des emprunts et retours

### 👥 Gestion des Membres
- Inscription de nouveaux membres
- Modification des informations des membres
- Suppression des membres (si aucun emprunt en cours)
- Suivi des emprunts par membre

## 🛠️ Technologies Utilisées

- Java 21
- Swing (Interface graphique)
- Persistance des données en fichiers texte
- Architecture MVC

## 📁 Structure du Projet

src/
├── model/
│ ├── Book.java (Gestion des livres)
│ └── Member.java (Gestion des membres)
├── service/
│ ├── ILibraryService.java (Interface des services)
│ ├── LibraryService.java (Implémentation des services)
│ └── DataPersistenceService.java (Gestion de la persistance)
├── ui/
│ └── BibliothequeGUI.java (Interface graphique)
├── utils/
│ └── Constants.java (Constantes de l'application)
└── Main.java (Point d'entrée)


## 🚀 Installation

1. Clonez le repository :

   git clone https://github.com/CedricHaegele/gestionnaire-bibliotheque.git

   
2. Ouvrez le projet dans IntelliJ IDEA
3. Assurez-vous d'avoir Java 21 installé
4. Compilez et exécutez Main.java

## 💾 Persistance des Données

Les données sont sauvegardées automatiquement dans le dossier `data/` :
- `books.txt` : Liste des livres
- `members.txt` : Liste des membres
- `emprunts.txt` : Historique des emprunts

## 🎯 Fonctionnalités Principales

- Interface utilisateur intuitive avec 3 onglets (Tableau de bord, Collection, Membres)
- Gestion complète des livres et des membres
- Système d'emprunt et de retour de livres
- Persistance automatique des données
- Validation des opérations (emprunts, suppressions, etc.)
- Statistiques en temps réel

  ![image](https://github.com/user-attachments/assets/9585f6a4-ded8-4aa8-9a06-c03539923f4b)
  ![image](https://github.com/user-attachments/assets/2930aa6f-5a9e-48ed-a301-eddbc5bf6b17)
  ![image](https://github.com/user-attachments/assets/d07094bc-8a43-46b2-9ddd-0f0fc30651fb)




   
