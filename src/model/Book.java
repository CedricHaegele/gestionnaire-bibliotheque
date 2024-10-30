package model;

/**
 * Classe représentant un livre dans la bibliothèque
 * Cette classe contient toutes les informations relatives à un livre
 */
public class Book {
    // Les attributs privés pour respecter l'encapsulation
    private String isbn;
    private String titre;
    private String auteur;
    private boolean estEmprunte;

    // Constructeur
    public Book(String isbn, String titre, String auteur) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.estEmprunte = false; // Par défaut, un nouveau livre n'est pas emprunté
    }

    // Getters et Setters
    public String getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean estEmprunte() {
        return estEmprunte;
    }

    public void setEstEmprunte(boolean estEmprunte) {
        this.estEmprunte = estEmprunte;
    }

    // Méthode toString pour afficher les informations du livre
    @Override
    public String toString() {
        return "Livre{" +
                "ISBN='" + isbn + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", emprunté=" + estEmprunte +
                '}';
    }
} 