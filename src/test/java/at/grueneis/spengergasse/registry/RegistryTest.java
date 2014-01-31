package at.grueneis.spengergasse.registry;

import org.junit.Test;

/**
 * Created by Daniel on 22/01/14.
 */
public class RegistryTest {


    @Test
    public void addObjectToRegistry() throws EntityAlreadyAddedException {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);

        Registry.add(objectToAdd);
    }

    @Test(expected = EntityAlreadyAddedException.class)
    public void addObjectToRegistryTwice() throws EntityAlreadyAddedException {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);

        Registry.add(objectToAdd);
        Registry.add(objectToAdd);
    }
}
