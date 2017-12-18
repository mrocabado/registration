package com.registration.core.entity.type;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NonEmptyStringTest {

	@Test(expected=IllegalArgumentException.class)
	public void when_null_is_provided_error_is_thrown() {
		NonEmptyString.valueOf(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void when_empty_string_is_provided_error_is_thrown() {
		NonEmptyString.valueOf("  ");
	}
	
	@Test
	public void when_non_empty_string_is_provided_value_spaces_are_trimmed_out() {	
		assertTrue(NonEmptyString.valueOf(" value ").value.equals("value"));
	}	
}
