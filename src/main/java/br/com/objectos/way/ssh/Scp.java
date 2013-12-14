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

import com.google.common.collect.ImmutableList;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
public class Scp implements HasStdout {

  private final List<Exception> exceptions;

  private final List<String> stdout;

  private Scp(HasStdout stdout) {
    this.exceptions = stdout.getExceptions();
    this.stdout = stdout.stdout();
  }

  private Scp(HasStdout stdout, Exception e) {
    this.exceptions = ImmutableList.<Exception> builder()
        .addAll(stdout.getExceptions())
        .add(e)
        .build();
    this.stdout = stdout.stdout();
  }

  static Scp scpOf(HasStdout stdout) {
    return new Scp(stdout);
  }

  static Scp failed(HasStdout stdout, Exception e) {
    return new Scp(stdout, e);
  }

  @Override
  public List<Exception> getExceptions() {
    return exceptions;
  }

  @Override
  public List<String> stdout() {
    return stdout;
  }

  public boolean success() {
    return exceptions.isEmpty();
  }

}