import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class ReaderForm extends JFrame {
    private JList<String> bookList;
    private DefaultListModel<String> bookListModel;
    private JTextArea bookDescriptionArea;
    private JButton addToFavoritesButton;
    private JButton viewFavoritesButton;
    private JPanel bookPanel;
    private JPanel descriptionPanel;
    private List<String> favorites;
    private BookModel bookModel;

    private boolean isEnglish;

    public ReaderForm(boolean isEnglish, BookModel bookModel) {
        this.isEnglish = isEnglish;
        this.bookModel = bookModel;
        favorites = new ArrayList<>();

        setTitle(isEnglish ? "Reader Form" : "Форма читателя");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Настройка основных компонентов
        bookListModel = new DefaultListModel<>();
        bookList = new JList<>(bookListModel);
        bookDescriptionArea = new JTextArea(10, 30);
        bookDescriptionArea.setEditable(false);
        bookDescriptionArea.setLineWrap(true);
        bookDescriptionArea.setWrapStyleWord(true);
        bookDescriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bookDescriptionArea.setBackground(new Color(245, 245, 245));

        addToFavoritesButton = new JButton(isEnglish ? "Add to Favorites" : "Добавить в избранное");
        viewFavoritesButton = new JButton(isEnglish ? "Favorites" : "Избранное");

        // Панель с книгами
        bookPanel = new JPanel(new BorderLayout());
        bookPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Books" : "Книги"));
        bookPanel.add(new JScrollPane(bookList), BorderLayout.CENTER);
        bookPanel.setPreferredSize(new Dimension(200, 0));

        // Панель с описанием
        descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Description" : "Описание"));
        descriptionPanel.add(new JScrollPane(bookDescriptionArea), BorderLayout.CENTER);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addToFavoritesButton);
        buttonPanel.add(viewFavoritesButton);

        // Основная компоновка
        setLayout(new BorderLayout(10, 10));
        add(bookPanel, BorderLayout.WEST);
        add(descriptionPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        // Заполнение списка книг
        loadBooks();

        // Обработчики событий
        bookList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedBook = bookList.getSelectedValue();
                if (selectedBook != null) {
                    showBookDescription(selectedBook);
                }
            }
        });

        addToFavoritesButton.addActionListener(e -> addBookToFavorites());
        viewFavoritesButton.addActionListener(e -> showFavorites());
    }

    private void loadBooks() {
        bookListModel.clear();
        for (BookModel.Book book : bookModel.getBooks()) {
            bookListModel.addElement(book.getTitle());
        }
    }

    private void showBookDescription(String bookTitle) {
        for (BookModel.Book book : bookModel.getBooks()) {
            if (book.getTitle().equals(bookTitle)) {
                bookDescriptionArea.setText(book.getDescription());
                return;
            }
        }
        bookDescriptionArea.setText("");
    }

    private void addBookToFavorites() {
        String selectedBook = bookList.getSelectedValue();
        if (selectedBook != null && !favorites.contains(selectedBook)) {
            favorites.add(selectedBook);
            JOptionPane.showMessageDialog(this, selectedBook + (isEnglish ? " added to favorites!" : " добавлена в избранное!"));
        }
    }

    private void showFavorites() {
        StringBuilder favoritesList = new StringBuilder(isEnglish ? "Favorite Books:\n" : "Избранные книги:\n");
        for (String favorite : favorites) {
            favoritesList.append(favorite).append("\n");
        }
        JOptionPane.showMessageDialog(this, favoritesList.toString());
    }

    public void updateLanguage(boolean isEnglish) {
        this.isEnglish = isEnglish;
        setTitle(isEnglish ? "Reader Form" : "Форма читателя");
        addToFavoritesButton.setText(isEnglish ? "Add to Favorites" : "Добавить в избранное");
        viewFavoritesButton.setText(isEnglish ? "Favorites" : "Избранное");
        bookPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Books" : "Книги"));
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Description" : "Описание"));
    }
}