package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Asiakkaat;

public class Dao {
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql = "";
	private String db = "Myynti.sqlite";

	private Connection yhdista() {
		Connection con = null;
		String path = System.getProperty("catalina.base");
		path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); 
		System.out.println(path); //tietokanta polku
		String url = "jdbc:sqlite:" + path + db;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection(url);
			System.out.println("Yhteys avattu.");
		} catch (Exception e) {
			System.out.println("Yhteyden avaus epäonnistui.");
			e.printStackTrace();
		}
		return con;
	}

	private void sulje() {		
		if (stmtPrep != null) {
			try {
				stmtPrep.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
				System.out.println("Yhteys suljettu.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public ArrayList<Asiakkaat> getAllItems() {
		ArrayList<Asiakkaat> asiakkaat = new ArrayList<Asiakkaat>();
		sql = "SELECT * FROM asiakkaat ORDER BY asiakas_id DESC"; 
		try {
			con = yhdista();
			if (con != null) { 
				stmtPrep = con.prepareStatement(sql);
				rs = stmtPrep.executeQuery();
				if (rs != null) { 
					while (rs.next()) {
						Asiakkaat Asiakkaat = new Asiakkaat();
						Asiakkaat.setId(rs.getInt(1));
						Asiakkaat.setEtunimi(rs.getString(2));
						Asiakkaat.setSukunimi(rs.getString(3));
						Asiakkaat.setPuhelin(rs.getString(4));
						Asiakkaat.setSposti(rs.getString(4));
						asiakkaat.add(Asiakkaat);						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return asiakkaat;
	}
	
	public ArrayList<Asiakkaat> getAllItems(String searchStr) { 
		ArrayList<Asiakkaat> asiakkaat = new ArrayList<Asiakkaat>();
		sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? or puhelin LIKE ? or sposti LIKE ? ORDER BY asiakas_id DESC";
		try {
			con = yhdista();
			if (con != null) { 
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + searchStr + "%");
				stmtPrep.setString(2, "%" + searchStr + "%");
				stmtPrep.setString(3, "%" + searchStr + "%");
				stmtPrep.setString(4, "%" + searchStr + "%");
				rs = stmtPrep.executeQuery();
				if (rs != null) { 
					while (rs.next()) {
						Asiakkaat Asiakkaat = new Asiakkaat();
						Asiakkaat.setId(rs.getInt(1));
						Asiakkaat.setEtunimi(rs.getString(2));
						Asiakkaat.setSukunimi(rs.getString(3));
						Asiakkaat.setPuhelin(rs.getString(4));
						Asiakkaat.setSposti(rs.getString(4));
						asiakkaat.add(Asiakkaat);		
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sulje();
		}
		return asiakkaat;
	}
}
