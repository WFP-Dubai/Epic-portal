/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package lu.globalepic.hook;

import java.util.List;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import lu.globalepic.util.LDAPUtil;
import lu.globalepic.util.LiferayUsersMapDAO;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.User;
/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class MyUserListener extends BaseModelListener<User> 
{
	private static Log _log = LogFactoryUtil.getLog(MyUserListener.class);
	 public void onBeforeCreate(User user) throws ModelListenerException 
	 {
		 	//user.setJobTitle("Web Developer");
		 	/*Phone phone = new PhoneWrapper();
		 	phone.setPrimary(true);
		 	phone.setNumber("0553609384");
		 	//phone.set
		 	
		 	Address address = new MyAddressWrapper();
		 	address.setZip("503060");
		 	address.setStreet1("BurDubai");
		 	address.setCity("Dubai");
			address.setPrimary(true);*/
		 	
		 	LDAPUtil.screenName=user.getScreenName();
		 	LDAPUtil.user = user;
		 	
		 	_log.info(" User ScreenName : "+user.getScreenName() );
		 	
		 	super.onBeforeCreate(user);
		 	
		 	
			_log.info(" #####   MyUserListener.onBeforeCreate : user"+ user );
	 }
	 	public void   onBeforeUpdate(User user) throws ModelListenerException 
		{
		 _log.info(" #####   START MyUserListener.onBeforeUpdate :");
		 _log.debug("user unencrypted:"+ user.getPasswordUnencrypted() );
		 
		 try
	 		{
	 			
//	 			if( user.getPasswordUnencrypted() != null )
//	 			{
//	 				user.setComments( user.getPasswordUnencrypted() );	 		
//	 			}
	 			
	 			_log.debug(" #####  user.getComments() :"+user.getComments() );
	 			if( user.getPasswordUnencrypted()!=null&& user.getPasswordUnencrypted()!="" &&  !user.isPasswordModified())
	 				LiferayUsersMapDAO.storePassword( user.getUserId(), user.getPasswordUnencrypted());
	 			
	 			LDAPUtil.screenName=user.getScreenName();
	 			LDAPUtil.user = user;
	 			List<Address> addressList = user.getAddresses();
	 			//Contact contact = user.getContact(); 
	 			List<Phone> phoneList = user.getPhones();
	 			//user.setJobTitle("Web Developer");  
	 			
	 			try
				{
					if( phoneList== null || phoneList.size()==0  )
					{
						LDAPUtil.importPhones( user);
					}
					if(addressList== null || addressList.size()==0  )
					{
						//LDAPUtil.importAddresses( user);
					}
					
					_log.info(" user.isPasswordModified  : "+user.isPasswordModified()  );
					//user.setPasswordUnencrypted( )
						
					
				}
				catch(Exception e){ e.printStackTrace(); }		
	 			
	 			//super.exportToLDAP(user);
	 		}
	 		catch (Exception e)
	 		{throw new ModelListenerException(e);		
	 		}
		 
		 _log.info(" #####   MyUserListener.onBeforeUpdate : user"+ user );
		}
	 
		public void onAfterUpdate(User user) throws ModelListenerException 
		{
			_log.info(" #####   START  MyUserListener.onAfterUpdate : iLDAPUtil.isPhoneAdded"+ LDAPUtil.isPhoneAdded );
			LDAPUtil.screenName=user.getScreenName();	
			LDAPUtil.user = user;
			try
			{	/* Not allowing user to remove all Phone numbers. */
			
				if( user.isPasswordModified() && user.getPasswordUnencrypted()!=null )
				{
					//String pwd = user.getPasswordUnencrypted();						
					// LDAPUtil.updatePassword( pwd );
					String pwd = user.getPassword();
					if( pwd!=null && pwd.length()> 0 ){
					LDAPUtil.updatePassword( user );
					
					}
					
				}
				else
				{
					if( user.getPhones()!= null && user.getPhones().size()>0 &&  !user.isPasswordModified() )
					{
						LDAPUtil.exportPhones( user );
					}
					if( user.getAddresses()== null || user.getAddresses().size()==0  )
					{
						//LDAPUtil.importAddresses( user );
					}
					//user.setPasswordUnencrypted( LiferayUsersMapDAO.getPlainPassword( user.getUserId()) );
					com.liferay.portal.theme.ThemeDisplay td = new com.liferay.portal.theme.ThemeDisplay();
					_log.info("  :getPortraitURL :"+ user.getPortraitURL( td )  );
					
					LDAPUtil.updateUser(user);
				}
				
			}
			catch(Exception e){ e.printStackTrace(); }			
			
			_log.info(" #####   END  MyUserListener.onAfterUpdate : user"+ user );			
		
		}
	 

	public void onAfterCreate(User user) throws ModelListenerException 
	{	
		_log.info(" #####   MyUserListener.onAfterCreate : user "+user );
		
 		try
 		{
 			_log.info(" #####  user.getAddresses() :"+user.getAddresses() );
 			
 			LDAPUtil.screenName=user.getScreenName();
 			LDAPUtil.user = user;
 			
 			List<Address> addressList = user.getAddresses();
 			//Contact contact = user.getContact(); 
 			List<Phone> phoneList = user.getPhones();
 			//user.setJobTitle("Web Developer");               
 			try
			{
				if( phoneList== null || phoneList.size()==0  )
				{
					LDAPUtil.importPhones( user);
				}
				if(addressList== null || addressList.size()==0  )
				{
					//LDAPUtil.importAddresses( user);
				}
				
			}
			catch(Exception e){ e.printStackTrace(); }		
 			
 			//super.exportToLDAP(user);
 		}
 		catch (Exception e)
 		{throw new ModelListenerException(e);		
 		}
 		
 		_log.info(" ##### END  MyUserListener.onAfterCreate :  ");

	}
	
}