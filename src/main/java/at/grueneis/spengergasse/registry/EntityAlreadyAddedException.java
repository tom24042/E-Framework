package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;

/**
 * Created by gradnig on 31.01.14.
 */
public class EntityAlreadyAddedException extends Exception {
    private BasePersistable failedObject;

    public EntityAlreadyAddedException(BasePersistable addedObject) {
        failedObject = addedObject;
    }

    public BasePersistable getFailedObject() {
        return failedObject;
    }
}
