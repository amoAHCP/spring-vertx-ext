module jacpfx.vertx.spring {
  requires vertx.core;
  requires io.netty.codec;
  requires io.netty.codec.http;
  requires java.logging;
  requires java.management;
  requires spring.core;
  requires spring.context;
  requires spring.beans;
  requires aspectjrt;

  exports org.jacpfx.vertx.spring;
}