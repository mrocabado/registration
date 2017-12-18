package com.registration.core.entity.type;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class NonEmptyString {

    protected final String value;

    NonEmptyString(String value) {
        this.value = value.trim();
    }

    public static NonEmptyString valueOf(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Blank string");
        }
        
        return new NonEmptyString(value.trim());
    }

    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object other) {
    	if ( Objects.isNull(other) || !(other instanceof NonEmptyString) ) {
    		return false;
    	}
    	
    	return value.equals(other.toString());
    }
    
    @Override
    public int hashCode() {
    	return value.hashCode();
    }
    
    @Override
    public String toString() {
    	return value;
    }
}
