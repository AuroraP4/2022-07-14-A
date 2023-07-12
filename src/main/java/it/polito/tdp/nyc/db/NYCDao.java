package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.NTA;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllBorought() {
		String sql = "SELECT DISTINCT n.Borough "
				   + "FROM nyc_wifi_hotspot_locations n ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Borough"));   }

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");  }

		return result;
	}

	public List<NTA> getNTA(String borough) {
		String sql = "SELECT DISTINCT NTAcode, SSID "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE Borough =? "
				+ "ORDER BY NTAcode ";
		List<NTA> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough);
			ResultSet res = st.executeQuery();

			String lastNTACode = "";
			while (res.next()) {
				if(!res.getString("NTAcode").equals(lastNTACode) ) {
					Set<String> ssdis = new HashSet<>();
					ssdis.add(res.getString("SSID"));
					result.add(new NTA(res.getString("NTAcode"), ssdis)); 
					lastNTACode = res.getString("NTAcode"); 
		   	} else { 
		   			result.get(result.size()-1).getSSID().add(res.getString("SSID"));   }
					}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");  }

		return result;
	}
		
}
