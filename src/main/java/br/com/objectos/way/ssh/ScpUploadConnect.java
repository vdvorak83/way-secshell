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

import java.util.List;

import br.com.objectos.way.base.io.HasStdout;
import br.com.objectos.way.base.io.Stdout;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ScpUploadConnect implements HasStdout {

  static ScpUploadConnect failed(Channel c, ChannelWriter w, ChannelReader r) {
    return new ScpUploadConnectFailed(c, w, r);
  }

  static ScpUploadConnect failed(Channel c, ChannelWriter w, ChannelReader r, Exception e) {
    return new ScpUploadConnectFailed(c, w, r, e);
  }

  static ScpUploadConnect failed(List<Exception> exceptions) {
    return new ScpUploadConnectFailedChannelLess(exceptions);
  }

  @Override
  public List<Exception> getExceptions() {
    return ImmutableList.of();
  }

  @Override
  public List<String> stdout() {
    return ImmutableList.of();
  }

  Scp send() {
    return Scp.scpOf(this);
  }

  abstract static class WithChannel extends ScpUploadConnect {

    final Channel channel;
    final ChannelWriter writer;
    final ChannelReader reader;

    public WithChannel(Channel channel, ChannelWriter writer, ChannelReader reader) {
      this.channel = channel;
      this.writer = writer;
      this.reader = reader;
    }

    @Override
    Scp send() {
      closeAll();
      return super.send();
    }

    void closeChannel() {
      if (channel != null) {
        channel.disconnect();
      }

      if (writer != null) {
        writer.close();
      }

      if (reader != null) {
        reader.close();
      }
    }

    void closeAll() {
      closeChannel();

      if (channel == null) {
        return;
      }

      try {
        Session session = channel.getSession();
        session.disconnect();
      } catch (JSchException e) {
      }
    }

    @Override
    public List<Exception> getExceptions() {
      Iterable<HasStdout> iterable = hasStdout();
      return Stdout.concatExceptions(iterable);
    }

    @Override
    public List<String> stdout() {
      Iterable<HasStdout> iterable = hasStdout();
      return Stdout.concatStdout(iterable);
    }

    private Iterable<HasStdout> hasStdout() {
      List<HasStdout> list = ImmutableList.<HasStdout> of(writer, reader);
      return Iterables.filter(list, Predicates.notNull());
    }

  }

}