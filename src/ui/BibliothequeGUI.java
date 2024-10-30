package ui;

import model.Book;
import model.Member;
import service.LibraryService;

import javax.swing.*;
import java.awt.*;

/**
 * Classe pour l'interface graphique de la bibliothèque
 */
public class BibliothequeGUI extends JFrame {
    private LibraryService bibliotheque;
    private JTextArea affichageArea;
    
    public BibliothequeGUI() {
        // Initialisation de la bibliothèque
        bibliotheque = new LibraryService();
        
        // Configuration de la fenêtre
        setTitle("Gestion de Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Création des composants
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Zone d'affichage
        affichageArea = new JTextArea();
        affichageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(affichageArea);
        
        // Panel pour les boutons
        JPanel boutonPanel = new JPanel();
        JButton ajouterLivreBtn = new JButton("Ajouter un livre");
        JButton ajouterMembreBtn = new JButton("Ajouter un membre");
        JButton emprunterBtn = new JButton("Emprunter un livre");
        JButton rendreBtn = new JButton("Rendre un livre");
        JButton afficherBtn = new JButton("Afficher tout");
        
        // Ajout des boutons au panel
        boutonPanel.add(ajouterLivreBtn);
        boutonPanel.add(ajouterMembreBtn);
        boutonPanel.add(emprunterBtn);
        boutonPanel.add(rendreBtn);
        boutonPanel.add(afficherBtn);
        
        // Ajout des listeners pour les boutons
        ajouterLivreBtn.addActionListener(e -> ajouterLivre());
        ajouterMembreBtn.addActionListener(e -> ajouterMembre());
        emprunterBtn.addActionListener(e -> emprunterLivre());
        rendreBtn.addActionListener(e -> rendreLivre());
        afficherBtn.addActionListener(e -> afficherTout());
        
        // Assemblage de l'interface
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(boutonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Ajout des données de test APRÈS l'initialisation de l'interface
        ajouterDonneesTest();
    }
    
    private void ajouterLivre() {
        String isbn = JOptionPane.showInputDialog("Entrez l'ISBN du livre:");
        if (isbn != null && !isbn.trim().isEmpty()) {
            String titre = JOptionPane.showInputDialog("Entrez le titre du livre:");
            String auteur = JOptionPane.showInputDialog("Entrez l'auteur du livre:");
            
            if (titre != null && auteur != null) {
                Book livre = new Book(isbn, titre, auteur);
                bibliotheque.ajouterLivre(livre);
                afficherTout();
            }
        }
    }
    
    private void ajouterMembre() {
        String idStr = JOptionPane.showInputDialog("Entrez l'ID du membre:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                String nom = JOptionPane.showInputDialog("Entrez le nom du membre:");
                String email = JOptionPane.showInputDialog("Entrez l'email du membre:");
                
                if (nom != null && email != null) {
                    Member membre = new Member(id, nom, email);
                    bibliotheque.ajouterMembre(membre);
                    afficherTout();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'ID doit être un nombre!");
            }
        }
    }
    
    private void emprunterLivre() {
        String isbn = JOptionPane.showInputDialog("Entrez l'ISBN du livre à emprunter:");
        String idStr = JOptionPane.showInputDialog("Entrez l'ID du membre:");
        
        if (isbn != null && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                bibliotheque.emprunterLivre(isbn, id);
                afficherTout();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'ID doit être un nombre!");
            }
        }
    }
    
    private void rendreLivre() {
        String isbn = JOptionPane.showInputDialog("Entrez l'ISBN du livre à rendre:");
        String idStr = JOptionPane.showInputDialog("Entrez l'ID du membre:");
        
        if (isbn != null && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                bibliotheque.rendreLivre(isbn, id);
                afficherTout();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'ID doit être un nombre!");
            }
        }
    }
    
    private void afficherTout() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== État de la bibliothèque ===\n\n");
        
        // Affichage de tous les livres
        sb.append("Liste complète des livres :\n");
        for (Book livre : bibliotheque.getLivres()) {
            sb.append(livre.toString()).append("\n");
        }
        
        // Affichage des membres
        sb.append("\nMembres de la bibliothèque :\n");
        for (Member membre : bibliotheque.getMembres()) {
            sb.append(membre.toString()).append("\n");
        }
        
        affichageArea.setText(sb.toString());
    }
    
    // Nouvelle méthode pour ajouter des données de test
    private void ajouterDonneesTest() {
        // Ajout de quelques livres
        bibliotheque.ajouterLivre(new Book("123", "Le Petit Prince", "Antoine de Saint-Exupéry"));
        bibliotheque.ajouterLivre(new Book("456", "1984", "George Orwell"));
        bibliotheque.ajouterLivre(new Book("789", "Harry Potter", "J.K. Rowling"));
        
        // Ajout de quelques membres
        bibliotheque.ajouterMembre(new Member(1, "Jean Dupont", "jean@email.com"));
        bibliotheque.ajouterMembre(new Member(2, "Marie Martin", "marie@email.com"));
        
        // Affichage initial
        afficherTout();
    }
} 