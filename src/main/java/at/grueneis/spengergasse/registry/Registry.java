package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 22/01/14.
 */
public class Registry {

    private static ArrayList<Entity> entities = new ArrayList<Entity>();

    public static void add(BasePersistable objectToAdd) throws EntityAlreadyAddedException {
        if (!entities.contains(new Entity(objectToAdd))) {
            entities.add(new Entity(objectToAdd));
        } else {
            throw new EntityAlreadyAddedException(objectToAdd);
        }
    }

    public static BasePersistable get(Long id, Class<BasePersistable> type) {
        for (Entity e : entities) {
            if (e.getObject().getId() == id) {
                return e.getObject();
            }
        }
        throw new EntityNotFoundException(id, type);
    }

    public static List<BasePersistable> getDirtyObjects() {
        return null;
    }

    public static void forceAdd(BasePersistable objectToAdd) {
        if (entities.contains(objectToAdd)) {
            entities.remove(new Entity(objectToAdd));
        }
        entities.add(new Entity(objectToAdd));
    }
}
