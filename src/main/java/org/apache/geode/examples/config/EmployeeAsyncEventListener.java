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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.examples.model.Employee;
import org.apache.geode.examples.repository.EmployeeRepository;

/**
 *
 */
@Component
public class EmployeeAsyncEventListener implements AsyncEventListener {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeAsyncEventListener.class);
  private final EmployeeRepository employeeRepository;

  public EmployeeAsyncEventListener(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public boolean processEvents(List<AsyncEvent> events) {
    events.forEach(asyncEvent -> {
      Employee employee = (Employee) asyncEvent.getDeserializedValue();
      logger.info("Processing Employee {}...", employee.toString());
      employeeRepository.save(employee);
      logger.info("Processing Employee {}... Done!", employee.toString());
    });

    return true;
  }
}