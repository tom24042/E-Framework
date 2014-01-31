package at.grueneis.spengergasse.registry;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Daniel on 22/01/14.
 */
public class EntityTest {

	@Test
	public void createEntityFromEmptyObject() {
		Object empty = new Object();
		Entity entity = new Entity(empty);

		assertThat(empty, is(entity.getObject()));
	}
}
