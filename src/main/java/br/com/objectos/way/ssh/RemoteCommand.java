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

  private final List<Exception> exceptions;

  private final List<String> stdout;

  private RemoteCommand(List<Exception> exceptions, List<String> stdout) {
    this.exceptions = exceptions;
    this.stdout = stdout;
  }

  private RemoteCommand(HasStdout stdout, Exception e) {
    this.exceptions = ImmutableList.<Exception> builder()
        .addAll(stdout.getExceptions())
        .add(e)
        .build();
    this.stdout = stdout.stdout();
  }

  static RemoteCommand success(List<String> stdout) {
    List<Exception> exceptions = ImmutableList.of();
    return new RemoteCommand(exceptions, stdout);
  }

  static RemoteCommand failed(HasStdout stdout, Exception e) {
    return new RemoteCommand(stdout, e);
  }

  static RemoteCommand failed(List<Exception> exceptions) {
    List<String> stdout = ImmutableList.of();
    return new RemoteCommand(exceptions, stdout);
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