/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package lu.globalepic.model;

import lu.globalepic.util.LPortalConnectionPool;
import lu.globalepic.model.UserVO;
import lu.globalepic.model.MarkVO;

import com.liferay.util.portlet.PortletProps;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Random;

import lu.maxmind.geoip.Location;



/**
 * <a href="GeoIPUsersMapDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class GeoIPUsersMapDAO {

	//constants
	private static final String _LOCALNET_ADDRESS = "192.168.0.X"; //CHANGE YOUR LOCALNET ADDRESS HERE TO IGNORE THATS ENTRIES
	
	private static final String _GET_USER_DATA = "select userId, screenName, loginIP, firstName, lastName from User_ where screenName<>'webmaster';";
	
	private static final String _GET_USER_DATA_BY_ID = "select userId, screenName, lastLoginIP, firstName, lastName from User_ where userid=?;";
	
	private static final String _UPDATE_USER_LOCATION = "update User_ set location= ? where userid=?;";
	
	private static final String _UPDATE_USER_CITY = "update User_ set city= ? where userid=?;";
	
	private static final String _UPDATE_IP = "update User_ set lastloginIP= ? where userid=?;";
	
	private static final String _UPDATE_USER_LAT = "update User_ set latitude= ? where userid=?;";
	
	private static final String _UPDATE_USER_LONG = "update User_ set longitude= ? where userid=?;";
	
	private static final String _GET_USER_LOCATION = "select location from  User_  where userid=?;";
	
	private static final String _UPDATE_USER_RESET_IS_AUTO = "update User_ set is_auto= ? where userid=?;";
	
	public String getUserCode( String userId )
	{
		System.out.println( " &&&&&&&&&&&&&&&&&  getUserCode  userId : "+userId );
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			
			con = LPortalConnectionPool.getConnection();
			ps = con.prepareStatement(_GET_USER_LOCATION);
			ps.setInt(1, Integer.parseInt(userId) );
			rs=ps.executeQuery();
			while (rs.next()) 
			{				
				String code = rs.getString(1);
				System.out.println( " 222222222222222222  getUserCode  userId : "+userId );
				if( code!=null) code =code.trim();
				
				System.out.println( "££££   code from DB : "+ code +" with userId : "+userId );
				
//				if( code!="" || code.isEmpty() )
//				{
//					
//						String countryName = lu.globalepic.portlet.GeoIPUsersMapPortlet.getUserCountry();
//					
//						CountryCodes cc = new CountryCodes();
//					
//						if(countryName!= null )
//						{
//							code =  cc.getCountryCodeByName( countryName );
//							
//							System.out.println( "££££   code from URL  : "+ code + " for countryName : "+countryName);
//						}
//					
//					
//				}
				
				return code; 
				
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return null;
		
	}
	
	public boolean updateUserLocation( String userId, String code )
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			
			con = LPortalConnectionPool.getConnection();
			ps = con.prepareStatement(_UPDATE_USER_LOCATION);
			ps.setString(1, code);
			ps.setInt(2, Integer.parseInt(userId) );
			int res =ps.executeUpdate();
			
			System.out.println("£££££££££  res : "+res);
			
			if( res >= 0 )
			{
				return true;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return false;
		
	}
	public static boolean updateIP( String userId, String ip, Location location )
	{
		System.out.println(" ################################################");
		System.out.println(" updateIP ######## UserID : "+userId + " ip : "+ip );
		System.out.println(" ################################################");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
			
			con = LPortalConnectionPool.getConnection();
			ps = con.prepareStatement(_UPDATE_IP);
			ps.setString(1, ip.trim() );
			ps.setInt(2, Integer.parseInt(userId) );
			int res =ps.executeUpdate();
			
			System.out.println("£££££££££  updateIP res : " +res);
			
			ps = con.prepareStatement(_UPDATE_USER_LOCATION);
			ps.setString(1, location.countryCode );
			ps.setInt(2, Integer.parseInt(userId) );
			res =ps.executeUpdate();
			
			if( location!=null)
			{
				System.out.println("£££££££££ _UPDATE_USER_LOCATION  res : "+res);
				
				ps = con.prepareStatement(_UPDATE_USER_CITY );
				ps.setString(1, location.city );
				ps.setInt(2, Integer.parseInt(userId) );
				res =ps.executeUpdate();
				
				System.out.println("£££££££££ _UPDATE_USER_CITY  res : "+res);
				
				
				ps = con.prepareStatement(_UPDATE_USER_LAT );
				ps.setFloat(1, location.latitude );
				ps.setInt(2, Integer.parseInt(userId) );
				res =ps.executeUpdate();
				
				System.out.println("£££££££££ _UPDATE_USER_LAT  res : "+res); 
				
				ps = con.prepareStatement(_UPDATE_USER_LONG );
				ps.setFloat(1, location.longitude );
				ps.setInt(2, Integer.parseInt(userId) );
				res =ps.executeUpdate();
				
				System.out.println("£££££££££ _UPDATE_USER_LONG  res : "+res);
				
				ps = con.prepareStatement(_UPDATE_USER_RESET_IS_AUTO );
				ps.setInt(1, 1 );
				ps.setInt(2, Integer.parseInt(userId) );
				res =ps.executeUpdate();
				
				System.out.println("£££££££££ _UPDATE_USER_RESET_IS_AUTO  res : "+res);
			}
			
			
			if( res >= 0 )
			{
				return true;
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		return false;
		
	}

	

	public static Integer getNumUsers() {
		Integer ret = 0; 
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ip = null;
		try {
			con = LPortalConnectionPool.getConnection();
			ps  = con.prepareStatement(_GET_USER_DATA);
			rs = ps.executeQuery();
			while (rs.next()) { 
				ip = rs.getString(3);
				if( ip!=null && ip.trim().equals("127.0.0.1"))
				{
					ip="94.200.128.204";
				}
				if ((ip!=null)&&(ip!="")&&(ip.length()>7)) {if(!GeoIPUsersMapDAO.isLocalIP(ip)) ret++;}
			}	
		} catch (Exception e) {e.printStackTrace();}
    		
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}

		return ret;
	}

	public static boolean isLocalIP(String ip) {
		boolean ret = true;
		try{		
			String aux = _LOCALNET_ADDRESS.substring(0,_LOCALNET_ADDRESS.indexOf("X")-1);
			String[] splitedAux = aux.split("\\.");
			
			String[] splitedIP  = ip.split("\\.");

			for (int i=0;i<splitedAux.length;i++) {if (!splitedAux[i].equals(splitedIP[i])) ret=false;}
		} catch (Exception e) {e.printStackTrace();}
		return ret;
	}
	

}
