/*
 * CommandChannelFailed.java criado em 05/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CommandChannelFailed extends CommandChannel {

  private final List<Exception> exceptions;

  public CommandChannelFailed(Exception e) {
    this.exceptions = ImmutableList.of(e);
  }

  @Override
  public List<Exception> getExceptions() {
    return exceptions;
  }

  @Override
  RemoteCommand exec() {
    return RemoteCommand.failed(exceptions);
  }

}