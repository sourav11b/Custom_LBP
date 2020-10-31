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

public class test {

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try (CqlSession session = CqlSession.builder().addContactPoint(new InetSocketAddress("54.67.24.25", 9042))
				.addContactPoint(new InetSocketAddress("50.18.97.114", 9042)).withLocalDatacenter("DC1").build()) {

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
