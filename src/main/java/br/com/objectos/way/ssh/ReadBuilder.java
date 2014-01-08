/*
 * ReadBuilder.java criado em 08/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

import java.io.InputStream;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class ReadBuilder {

  private final WaySSH ssh;

  private final InputStream in;

  ReadBuilder(WaySSH ssh) {
    this(ssh, null);
  }

  ReadBuilder(WaySSH ssh, InputStream in) {
    this.ssh = ssh;
    this.in = in;
  }

  public RemoteCommand andExecute(String command) {
    return ssh.executeCommand(command, in);
  }

}