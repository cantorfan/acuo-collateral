package com.acuo.common.specification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AndSpecificationTest
{

	@Test
	public void testAndIsSatisifedBy() throws Exception
	{
		AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
		AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();
		AndSpecification<Object> andSpecification = new AndSpecification<Object>(trueSpec, trueSpec);
		assertTrue(andSpecification.isSatisfiedBy(new Object()));
		andSpecification = new AndSpecification<Object>(falseSpec, trueSpec);
		assertFalse(andSpecification.isSatisfiedBy(new Object()));
		andSpecification = new AndSpecification<Object>(trueSpec, falseSpec);
		assertFalse(andSpecification.isSatisfiedBy(new Object()));
		andSpecification = new AndSpecification<Object>(falseSpec, falseSpec);
		assertFalse(andSpecification.isSatisfiedBy(new Object()));
	}
}
