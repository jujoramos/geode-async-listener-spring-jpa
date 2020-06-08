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
package org.apache.geode.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.apache.geode.examples.model.Employee;
import org.apache.geode.examples.repository.EmployeeRepository;

@SpringBootTest
public class JPAAsyncListenerApplicationTest {
  private static final Logger logger = LoggerFactory.getLogger(JPAAsyncListenerApplicationTest.class);

  @Autowired
  private EmployeeRepository repository;

  @Autowired
  private AsyncEventQueue asyncEventQueue;

  @Resource(name = "Employees")
  private Region<Long, Employee> employeesRegion;

  @Test
  public void jpaListenerTest() {
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee(1L, "Zell", "Dincht"));
    employees.add(new Employee(2L, "Quistis", "Trepe"));
    employees.add(new Employee(3L, "Irvine", "Kinneas"));
    employees.add(new Employee(4L, "Rinoa", "Heartilly"));
    employees.add(new Employee(5L, "Squall", "Leonhart"));

    // DB empty at the beginning.
    assertThat(repository.findAll()).isEmpty();
    logger.info("Employees found with EmployeeRepository.findAll():");
    logger.info("-------------------------------");
    repository.findAll().forEach(employee -> logger.info(employee.toString()));
    logger.info("-------------------------------");
    logger.info("");

    // Insert some employees into the Geode Region
    employees.forEach(employee -> employeesRegion.put(employee.getId(), employee));

    // Wait for queues to drain (AsyncEventListener invoked).
    logger.info("Waiting for AsyncEventQueue to drain...");
    await().untilAsserted(() -> assertThat(asyncEventQueue.size()).isEqualTo(0));
    logger.info("Waiting for AsyncEventQueue to drain... Done!.");
    logger.info("");

    // DB should now have all the employees inserted through the AsyncEventListener.
    Iterable<Employee> employeeList = repository.findAll();
    assertThat(employeeList).hasSize(employees.size());
    employeeList.forEach(employee -> assertThat(employees.contains(employee)));
    logger.info("Employees found with EmployeeRepository.findAll():");
    logger.info("-------------------------------");
    employeeList.forEach(employee -> logger.info(employee.toString()));
    logger.info("-------------------------------");
    logger.info("");
  }
}
