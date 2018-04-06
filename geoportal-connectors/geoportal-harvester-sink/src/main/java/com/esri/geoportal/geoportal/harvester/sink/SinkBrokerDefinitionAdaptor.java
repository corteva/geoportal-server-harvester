/*
 * Copyright 2016 Esri, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.geoportal.geoportal.harvester.sink;

import static com.esri.geoportal.geoportal.harvester.sink.SinkConstants.P_ROOT_FOLDER;
import com.esri.geoportal.harvester.api.base.BrokerDefinitionAdaptor;
import com.esri.geoportal.harvester.api.defs.EntityDefinition;
import com.esri.geoportal.harvester.api.ex.InvalidDefinitionException;
import java.io.File;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * UNC broker definition adaptor.
 */
public class SinkBrokerDefinitionAdaptor extends BrokerDefinitionAdaptor {
  
  private File rootFolder;

  /**
   * Creates instance of the adaptor.
   * @param def broker definition
   * @throws InvalidDefinitionException if invalid broker definition
   */
  public SinkBrokerDefinitionAdaptor(EntityDefinition def) throws InvalidDefinitionException {
    super(def);
    if (StringUtils.trimToEmpty(def.getType()).isEmpty()) {
      def.setType(SinkConnector.TYPE);
    } else if (!SinkConnector.TYPE.equals(def.getType())) {
      throw new InvalidDefinitionException("Broker definition doesn't match");
    } else {
      try {
        rootFolder = new File(get(P_ROOT_FOLDER));
      } catch (Exception ex) {
        throw new InvalidDefinitionException(String.format("Invalid %s: %s", P_ROOT_FOLDER, get(P_ROOT_FOLDER)), ex);
      }
    }
  }

  @Override
  public void override(Map<String, String> params) {
    consume(params,P_ROOT_FOLDER);
  }
  
  /**
   * Gets root folder.
   * @return root folder
   */
  public File getRootFolder() {
    return rootFolder;
  }
  
  /**
   * Sets root folder
   * @param rootFolder host URL
   */
  public void setRootFolder(File rootFolder) {
    this.rootFolder = rootFolder;
    set(P_ROOT_FOLDER, rootFolder.getAbsolutePath());
  }
  
}
