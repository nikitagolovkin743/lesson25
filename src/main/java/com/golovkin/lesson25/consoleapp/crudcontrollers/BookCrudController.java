package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Author;
import com.golovkin.lesson25.model.Book;
import com.golovkin.lesson25.model.Genre;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<Book> books = getDao().findAll();

        System.out.println("Available books: ");

        if (books.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(books);
        }

        int chosenIndex = ConsoleHelper.requestInput("Enter book index: ", x -> x >= 0 && x < books.size());

        return books.get(chosenIndex);
    }

    public int requestPageCount() {
        return ConsoleHelper.requestInput("Enter page count: ", x -> x >= 0 && x < MAX_PAGE_COUNT);
    }

    @Override
    public void handleCreate() {
        String name = requestName();
        int pageCount = requestPageCount();
        Genre genre = genreCrudController.requestGenre();
        Author author = authorCrudController.requestAuthor();

        Book book = new Book(name, genre, author, pageCount);
        getDao().create(book);
    }

    @Override
    public void handleUpdate() {
        Book updateableBook = requestBook();
        String updateableField = requestEntityUpdateableField();

        switch (updateableField) {
            case "Name":
                String name = requestName();
                updateableBook.setName(name);
                break;
            case "Author":
                Author author = authorCrudController.requestAuthor();
                updateableBook.setAuthor(author);
                break;
            case "Genre":
                Genre genre = genreCrudController.requestGenre();
                updateableBook.setGenre(genre);
                break;
            case "Page count":
                int pageCount = requestPageCount();
                updateableBook.setPageCount(pageCount);
                break;
        }

        getDao().update(updateableBook, updateableBook.getId());
    }
}
