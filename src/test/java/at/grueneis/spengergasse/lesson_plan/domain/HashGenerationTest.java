package at.grueneis.spengergasse.lesson_plan.domain;

import static junit.framework.Assert.*;

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
				return new String[]{""+this.getId(), ""+integer, string, this.getMd5Hash()};
			}
		};
		base0.setId(0l);
		base0.updateMd5Hash();
		
		base1 = new BasePersistable() {
			private int integer  = 100;
			private String string = "Name";
			@Override
			public String[] getAllAttributesAsString() {
				return new String[]{""+this.getId(), ""+integer, string, this.getMd5Hash()};
			}
		};
		base1.setId(0l);
		base1.updateMd5Hash();
	}
	
	@Test
	public void hashEquivalent(){
		assertTrue(base0.getMd5Hash().equals(base1.getMd5Hash()));
	}
	
	@Test
	public void equivalentHashOneAttributeChanged(){
		base0.setId(2l);
		assertTrue(base0.getMd5Hash().equals(base1.getMd5Hash()));
	}
}
