package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorCrudController extends AbstractCrudController<Author> {
    private static final String[] ENTITY_UPDATEABLE_FIELDS = new String[]{"First name", "Last name", "Phone"};

    public AuthorCrudController(Dao<Author> authorDao) {
        super(authorDao, ENTITY_UPDATEABLE_FIELDS);
    }

    public String requestFirstName() {
        System.out.print("Enter first name: ");
        return ConsoleHelper.getScanner().nextLine();
    }

    public String requestLastName() {
        System.out.print("Enter last name: ");
        return ConsoleHelper.getScanner().nextLine();
    }

    public String requestPhone() {
        System.out.print("Enter phone: ");
        return ConsoleHelper.getScanner().nextLine();
    }

    public Author requestAuthor() {
        System.out.println("Available authors: ");
        var authors = getDao().findAll();

        if (authors.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(authors);
        }

        var authorIndex = ConsoleHelper.requestInput("Enter author index: ", x -> x >= 0 && x < authors.size());

        return authors.get(authorIndex);
    }

    @Override
    public void handleCreate() {
        var firstName = requestFirstName();
        var lastName = requestLastName();
        var phone = requestPhone();

        var author = new Author(firstName, lastName, phone);
        getDao().create(author);
    }

    @Override
    public void handleUpdate() {
        var updateableAuthor = requestAuthor();
        var updateableField = requestEntityUpdateableField();

        switch (updateableField) {
            case "First name":
                var firstName = requestFirstName();
                updateableAuthor.setFirstName(firstName);
                break;
            case "Last name":
                var lastName = requestLastName();
                updateableAuthor.setLastName(lastName);
                break;
            case "Phone":
                var phone = requestPhone();
                updateableAuthor.setPhone(phone);
                break;
        }

        getDao().update(updateableAuthor, updateableAuthor.getId());
    }
}
