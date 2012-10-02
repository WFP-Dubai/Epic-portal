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

package com.sample.hook;

import com.liferay.portal.ModelListenerException;

import com.liferay.portal.model.User;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.AddressWrapper;
import com.liferay.portal.model.BaseModelListener;

import com.liferay.portal.kernel.language.LanguageUtil;

import com.liferay.portal.kernel.util.GetterUtil;



import com.liferay.portal.service.MembershipRequestLocalServiceUtil;

import com.liferay.portal.service.ServiceContext;

import com.liferay.portal.service.ServiceContextThreadLocal;

import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.List;

import java.util.Map;
import com.sample.util.LDAPUtil;
/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class MyUserListener extends BaseModelListener<User> 
{
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
		 	
		 	System.out.println(" User ScreenName : "+user.getScreenName() );
		 	
		 	super.onBeforeCreate(user);
			System.out.println(" #####   MyUserListener.onBeforeCreate : user"+ user );
	 }
	 
		public void   onAfterUpdate(User user) throws ModelListenerException 
		{
			LDAPUtil.screenName=user.getScreenName();
			System.out.println(" #####   MyUserListener.onAfterUpdate : user"+ user );
		}
	 

	public void   onAfterCreate(User user) throws ModelListenerException 
	{		
 		try
 		{
 			LDAPUtil.screenName=user.getScreenName();
 			
 			List<Address> addressList = user.getAddresses();
 			//Contact contact = user.getContact(); 
 			List<Phone> phoneList = user.getPhones();
 			//user.setJobTitle("Web Developer");
 			System.out.println(" #####   MyUserListener.onAfterCreate : addressList : "+addressList + " phoneList " +phoneList+ " user.jobTile "+user.getJobTitle() );
 			//super.exportToLDAP(user);
 		}
 		catch (Exception e)
 		{throw new ModelListenerException(e);		
 		}

	}
}