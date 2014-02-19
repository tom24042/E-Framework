package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.Lesson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Daniel on 22/01/14.
 */
public class RegistryTest {

    @Before
    public void cleanup() {
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
    public void addEntityToRegistryAndGetItAgainByID() throws EntityAlreadyAddedException {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);

        Registry.add(objectToAdd);

        EFAttributeTestClass objectFromRegistry = (EFAttributeTestClass) Registry.get((long) 1, EFAttributeTestClass.class);
        Assert.assertTrue(objectFromRegistry.getId() == 1);
    }

    @Test
    public void tryAddEmptyObject() throws EntityAlreadyAddedException {
        Registry.add(null);
    }

    @Test
    public void forceAddObject() {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);
        Registry.forceAdd(objectToAdd);

        Assert.assertTrue(Registry.get(objectToAdd.getId(), EFAttributeTestClass.class) != null);
    }

    @Test
    public void forceAddObjectTwice() {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);
        objectToAdd.setString("Sad");
        Registry.forceAdd(objectToAdd);

        EFAttributeTestClass duplicateObject = new EFAttributeTestClass(1);
        duplicateObject.setString("Happy");
        Registry.forceAdd(duplicateObject);

        Assert.assertTrue(Registry.getDirtyObjects().size() == 1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void tryGetNonExistentObject(){
        Registry.get((long) 1, EFAttributeTestClass.class);
    }

    @Test(expected =  EntityNotFoundException.class)
    public void tryCleanNullObject(){
        Registry.clean((EFPersistable) null);
    }

    @Test (expected = EntityNotFoundException.class)
    public void tryCleanObjectWhichIsNotInRegistry(){
        Registry.clean(new EFPersistable() {
            @Override
            public Long getId() {
                return (long) 123;
            }
        });
    }

    @Test
    public void addObjectWithReferenceAndChangeReference() {
        EFAttributeTestClass objectToAdd = new EFAttributeTestClass(1);
        EFAttributeTestClass reference = new EFAttributeTestClass(2);

        reference.setString("Not Daniel D:");
        objectToAdd.setChild(reference);

        Registry.add(objectToAdd);

        reference.setString("Daniel :D");
        Assert.assertTrue(Registry.getDirtyObjects().size() == 1);
    }

    @Test
    public void addObjectWithReferenceAndChangeBoth() {
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
    public void makeObjectDirtyAndCleanItAgain() {
        EFAttributeTestClass empty = new EFAttributeTestClass(1);
        empty.setString("Foo");

        Registry.add(empty);
        Registry.clean(empty);

        Assert.assertTrue(Registry.getDirtyObjects().size() == 0);
    }

    @Test
    public void makeDirtyObjectsAndCleanAll() {
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
    @Test(expected = EntityNotFoundException.class)
    public void cleanWithNotAddedObject()
    {
        EFAttributeTestClass foo = new EFAttributeTestClass(1);
        Registry.clean(foo);
    }

    @Test
    public void addTwoDifferentObjectsAndGetBoth(){
        EFAttributeTestClass foo = new EFAttributeTestClass(1);
        EFAttributeTestClassTwo bar = new EFAttributeTestClassTwo(1);

        foo.setString("Hello");
        bar.setString("Yellow");

        try {
            Registry.add(foo);
            Registry.add(bar);
        }
        catch(EntityAlreadyAddedException e){
            Assert.fail("Two entities of different classes with same IDs couldn't be added both");
        }

        foo.setString("Hello");
        bar.setString("Yellow");

        Assert.assertTrue(Registry.getDirtyObjects().size() == 0);

        try {
            EFAttributeTestClass fooReturn = (EFAttributeTestClass) Registry.get(foo.getId(), EFAttributeTestClass.class);
            EFAttributeTestClassTwo barReturn = (EFAttributeTestClassTwo) Registry.get(bar.getId(), EFAttributeTestClassTwo.class);

            Assert.assertTrue(fooReturn != null);
            Assert.assertTrue(barReturn != null);
        }
        catch(EntityNotFoundException e){
            Assert.fail("Couldn't find both entities");
        }
    }
}
