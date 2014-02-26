package at.grueneis.spengergasse.registry;

/**
 * Created by gradnig on 31.01.14.
 */
public class EntityAlreadyAddedException extends RuntimeException {
    private EFPersistable failedObject;

    public EntityAlreadyAddedException(EFPersistable addedObject) {
        failedObject = addedObject;
    }

    public EFPersistable getFailedObject() {
        return failedObject;
    }
}
