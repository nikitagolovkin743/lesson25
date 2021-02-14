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
        List<T> entities = dao.findAll();
        printEntities(entities);
    }

    public abstract void handleUpdate();

    public void handleDelete() {
        List<T> entities = dao.findAll();
        printEntities(entities);

        int chosenEntityToDeleteIndex = ConsoleHelper.requestIndexInput("Choose entity to delete:", entities);
        T chosenEntityToDelete = entities.get(chosenEntityToDeleteIndex);

        dao.delete(chosenEntityToDelete.getId());
    }

    protected String requestEntityUpdateableField() {
        for (int i = 0; i < ENTITY_UPDATEABLE_FIELDS.length; i++) {
            System.out.printf("%d. %s\n", i + 1, ENTITY_UPDATEABLE_FIELDS[i]);
        }

        int chosenIndex = ConsoleHelper.requestIndexInput("Choose the index of a field to update: ", ENTITY_UPDATEABLE_FIELDS);

        return ENTITY_UPDATEABLE_FIELDS[chosenIndex];
    }

    protected void printEntities(List<T> entities) {
        for (int i = 0; i < entities.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, entities.get(i));
        }
    }

    protected Dao<T> getDao() {
        return dao;
    }
}
