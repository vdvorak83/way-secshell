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
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.objectos.way.base.io.Stdout;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class ScpTest {

  private File tmp;
  private File file0;

  @BeforeClass
  public void setUp() throws IOException {
    String tmpDir = System.getProperty("java.io.tmpdir");
    tmp = new File(tmpDir);
    file0 = new File(tmp, "file0.txt");
    Files.write("whatever0", file0, Charsets.UTF_8);
  }

  public void file() {
    Scp res = WaySSH.scp()
        .file(file0)
        .toHost("localhost")
        .send();

    boolean success = res.success();

    if (!success) {
      Stdout.print(res);
    }

    assertThat(res.success(), is(true));

    String home = System.getProperty("user.home");
    File homeDir = new File(home);

    assertExists(homeDir, file0);
  }

  private void assertExists(File homeDir, File other) {
    File res = new File(homeDir, other.getName());
    assertThat(res.exists(), is(true));
  }

}