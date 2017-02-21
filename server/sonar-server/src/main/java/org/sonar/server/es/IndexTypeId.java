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
package org.sonar.server.es;

import java.util.Arrays;
import java.util.function.Function;
import org.sonar.core.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class IndexTypeId {

  private final String index;
  private final String type;

  public IndexTypeId(String index, String type) {
    this.index = requireNonNull(index);
    this.type = requireNonNull(type);
  }

  public String getIndex() {
    return index;
  }

  public String getType() {
    return type;
  }

  public static String[] getIndices(IndexTypeId... indexTypes) {
    return getDetails(IndexTypeId::getIndex, indexTypes);
  }

  public static String[] getTypes(IndexTypeId... indexTypes) {
    return getDetails(IndexTypeId::getType, indexTypes);
  }

  private static String[] getDetails(Function<? super IndexTypeId, ? extends String> function, IndexTypeId... indexTypes) {
    return Arrays.stream(indexTypes).map(function).collect(Collectors.toSet(indexTypes.length)).toArray(new String[0]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndexTypeId that = (IndexTypeId) o;
    if (!index.equals(that.index)) {
      return false;
    }
    return type.equals(that.type);
  }

  @Override
  public int hashCode() {
    int result = index.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "[" + index + "/" + type + "]";
  }
}
