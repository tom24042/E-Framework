package at.grueneis.spengergasse.registry;

/**
 * Internal class used for saving objects and information
 */

public class Entity {
	private Object originalObject;

	public Entity(Object objectToSave) {
		this.originalObject = objectToSave;
	}

	public Object getObject() {
		return this.originalObject;
	}
}
