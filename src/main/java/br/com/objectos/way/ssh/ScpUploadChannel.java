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

import br.com.objectos.way.base.io.HasStdout;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.jcraft.jsch.Session;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
abstract class ScpUploadChannel implements HasStdout {

  static ScpUploadChannel success(Session session, File src, String name) {
    String dest = Strings.isNullOrEmpty(name) ? src.getName() : name;

    ScpUploadChannelBuilder builder = src.isDirectory() ?
        new ScpUploadChannelBuilderDir(session, src, dest) :
        new ScpUploadChannelBuilderFile(session, src, dest);

    return builder.build();
  }

  static ScpUploadChannel failed(List<Exception> exceptions) {
    return new ScpUploadChannelFailed(exceptions);
  }

  @Override
  public List<Exception> getExceptions() {
    return ImmutableList.of();
  }

  @Override
  public List<String> stdout() {
    return ImmutableList.of();
  }

  abstract ScpUploadConnect connect();

}