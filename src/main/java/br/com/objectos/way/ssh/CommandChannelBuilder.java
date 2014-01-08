/*
 * CommandChannelBuilder.java criado em 05/01/2014
 * 
 * Propriedade de Objectos Fábrica de Software LTDA.
 * Reprodução parcial ou total proibida.
 */
package br.com.objectos.way.ssh;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class CommandChannelBuilder {

  private final WaySSH ssh;

  private final Session session;

  private final String command;

  private final InputStream in;

  public CommandChannelBuilder(WaySSH ssh, Session session, String command, InputStream in) {
    this.ssh = ssh;
    this.session = session;
    this.command = command;
    this.in = in;
  }

  public CommandChannel build() {
    try {
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command);
      channel.setInputStream(in);
      return new CommandChannelSuccess(ssh, channel);

    } catch (JSchException e) {
      return new CommandChannelFailed(ssh, e);

    }
  }

}