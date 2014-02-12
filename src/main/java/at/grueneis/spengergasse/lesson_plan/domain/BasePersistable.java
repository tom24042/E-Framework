/*
 * Joachim Grüneis
 * Copyright (C) 2013
 * All rights reserved.
 */
package at.grueneis.spengergasse.lesson_plan.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A common base class for persistable domain classes that holds the id
 * attribute and implements equals, hashCode and toString using the id attribute
 * only.
 */
public abstract class BasePersistable {

   
	

	private Long id;
    private String md5Hash;
    
    public String getMd5Hash() {
		return md5Hash;
	}

    public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasePersistable that = (BasePersistable) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + "]";
    }
    
    /**
     * -) If the attribute is an object reference, the value in the string should be the ID of the referenced object.
     * -) If the attribute is a list containing object references , the value should be the IDs of all elements in the list
     * @return String[]: Returns a Stringarray containing the values of all attributes. (1 element per attribute). 
     */
    public abstract String[] getAllAttributesAsString();
    
    public String calculateMd5Hash(){
    	try {
			MessageDigest md5Generator = MessageDigest.getInstance("MD5");
			String str = "";
			for(String s : getAllAttributesAsString()){
				str += s;
			}
			byte[] md5bytes = md5Generator.digest(str.getBytes());
			return new String(md5bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to generate md5Hash of basePersistable with id " + getId(),e);
		}
    }
    
    public void updateMd5Hash(){
    	setMd5Hash(calculateMd5Hash());
    }
    
    
}
