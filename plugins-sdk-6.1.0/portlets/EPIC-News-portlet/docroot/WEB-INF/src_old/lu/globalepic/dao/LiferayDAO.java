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

package lu.globalepic.dao;

import lu.globalepic.util.LPortalConnectionPool;
import java.text.DateFormat;
import java.util.Date;
import lu.globalepic.dao.UserVO;
import lu.globalepic.dao.MarkVO;

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
import lu.globalepic.dao.BlogDAO;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import  lu.globalepic.util.BlogsWrapper;
/**
 * <a href="LiferayDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jose Miguel Trinchan
 *
 */

public class LiferayDAO {

	private static Log _log = LogFactoryUtil.getLog(LiferayDAO.class);
	private static final String _UPDATE_USER_LOCATION = "update User_ set location= ? where userid=?;";
	private static final String _GET_USER_LIST_BY_POPULAR = "select userId from  blogsstatsuser order by ratingstotalentries  desc";
	private static final String _GET_ALL_BLOGS = "select userid, username,entryid,title,urltitle,statusbyusername,createdate,content from blogsentry order by displaydate desc";
	private static final String _GET_POPULAR_BLOGS = "select viewcount, entryid-2  entryid from assetentry where entryid in ( select entryid+2 from blogsentry );";
	public static List<String> getUserList(Connection con)
	{
		_log.info( " ####################  START getUserList  ####################" );
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<String> userList = null;
		
		try 
		{
			
			ps = con.prepareStatement(_GET_USER_LIST_BY_POPULAR);
			rs=ps.executeQuery();
			userList = new ArrayList<String>();
			while (rs.next()) 
			{				
				userList.add( rs.getString(1) );
			}			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		_log.info( " ####################  END getUserList  ####################" );
		
		return userList;
		
	}
	public static List<BlogDAO> getPopularBlogs(Connection con)
	{
		_log.info( " ####################  START getPopularBlogs  ####################" );
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<BlogDAO> blogList = null;
		
		try 
		{
			
			ps = con.prepareStatement(_GET_POPULAR_BLOGS);
			rs=ps.executeQuery();
			blogList = new ArrayList<BlogDAO>();
			while (rs.next()) 
			{		
				BlogDAO blogentry = new BlogDAO();
				blogentry.setViewsCount( rs.getString(1) );
				blogentry.setEntryId( rs.getString(2) );
				blogList.add( blogentry );
			}			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		_log.info( " ####################  END getPopularBlogs  ####################" );
		
		return blogList;
		
	}
	public static BlogsWrapper getAllBlogs()
	{
		_log.info( " ####################  START BlogDAO  ####################" );
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<BlogDAO> blogsList = null;
		List<BlogDAO> resultList = null;
		
		BlogsWrapper blogWrapper = new BlogsWrapper();
		try 
		{
			
			con = LPortalConnectionPool.getConnection();
			List<BlogDAO> popularBlogList = null;			
			
			ps = con.prepareStatement(_GET_ALL_BLOGS);
			
			rs=ps.executeQuery();
			blogsList = new ArrayList<BlogDAO>();
			System.out.println(" con : "+con +" rs :"+rs );
			while (rs.next()) 
			{		
				BlogDAO blogDao = new BlogDAO();
				blogDao.setUserId( rs.getString(1) ) ;
				blogDao.setUserName(rs.getString(2) );
				blogDao.setEntryId( rs.getString(3) );
				blogDao.setTitle( rs.getString(4) );
				blogDao.setUrlTitle(rs.getString(5) );	
				System.out.println(" rs.getString(5) : "+rs.getString(5) );
				String dateStr = DateFormat.getDateInstance().format(rs.getDate(7));
				
				System.out.println(" dateStr : "+ dateStr );
				String dateArray[] = dateStr.split("-");
				//String dateArray[] = dateStr.split(",");
				//dateStr = dateArray[1];
				//dateArray = dateArray[0].split(" ");
			//blogDao.setCreatedDate( dateArray[1] +" "+ dateArray[0]+" "+ dateStr );
				blogDao.setCreatedDate( dateArray[0] +" "+ dateArray[1]+" "+ dateArray[2] );
				blogDao.setDescription(rs.getString(8).substring(0, 100) );
				System.out.println( " rs.getString(8) : "+rs.getString(8));
				blogsList.add(  blogDao );
				
			}	
			resultList = new ArrayList<BlogDAO>();
			blogWrapper.setRecentBlogsList( blogsList );
			
			popularBlogList = getPopularBlogs(con);
			
			for( BlogDAO popularBlog : popularBlogList)
			{
				for( BlogDAO temp : blogsList)
				{
					if( popularBlog.getEntryId().equals(temp.getEntryId()))
					{
						resultList.add(temp);
					}
				}
			}
			blogWrapper.setPopularBlogsList( resultList );
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		_log.info( " ####################  END BlogDAO  ####################" );
		
		return blogWrapper;
		
	}
	public String getDateString(String date)
	{
		String dateString="";
		String dateArray[] = date.split("-");
		dateString = dateArray[2];
		
		return dateString;
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
			
			_log.info("£££££££££  res : "+res);
			
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
	

}
