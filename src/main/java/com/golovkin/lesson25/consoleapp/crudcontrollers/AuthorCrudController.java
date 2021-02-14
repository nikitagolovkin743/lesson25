package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Author;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<Author> authors = getDao().findAll();

        if (authors.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(authors);
        }

        int authorIndex = ConsoleHelper.requestIndexInput("Enter author index: ", authors);

        return authors.get(authorIndex);
    }

    @Override
    public void handleCreate() {
        String firstName = requestFirstName();
        String lastName = requestLastName();
        String phone = requestPhone();

        Author author = new Author(firstName, lastName, phone);
        getDao().create(author);
    }

    @Override
    public void handleUpdate() {
        Author updateableAuthor = requestAuthor();
        String updateableField = requestEntityUpdateableField();

        switch (updateableField) {
            case "First name":
                String firstName = requestFirstName();
                updateableAuthor.setFirstName(firstName);
                break;
            case "Last name":
                String lastName = requestLastName();
                updateableAuthor.setLastName(lastName);
                break;
            case "Phone":
                String phone = requestPhone();
                updateableAuthor.setPhone(phone);
                break;
        }

        getDao().update(updateableAuthor, updateableAuthor.getId());
    }
}
