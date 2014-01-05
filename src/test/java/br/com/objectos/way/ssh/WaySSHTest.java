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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class WaySSHTest {

  private WaySSH ssh;

  @BeforeClass
  public void setUp() {
    ssh = WaySSH.connect()
        .toHost("localhost")
        .atPort(22)
        .get();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    ssh.disconnect();
  }

  public void connect() {
    if (!ssh.isConnected()) {
      List<Exception> exceptions = ssh.getExceptions();
      for (Exception exception : exceptions) {
        exception.printStackTrace();
      }
      fail();
    }
  }

  public void exec() {
    String cmd = new String("a,b,c");
    byte[] bytes = cmd.getBytes(Charsets.ISO_8859_1);
    InputStream in = new ByteArrayInputStream(bytes);

    RemoteCommand res = ssh.execute("cut -d',' -f2", in);
    List<String> stdout = res.stdout();
    assertThat(stdout.get(0), equalTo("b"));
  }

}