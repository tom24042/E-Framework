package at.grueneis.spengergasse.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel on 22/01/14.
 */
public class Registry {

    private static ArrayList<Entity> entities = new ArrayList<Entity>();

    public static void add(EFPersistable objectToAdd) throws EntityAlreadyAddedException {
        if (!entities.contains(new Entity(objectToAdd))) {
            entities.add(new Entity(objectToAdd));
        } else {
            throw new EntityAlreadyAddedException(objectToAdd);
        }
    }

    public static EFPersistable get(Long id, Class<EFPersistable> type) {
        for (Entity e : entities) {
            if (e.getObject().getId() == id) {
                return e.getObject();
            }
        }
        throw new EntityNotFoundException(id, type);
    }

    public static Collection<EFPersistable> getDirtyObjects() {
        ArrayList<EFPersistable> dirtyEntities = new ArrayList<>();
        for(Entity entity: entities)
        {
            if(entity.isObjectDirty())
                dirtyEntities.add(entity.getObject());
        }

        return Collections.unmodifiableCollection(dirtyEntities);
    }

    public static void forceAdd(EFPersistable objectToAdd) {
        if (entities.contains(objectToAdd)) {
            entities.remove(new Entity(objectToAdd));
        }
        Entity newEntity = new Entity(objectToAdd);
        newEntity.markDirty();
        entities.add(newEntity);

    }
}
