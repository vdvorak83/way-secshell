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

import com.jcraft.jsch.IdentityRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.agentproxy.AgentProxyException;
import com.jcraft.jsch.agentproxy.Connector;
import com.jcraft.jsch.agentproxy.ConnectorFactory;
import com.jcraft.jsch.agentproxy.RemoteIdentityRepository;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
class SSHBuilderPojo
    implements
    SshBuilder,
    SshBuilder.HostBuider,
    SshBuilder.PortBuilder {

  static {
    JSch.setConfig("PreferredAuthentications", "publickey");
    JSch.setConfig("StrictHostKeyChecking", "no");
    JSch.setLogger(WaySSHLogger.INSTANCE);
  }

  private String user = System.getProperty("user.name");

  private String host = "localhost";

  private int port = 22;

  private String knownHosts;

  @Override
  public HostBuider toHost(String host) {
    this.host = host;
    return this;
  }

  @Override
  public PortBuilder atPort(int port) {
    this.port = port;
    return this;
  }

  @Override
  public ConnectBuilder asUser(String user) {
    this.user = user;
    return this;
  }

  @Override
  public WaySSH connect() {
    try {
      JSch sch = new JSch();

      ConnectorFactory cf = ConnectorFactory.getDefault();
      Connector con = cf.createConnector();
      IdentityRepository irepo = new RemoteIdentityRepository(con);
      sch.setIdentityRepository(irepo);

      String knownHosts = knownHosts();
      sch.setKnownHosts(knownHosts);

      Session session = sch.getSession(user, host, port);
      session.connect();

      return WaySSH.success(session);

    } catch (JSchException e) {
      return WaySSH.failed(e);

    } catch (AgentProxyException e) {
      return WaySSH.failed(e);

    }
  }

  private String knownHosts() {
    String res = knownHosts;

    if (res == null) {
      String userHome = System.getProperty("user.home");
      res = String.format("%s/.ssh/known_hosts", userHome);
    }

    return res;
  }

}