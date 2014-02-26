package at.grueneis.spengergasse.registry;

import java.lang.reflect.Method;
import java.rmi.registry.*;

/**
 * Internal class used for saving objects and information
 */

public class Entity {
    private EFPersistable originalObject;
    private int originalHashValue;
    private boolean dirtyFlag;

    public Entity(EFPersistable objectToSave) {
        this.originalObject = objectToSave;
        this.originalHashValue = calculateHashValueOfOriginalObject();
        addMissingReferencesToRegistry();
    }

    protected EFPersistable getObject() {
        return this.originalObject;
    }

    public boolean isObjectDirty() {
        return (originalHashValue != calculateHashValueOfOriginalObject()) || dirtyFlag;
    }

    private int calculateHashValueOfOriginalObject() {
        Class originalObjectClass = originalObject.getClass();
        Method[] methods = originalObjectClass.getDeclaredMethods();

        int hashValue = 0;

        for (Method method : methods) {
            if (method.getParameterTypes().length == 0) {
                if (method.isAnnotationPresent(EFAttribute.class)) {
                    try {
                        hashValue += (method.invoke(originalObject, null) + "").hashCode();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (method.isAnnotationPresent(EFReference.class)) {
                    try {
                        EFPersistable reference = (EFPersistable) (method.invoke(originalObject, null));
                        if (reference != null)
                            hashValue += reference.getId().hashCode();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return hashValue;
    }

    private void addMissingReferencesToRegistry() {
        Class originalObjectClass = originalObject.getClass();
        Method[] methods = originalObjectClass.getDeclaredMethods();

        int hashValue = 0;

        for (Method method : methods) {
            if (method.getParameterTypes().length == 0) {
                if (method.isAnnotationPresent(EFReference.class)) {
                    try {
                        Registry.getInstance().add((EFPersistable) (method.invoke(originalObject, null)));
                    } catch (EntityAlreadyAddedException ex) {

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Entity) {
            return originalObject.getId() == ((Entity) other).getObject().getId() && originalObject.getClass().equals(((Entity) other).getObject().getClass());
        } else {
            return false;
        }
    }

    protected void markDirty() {
        dirtyFlag = true;
    }

    protected void markClean() {
        dirtyFlag = false;
        originalHashValue = calculateHashValueOfOriginalObject();
    }

    protected boolean compareWithOtherEntity(Entity other) {
        return other.calculateHashValueOfOriginalObject() == calculateHashValueOfOriginalObject();
    }
}
