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
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.jcraft.jsch.Session;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public abstract class WaySSH {

  WaySSH() {
  }

  public static WaySSHBuilder connect() {
    return new WaySSHBuilder();
  }

  public static ScpBuilder scp() {
    return new ScpBuilder();
  }

  static WaySSH success(Session session) {
    return new Success(session);
  }

  static WaySSH failed(Exception e) {
    return new Failed(e);
  }

  public List<Exception> getExceptions() {
    return ImmutableList.of();
  }

  public boolean isConnected() {
    return false;
  }

  public void disconnect() {
  }

  abstract ScpUploadChannel uploadChannelOf(File file, String dest);

  private static class Success extends WaySSH {

    private final Session session;

    private Success(Session session) {
      this.session = session;
    }

    @Override
    public boolean isConnected() {
      return true;
    }

    @Override
    public void disconnect() {
      session.disconnect();
    }

    @Override
    ScpUploadChannel uploadChannelOf(File file, String dest) {
      return ScpUploadChannel.success(session, file, dest);
    }

  }

  private static class Failed extends WaySSH {

    private final List<Exception> exceptions;

    public Failed(Exception e) {
      exceptions = ImmutableList.of(e);
    }

    @Override
    public List<Exception> getExceptions() {
      return exceptions;
    }

    @Override
    ScpUploadChannel uploadChannelOf(File file, String dest) {
      return ScpUploadChannel.failed(exceptions);
    }

  }

}