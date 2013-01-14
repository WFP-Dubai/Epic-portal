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
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portal.util.PortalUtil;
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
import java.util.Map;
import java.util.Random;

import lu.maxmind.geoip.Location;
import lu.globalepic.dao.BlogDAO;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import  lu.globalepic.util.BlogsWrapper;
import java.util.HashMap;
import java.util.Iterator;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
	private static final String _GET_ALL_BLOGS = "select userid, username, entryid, title, urltitle, statusbyusername, createdate, description from blogsentry order by createdate desc";
	private static final String _GET_POPULAR_BLOGS = "select viewcount, entryid-2  entryid from assetentry where entryid in ( select entryid+2 from blogsentry ) order by viewcount desc;";
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
	
	public static List getMostRatedList(List inputList, String key, int size )
	{
		_log.info( " ####################  START getMostRatedList  ####################"+inputList.size() );
		List<RatingsStats> result =null;
		List ratedList = null;
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(RatingsStats.class, PortalClassLoaderUtil.getClassLoader());
		query.addOrder(OrderFactoryUtil.desc("averageScore"));
		Criterion className = RestrictionsFactoryUtil.eq("classNameId", PortalUtil.getClassNameId(BlogsEntry.class.getName()) );
	    query.add( className );
		query.setLimit(0,size );
		try
		{
			result = RatingsStatsLocalServiceUtil.dynamicQuery(query);
			_log.info(" result : "+result );
			if( result!= null )
			{ 
				ratedList = new ArrayList();				
			 	for(RatingsStats ratingStats : result)
			 	{
			 		for(int i=0; i<inputList.size(); i++ )
			    		{
			 				Map map = (HashMap) inputList.get(i);    
			 				if(  Long.parseLong( map.get(key).toString()) == ratingStats.getClassPK()  ) 
							{
								_log.info(" ratingStats.getClassPK() : "+ratingStats.getClassPK()  );
								map.put("rating", ratingStats.getAverageScore() );
								ratedList.add(map );					
							}    					
			    		}
			 	}
			 }
		
		}catch(Exception e){ e.printStackTrace(); }
		
		
		_log.info( " ####################  END getMostRatedList  ####################"+ratedList.size() );
		
		return ratedList;
	}
	public static List getMostCommentedList(List ratedList, String key, int size )
	{
		_log.info( " ####################  START getMostCommentedList  ####################"+ratedList.size() );
		List newList = new ArrayList();
		for(int i=0; i<ratedList.size()-1; i++ )
		{
			Map map = (HashMap) ratedList.get(i);
			int k=i;
			for(int j=i+1; j<ratedList.size();j++)
			{
				Map map2 = (HashMap) ratedList.get(j);
				if( Integer.parseInt(  map.get(key).toString() ) < Integer.parseInt(  map2.get(key).toString() ) ) k=j;
				
			}			
			if(k>i)
			{
				_log.info(" k = "+k +" i: "+i);
				ratedList.set(i , (HashMap) ratedList.get(k) );
				ratedList.set(k,  map ) ;
			}
		}		
		_log.info( " ####################  END getMostCommentedList  ####################" );
		
		return ratedList;
	}
	
	public static Map getAllBlogs(int size)
	{
		_log.info( " ####################  START BlogDAO  ####################" );
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		List<BlogDAO> blogsList = null;
		List<BlogDAO> resultList = null;
		 Map resultMap = new HashMap();
		 List list = new ArrayList();
		 List commentList = new ArrayList();
		 List ratedList = new ArrayList();
		//BlogsWrapper blogWrapper = new BlogsWrapper();
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
				  Map recentMap = new HashMap();
				  String dateStr = DateFormat.getDateInstance().format(rs.getDate(7));					
				  System.out.println(" dateStr : "+ dateStr );
				  //String dateArray[] = dateStr.split("-");
				  String dateArray[] = dateStr.split(",");
				  dateStr = dateArray[1];
				  dateArray = dateArray[0].split(" ");		 
				  
				  recentMap.put("userId", rs.getString(1) );
				  recentMap.put("userName", rs.getString(2) );
				  recentMap.put("entryId", rs.getString(3) );		
				  double score =RatingsStatsLocalServiceUtil.getStats( BlogsEntry.class.getName(), Long.parseLong( rs.getString(3) ) ).getAverageScore();
				 _log.info(" rs.getString(3) : "+rs.getString(4) );
				  //recentMap.put("rating", ""+ new Double(score).intValue() );				  
				  recentMap.put("title", rs.getString(4) );
				  recentMap.put("urlTitle", rs.getString(5)  );					
				  //recentMap.put("createdDate", dateArray[0] +" "+ dateArray[1]+" "+ dateArray[2] );
				  recentMap.put("createdDate", dateArray[1] +" "+ dateArray[0]+" "+ dateStr );
				  recentMap.put("description", rs.getString(8) );
				  
				  long classNameId = PortalUtil.getClassNameId(BlogsEntry.class.getName());
				  int messagesCount = com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil.getDiscussionMessagesCount(classNameId, Long.parseLong( rs.getString(3) ), com.liferay.portal.kernel.workflow.WorkflowConstants.STATUS_APPROVED);
				  System.out.println(" messagesCount "+ messagesCount );
				  list.add(recentMap);
				  recentMap.put("comments", ""+messagesCount );
				  commentList.add( recentMap );
				  ratedList.add( recentMap );
				  BlogDAO blogDao = new BlogDAO();
				  blogDao.setUserId( rs.getString(1) ) ;
				  blogDao.setUserName(rs.getString(2) );
				  blogDao.setEntryId( rs.getString(3) );
				  blogDao.setTitle( rs.getString(4) );
				  blogDao.setUrlTitle(rs.getString(5) );	
				  blogDao.setCreatedDate( dateArray[1] +" "+ dateArray[0]+" "+ dateStr );
				  //blogDao.setCreatedDate( dateArray[0] +" "+ dateArray[1]+" "+ dateArray[2] );
				  blogDao.setDescription(rs.getString(8) );
				  System.out.println( " rs.getString(8) : "+rs.getString(8));
				  blogsList.add(  blogDao );
			}	
			resultMap.put("recentMap", list );
			resultMap.put("commentMap", getMostCommentedList( commentList,"comments", size  )  );
			resultMap.put("ratedMap", getMostRatedList( ratedList ,"entryId", size )  );
			
			list = new ArrayList();
			
			popularBlogList = getPopularBlogs(con);
			
			for( BlogDAO popularBlog : popularBlogList)
			{
				for( BlogDAO temp : blogsList)
				{
					if( popularBlog.getEntryId().equals(temp.getEntryId()))
					{ 
						Map popularMap = new HashMap();
						popularMap.put("userId", temp.getUserId()  );
						popularMap.put("userName", temp.getUserName() );
						popularMap.put("entryId", temp.getEntryId() );
						popularMap.put("title", temp.getTitle() );
						popularMap.put("urlTitle", temp.getUrlTitle()  );					
						popularMap.put("createdDate", temp.getCreatedDate() );
						popularMap.put("description", temp.getDescription() );
						popularMap.put("viewsCount", popularBlog.getViewsCount() );						
						list.add( popularMap  );
						//resultList.add(temp);
					}
				}
			}
			
			resultMap.put("popularMap", list );
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {LPortalConnectionPool.cleanUp(con, ps, rs);}
		
		_log.info( " ####################  END BlogDAO  ####################" );
		
		return resultMap;
		
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
