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
import java.io.IOException;

import br.com.objectos.way.ssh.ScpUploadConnect.WithChannel;

import com.jcraft.jsch.Channel;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ScpUploadConnectSuccess extends WithChannel {

  final File src;

  public ScpUploadConnectSuccess(Channel channel,
                                 ChannelWriter writer,
                                 ChannelReader reader,
                                 File src) {
    super(channel, writer, reader);
    this.src = src;
  }

  @Override
  final Scp send() {
    try {

      return tryToSend();

    } catch (IOException e) {

      return Scp.failed(this, e);

    } finally {

      closeAll();

    }
  }

  abstract Scp tryToSend() throws IOException;

}