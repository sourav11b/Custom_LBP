/*
 * Copyright DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.custom_lbp.session_handling;

import com.datastax.oss.driver.api.core.CqlSession;
import edu.umd.cs.findbugs.annotations.NonNull;
import net.jcip.annotations.NotThreadSafe;

/**
 * Helper class to build a {@link CqlSession} instance.
 *
 * <p>This class is mutable and not thread-safe.
 */
@NotThreadSafe
public class CqlSessionBuilder extends SessionBuilder<CqlSessionBuilder, CqlSession> {

  @Override
  protected CqlSession wrap(@NonNull CqlSession defaultSession) {
    return defaultSession;
  }
}