package at.grueneis.spengergasse.registry5;

import org.junit.Test;

/**
 * Created by Daniel on 22/01/14.
 */
public class RegistryTest {



	@Test
	public void addObjectToRegistry() {
		Parent parent = new Parent();

		Registry.add(parent);
	}

	@Test
	public void addObjectToRegistryTwice() {
		Parent parent = new Parent();

		Registry.add(parent);
		Registry.add(parent);
	}
}
