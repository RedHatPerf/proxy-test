package org.jboss.perf.proxytest;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BackendServer extends AbstractVerticle {
   private int port = Integer.getInteger("backend.port", 8080);

   @Override
   public void start(Future<Void> startFuture) {
      Router router = Router.router(vertx);
      router.route(HttpMethod.GET, "/").handler(ctx -> {
         HttpServerResponse response = ctx.response();
         response.putHeader(HttpHeaderNames.SERVER, "Vert.x");
         response.setStatusCode(HttpResponseStatus.NO_CONTENT.code()).end();
      });
      router.route(HttpMethod.POST, "/").handler(BodyHandler.create()).handler(ctx -> {
         HttpServerResponse response = ctx.response();
         response.setStatusCode(HttpResponseStatus.OK.code()).end(ctx.getBody());
      });
      vertx.createHttpServer().requestHandler(router::accept).listen(port, result -> {
         if (result.failed()) {
            System.err.printf("Cannot listen on port %d%n", port);
            vertx.close();
         } else {
            HttpServer server = result.result();
            System.out.printf("Backend listening on port %d%n", server.actualPort());
         }
      });
   }
}
