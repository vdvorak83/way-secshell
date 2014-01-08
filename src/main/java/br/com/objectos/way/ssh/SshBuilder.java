/*
 * SshBuilder.java criado em 08/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public interface SshBuilder {

  interface HostBuider extends ConnectBuilder {

    PortBuilder atPort(int port);

  }

  interface PortBuilder extends ConnectBuilder {

    ConnectBuilder asUser(String user);

  }

  interface ConnectBuilder {

    WaySSH connect();

  }

  HostBuider toHost(String host);

}