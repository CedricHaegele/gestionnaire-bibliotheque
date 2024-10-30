package model;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private int id;
    private String nom;
    private String email;
    private List<Book> livresEmpruntes;

    public Member(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.livresEmpruntes = new ArrayList<>();
    }

    // Getters
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

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Méthodes pour gérer les emprunts
    public void emprunterLivre(Book livre) {
        if (!livresEmpruntes.contains(livre)) {
            livresEmpruntes.add(livre);
        }
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
                ", livres empruntés=" + livresEmpruntes.size() +
                '}';
    }
} 