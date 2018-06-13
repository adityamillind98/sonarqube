/*
 * SonarQube
 * Copyright (C) 2009-2018 SonarSource SA
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
import * as React from 'react';
import { shallow } from 'enzyme';
import ConciseIssue from '../ConciseIssue';

const issue = {
  component: '',
  componentLongName: 'src/file.js',
  componentQualifier: '',
  componentUuid: '',
  creationDate: '',
  key: '',
  message: '',
  organization: '',
  project: '',
  projectName: '',
  projectOrganization: '',
  projectUuid: '',
  rule: '',
  ruleName: '',
  severity: '',
  status: '',
  type: '',
  secondaryLocations: [],
  flows: [],
  fromHotspot: false
};

it('should render', () => {
  expect(
    shallow(
      <ConciseIssue
        issue={issue}
        onFlowSelect={jest.fn()}
        onLocationSelect={jest.fn()}
        onSelect={jest.fn()}
        previousIssue={undefined}
        scroll={jest.fn()}
        selected={false}
        selectedFlowIndex={undefined}
        selectedLocationIndex={undefined}
      />
    )
  ).toMatchSnapshot();
});

it('should not render component', () => {
  expect(
    shallow(
      <ConciseIssue
        issue={{ ...issue, component: 'foo' }}
        onFlowSelect={jest.fn()}
        onLocationSelect={jest.fn()}
        onSelect={jest.fn()}
        previousIssue={{ ...issue, component: 'foo' }}
        scroll={jest.fn()}
        selected={false}
        selectedFlowIndex={undefined}
        selectedLocationIndex={undefined}
      />
    ).find('ConciseIssueComponent')
  ).toHaveLength(0);
});
