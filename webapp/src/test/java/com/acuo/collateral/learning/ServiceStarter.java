package com.acuo.collateral.learning;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * Utility class for starting up all services that have been configured.
 */
@Slf4j
public class ServiceStarter {

  private final ImmutableSet<Service> services;

  @Inject
  ServiceStarter(Set<Service> services) {
    this.services = ImmutableSet.copyOf(services);
  }

  /**
   * Requests that all services that have been registered be started.
   */
  public void startServices() {
    int count = 0;
    log.info("Starting all services");
    for (Service service : services) {
      log.info("[Service #%d] Starting service %s", ++count, service);
      service.startAsync();
    }
    log.info("Finished starting %d services", count);
  }
}
