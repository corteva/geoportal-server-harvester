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
package com.esri.geoportal.harvester.beans;

import com.esri.geoportal.harvester.api.Trigger;
import com.esri.geoportal.harvester.engine.registers.TriggerRegistry;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Trigger registry bean.
 */
@Service
public class TriggerRegistryBean extends TriggerRegistry {
  private final Logger LOG = LoggerFactory.getLogger(TriggerManagerBean.class);
   
  @Autowired(required = false)
  private List<Trigger> triggers;
  
  /**
   * Initializes bean.
   */
  @PostConstruct
  public void init() {
    if (triggers!=null) {
      triggers.stream().forEach(t->put(t.getType(),t));
    }
    LOG.info("TriggerRegistryBean initialized.");
  }
  
  /**
   * Destroys bean.
   */
  @PreDestroy
  public void destroy() {
    try {
      close();
      LOG.info("TriggerRegistryBean destroyed.");
    } catch (Exception ex) {
      LOG.warn(String.format("Error closing trigger registry bean."), ex);
    }
  }
 
}
