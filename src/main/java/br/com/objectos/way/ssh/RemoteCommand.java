/*
 * WaySSHCommand.java criado em 05/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

import java.util.List;

import br.com.objectos.way.base.io.HasStdout;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class RemoteCommand implements HasStdout {

  private final WaySSH ssh;

  private final List<Exception> exceptions;

  private final List<String> stdout;

  private RemoteCommand(WaySSH ssh, List<Exception> exceptions, List<String> stdout) {
    this.ssh = ssh;
    this.exceptions = exceptions;
    this.stdout = stdout;
  }

  private RemoteCommand(WaySSH ssh, HasStdout stdout, Exception e) {
    this.ssh = ssh;
    this.exceptions = ImmutableList.<Exception> builder()
        .addAll(stdout.getExceptions())
        .add(e)
        .build();
    this.stdout = stdout.stdout();
  }

  static RemoteCommand success(WaySSH ssh, List<String> stdout) {
    List<Exception> exceptions = ImmutableList.of();
    return new RemoteCommand(ssh, exceptions, stdout);
  }

  static RemoteCommand failed(WaySSH ssh, HasStdout stdout, Exception e) {
    return new RemoteCommand(ssh, stdout, e);
  }

  static RemoteCommand failed(WaySSH ssh, List<Exception> exceptions) {
    List<String> stdout = ImmutableList.of();
    return new RemoteCommand(ssh, exceptions, stdout);
  }

  public void disconnect() {
    if (ssh != null) {
      ssh.disconnect();
    }
  }

  @Override
  public List<Exception> getExceptions() {
    return exceptions;
  }

  @Override
  public List<String> stdout() {
    return stdout;
  }

  public boolean success() {
    return exceptions.isEmpty();
  }

}