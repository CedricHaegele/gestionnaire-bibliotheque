package ui;

import model.Book;
import model.Member;
import service.LibraryService;
import utils.Constants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour l'interface graphique de la bibliothèque
 */
public class BibliothequeGUI extends JFrame {
    private LibraryService bibliotheque;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private DefaultListModel<String> livresModel;
    private DefaultListModel<String> membresModel;
    private Map<String, JLabel> statsLabels;
    private Color backgroundColor = new Color(240, 242, 245);
    private Color primaryColor = new Color(66, 103, 178);  // Bleu Facebook
    private Color accentColor = new Color(24, 119, 242);   // Bleu clair
    private String currentTab = Constants.TAB_DASHBOARD;
    private JPanel buttonPanel;
    
    public BibliothequeGUI() {
        // Initialisation de la bibliothèque
        bibliotheque = new LibraryService();
        
        // Configuration de la fenêtre
        setTitle("📚 Gestionnaire de Bibliothèque");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre
        
        add(createMainPanel());
        updateButtonPanel(); // Initialisation des boutons
        ajouterDonneesTest();
        updateDashboard();
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(accentColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(accentColor);
            }
        });
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        headerPanel.setBackground(backgroundColor);
        
        String[] tabs = {"📊 Tableau de bord", "📚 Collection", "👥 Membres"};
        ButtonGroup group = new ButtonGroup();
        
        for (String tab : tabs) {
            JToggleButton tabButton = new JToggleButton(tab);
            tabButton.setFont(new Font("Arial", Font.BOLD, 14));
            tabButton.setBackground(backgroundColor);
            tabButton.setForeground(primaryColor);
            group.add(tabButton);
            headerPanel.add(tabButton);
            
            tabButton.addActionListener(e -> {
                if (tab.contains("Tableau")) {
                    currentTab = Constants.TAB_DASHBOARD;
                    cardLayout.show(cardPanel, "bibliotheque");
                    updateDashboard();
                } else if (tab.contains("Collection")) {
                    currentTab = Constants.TAB_BOOKS;
                    cardLayout.show(cardPanel, "livres");
                } else {
                    currentTab = Constants.TAB_MEMBERS;
                    cardLayout.show(cardPanel, "membres");
                }
                updateButtonPanel();
            });
        }
        
        return headerPanel;
    }
    
    private void updateDashboard() {
        if (bibliotheque != null && statsLabels != null) {
            // Calcul des statistiques
            int totalLivres = bibliotheque.getLivres().size();
            long livresEmpruntes = bibliotheque.getLivres().stream()
                    .filter(Book::estEmprunte)
                    .count();
            long livresDisponibles = totalLivres - livresEmpruntes;
            int totalMembres = bibliotheque.getMembres().size();

            // Mise à jour des labels
            statsLabels.get("totalLivres").setText(String.valueOf(totalLivres));
            statsLabels.get("livresEmpruntes").setText(String.valueOf(livresEmpruntes));
            statsLabels.get("livresDisponibles").setText(String.valueOf(livresDisponibles));
            statsLabels.get("totalMembres").setText(String.valueOf(totalMembres));
        }
    }
    
    private JPanel createBibliothequeView() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(backgroundColor);
        
        // Titre
        JLabel titleLabel = new JLabel(Constants.TITRE_TABLEAU_BORD, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        
        // Panel des statistiques
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(backgroundColor);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Initialisation de la Map pour stocker les labels de statistiques
        statsLabels = new HashMap<>();
        
        // Création des cartes de statistiques avec labels stockés
        statsLabels.put("totalLivres", createStatLabel("0"));
        statsLabels.put("livresEmpruntes", createStatLabel("0"));
        statsLabels.put("livresDisponibles", createStatLabel("0"));
        statsLabels.put("totalMembres", createStatLabel("0"));
        
        addStatCard(statsPanel, Constants.STAT_TOTAL_LIVRES, statsLabels.get("totalLivres"));
        addStatCard(statsPanel, Constants.STAT_LIVRES_EMPRUNTES, statsLabels.get("livresEmpruntes"));
        addStatCard(statsPanel, Constants.STAT_LIVRES_DISPONIBLES, statsLabels.get("livresDisponibles"));
        addStatCard(statsPanel, Constants.STAT_TOTAL_MEMBRES, statsLabels.get("totalMembres"));
        
        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Ajout des composants au panel principal
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Mise à jour immédiate des statistiques
        updateDashboard();
        
        return panel;
    }
    
    private JLabel createStatLabel(String initialValue) {
        JLabel label = new JLabel(initialValue);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(accentColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    private void addStatCard(JPanel container, String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout(5, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(accentColor, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(primaryColor);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        container.add(card);
    }
    
    private JPanel createLivresView() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(backgroundColor);
        
        JLabel titleLabel = new JLabel(Constants.TITRE_COLLECTION, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        
        // Légende
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        legendPanel.setBackground(backgroundColor);
        legendPanel.add(new JLabel("🟢 Disponible    🔴 Emprunté"));
        
        // Liste des livres
        livresModel = new DefaultListModel<>();
        JList<String> livresList = new JList<>(livresModel);
        styleListe(livresList);
        
        JScrollPane scrollPane = new JScrollPane(livresList);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(legendPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Mettre à jour la liste immédiatement
        updateLists();
        
        return panel;
    }
    
    private JPanel createMembresView() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(backgroundColor);
        
        JLabel titleLabel = new JLabel(Constants.TITRE_MEMBRES, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        
        // Liste des membres
        membresModel = new DefaultListModel<>();
        JList<String> membresList = new JList<>(membresModel);
        styleListe(membresList);
        
        JScrollPane scrollPane = new JScrollPane(membresList);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Mettre à jour la liste immédiatement
        updateLists();
        
        return panel;
    }
    
    private JPanel createListPanel(String title, JList<String> list) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(backgroundColor);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(primaryColor);
        
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleListe(JList<String> list) {
        list.setBackground(Color.WHITE);
        list.setFont(new Font("Arial", Font.PLAIN, 14));
        list.setSelectionBackground(accentColor);
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(60);
        
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if (!isSelected) {
                    label.setBackground(index % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                return label;
            }
        });
    }
    
    private void updateLists() {
        // Initialiser les modèles s'ils sont null
        if (livresModel == null) {
            livresModel = new DefaultListModel<>();
        }
        if (membresModel == null) {
            membresModel = new DefaultListModel<>();
        }

        // Vider les listes existantes
        livresModel.clear();
        membresModel.clear();
        
        // Mise à jour de la liste des livres
        for (Book livre : bibliotheque.getLivres()) {
            String status = livre.estEmprunte() ? "🔴" : "🟢";
            livresModel.addElement(String.format("%s  %s\n    ✍️ %s", 
                status, 
                livre.getTitre(), 
                livre.getAuteur()));
        }
        
        // Mise à jour de la liste des membres
        for (Member membre : bibliotheque.getMembres()) {
            membresModel.addElement(String.format("👤  %s\n    📧 %s\n    📚 Emprunts : %d", 
                membre.getNom(), 
                membre.getEmail(), 
                membre.getLivresEmpruntes().size()));
        }

        // Mettre à jour le tableau de bord
        updateDashboard();
    }
    
    // Classe pour personnaliser la barre de défilement
    private class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = accentColor;
            this.trackColor = backgroundColor;
        }
        
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }
    }
    
    // Nouvelle méthode pour ajouter des données de test
    private void ajouterDonneesTest() {
        // Vérifier si des données existent déjà
        if (!bibliotheque.getLivres().isEmpty() || !bibliotheque.getMembres().isEmpty()) {
            return;
        }
        
        // Ajout de quelques livres de test
        bibliotheque.ajouterLivre(new Book("123", "Le Petit Prince", "Antoine de Saint-Exupéry"));
        bibliotheque.ajouterLivre(new Book("456", "1984", "George Orwell"));
        bibliotheque.ajouterLivre(new Book("789", "Harry Potter", "J.K. Rowling"));
        bibliotheque.ajouterLivre(new Book("101", "Rose Blanche", "Hemingway"));
        
        // Ajout de quelques membres de test
        bibliotheque.ajouterMembre(new Member(1, "Jean Dupont", "jean@email.com"));
        bibliotheque.ajouterMembre(new Member(2, "Marie Martin", "marie@email.com"));
        
        // Mise à jour des listes
        updateLists();
    }
    
    private void ajouterLivre() {
        String titre = JOptionPane.showInputDialog(this, "Titre du livre :");
        if (titre != null && !titre.trim().isEmpty()) {
            String auteur = JOptionPane.showInputDialog(this, "Auteur du livre :");
            if (auteur != null && !auteur.trim().isEmpty()) {
                // Génération automatique d'un ISBN
                String isbn = String.valueOf(System.currentTimeMillis()).substring(0, 13);
                Book livre = new Book(isbn, titre, auteur);
                bibliotheque.ajouterLivre(livre);
                updateLists();
            }
        }
    }
    
    private void ajouterMembre() {
        // Création d'un panneau personnalisé pour le formulaire
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField prenomField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();
        
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Ajouter un membre", JOptionPane.OK_CANCEL_OPTION);
                
        if (result == JOptionPane.OK_OPTION) {
            String prenom = capitalizeFirstLetter(prenomField.getText().trim());
            String nom = capitalizeFirstLetter(nomField.getText().trim());
            String email = emailField.getText().trim();
            
            // Validation des champs
            if (prenom.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Le prénom et le nom sont requis",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Si l'email est vide, on en génère un
            if (email.isEmpty()) {
                email = generateEmail(prenom, nom);
            } else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this,
                    "Format d'email invalide",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String nomComplet = prenom + " " + nom;
            int id = bibliotheque.getMembres().size() + 1;
            Member membre = new Member(id, nomComplet, email);
            bibliotheque.ajouterMembre(membre);
            updateLists();
        }
    }
    
    // Méthode pour mettre en majuscule la première lettre
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    // Méthode pour générer un email
    private String generateEmail(String prenom, String nom) {
        String baseEmail = prenom.toLowerCase() + "." + nom.toLowerCase() + "@bibliotheque.fr";
        // Enlever les accents et caractères spéciaux
        return baseEmail
            .replaceAll("[éèêë]", "e")
            .replaceAll("[àâä]", "a")
            .replaceAll("[ïî]", "i")
            .replaceAll("[ôö]", "o")
            .replaceAll("[ùûü]", "u")
            .replaceAll("[ç]", "c")
            .replaceAll("[^a-zA-Z0-9.@]", "");
    }
    
    // Méthode pour valider l'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
    
    private void emprunterLivre() {
        // Création d'une liste de titres de livres disponibles
        List<Book> livresDisponibles = bibliotheque.getLivres().stream()
                .filter(l -> !l.estEmprunte())
                .collect(Collectors.toList());
                
        if (livresDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun livre disponible !");
            return;
        }

        // Création du menu déroulant pour les livres
        String[] titresLivres = livresDisponibles.stream()
                .map(Book::getTitre)
                .toArray(String[]::new);
        
        String titreLivre = (String) JOptionPane.showInputDialog(this,
                "Choisissez un livre :",
                "Emprunter un livre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titresLivres,
                titresLivres[0]);

        if (titreLivre != null) {
            // Création du menu déroulant pour les membres
            String[] nomsMembres = bibliotheque.getMembres().stream()
                    .map(Member::getNom)
                    .toArray(String[]::new);
            
            String nomMembre = (String) JOptionPane.showInputDialog(this,
                    "Choisissez un membre :",
                    "Emprunter un livre",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nomsMembres,
                    nomsMembres[0]);

            if (nomMembre != null) {
                // Recherche du livre et du membre sélectionnés
                Book livre = livresDisponibles.stream()
                        .filter(l -> l.getTitre().equals(titreLivre))
                        .findFirst().get();
                        
                Member membre = bibliotheque.getMembres().stream()
                        .filter(m -> m.getNom().equals(nomMembre))
                        .findFirst().get();

                bibliotheque.emprunterLivre(livre.getIsbn(), membre.getId());
                updateLists();
            }
        }
    }
    
    private void rendreLivre() {
        // Création d'une liste de membres ayant des livres empruntés
        List<Member> membresAvecLivres = bibliotheque.getMembres().stream()
                .filter(m -> !m.getLivresEmpruntes().isEmpty())
                .collect(Collectors.toList());

        if (membresAvecLivres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun livre à rendre !");
            return;
        }

        // Menu déroulant pour les membres
        String[] nomsMembres = membresAvecLivres.stream()
                .map(Member::getNom)
                .toArray(String[]::new);

        String nomMembre = (String) JOptionPane.showInputDialog(this,
                "Choisissez un membre :",
                "Rendre un livre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nomsMembres,
                nomsMembres[0]);

        if (nomMembre != null) {
            Member membre = membresAvecLivres.stream()
                    .filter(m -> m.getNom().equals(nomMembre))
                    .findFirst().get();

            // Menu déroulant pour les livres empruntés par ce membre
            String[] titresLivres = membre.getLivresEmpruntes().stream()
                    .map(Book::getTitre)
                    .toArray(String[]::new);

            String titreLivre = (String) JOptionPane.showInputDialog(this,
                    "Choisissez un livre à rendre :",
                    "Rendre un livre",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    titresLivres,
                    titresLivres[0]);

            if (titreLivre != null) {
                Book livre = membre.getLivresEmpruntes().stream()
                        .filter(l -> l.getTitre().equals(titreLivre))
                        .findFirst().get();

                bibliotheque.rendreLivre(livre.getIsbn(), membre.getId());
                updateLists();
            }
        }
    }
    
    private void afficherTout() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════ ÉTAT DE LA BIBLIOTHÈQUE ═══════════════\n\n");
        
        // Affichage des livres
        sb.append("📚 LIVRES :\n");
        sb.append("──────────────────────────────────\n");
        for (Book livre : bibliotheque.getLivres()) {
            sb.append(String.format("📖 %s\n", livre.getTitre()));
            sb.append(String.format("   Auteur: %s\n", livre.getAuteur()));
            sb.append(String.format("   ISBN: %s\n", livre.getIsbn()));
            sb.append(String.format("   Statut: %s\n", 
                livre.estEmprunte() ? "🔴 Emprunté" : "🟢 Disponible"));
            sb.append("──────────────────────────────────\n");
        }
        
        // Affichage des membres
        sb.append("\n👥 MEMBRES :\n");
        sb.append("──────────────────────────────────\n");
        for (Member membre : bibliotheque.getMembres()) {
            sb.append(String.format("👤 %s\n", membre.getNom()));
            sb.append(String.format("   ID: %d\n", membre.getId()));
            sb.append(String.format("   Email: %s\n", membre.getEmail()));
            sb.append(String.format("   Livres empruntés: %d\n", 
                membre.getLivresEmpruntes().size()));
            sb.append("──────────────────────────────────\n");
        }
        
        //affichageArea.setText(sb.toString());
    }
    
    private JPanel createButtonPanel() {
        JPanel mainButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        mainButtonPanel.setBackground(backgroundColor);
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel pour les livres
        JPanel livresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        livresPanel.setBackground(backgroundColor);
        JLabel livresLabel = new JLabel("📚 Livres :");
        livresLabel.setFont(new Font("Arial", Font.BOLD, 12));
        livresPanel.add(livresLabel);
        livresPanel.add(createStyledButton("📕 Ajouter", e -> ajouterLivre()));
        livresPanel.add(createStyledButton("✏️ Modifier", e -> modifierLivre()));
        livresPanel.add(createStyledButton("🗑️ Supprimer", e -> supprimerLivre()));
        livresPanel.add(createStyledButton("📚 Emprunter", e -> emprunterLivre()));
        livresPanel.add(createStyledButton("↩️ Rendre", e -> rendreLivre()));

        // Panel pour les membres
        JPanel membresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        membresPanel.setBackground(backgroundColor);
        JLabel membresLabel = new JLabel("👥 Membres :");
        membresLabel.setFont(new Font("Arial", Font.BOLD, 12));
        membresPanel.add(membresLabel);
        membresPanel.add(createStyledButton("➕ Ajouter", e -> ajouterMembre()));
        membresPanel.add(createStyledButton("✏️ Modifier", e -> modifierMembre()));
        membresPanel.add(createStyledButton("🗑️ Supprimer", e -> supprimerMembre()));

        // Panel pour les emprunts
        JPanel empruntsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        empruntsPanel.setBackground(backgroundColor);
        JLabel empruntsLabel = new JLabel("📖 Emprunts :");
        empruntsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        empruntsPanel.add(empruntsLabel);
        empruntsPanel.add(createStyledButton("📚 Emprunter", e -> emprunterLivre()));
        empruntsPanel.add(createStyledButton("↩️ Rendre", e -> rendreLivre()));

        mainButtonPanel.add(livresPanel);
        mainButtonPanel.add(membresPanel);
        mainButtonPanel.add(empruntsPanel);

        return mainButtonPanel;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        // Effet de survol
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(accentColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(accentColor);
            }
        });
        
        button.addActionListener(listener);
        return button;
    }
    
    private void modifierLivre() {
        String[] titresLivres = bibliotheque.getLivres().stream()
                .map(Book::getTitre)
                .toArray(String[]::new);
                
        if (titresLivres.length == 0) {
            JOptionPane.showMessageDialog(this, "Aucun livre à modifier");
            return;
        }

        String titreLivre = (String) JOptionPane.showInputDialog(this,
                "Choisissez le livre à modifier :",
                "Modifier un livre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                titresLivres,
                titresLivres[0]);
                
        if (titreLivre != null) {
            Book livre = bibliotheque.getLivres().stream()
                    .filter(l -> l.getTitre().equals(titreLivre))
                    .findFirst()
                    .orElse(null);
                    
            if (livre != null) {
                JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
                JTextField titreField = new JTextField(livre.getTitre());
                JTextField auteurField = new JTextField(livre.getAuteur());
                
                panel.add(new JLabel("Nouveau titre :"));
                panel.add(titreField);
                panel.add(new JLabel("Nouvel auteur :"));
                panel.add(auteurField);
                
                int result = JOptionPane.showConfirmDialog(this, panel, 
                        "Modifier le livre", JOptionPane.OK_CANCEL_OPTION);
                        
                if (result == JOptionPane.OK_OPTION) {
                    livre.setTitre(titreField.getText().trim());
                    livre.setAuteur(auteurField.getText().trim());
                    updateLists();
                    updateDashboard();
                }
            }
        }
    }
    
    private void modifierMembre() {
        String[] nomsMembres = bibliotheque.getMembres().stream()
                .map(Member::getNom)
                .toArray(String[]::new);
                
        if (nomsMembres.length == 0) {
            JOptionPane.showMessageDialog(this, "Aucun membre à modifier");
            return;
        }

        String nomMembre = (String) JOptionPane.showInputDialog(this,
                "Choisissez le membre à modifier :",
                "Modifier un membre",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nomsMembres,
                nomsMembres[0]);
                
        if (nomMembre != null) {
            Member membre = bibliotheque.getMembres().stream()
                    .filter(m -> m.getNom().equals(nomMembre))
                    .findFirst()
                    .orElse(null);
                    
            if (membre != null) {
                JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
                JTextField nomField = new JTextField(membre.getNom());
                JTextField emailField = new JTextField(membre.getEmail());
                
                panel.add(new JLabel("Nouveau nom :"));
                panel.add(nomField);
                panel.add(new JLabel("Nouvel email :"));
                panel.add(emailField);
                
                int result = JOptionPane.showConfirmDialog(this, panel, 
                        "Modifier le membre", JOptionPane.OK_CANCEL_OPTION);
                        
                if (result == JOptionPane.OK_OPTION) {
                    membre.setNom(nomField.getText().trim());
                    membre.setEmail(emailField.getText().trim());
                    updateLists();
                    updateDashboard();
                }
            }
        }
    }
    
    private void supprimerLivre() {
        // Récupérer la liste des livres non empruntés
        List<Book> livresDisponibles = bibliotheque.getLivres().stream()
                .filter(l -> !l.estEmprunte())
                .collect(Collectors.toList());
                
        if (livresDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Aucun livre disponible à supprimer\n(Les livres empruntés ne peuvent pas être supprimés)",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] titresLivres = livresDisponibles.stream()
                .map(Book::getTitre)
                .toArray(String[]::new);

        String titreLivre = (String) JOptionPane.showInputDialog(this,
                "Choisissez le livre à supprimer :",
                "Supprimer un livre",
                JOptionPane.WARNING_MESSAGE,
                null,
                titresLivres,
                titresLivres[0]);
                
        if (titreLivre != null) {
            Book livreASupprimer = livresDisponibles.stream()
                    .filter(l -> l.getTitre().equals(titreLivre))
                    .findFirst()
                    .orElse(null);
                    
            if (livreASupprimer != null) {
                int confirmation = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer le livre :\n" + titreLivre,
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                    
                if (confirmation == JOptionPane.YES_OPTION) {
                    bibliotheque.supprimerLivre(livreASupprimer);
                    updateLists();
                    updateDashboard();
                    JOptionPane.showMessageDialog(this,
                        "Le livre a été supprimé avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void supprimerMembre() {
        // Récupérer la liste des membres sans emprunts
        List<Member> membresDisponibles = bibliotheque.getMembres().stream()
                .filter(m -> m.getLivresEmpruntes().isEmpty())
                .collect(Collectors.toList());
                
        if (membresDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Aucun membre ne peut être supprimé\n(Les membres ayant des emprunts ne peuvent pas être supprimés)",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] nomsMembres = membresDisponibles.stream()
                .map(Member::getNom)
                .toArray(String[]::new);

        String nomMembre = (String) JOptionPane.showInputDialog(this,
                "Choisissez le membre à supprimer :",
                "Supprimer un membre",
                JOptionPane.WARNING_MESSAGE,
                null,
                nomsMembres,
                nomsMembres[0]);
                
        if (nomMembre != null) {
            Member membreASupprimer = membresDisponibles.stream()
                    .filter(m -> m.getNom().equals(nomMembre))
                    .findFirst()
                    .orElse(null);
                    
            if (membreASupprimer != null) {
                int confirmation = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer le membre :\n" + nomMembre,
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                    
                if (confirmation == JOptionPane.YES_OPTION) {
                    bibliotheque.supprimerMembre(membreASupprimer);
                    updateLists();
                    updateDashboard();
                    JOptionPane.showMessageDialog(this,
                        "Le membre a été supprimé avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // En-tête avec navigation
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Panel central avec CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(backgroundColor);
        
        cardPanel.add(createBibliothequeView(), "bibliotheque");
        cardPanel.add(createLivresView(), "livres");
        cardPanel.add(createMembresView(), "membres");
        
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        
        // Panel des boutons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(backgroundColor);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return mainPanel;
    }

    private void updateButtonPanel() {
        if (buttonPanel != null) {
            buttonPanel.removeAll();
            
            switch (currentTab) {
                case Constants.TAB_DASHBOARD:
                    // Aucun bouton sur le tableau de bord
                    break;
                    
                case Constants.TAB_BOOKS:
                    // Boutons pour la gestion des livres et emprunts
                    JPanel livresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    livresPanel.setBackground(backgroundColor);
                    livresPanel.add(createStyledButton("📕 Ajouter un livre", e -> ajouterLivre()));
                    livresPanel.add(createStyledButton("✏️ Modifier", e -> modifierLivre()));
                    livresPanel.add(createStyledButton("🗑️ Supprimer", e -> supprimerLivre()));
                    livresPanel.add(createStyledButton("📚 Emprunter", e -> emprunterLivre()));
                    livresPanel.add(createStyledButton("↩️ Rendre", e -> rendreLivre()));
                    buttonPanel.add(livresPanel);
                    break;
                    
                case Constants.TAB_MEMBERS:
                    // Boutons pour la gestion des membres
                    JPanel membresPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    membresPanel.setBackground(backgroundColor);
                    membresPanel.add(createStyledButton("👤 Ajouter un membre", e -> ajouterMembre()));
                    membresPanel.add(createStyledButton("✏️ Modifier", e -> modifierMembre()));
                    membresPanel.add(createStyledButton("🗑️ Supprimer", e -> supprimerMembre()));
                    buttonPanel.add(membresPanel);
                    break;
            }
            
            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }
} 