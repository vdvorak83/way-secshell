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

import static com.google.common.collect.Lists.newArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.com.objectos.way.base.io.HasStdout;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ChannelReader implements HasStdout {

  private final BufferedReader reader;

  private final List<Exception> exceptions = newArrayList();

  private final List<String> stdout = newArrayList();

  private ChannelReader(BufferedReader reader) {
    this.reader = reader;
  }

  public static ChannelReader readerOf(Channel channel) throws IOException {
    InputStream in = channel.getInputStream();
    InputStreamReader isr = new InputStreamReader(in);
    BufferedReader reader = new BufferedReader(isr);
    return new ChannelReader(reader);
  }

  boolean ack() throws IOException {
    int b = reader.read();
    return b == 0;
  }

  public List<String> readLines() throws IOException {
    return CharStreams.readLines(reader);
  }

  void close() {
    try {
      List<String> lines = CharStreams.readLines(reader);
      stdout.addAll(lines);
    } catch (IOException e) {
      exceptions.add(e);
    }

    try {
      reader.close();
    } catch (IOException e) {
      exceptions.add(e);
    }
  }

  @Override
  public List<Exception> getExceptions() {
    return exceptions;
  }

  @Override
  public List<String> stdout() {
    return stdout;
  }

}