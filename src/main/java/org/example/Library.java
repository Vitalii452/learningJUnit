package org.example;

import java.util.*;

public class Library {

    private Map<String, Book> books = new HashMap<>();

    public void addBook(String title, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        if (books.containsKey(title)) {
            Book book = books.get(title);
            book.setQuantity(book.getQuantity() + quantity);

        } else {
            books.put(title, new Book(title, quantity));
        }
    }


    private boolean hasAvailableCopies(String title) {
        Book book = books.get(title);

        return book != null && book.getQuantity() > 0;
    }


    public Optional<Book> takeBook(String title) {
        if (decrementBookQuantity(title)) {
            return Optional.of(new Book(title, 1));

        } else {
            return Optional.empty();
        }
    }


    public Optional<Integer> getBookQuantity(String title) {
        if (hasAvailableCopies(title)) {
            return Optional.of(books.get(title).getQuantity());

        } else {
            return Optional.empty();
        }
    }


    public Optional<Set<String>> getAllBookTitles() {
        if (!books.isEmpty()) {
            return Optional.of(books.keySet());

        } else {
            return Optional.of(new HashSet<>());
        }
    }


    public Optional<Integer> getTotalBookQuantity() {
        return Optional.of(books.values().stream().mapToInt(Book::getQuantity).sum());
    }



    public boolean decrementBookQuantity(String title) {
        if (hasAvailableCopies(title)) {
            Book book = books.get(title);
            book.setQuantity(book.getQuantity() - 1);
            return true;

        } else {
            return false;
        }
    }


    public boolean deleteBook(String title) {
        return books.remove(title) != null;
    }
}