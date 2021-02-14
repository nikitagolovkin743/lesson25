package com.golovkin.lesson25.consoleapp;

import com.golovkin.lesson25.consoleapp.crudcontrollers.AbstractCrudController;
import com.golovkin.lesson25.model.Identifiable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConsoleApp {
    private Map<Class<? extends Identifiable>, AbstractCrudController<? extends Identifiable>> crudControllers;

    public ConsoleApp(List<AbstractCrudController<? extends Identifiable>> crudControllerList) {
        initializeCrudControllers(crudControllerList);
    }

    private void initializeCrudControllers(List<AbstractCrudController<? extends Identifiable>> crudControllerList) {
        this.crudControllers = new HashMap<>();
        for (AbstractCrudController<? extends Identifiable> crudController : crudControllerList) {
            Class clazz = (Class) ((ParameterizedType) crudController.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            crudControllers.put(clazz, crudController);
        }
    }

    public void run() {
        Class[] availableEntityTypes = getAvailableEntityTypes();

        while (true) {
            AbstractCrudController<? extends Identifiable> chosenCrudController = chooseEntityType(availableEntityTypes);
            CrudActions chosenCrudAction = chooseCrudAction();

            try {
                switch (chosenCrudAction) {
                    case CREATE:
                        chosenCrudController.handleCreate();
                        break;
                    case READ:
                        chosenCrudController.handleRead();
                        break;
                    case UPDATE:
                        chosenCrudController.handleUpdate();
                        break;
                    case DELETE:
                        chosenCrudController.handleDelete();
                        break;
                }
            } catch (DataAccessException e) {
                System.out.println("There was an error while working with the database");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Class[] getAvailableEntityTypes() {
        return crudControllers.keySet().toArray(Class[]::new);
    }

    private AbstractCrudController<? extends Identifiable> chooseEntityType(Class[] entityTypes) {
        printEntityTypes(entityTypes);

        int chosenIndex = ConsoleHelper.requestIndexInput("Choose entity: ", entityTypes);
        Class chosenEntityType = entityTypes[chosenIndex];

        return crudControllers.get(chosenEntityType);
    }

    private void printEntityTypes(Class[] entityTypes) {
        for (int i = 0; i < entityTypes.length; i++) {
            String entityName = entityTypes[i].getSimpleName();
            System.out.printf("%d. %s\n", i + 1, entityName);
        }
    }

    private CrudActions chooseCrudAction() {
        CrudActions[] crudActions = CrudActions.values();
        printCrudActions(crudActions);

        int chosenIndex = ConsoleHelper.requestIndexInput("Choose CRUD action: ", crudActions);

        return crudActions[chosenIndex];
    }

    private void printCrudActions(CrudActions[] crudActions) {
        for (int i = 0; i < crudActions.length; i++) {
            CrudActions crudAction = crudActions[i];
            System.out.printf("%d. %s\n", i + 1, crudAction);
        }
    }
}
