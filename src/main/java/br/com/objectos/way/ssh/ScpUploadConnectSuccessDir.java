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

import com.jcraft.jsch.Channel;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ScpUploadConnectSuccessDir extends ScpUploadConnectSuccess {

  public ScpUploadConnectSuccessDir(Channel channel,
                                    ChannelWriter writer,
                                    ChannelReader reader,
                                    File src) {
    super(channel, writer, reader, src);
  }

  @Override
  Scp tryToSend() throws IOException {
    sendDir(src);

    return Scp.scpOf(this);
  }

  private void sendDir(File dir) throws IOException {
    writer.writeDirStart(dir);
    if (!reader.ack()) {
      return;
    }

    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          sendDir(file);
        } else {
          sendFile(file);
        }
      }
    }

    writer.writeDirEnd();
    if (!reader.ack()) {
      return;
    }
  }

  private void sendFile(File file) throws IOException {
    writer.writeTimestamp(file);
    if (!reader.ack()) {
      return;
    }

    writer.writeFile(file);
    if (!reader.ack()) {
      return;
    }
  }

}