package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookCrudController extends AbstractCrudController<Book> {
    public static final int MAX_PAGE_COUNT = 1000;
    private static final String[] ENTITY_UPDATEABLE_FIELDS = new String[]{"Name", "Author", "Genre", "Page count"};
    private AuthorCrudController authorCrudController;
    private GenreCrudController genreCrudController;

    public BookCrudController(Dao<Book> bookDao, AuthorCrudController authorCrudController, GenreCrudController genreCrudController) {
        super(bookDao, ENTITY_UPDATEABLE_FIELDS);
        this.authorCrudController = authorCrudController;
        this.genreCrudController = genreCrudController;
    }

    public String requestName() {
        System.out.print("Enter name: ");
        return ConsoleHelper.getScanner().nextLine();
    }

    public Book requestBook() {
        var books = getDao().findAll();

        System.out.println("Available books: ");

        if (books.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(books);
        }

        var chosenIndex = ConsoleHelper.requestInput("Enter book index: ", x -> x >= 0 && x < books.size());

        return books.get(chosenIndex);
    }

    public int requestPageCount() {
        return ConsoleHelper.requestInput("Enter page count: ", x -> x >= 0 && x < MAX_PAGE_COUNT);
    }

    @Override
    public void handleCreate() {
        var name = requestName();
        var pageCount = requestPageCount();
        var genre = genreCrudController.requestGenre();
        var author = authorCrudController.requestAuthor();

        var book = new Book(name, genre, author, pageCount);
        getDao().create(book);
    }

    @Override
    public void handleUpdate() {
        var updateableBook = requestBook();
        var updateableField = requestEntityUpdateableField();

        switch (updateableField) {
            case "Name":
                var name = requestName();
                updateableBook.setName(name);
                break;
            case "Author":
                var author = authorCrudController.requestAuthor();
                updateableBook.setAuthor(author);
                break;
            case "Genre":
                var genre = genreCrudController.requestGenre();
                updateableBook.setGenre(genre);
                break;
            case "Page count":
                var pageCount = requestPageCount();
                updateableBook.setPageCount(pageCount);
                break;
        }

        getDao().update(updateableBook, updateableBook.getId());
    }
}
