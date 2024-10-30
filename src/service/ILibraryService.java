package service;

import model.Book;
import model.Member;
import java.util.List;

public interface ILibraryService {
    void ajouterLivre(Book livre);
    void supprimerLivre(Book livre);
    void modifierLivre(Book livre);
    void ajouterMembre(Member membre);
    void supprimerMembre(Member membre);
    void modifierMembre(Member membre);
    void emprunterLivre(String isbn, int membreId);
    void rendreLivre(String isbn, int membreId);
    List<Book> getLivres();
    List<Member> getMembres();
} 