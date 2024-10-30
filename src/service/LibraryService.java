package service;

import model.Book;
import model.Member;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.*;

public class LibraryService implements ILibraryService {
    private static final Logger logger = Logger.getLogger(LibraryService.class.getName());
    private List<Book> livres;
    private List<Member> membres;

    public LibraryService() {
        // Configuration du logger pour un affichage plus simple
        configureLogger();
        
        this.livres = new ArrayList<>();
        this.membres = new ArrayList<>();
    }

    private void configureLogger() {
        // Supprime tous les handlers existants
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }
        
        // Configure notre logger
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                // Retourne uniquement le message sans décoration
                return record.getMessage() + System.lineSeparator();
            }
        });
        
        // Désactive la propagation au parent
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    @Override
    public void ajouterLivre(Book livre) {
        livres.add(livre);
        System.out.println(Constants.MSG_LIVRE_AJOUTE + livre.getTitre());  // Utilisation de println au lieu de logger
    }

    @Override
    public void ajouterMembre(Member membre) {
        membres.add(membre);
        System.out.println(Constants.MSG_MEMBRE_AJOUTE + membre.getNom());  // Utilisation de println au lieu de logger
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

    @Override
    public List<Book> getLivres() {
        return livres;
    }

    @Override
    public List<Member> getMembres() {
        return membres;
    }
} 