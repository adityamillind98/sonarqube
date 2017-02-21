/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.es.request;

import org.elasticsearch.common.unit.TimeValue;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.api.utils.log.LogTester;
import org.sonar.api.utils.log.LoggerLevel;
import org.sonar.server.es.EsTester;
import org.sonar.server.es.FakeIndexDefinition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class ProxyCountRequestBuilderTest {

  @Rule
  public EsTester esTester = new EsTester(new FakeIndexDefinition());

  @Rule
  public LogTester logTester = new LogTester();

  @Test
  public void count() {
    esTester.client().prepareCount(FakeIndexDefinition.INDEX).get();
  }

  @Test
  public void to_string() {
    assertThat(esTester.client().prepareCount(FakeIndexDefinition.INDEX).setTypes(FakeIndexDefinition.INDEX_TYPE_FAKE.getType()).toString())
      .isEqualTo("ES count request on indices 'fakes' on types 'fake'");
    assertThat(esTester.client().prepareCount(FakeIndexDefinition.INDEX).toString()).isEqualTo("ES count request on indices 'fakes'");
    assertThat(esTester.client().prepareCount().toString()).isEqualTo("ES count request");
  }

  @Test
  public void trace_logs() {
    logTester.setLevel(LoggerLevel.TRACE);

    esTester.client().prepareCount(FakeIndexDefinition.INDEX).get();
    assertThat(logTester.logs(LoggerLevel.TRACE)).hasSize(1);
  }

  @Test
  public void fail_to_count_bad_query() {
    try {
      esTester.client().prepareCount("unknown_index1, unknown_index2").setTypes("unknown_type").get();
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalStateException.class);
      assertThat(e.getMessage()).contains("Fail to execute ES count request on indices 'unknown_index1, unknown_index2' on types 'unknown_type'");
    }
  }

  @Test
  public void get_with_string_timeout_is_not_yet_implemented() {
    try {
      esTester.client().prepareCount(FakeIndexDefinition.INDEX).get("1");
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalStateException.class).hasMessage("Not yet implemented");
    }
  }

  @Test
  public void get_with_time_value_timeout_is_not_yet_implemented() {
    try {
      esTester.client().prepareCount(FakeIndexDefinition.INDEX).get(TimeValue.timeValueMinutes(1));
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(IllegalStateException.class).hasMessage("Not yet implemented");
    }
  }

  @Test
  public void execute_should_throw_an_unsupported_operation_exception() {
    try {
      esTester.client().prepareCount(FakeIndexDefinition.INDEX).execute();
      fail();
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnsupportedOperationException.class).hasMessage("execute() should not be called as it's used for asynchronous");
    }
  }

}
