package com.acuo.common.specification;

public class AlwaysTrueSpec extends AbstractSpecification<Object>
{

	@Override
	public boolean isSatisfiedBy(Object o)
	{
		return true;
	}
}
