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

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ScpUploadChannelSuccess extends ScpUploadChannel {

  private final Channel channel;

  private final File src;

  ScpUploadChannelSuccess(Channel channel, File src) {
    this.channel = channel;
    this.src = src;
  }

  @Override
  ScpUploadConnect connect() {
    ChannelWriter writer = null;
    ChannelReader reader = null;

    try {

      writer = ChannelWriter.writerOf(channel);
      reader = ChannelReader.readerOf(channel);

      channel.connect();

      ScpUploadConnect connect = successInstance(channel, writer, reader, src);

      if (!reader.ack()) {
        connect = ScpUploadConnect.failed(channel, writer, reader);
      }

      return connect;

    } catch (Exception e) {

      return ScpUploadConnect.failed(channel, writer, reader, e);

    }
  }

  abstract ScpUploadConnect successInstance(Channel c, ChannelWriter w, ChannelReader r, File src);

}