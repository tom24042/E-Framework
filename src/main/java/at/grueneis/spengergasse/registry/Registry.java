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
        if (objectToAdd != null) {
            if (!entities.contains(new Entity(objectToAdd))) {
                entities.add(new Entity(objectToAdd));
            } else {
                throw new EntityAlreadyAddedException(objectToAdd);
            }
        }
    }

    public static EFPersistable get(Long id, Class type) {
        for (Entity e : entities) {
            if (e.getObject().getId() == id && e.getObject().getClass() == type) {
                return e.getObject();
            }
        }
        throw new EntityNotFoundException(id, type);
    }

    public static List<EFPersistable> getDirtyObjects() {
        ArrayList<EFPersistable> dirtyEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.isObjectDirty())
                dirtyEntities.add(entity.getObject());
        }

        return Collections.unmodifiableList(dirtyEntities);
    }

    public static void forceAdd(EFPersistable objectToAdd) {
        Entity oldEntity = getEntityById(objectToAdd.getId());
        if (oldEntity != null) {
            entities.remove(oldEntity);
            Entity newEntity = new Entity(objectToAdd);
            if (oldEntity.compareWithOtherEntity(newEntity)) ;
            newEntity.markDirty();
            entities.add(newEntity);
        } else {
            entities.add(new Entity(objectToAdd));
        }
    }

    private static Entity getEntityById(Long id) {
        for (Entity e : entities) {
            if (e.getObject().getId() == id)
                return e;
        }
        return null;
    }

    public static void emptyRegistry() {
        entities = new ArrayList<Entity>();
    }

    public static void clean(EFPersistable entity){
        getEntityById(entity.getId()).markClean();
    }

    public static void clean(List<EFPersistable> list){
        for(EFPersistable e : list){
            clean(e);
        }
    }
}
