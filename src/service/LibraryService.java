package service;

import model.Book;
import model.Member;
import java.util.*;

public class LibraryService implements ILibraryService {
    private List<Book> livres;
    private List<Member> membres;
    private final DataPersistenceService persistenceService;

    public LibraryService() {
        this.persistenceService = new DataPersistenceService();
        this.livres = persistenceService.loadBooks();
        this.membres = persistenceService.loadMembers(this.livres);
        
        // Charger les données initiales seulement si aucune donnée n'existe
        if (livres.isEmpty() && membres.isEmpty()) {
            chargerDonneesInitiales();
        }
    }

    private void chargerDonneesInitiales() {
        // Ajout des livres de test
        ajouterLivre(new Book("123", "Le Petit Prince", "Antoine de Saint-Exupéry"));
        ajouterLivre(new Book("456", "1984", "George Orwell"));
        ajouterLivre(new Book("789", "Harry Potter", "J.K. Rowling"));
        
        // Ajout des membres de test
        ajouterMembre(new Member(1, "Jean Dupont", "jean@email.com"));
        ajouterMembre(new Member(2, "Marie Martin", "marie@email.com"));
    }

    @Override
    public void ajouterLivre(Book livre) {
        if (!livreExiste(livre.getIsbn())) {
            livres.add(livre);
            persistenceService.saveBooks(livres);
            System.out.println("Livre ajouté : " + livre.getTitre());
        }
    }

    @Override
    public void supprimerLivre(Book livre) {
        if (!livre.estEmprunte()) {
            livres.remove(livre);
            persistenceService.saveBooks(livres);
            System.out.println("Livre supprimé : " + livre.getTitre());
        }
    }

    @Override
    public void modifierLivre(Book livre) {
        Book existingBook = trouverLivre(livre.getIsbn()).orElse(null);
        if (existingBook != null) {
            existingBook.setTitre(livre.getTitre());
            existingBook.setAuteur(livre.getAuteur());
            persistenceService.saveBooks(livres);
            System.out.println("Livre modifié : " + livre.getTitre());
        }
    }

    @Override
    public void ajouterMembre(Member membre) {
        if (!membreExiste(membre.getId())) {
            membres.add(membre);
            persistenceService.saveMembers(membres);
            System.out.println("Membre ajouté : " + membre.getNom());
        }
    }

    @Override
    public void supprimerMembre(Member membre) {
        if (membre.getLivresEmpruntes().isEmpty()) {
            membres.remove(membre);
            persistenceService.saveMembers(membres);
            System.out.println("Membre supprimé : " + membre.getNom());
        }
    }

    @Override
    public void modifierMembre(Member membre) {
        Member existingMember = trouverMembre(membre.getId()).orElse(null);
        if (existingMember != null) {
            existingMember.setNom(membre.getNom());
            existingMember.setEmail(membre.getEmail());
            persistenceService.saveMembers(membres);
            System.out.println("Membre modifié : " + membre.getNom());
        }
    }

    @Override
    public void emprunterLivre(String isbn, int membreId) {
        Optional<Book> livre = trouverLivre(isbn);
        Optional<Member> membre = trouverMembre(membreId);

        if (livre.isPresent() && membre.isPresent()) {
            Book l = livre.get();
            Member m = membre.get();

            if (!l.estEmprunte()) {
                l.setEstEmprunte(true);
                m.emprunterLivre(l);
                persistenceService.saveBooks(livres);
                persistenceService.saveMembers(membres);
                System.out.println("Livre emprunté avec succès");
            } else {
                System.out.println("Le livre n'est pas disponible");
            }
        } else {
            System.out.println("Livre ou membre non trouvé");
        }
    }

    @Override
    public void rendreLivre(String isbn, int membreId) {
        Optional<Book> livre = trouverLivre(isbn);
        Optional<Member> membre = trouverMembre(membreId);

        if (livre.isPresent() && membre.isPresent()) {
            Book l = livre.get();
            Member m = membre.get();

            l.setEstEmprunte(false);
            m.rendreLivre(l);
            persistenceService.saveBooks(livres);
            persistenceService.saveMembers(membres);
            System.out.println("Livre rendu avec succès");
        } else {
            System.out.println("Livre ou membre non trouvé");
        }
    }

    private Optional<Book> trouverLivre(String isbn) {
        return livres.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }

    private Optional<Member> trouverMembre(int id) {
        return membres.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
    }

    private boolean livreExiste(String isbn) {
        return livres.stream().anyMatch(l -> l.getIsbn().equals(isbn));
    }

    private boolean membreExiste(int id) {
        return membres.stream().anyMatch(m -> m.getId() == id);
    }

    @Override
    public List<Book> getLivres() {
        return new ArrayList<>(livres);
    }

    @Override
    public List<Member> getMembres() {
        return new ArrayList<>(membres);
    }
} 