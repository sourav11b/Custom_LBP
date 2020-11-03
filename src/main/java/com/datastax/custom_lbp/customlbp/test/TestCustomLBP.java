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

package com.datastax.custom_lbp.customlbp.test;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestCustomLBP {

	public TestCustomLBP() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try (CqlSession session = CqlSession.builder().build()) {

			PreparedStatement prepared = session.prepare("select * from cycling.cyclist_semi_pro  where id = ?");
			while (true) {
				try {
					BoundStatement bound = prepared.bind(new Random().nextInt(20));

					ResultSet rs = session.execute(bound);

					for (Row row : rs) {
						System.out.println(" found row : " + row.getInt("id"));
					}
					System.out.println(" coordinator node : "
							+ rs.getExecutionInfo().getCoordinator().getBroadcastAddress().get().getHostName());
					System.out.println(
							" coordinator node DC : " + rs.getExecutionInfo().getCoordinator().getDatacenter());

					System.out.println("------------------------------------------------------------");

					Thread.sleep(2000);
				} catch (Exception e) {
					System.err.println(" Exception : " + e.getMessage());
				}
			}

		}

	}

}
