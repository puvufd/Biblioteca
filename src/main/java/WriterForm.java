import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class WriterForm extends JFrame {
    private JList<String> bookList;
    private DefaultListModel<String> bookListModel;
    private JTextArea bookDescriptionArea;
    private JButton addToFavoritesButton;
    private JButton viewFavoritesButton;
    private JButton createBookButton;
    private JButton viewCreatedBooksButton;
    private JPanel bookPanel;
    private JPanel descriptionPanel;
    private List<String> favorites;
    private BookModel bookModel;

    private boolean isEnglish;

    public WriterForm(boolean isEnglish, BookModel bookModel) {
        this.isEnglish = isEnglish;
        this.bookModel = bookModel;
        favorites = new ArrayList<>();

        setTitle(isEnglish ? "Writer Form" : "Форма писателя");
        setSize(600, 500);
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
        createBookButton = new JButton(isEnglish ? "Create New Book" : "Создать новую книгу");
        viewCreatedBooksButton = new JButton(isEnglish ? "View My Books" : "Просмотр моих книг");

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
        buttonPanel.add(createBookButton);
        buttonPanel.add(viewCreatedBooksButton);

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
        createBookButton.addActionListener(e -> openCreateBookDialog());
        viewCreatedBooksButton.addActionListener(e -> showCreatedBooks());
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

    private void openCreateBookDialog() {
        JDialog dialog = new JDialog(this, isEnglish ? "Create New Book" : "Создать новую книгу", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        panel.add(new JLabel(isEnglish ? "Book Title:" : "Название книги:"));
        panel.add(titleField);
        panel.add(new JLabel(isEnglish ? "Book Description:" : "Описание книги:"));
        panel.add(new JScrollPane(descriptionArea));

        JButton createButton = new JButton(isEnglish ? "Create" : "Создать");
        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            if (!title.isEmpty() && !description.isEmpty()) {
                bookModel.addBook(title, description);
                loadBooks(); // Обновить список книг
                dialog.dispose();
                JOptionPane.showMessageDialog(this, title + (isEnglish ? " has been created." : " была создана."));
            } else {
                JOptionPane.showMessageDialog(this, isEnglish ? "Please enter both title and description." : "Пожалуйста, введите название и описание.");
            }
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(createButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showCreatedBooks() {
        StringBuilder createdBooksList = new StringBuilder(isEnglish ? "My Books:\n" : "Мои книги:\n");
        for (BookModel.Book book : bookModel.getBooks()) {
            createdBooksList.append(book.getTitle()).append("\n");
        }
        JOptionPane.showMessageDialog(this, createdBooksList.toString());
    }

    public void updateLanguage(boolean isEnglish) {
        this.isEnglish = isEnglish;
        setTitle(isEnglish ? "Writer Form" : "Форма писателя");
        addToFavoritesButton.setText(isEnglish ? "Add to Favorites" : "Добавить в избранное");
        viewFavoritesButton.setText(isEnglish ? "Favorites" : "Избранное");
        createBookButton.setText(isEnglish ? "Create New Book" : "Создать новую книгу");
        viewCreatedBooksButton.setText(isEnglish ? "View My Books" : "Просмотр моих книг");
        bookPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Books" : "Книги"));
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(isEnglish ? "Description" : "Описание"));
    }
}