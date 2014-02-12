package at.grueneis.spengergasse.registry;

/**
 * Created by gradnig on 31.01.14.
 */
public class EntityNotFoundException extends RuntimeException{
    private String errorMessage;

    public EntityNotFoundException(long idOfObject, Class<EFPersistable> classOfObject) {
        errorMessage = "Entity of Type [" + classOfObject.getSimpleName() + "] with ID [" + idOfObject + "] was not found in Registry";
    }
}
