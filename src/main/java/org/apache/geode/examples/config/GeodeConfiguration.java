/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.examples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.wan.AsyncEventQueueFactoryBean;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.apache.geode.examples.model.Employee;

/**
 *
 */
@Configuration
public class GeodeConfiguration {

  @Bean
  AsyncEventQueueFactoryBean employeeAsyncEventQueue(GemFireCache cache, EmployeeAsyncEventListener asyncEventListener) {
    AsyncEventQueueFactoryBean queueFactoryBean = new AsyncEventQueueFactoryBean((Cache) cache);
    queueFactoryBean.setAsyncEventListener(asyncEventListener);

    return queueFactoryBean;
  }

  @Bean("Employees")
  ReplicatedRegionFactoryBean<Long, Employee> employeesRegion(GemFireCache cache, AsyncEventQueue asyncEventQueue) {
    ReplicatedRegionFactoryBean<Long, Employee> regionFactoryBean = new ReplicatedRegionFactoryBean<>();
    regionFactoryBean.setCache(cache);
    regionFactoryBean.setPersistent(false);
    regionFactoryBean.setAsyncEventQueues(new AsyncEventQueue[]{asyncEventQueue});

    return regionFactoryBean;
  }
}
