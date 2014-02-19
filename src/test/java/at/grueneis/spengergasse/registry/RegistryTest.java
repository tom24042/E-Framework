package at.grueneis.spengergasse.registry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Daniel on 22/01/14.
 */
public class RegistryTest {

    @Before
    public void cleanup(){
        Registry.emptyRegistry();
    }

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

    @Test
    public void createMultipleEntitiesAndMakeThemDirty() throws EntityAlreadyAddedException {
        EFAttributeTestClass unmodifiedObject = new EFAttributeTestClass(1);
        EFAttributeTestClass modifiedObject = new EFAttributeTestClass(5);

        unmodifiedObject.setString("Hansi");
        modifiedObject.setString("Herbert");

        Registry.add(unmodifiedObject);
        Registry.add(modifiedObject);
        modifiedObject.setString("Hugo");

        Assert.assertTrue(Registry.getDirtyObjects().size() == 1);
    }

    @Test
    public void addEntityToRegistryAndGetItAgainByID() throws EntityAlreadyAddedException  {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);

        Registry.add(objectToAdd);

        EFAttributeTestClass objectFromRegistry = (EFAttributeTestClass) Registry.get((long)1, EFAttributeTestClass.class);
        Assert.assertTrue(objectFromRegistry.getId() == 1);
    }

    @Test
    public void tryAddEmptyObject() throws EntityAlreadyAddedException{
        Registry.add(null);
    }

    @Test
    public void addObjectWithReferenceAndChangeReference(){
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);
        EFAttributeTestClass reference = new EFAttributeTestClass(2);

        reference.setString("Not Daniel D:");
        objectToAdd.setChild(reference);

        Registry.add(objectToAdd);

        reference.setString("Daniel :D");
        Assert.assertTrue(Registry.getDirtyObjects().size() == 1);
    }

    @Test
    public void addObjectWithReferenceAndChangeBoth(){
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);
        EFAttributeTestClass reference = new EFAttributeTestClass(2);

        objectToAdd.setString("Hugo");
        reference.setString("Not Daniel D:");
        objectToAdd.setChild(reference);

        Registry.add(objectToAdd);

        objectToAdd.setString("Herbert :/");
        reference.setString("Daniel :D");
        Assert.assertTrue(Registry.getDirtyObjects().size() == 2);
    }

    @Test
    public void makeObjectDirtyAndCleanItAgain(){
        EFAttributeTestClass empty = new EFAttributeTestClass(1);
        empty.setString("Foo");

        Registry.add(empty);
        Registry.clean(empty);

        Assert.assertTrue(Registry.getDirtyObjects().size() == 0);
    }

    @Test
    public void makeDirtyObjectsAndCleanAll(){
        EFAttributeTestClass foo = new EFAttributeTestClass(1);
        EFAttributeTestClass bar = new EFAttributeTestClass(2);
        EFAttributeTestClass tom = new EFAttributeTestClass(3);

        foo.setString("Foo");
        bar.setString("Bar");
        tom.setString("Tom");

        Registry.add(foo);
        Registry.add(bar);
        Registry.add(tom);

        foo.setString("WRONG");
        bar.setString("WRONG");
        tom.setString("WRONG");

        Assert.assertTrue(Registry.getDirtyObjects().size() == 3);
        Registry.clean(Registry.getDirtyObjects());
        Assert.assertTrue(Registry.getDirtyObjects().size() == 0);
    }
}
