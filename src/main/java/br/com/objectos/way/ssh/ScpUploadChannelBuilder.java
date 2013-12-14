/*
 * Copyright 2013 Objectos, Fábrica de Software LTDA.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.objectos.way.ssh;

import java.io.File;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ScpUploadChannelBuilder {

  private final Session session;

  private final File src;

  private final String dest;

  public ScpUploadChannelBuilder(Session session, File src, String dest) {
    this.session = session;
    this.src = src;
    this.dest = dest;
  }

  ScpUploadChannel build() {
    try {
      String flags = getFlags();
      String command = String.format("scp %s %s", flags, dest);
      Channel channel = session.openChannel("exec");
      ((ChannelExec) channel).setCommand(command);
      return successInstance(channel, src);

    } catch (JSchException e) {
      return new ScpUploadChannelFailed(e);

    }
  }

  abstract String getFlags();

  abstract ScpUploadChannel successInstance(Channel channel, File src);

}