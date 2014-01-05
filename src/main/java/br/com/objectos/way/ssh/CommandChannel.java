/*
 * CommandChannel.java criado em 05/01/2014
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
abstract class CommandChannel implements HasStdout {

  @Override
  public List<Exception> getExceptions() {
    return ImmutableList.of();
  }

  @Override
  public List<String> stdout() {
    return ImmutableList.of();
  }

  abstract RemoteCommand exec();

}