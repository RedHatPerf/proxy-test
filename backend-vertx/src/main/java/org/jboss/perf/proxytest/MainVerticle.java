package org.jboss.perf.proxytest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class MainVerticle extends AbstractVerticle {
   private final String servers = System.getProperty("backend.servers", "1");

   @Override
   public void start() {
      DeploymentOptions options = new DeploymentOptions().setInstances(Integer.parseInt(servers));
      vertx.deployVerticle(BackendServer.class, options);
   }
}
