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





/**
 * <a href="BlogDAO.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mohammed Kaleem
 *
 */

public class BlogDAO {
	
	private String userId;
	private String entryId;
	private String userName;
	private String title;
	private String urlTitle;
	private String viewsCount;
	private String createdDate;
	private String description;
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setEntryId(String entryId)
	{
		this.entryId = entryId;
	}
	public String getEntryId()
	{
		return entryId;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	public void setUrlTitle(String urlTitle)
	{
		this.urlTitle = urlTitle;
	}
	public String getUrlTitle()
	{
		return urlTitle;
	}
	public void setViewsCount(String viewsCount)
	{
		this.viewsCount = viewsCount;
	}
	public String getViewsCount()
	{
		return viewsCount;
	}
	
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getCreatedDate()
	{
		return createdDate;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getDescription()
	{
		return description;
	}
	
}
