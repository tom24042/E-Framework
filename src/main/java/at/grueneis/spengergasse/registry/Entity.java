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

    public BasePersistable getObject() {
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
                for (Annotation a : Arrays.asList(method.getAnnotations())) {
                    if (a.annotationType().equals(EFAttribute.class)) {
                        try {
                            hashValue += method.invoke(null).hashCode();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        }

        return hashValue;
    }
}
