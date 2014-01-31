package at.grueneis.spengergasse.registry;

import at.grueneis.spengergasse.lesson_plan.domain.BasePersistable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 22/01/14.
 */
public class Registry {

    private HashMap<String, BasePersistable> m_Entities;

	public static void add(BasePersistable objectToAdd) {


	}

    public static BasePersistable get(Long id, Class<BasePersistable> type) {
        return null;
    }

    public static List<BasePersistable> getDirtyObjects() {
        return null;
    }
}
