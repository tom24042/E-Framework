package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Internal class used for saving objects and information
 */

public class Entity {
    private BasePersistable originalObject;
    private int originalHashValue;

    public Entity(BasePersistable objectToSave) {
        this.originalObject = objectToSave;
        this.originalHashValue = calculateHashValueOfOriginalObject();
    }

    protected BasePersistable getObject() {
        return this.originalObject;
    }

    public boolean isObjectDirty() {
        return originalHashValue != calculateHashValueOfOriginalObject();
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
            }
        }

        return hashValue;
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
}
