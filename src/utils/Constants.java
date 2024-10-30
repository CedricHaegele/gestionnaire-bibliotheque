package utils;

public class Constants {
    public static final String WINDOW_TITLE = "Gestion de Bibliothèque";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    
    // Messages
    public static final String MSG_LIVRE_AJOUTE = "Livre ajouté : ";
    public static final String MSG_LIVRE_SUPPRIME = "Livre supprimé : ";
    public static final String MSG_MEMBRE_AJOUTE = "Membre ajouté : ";
    public static final String MSG_MEMBRE_SUPPRIME = "Membre supprimé : ";
    
    // Titres
    public static final String TITRE_APPLICATION = "📚 Gestionnaire de Bibliothèque";
    public static final String TITRE_TABLEAU_BORD = "📊 Tableau de Bord";
    public static final String TITRE_COLLECTION = "📚 Collection";
    public static final String TITRE_MEMBRES = "👥 Membres";
    
    // Stats
    public static final String STAT_TOTAL_LIVRES = "📚 Total Livres";
    public static final String STAT_LIVRES_EMPRUNTES = "📖 Livres Empruntés";
    public static final String STAT_LIVRES_DISPONIBLES = "📗 Livres Disponibles";
    public static final String STAT_TOTAL_MEMBRES = "👥 Total Membres";

    // Onglets
    public static final String TAB_DASHBOARD = "dashboard";
    public static final String TAB_BOOKS = "books";
    public static final String TAB_MEMBERS = "members";

    // Fichiers
    public static final String DATA_DIR = "data";
    public static final String BOOKS_FILE = DATA_DIR + "/books.txt";
    public static final String MEMBERS_FILE = DATA_DIR + "/members.txt";
    public static final String EMPRUNTS_FILE = DATA_DIR + "/emprunts.txt";

    // Messages d'erreur
    public static final String ERR_LOAD_BOOKS = "Erreur lors du chargement des livres: ";
    public static final String ERR_LOAD_MEMBERS = "Erreur lors du chargement des membres: ";
    public static final String ERR_LOAD_EMPRUNTS = "Erreur lors du chargement des emprunts: ";
    public static final String ERR_SAVE_BOOKS = "Erreur lors de la sauvegarde des livres: ";
    public static final String ERR_SAVE_MEMBERS = "Erreur lors de la sauvegarde des membres: ";
    public static final String ERR_SAVE_EMPRUNTS = "Erreur lors de la sauvegarde des emprunts: ";
} 