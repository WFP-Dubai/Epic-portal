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

import lu.globalepic.util.LDAPUtil;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.BaseModelListener;

/**
 * 
 * @author Mohammed Kaleem
 */
public class MyAddressListener extends BaseModelListener<Address> 
{
	
	 public void onBeforeCreate(Address address) throws ModelListenerException 
	 {
		 	System.out.println(" #####   MyAddressListener.onBeforeCreate : address"+ address );
		 	
		 	LDAPUtil.importAddresses(address);
		 	
		 	/*address.setZip("503060");
		 	address.setStreet1("Dubai Pearl Building");
		 	address.setStreet2("Bur Dubai");
		 	address.setStreet3("Flat No 303");
		 	address.setCity("Dubai");
		 	address.setPrimary(true);*/
			
		 	super.onBeforeCreate(address);
			
	 }
	 public void onBeforeUpdate(Address address) throws ModelListenerException 
	 {
		 	System.out.println(" #####   MyAddressListener.onBeforeUpdate : address"+ address );
		 	
		 	if( address.isNew() ) LDAPUtil.importAddresses(address);
		 	
		 	/*address.setZip("503060");
		 	address.setStreet1("Dubai Pearl Building");
		 	address.setStreet2("Bur Dubai");
		 	address.setStreet3("Flat No 303");
		 	address.setCity("Dubai");
		 	address.setPrimary(true);*/
			
		 	super.onBeforeUpdate(address);
			
	 }
	 public void onAfterUpdate(Address address) throws ModelListenerException 
	 {
		 	System.out.println(" #####   START MyAddressListener.onAfterUpdate : address"+ address );
		 
		 	LDAPUtil.exportAddress( address);
		 	
		 	//super.onAfterUpdate(address);
		 	
		 	System.out.println(" #####   END MyAddressListener.onAfterUpdate : address"+ address );
			
	 }

	
}