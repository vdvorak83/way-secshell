/*
 * CommandChannelSuccess.java criado em 05/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

import java.util.List;

import com.jcraft.jsch.Channel;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CommandChannelSuccess extends CommandChannel {

  private final Channel channel;

  public CommandChannelSuccess(Channel channel) {
    this.channel = channel;
  }

  @Override
  RemoteCommand exec() {
    ChannelReader reader = null;

    try {

      reader = ChannelReader.readerOf(channel);

      channel.connect();

      List<String> stdout = reader.readLines();

      return RemoteCommand.success(stdout);

    } catch (Exception e) {

      return RemoteCommand.failed(this, e);

    }
  }

}