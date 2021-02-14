package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Genre;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreCrudController extends AbstractCrudController<Genre> {
    private static final String[] ENTITY_UPDATEABLE_FIELDS = new String[]{"Name"};

    public GenreCrudController(Dao<Genre> genreDao) {
        super(genreDao, ENTITY_UPDATEABLE_FIELDS);
    }

    public String requestName() {
        System.out.print("Enter name: ");
        return ConsoleHelper.getScanner().nextLine();
    }

    public Genre requestGenre() {
        System.out.println("Available genres: ");
        List<Genre> genres = getDao().findAll();

        if (genres.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(genres);
        }

        int genreIndex = ConsoleHelper.requestIndexInput("Enter genre index: ", genres);

        return genres.get(genreIndex);
    }

    @Override
    public void handleCreate() {
        String name = requestName();

        Genre genre = new Genre(name);

        getDao().create(genre);
    }

    @Override
    public void handleUpdate() {
        Genre updateableGenre = requestGenre();
        String updateableField = requestEntityUpdateableField();

        if (updateableField.equals("Name")) {
            String name = requestName();
            updateableGenre.setName(name);
        }

        getDao().update(updateableGenre, updateableGenre.getId());
    }
}
