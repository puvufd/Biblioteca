import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private List<Book> books;

    public BookModel() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String description) {
        books.add(new Book(title, description));
    }

    public List<Book> getBooks() {
        return books;
    }

    public class Book {
        private String title;
        private String description;

        public Book(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}