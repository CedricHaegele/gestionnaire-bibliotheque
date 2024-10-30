package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un membre de la bibliothèque
 * Cette classe gère les informations des adhérents
 */
public class Member {
    // Les attributs privés
    private int id;
    private String nom;
    private String email;
    private List<Book> livresEmpruntes;

    // Constructeur
    public Member(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.livresEmpruntes = new ArrayList<>(); // Initialisation de la liste des livres empruntés
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getLivresEmpruntes() {
        return livresEmpruntes;
    }

    // Méthodes pour gérer les emprunts
    public void emprunterLivre(Book livre) {
        livresEmpruntes.add(livre);
    }

    public void rendreLivre(Book livre) {
        livresEmpruntes.remove(livre);
    }

    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", nombre de livres empruntés=" + livresEmpruntes.size() +
                '}';
    }
} 