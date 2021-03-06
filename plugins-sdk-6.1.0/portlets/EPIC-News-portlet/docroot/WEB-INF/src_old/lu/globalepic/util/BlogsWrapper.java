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

package lu.globalepic.util;



import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import lu.globalepic.dao.BlogDAO;
import java.util.List;

/**
 * <a href="BlogsWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author MOHAMMED KALEEM
 *
 */

public class BlogsWrapper   {

	
	private List<BlogDAO> recentBlogsList;
	private List<BlogDAO> popularBlogsList;
	private List<BlogDAO> commentedBlogsList;
	private List<BlogDAO> ratedBlogsList;
	
	public void setRecentBlogsList(List<BlogDAO>  recentBlogsList)
	{
		this.recentBlogsList = recentBlogsList;
	}
	public List<BlogDAO> getRecentBlogsList()
	{
		return recentBlogsList;
	}
	public void setPopularBlogsList(List<BlogDAO>  popularBlogsList)
	{
		this.popularBlogsList = popularBlogsList;
	}
	public List<BlogDAO> getPopularBlogsList()
	{
		return popularBlogsList;
	}	
	public void setCommentedBlogsList(List<BlogDAO>  commentedBlogsList)
	{
		this.commentedBlogsList = commentedBlogsList;
	}
	public List<BlogDAO> getCommentedBlogsList()
	{
		return commentedBlogsList;
	}
	public void setRatedBlogsList(List<BlogDAO>  ratedBlogsList)
	{
		this.ratedBlogsList = ratedBlogsList;
	}
	public List<BlogDAO> getRatedBlogsList()
	{
		return ratedBlogsList;
	}
	

	
}
