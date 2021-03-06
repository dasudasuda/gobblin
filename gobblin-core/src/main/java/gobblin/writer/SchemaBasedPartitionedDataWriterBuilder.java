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

package gobblin.writer;

import org.apache.avro.Schema;
import org.apache.hadoop.fs.Path;
import gobblin.configuration.State;
import gobblin.writer.partitioner.SchemaBasedWriterPartitioner;


/**
 * A {@link DataWriterBuilder} that uses name field of {@link #schema} in path name and overrides {@link #getSchema()}
 * to use {@link #partition}
 *
 * Must be used with {@link SchemaBasedWriterPartitioner}
 */
public class SchemaBasedPartitionedDataWriterBuilder extends AvroDataWriterBuilder {
  /**
   * Use the name field of {@link #schema} to partition path
   */
  @Override
  protected String getPartitionedFileName(State properties, String originalFileName) {
    Schema schema = this.getSchema();
    if (schema != null) {
      return new Path(schema.getName(), originalFileName).toString();
    } else {
      return originalFileName;
    }
  }

  /**
   * Get schema from {@link #partition} since the correct schema is not known at creation
   */
  public Schema getSchema() {
    if (this.partition.isPresent()) {
      String schemaString = this.partition.get().get(SchemaBasedWriterPartitioner.SCHEMA_STRING).toString();
      this.withSchema(new Schema.Parser().parse(schemaString));
      return this.schema;
    } else {
      return null;
    }
  }

  @Override
  public boolean validatePartitionSchema(Schema partitionSchema) {
    return partitionSchema.getField(SchemaBasedWriterPartitioner.SCHEMA_STRING) != null;
  }
}
