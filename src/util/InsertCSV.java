package util;

import java.io.*;
import java.sql.PreparedStatement;

import com.mysql.jdbc.Connection;

public class InsertCSV {
	private Connection conn;
	private File[] files;

	//
	public InsertCSV(Connection conn, String saveDir) {
		this.conn = conn;

		// traversing directories
		File root = new File(saveDir);
		File rootFiles[] = root.listFiles();
		int count = 0;
		for (File file : rootFiles) {
			if (file.isFile()) {
				files[count] = file.getAbsoluteFile();
			}
		}
	}

	public File[] getFiles() {
		return this.files;
	}

	//
	public void insertFiles() throws Exception {
		for (File file : files) {
			int count = 0;
			String sql = " insert into tall (lot, wafer, x, y, measurement, value, yield) "
					+ " values(?, ?, ?, ?, ?, ?,?)";
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			BufferedReader bfr = new BufferedReader(read);
			String line = null;
			while ((line = bfr.readLine()) != null) {
				String item[] = line.split(",");
				if (!item[6].equalsIgnoreCase("yield")
						&& item.length == 7) {
					pstmt.setString(1, item[0]);
					pstmt.setString(2, item[1]);
					pstmt.setString(3, item[2]);
					pstmt.setString(4, item[3]);
					pstmt.setString(5, item[4]);
					pstmt.setString(6, item[5]);
					pstmt.setString(7, item[6]);
				}
				pstmt.addBatch();
			}

			pstmt.executeBatch();
			conn.commit();
		}
	}
}
