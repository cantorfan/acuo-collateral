package com.acuo.collateral.modules.persistence;

public interface FooAdder<X extends Exception> {
  void addFoo(Foo foo) throws X;
}