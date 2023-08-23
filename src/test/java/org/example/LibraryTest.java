package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @DisplayName("Adding books with valid title and quantity")
    @ParameterizedTest
    @CsvSource({
            "'The biggest book in the world', 5",
            "'I love java', 100",
    })
    void addBook_WithValidTitleAndQuantity_increasesBooksQuantity(String title, int quantity) {
        library.addBook(title, quantity);

        Optional<Integer> bookQuantity = library.getBookQuantity(title);
        assertTrue(bookQuantity.isPresent());
        assertEquals(quantity, bookQuantity.get().intValue());
    }

    @Test
    void addBook_WithValidTitleAndZeroQuantity_havingBookNameInLibraryWithZeroQuantity() {
        String title = "I love java";
        int quantity = 0;

        library.addBook(title, quantity);
        Optional<Set<String>> bookTitles = library.getAllBookTitles();

        assertTrue(bookTitles.isPresent(), "Book titles should be present");
        assertTrue(bookTitles.get().contains(title), "Book titles should contain the added title");
    }

    @ParameterizedTest
    @CsvSource({
            "'Everything about java!', -1",
            "'Do you like java?', -987654321"
    })
    void addBook_WithNotValidQuantity_throwIllegalArgumentException(String title, int quantity){
        assertThrows(IllegalArgumentException.class, () -> library.addBook(title, quantity));
    }

    @Test
    void takeBook_WhenTheBookExists_requestedBook() {
        library.addBook("I love java", 10);

        Optional<Book> book = library.takeBook("I love java");

        assertTrue(book.isPresent());
        assertEquals(book.get().getTitle(), "I love java");
        assertEquals(book.get().getQuantity(), 1);
    }

    @Test
    void takeBook_WhenTheBookIsNotExist_OptionalEmpty() {
        Optional<Book>book = library.takeBook("I love java");
        assertFalse(book.isPresent());
    }

    @Test
    void takeBook_WhenTheBookIsExistsButNoCopiesInLibrary_OptionalEmpty() {
        String bookName = "I love java";
        library.addBook(bookName, 0);

        Optional<Book>book = library.takeBook(bookName);

        assertFalse(book.isPresent());
    }

    @Test
    void getBookQuantity_WhenThereAreBooksInTheLibrary_totalBooksQuantity() {
        library.addBook("I love java", 23);
        library.addBook("How to learn java?", 3);
        library.addBook("How did we live without java?", 2);

        Optional<Integer>totalBooksQuantity = library.getTotalBookQuantity();

        assertTrue(totalBooksQuantity.isPresent());
        assertEquals(totalBooksQuantity.get().intValue(), 28);
    }

    @Test
    void getBookQuantity_WhenThereArentBooksInTheLibrary_totalBook() {
        Optional<Integer>totalBooksQuantity = library.getTotalBookQuantity();
        assertTrue(totalBooksQuantity.isPresent());
        assertEquals(totalBooksQuantity.get().intValue(), 0);
    }


    @Test
    void getAllBookTitles_WhenThereAreBooksInTheLibrary_SetBookTitles() {
        String firstBook, secondBook, thirdBook;
        firstBook = "I love java";
        secondBook = "How to learn java?";
        thirdBook = "How did we live without java?";
        library.addBook(firstBook, 23);
        library.addBook(secondBook, 3);
        library.addBook(thirdBook, 2);

        Optional<Set<String>> bookTitles;
        bookTitles = library.getAllBookTitles();

        assertTrue(bookTitles.isPresent());
        assertEquals(bookTitles.get(), new HashSet<>(Arrays.asList(firstBook, secondBook, thirdBook)));
    }

    @Disabled("Test is temporarily disabled")
    @Test
    void getTotalBookQuantity_WhenThereAreBooksInTheLibrary_OptionalTotalBookQuantity() {
        library.addBook("firstBook", 23);
        library.addBook("secondBook", 3);
        library.addBook("thirdBook", 2);

        Optional<Integer> totalBookQuantity = library.getTotalBookQuantity();

        assertTrue(totalBookQuantity.isPresent());
        assertEquals(totalBookQuantity.get().intValue(), 28);
    }

    @Test
    void getTotalBookQuantity_WhenThereAreNotBooksInTheLibrary_OptionalIntZero() {
        Optional<Integer> totalBookQuantity = library.getTotalBookQuantity();

        assertTrue(totalBookQuantity.isPresent());
        assertEquals(totalBookQuantity.get().intValue(), 0);
    }

    @Test
    void decrementBookQuantity_WhenThereIsBookWithNonZeroQuantity_booleanTrueAndDecrementedBookQuantity() {
        String title = "I love java";
        library.addBook(title, 3);

        boolean successOfTheAttempt = library.decrementBookQuantity(title);
        Optional<Integer> bookQuantity = library.getBookQuantity(title);

        assertTrue(successOfTheAttempt);
        assertTrue(bookQuantity.isPresent());
        assertEquals(bookQuantity.get().intValue(), 2);
    }

    @Test
    void decrementBookQuantity_WhenThereIsNotBookInTheLibrary_booleanFalse() {
        String title = "I love java";

        boolean successOfTheAttempt = library.decrementBookQuantity(title);

        assertFalse(successOfTheAttempt);
    }

    @Test
    void deleteBook_WhenThereIsBookInTheLibrary_firstBooleanTrueSecondBooleanFalse() {
        String title = "I love java";
        library.addBook(title, 50);

        boolean successOfTheAttempt = library.deleteBook(title);
        Optional<Integer> bookQuantity = library.getBookQuantity(title);

        assertTrue(successOfTheAttempt);
        assertFalse(bookQuantity.isPresent());
    }
}