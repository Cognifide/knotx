/*
 * Knot.x - Reactive microservice assembler - View Knot
 *
 * Copyright (C) 2016 Cognifide Limited
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
package com.cognifide.knotx.knot.view.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class ServiceAttributeUtilTest {

  @Test
  public void testAttributeWithoutNamespace() throws Exception {
    String serviceNamespace = ServiceAttributeUtil.extractNamespace("data-service");
    String paramsNamespace = ServiceAttributeUtil.extractNamespace("data-params");
    Assert.assertThat(serviceNamespace, is(StringUtils.EMPTY));
    Assert.assertThat(paramsNamespace, is(StringUtils.EMPTY));
  }

  @Test
  public void testAttributeWithNamespace() throws Exception {
    String serviceNamespace = ServiceAttributeUtil.extractNamespace("data-service-label");
    String paramsNamespace = ServiceAttributeUtil.extractNamespace("data-params-label");
    Assert.assertThat(serviceNamespace, is("label"));
    Assert.assertThat(paramsNamespace, is("label"));
  }

  @Test(expected = RuntimeException.class)
  public void testAttributeWithTwoNamespaces() throws Exception {
    String attributeInput = "data-service-message-label";
    ServiceAttributeUtil.extractNamespace(attributeInput);
  }

  @Test(expected = RuntimeException.class)
  public void testAttributeWithBrokenNamespace() throws Exception {
    String attributeInput = "data-service--label";
    ServiceAttributeUtil.extractNamespace(attributeInput);
  }

}
