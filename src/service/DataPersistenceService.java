package service;

import model.Book;
import model.Member;
import java.io.*;
import java.util.*;

public class DataPersistenceService {
    private static final String DATA_DIR = "data";
    private static final String BOOKS_FILE = DATA_DIR + "/books.txt";
    private static final String MEMBERS_FILE = DATA_DIR + "/members.txt";
    private static final String EMPRUNTS_FILE = DATA_DIR + "/emprunts.txt";

    public DataPersistenceService() {
        createDataDirectoryIfNotExists();
    }

    private void createDataDirectoryIfNotExists() {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                // Format: isbn,titre,auteur,estEmprunte
                writer.println(String.format("%s,%s,%s,%b",
                    book.getIsbn(),
                    book.getTitre().replace(",", "\\,"),
                    book.getAuteur().replace(",", "\\,"),
                    book.estEmprunte()));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des livres: " + e.getMessage());
        }
    }

    public void saveMembers(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                // Format: id,nom,email
                writer.println(String.format("%d,%s,%s",
                    member.getId(),
                    member.getNom().replace(",", "\\,"),
                    member.getEmail().replace(",", "\\,")));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des membres: " + e.getMessage());
        }

        // Sauvegarder les emprunts séparément
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPRUNTS_FILE))) {
            for (Member member : members) {
                for (Book book : member.getLivresEmpruntes()) {
                    // Format: memberId,bookIsbn
                    writer.println(String.format("%d,%s", member.getId(), book.getIsbn()));
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des emprunts: " + e.getMessage());
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",(?=([^\\\\]|\\\\[^,])*$)");
                    if (parts.length == 4) {
                        Book book = new Book(
                            parts[0],
                            parts[1].replace("\\,", ","),
                            parts[2].replace("\\,", ",")
                        );
                        book.setEstEmprunte(false);
                        books.add(book);
                        System.out.println("Livre chargé: " + book.getTitre() + " (Emprunté: " + book.estEmprunte() + ")");
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des livres: " + e.getMessage());
            }
        }
        return books;
    }

    public List<Member> loadMembers(List<Book> books) {
        List<Member> members = new ArrayList<>();
        Map<Integer, Member> memberMap = new HashMap<>();
        
        // Charger les membres
        File file = new File(MEMBERS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",(?=([^\\\\]|\\\\[^,])*$)");
                    if (parts.length == 3) {
                        Member member = new Member(
                            Integer.parseInt(parts[0]),
                            parts[1].replace("\\,", ","),
                            parts[2].replace("\\,", ",")
                        );
                        members.add(member);
                        memberMap.put(member.getId(), member);
                        System.out.println("Membre chargé: " + member.getNom());
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des membres: " + e.getMessage());
            }
        }

        // Réinitialiser l'état des emprunts
        for (Book book : books) {
            book.setEstEmprunte(false);
        }
        for (Member member : members) {
            member.getLivresEmpruntes().clear();
        }

        // Charger les emprunts
        File empruntsFile = new File(EMPRUNTS_FILE);
        if (empruntsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(empruntsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        int memberId = Integer.parseInt(parts[0]);
                        String bookIsbn = parts[1];
                        
                        Member member = memberMap.get(memberId);
                        Book book = books.stream()
                                .filter(b -> b.getIsbn().equals(bookIsbn))
                                .findFirst()
                                .orElse(null);
                                
                        if (member != null && book != null) {
                            member.emprunterLivre(book);
                            book.setEstEmprunte(true);
                            System.out.println("Emprunt chargé: " + book.getTitre() + " par " + member.getNom());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des emprunts: " + e.getMessage());
            }
        }
        
        return members;
    }
} 