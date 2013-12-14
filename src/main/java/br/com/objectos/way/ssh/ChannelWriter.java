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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import br.com.objectos.way.base.io.HasStdout;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.jcraft.jsch.Channel;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class ChannelWriter implements HasStdout {

  private final OutputStream out;

  private final BufferedWriter writer;

  private final List<Exception> exceptions = newArrayList();

  private ChannelWriter(OutputStream out, BufferedWriter writer) {
    this.out = out;
    this.writer = writer;
  }

  public static ChannelWriter writerOf(Channel channel) throws IOException {
    OutputStream out = channel.getOutputStream();
    OutputStreamWriter osw = new OutputStreamWriter(out);
    BufferedWriter writer = new BufferedWriter(osw);
    return new ChannelWriter(out, writer);
  }

  void writeTimestamp(File file) throws IOException {
    long timestamp = file.lastModified() / 1000;
    sendCommand("T %d 0 %d 0", timestamp, timestamp);
  }

  void writeFile(File src) throws IOException {
    long filesize = src.length();
    String name = src.getName();
    sendCommand("C0644 %d %s", filesize, name);

    Files.copy(src, out);

    out.write(0);
    out.flush();
  }

  void writeDirStart(File dir) throws IOException {
    String name = dir.getName();
    sendCommand("D0755 0 %s", name);
  }

  void writeDirEnd() throws IOException {
    sendCommand("E");
  }

  void close() {
    try {
      writer.close();
    } catch (IOException e) {
      exceptions.add(e);
    }
  }

  private void sendCommand(String format, Object... args) throws IOException {
    String command = String.format(format, args);
    writer.write(command);
    writer.newLine();
    writer.flush();
  }

  @Override
  public List<Exception> getExceptions() {
    return exceptions;
  }

  @Override
  public List<String> stdout() {
    return ImmutableList.of();
  }

}