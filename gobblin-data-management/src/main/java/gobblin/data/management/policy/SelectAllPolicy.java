/*
* Copyright (C) 2014-2016 LinkedIn Corp. All rights reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License. You may obtain a copy of the
* License at  http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed
* under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
* CONDITIONS OF ANY KIND, either express or implied.
*/

package gobblin.data.management.policy;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import lombok.ToString;

import gobblin.data.management.version.FileSystemDatasetVersion;


/**
 * Implementation of {@link VersionSelectionPolicy} that selects all {@link FileSystemDatasetVersion}s.
 */
@ToString
public class SelectAllPolicy implements VersionSelectionPolicy<FileSystemDatasetVersion> {

  public SelectAllPolicy(Properties properties) {}

  @Override
  public Class<? extends FileSystemDatasetVersion> versionClass() {
    return FileSystemDatasetVersion.class;
  }

  @Override
  public Collection<FileSystemDatasetVersion> listSelectedVersions(List<FileSystemDatasetVersion> allVersions) {
    return allVersions;
  }
}
