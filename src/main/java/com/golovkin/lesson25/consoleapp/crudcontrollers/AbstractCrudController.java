package com.golovkin.lesson25.consoleapp.crudcontrollers;

import com.golovkin.lesson25.consoleapp.ConsoleHelper;
import com.golovkin.lesson25.dao.Dao;
import com.golovkin.lesson25.model.Identifiable;

import java.util.List;

public abstract class AbstractCrudController<T extends Identifiable> {
    private final String[] ENTITY_UPDATEABLE_FIELDS;

    private Dao<T> dao;

    public AbstractCrudController(Dao<T> dao, String[] entityUpdateableFields) {
        this.dao = dao;
        ENTITY_UPDATEABLE_FIELDS = entityUpdateableFields;
    }

    public abstract void handleCreate();

    public void handleRead() {
        var entities = dao.findAll();
        printEntities(entities);
    }

    public abstract void handleUpdate();

    public void handleDelete() {
        var entities = dao.findAll();
        printEntities(entities);

        var chosenEntityToDeleteIndex = ConsoleHelper.requestInput("Choose entity to delete:", x -> x < entities.size() && x >= 0);
        var chosenEntityToDelete = entities.get(chosenEntityToDeleteIndex);

        dao.delete(chosenEntityToDelete.getId());
    }

    protected String requestEntityUpdateableField() {
        for (var i = 0; i < ENTITY_UPDATEABLE_FIELDS.length; i++) {
            System.out.printf("%d. %s\n", i + 1, ENTITY_UPDATEABLE_FIELDS[i]);
        }

        var chosenIndex = ConsoleHelper.requestInput("Choose the index of a field to update: ", x -> x >= 0 && x < ENTITY_UPDATEABLE_FIELDS.length);

        return ENTITY_UPDATEABLE_FIELDS[chosenIndex];
    }

    protected void printEntities(List<T> entities) {
        for (var i = 0; i < entities.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, entities.get(i));
        }
    }

    protected Dao<T> getDao() {
        return dao;
    }
}
