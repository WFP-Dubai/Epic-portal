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

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class MyAddressListener extends BaseModelListener<Address> 
{
	static
	{
		
		 System.out.println(" #####   MyAddressListener.static : " );
	}
	 public void onBeforeCreate(Address address) throws ModelListenerException 
	 {
		 System.out.println(" #####   MyAddressListener.onBeforeCreate : address"+ address );
		 	address.setZip("503060");
		 	address.setStreet1("Dubai Pearl Building");
		 	address.setStreet2("Bur Dubai");
		 	address.setStreet3("Flat No 303");
		 	address.setCity("Dubai");
		 	address.setPrimary(true);
			
		 	super.onBeforeCreate(address);
			
	 }

	
}