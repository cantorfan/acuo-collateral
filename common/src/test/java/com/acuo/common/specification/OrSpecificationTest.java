package com.acuo.common.specification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrSpecificationTest
{

	@Test
	public void testAndIsSatisifedBy() throws Exception
	{
		AlwaysTrueSpec trueSpec = new AlwaysTrueSpec();
		AlwaysFalseSpec falseSpec = new AlwaysFalseSpec();
		OrSpecification<Object> orSpecification = new OrSpecification<Object>(trueSpec, trueSpec);
		assertTrue(orSpecification.isSatisfiedBy(new Object()));
		orSpecification = new OrSpecification<Object>(falseSpec, trueSpec);
		assertTrue(orSpecification.isSatisfiedBy(new Object()));
		orSpecification = new OrSpecification<Object>(trueSpec, falseSpec);
		assertTrue(orSpecification.isSatisfiedBy(new Object()));
		orSpecification = new OrSpecification<Object>(falseSpec, falseSpec);
		assertFalse(orSpecification.isSatisfiedBy(new Object()));
	}
}
