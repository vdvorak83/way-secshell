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

import br.com.objectos.way.base.io.Directories;
import br.com.objectos.way.base.io.Stdout;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
@Test
public class ScpDirTest {

  private File dir;
  private File file0;
  private File file1;

  private File scpres;

  @BeforeClass
  public void setUp() throws IOException {
    String tmpDir = System.getProperty("java.io.tmpdir");
    File tmp = new File(tmpDir);
    dir = new File(tmp, "scp");

    if (dir.exists()) {
      Directories.rm_rf(dir);
    }
    dir.mkdir();

    file0 = new File(dir, "file0.txt");
    Files.write("whatever 0", file0, Charsets.UTF_8);

    File sub = new File(dir, "sub");
    sub.mkdir();
    file1 = new File(sub, "file1.txt");
    Files.write("whatever 1", file1, Charsets.UTF_8);

    scpres = new File(tmp, "scpres");
    if (scpres.exists()) {
      Directories.rm_rf(scpres);
    }
    scpres.mkdir();
  }

  public void file() {
    Scp res = WaySSH.scp()
        .file(dir)
        .toHost("localhost")
        .at("/tmp/scpres")
        .send();

    boolean success = res.success();

    if (!success) {
      Stdout.print(res);
    }

    assertThat(res.success(), is(true));

    assertExists(scpres, "scp/file0.txt");
    assertExists(scpres, "scp/sub/file1.txt");
  }

  private void assertExists(File dir, String name) {
    File res = new File(dir, name);
    assertThat(res.exists(), is(true));
  }

}