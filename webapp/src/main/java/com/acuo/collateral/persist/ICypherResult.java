package com.acuo.collateral.persist;

public interface ICypherResult {
    Boolean hasProperty(String key);

    String getStringResult(String key);

    Integer getIntegerResult(String key);
}