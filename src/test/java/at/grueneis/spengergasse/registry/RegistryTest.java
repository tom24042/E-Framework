package at.grueneis.spengergasse.registry;

import org.junit.Test;

/**
 * Created by Daniel on 22/01/14.
 */
public class RegistryTest {



	@Test
	public void addObjectToRegistry() {
		EFAttributeTestClass parent = new EFAttributeTestClass();

		Registry.add(parent);
	}

	@Test
	public void addObjectToRegistryTwice() {
		EFAttributeTestClass parent = new EFAttributeTestClass();

		Registry.add(parent);
		Registry.add(parent);
	}
}
