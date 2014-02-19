package at.grueneis.spengergasse.lesson_plan.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HashGenerationTest {
	private BasePersistable base0;
	private BasePersistable base1;
	
	@Before
	public void init(){
		base0 = new BasePersistable() {
			private int integer  = 100;
			private String string = "Name";
			@Override
			public String[] getAllAttributesAsString() {
				return new String[]{""+this.getId(), ""+integer, string};
			}
		};
		base0.setId(0l);
		base0.updateMd5Hash();
		
		base1 = new BasePersistable() {
			private int integer  = 100;
			private String string = "Name";
			@Override
			public String[] getAllAttributesAsString() {
				return new String[]{""+this.getId(), ""+integer, string};
			}
		};
		base1.setId(0l);
		base1.updateMd5Hash();
	}
	
	@Test
	public void hashEqualWithoutChanges(){
		base0.updateMd5Hash();
		String hash0 = base0.getMd5Hash();
		base0.updateMd5Hash();
		assertTrue(hash0.equals(base0.getMd5Hash()));
	}
	
	@Test
	public void hashEquivalent(){
		assertTrue(base0.getMd5Hash().equals(base1.getMd5Hash()));
	}
	
	@Test
	public void hashNotEquivalentAfterChanges(){
		base0.setId(10l);
		base0.updateMd5Hash();
		assertFalse(base0.getMd5Hash().equals(base1.getMd5Hash()));
	}
	
}
