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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marcio.endo@objectos.com.br (Marcio Endo)
 */
enum WaySSHLogger implements com.jcraft.jsch.Logger {

  INSTANCE;

  static final Logger logger = LoggerFactory.getLogger(WaySSH.class);

  @Override
  public boolean isEnabled(int level) {
    switch (level) {
    default:
    case com.jcraft.jsch.Logger.DEBUG:
      return logger.isDebugEnabled();

    case com.jcraft.jsch.Logger.INFO:
      return logger.isInfoEnabled();

    case com.jcraft.jsch.Logger.WARN:
      return logger.isWarnEnabled();

    case com.jcraft.jsch.Logger.ERROR:
    case com.jcraft.jsch.Logger.FATAL:
      return logger.isErrorEnabled();
    }
  }

  @Override
  public void log(int level, String message) {
    switch (level) {
    default:
    case com.jcraft.jsch.Logger.DEBUG:
      logger.debug(message);
      break;

    case com.jcraft.jsch.Logger.INFO:
      logger.info(message);
      break;

    case com.jcraft.jsch.Logger.WARN:
      logger.warn(message);
      break;

    case com.jcraft.jsch.Logger.ERROR:
    case com.jcraft.jsch.Logger.FATAL:
      logger.error(message);
      break;
    }
  }

}