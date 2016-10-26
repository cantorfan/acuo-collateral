package com.acuo.collateral.learning;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Tests for the {@link ServiceStarter} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceStarterTest {

  @Mock
  private Service mockService1;

  @Mock
  private Service mockService2;

  @Mock
  private Service mockService3;

  private ServiceStarter starter;

  @Before
  public void createTestInstances() {
    starter = new ServiceStarter(ImmutableSet.of(mockService1, mockService2, mockService3));
  }

  @Test
  public void startServices_startsAllRegisteredServices() {
    starter.startServices();

    verify(mockService1).startAsync();
    verify(mockService2).startAsync();
    verify(mockService3).startAsync();
  }
}