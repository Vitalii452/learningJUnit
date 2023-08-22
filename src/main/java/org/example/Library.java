package org.example;

import java.util.*;

public class Library {

    private class Book {
        private final String title;
        private int quantity;

        Book(String title, int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }

            this.title = title;
            this.quantity = quantity;
        }

        public String getTitle() {
            return title;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            if (quantity < 0) {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
            this.quantity = quantity;
        }
    }

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

        if (book != null && book.getQuantity() > 0) {
            return true;

        } else {
            return false;
        }
    }


    public Optional<Book> takeBook(String title) {
        if (hasAvailableCopies(title)) {
            Book book = books.get(title);

            if (book.getQuantity() <= 0) {
                throw new IllegalStateException("Book quantity is already 0 or less.");
            }

            book.setQuantity(book.getQuantity() - 1);

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