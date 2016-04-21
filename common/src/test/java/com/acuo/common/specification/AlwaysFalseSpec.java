package com.acuo.common.specification;

public class AlwaysFalseSpec extends AbstractSpecification<Object>
{

	@Override
	public boolean isSatisfiedBy(Object o)
	{
		return false;
	}
}
