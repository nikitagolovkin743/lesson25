package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Genre;
import org.springframework.stereotype.Component;

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
        var genres = getDao().findAll();

        if (genres.isEmpty()) {
            throw new IllegalStateException("There are no available entities");
        } else {
            printEntities(genres);
        }

        var genreIndex = ConsoleHelper.requestInput("Enter genre index: ", x -> x >= 0 && x < genres.size());

        return genres.get(genreIndex);
    }

    @Override
    public void handleCreate() {
        var name = requestName();

        var genre = new Genre(name);

        getDao().create(genre);
    }

    @Override
    public void handleUpdate() {
        var updateableGenre = requestGenre();
        var updateableField = requestEntityUpdateableField();

        if (updateableField.equals("Name")) {
            var name = requestName();
            updateableGenre.setName(name);
        }

        getDao().update(updateableGenre, updateableGenre.getId());
    }
}
