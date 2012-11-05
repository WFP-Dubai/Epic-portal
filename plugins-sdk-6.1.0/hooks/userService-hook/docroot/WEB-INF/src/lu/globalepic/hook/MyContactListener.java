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
import lu.globalepic.util.LDAPUserInfo;
import lu.globalepic.util.LDAPUtil;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.ListTypeServiceUtil;
/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Hugo Huijser
 */
public class MyContactListener extends BaseModelListener<Contact> 
{
	public static boolean flag=true;
	
	 public void onBeforeCreate(Contact contact) throws ModelListenerException 
	 {
		 	System.out.println(" #####   START MyContactListener.onBeforeCreate : contact"+ contact );
		 	LDAPUserInfo ldapUser = LDAPUtil.getLDAPUserInfoByContact(contact);
		 	System.out.println( " ldapUser "+ ldapUser );
		 	contact.setFacebookSn(ldapUser.facebook);
			contact.setYmSn(ldapUser.ym);
			contact.setSkypeSn(ldapUser.skype);
			contact.setMySpaceSn(ldapUser.mySpace);
			contact.setTwitterSn(ldapUser.twitter);
			contact.setMsnSn(ldapUser.msn);
			contact.setIcqSn(ldapUser.gtalk);
			contact.setAimSn(ldapUser.sip);
			contact.setJabberSn(ldapUser.vhf);		
			
		 	super.onBeforeCreate(contact);
			System.out.println("##### END  MyContactListener.onBeforeCreate : contact"+ contact );
	 }
	 public void onAfterCreate(Contact contact) throws ModelListenerException 
	 {
		 System.out.println(" #####  start MyContactListener.onAfterCreate : contact"+ contact );
		 
		 	if( (contact.getJabberSn()==null||contact.getJabberSn()!="") &&
		 			(contact.getMsnSn()!=null||contact.getMsnSn()!="") && 
		 			(contact.getMySpaceSn()!=null||contact.getMySpaceSn()!="") &&
		 			(contact.getSkypeSn()!=null||contact.getSkypeSn()!="") &&
		 			(contact.getIcqSn()!=null||contact.getIcqSn()!="") 
		 			
		 			 )
		 	{
		 		
		 	LDAPUserInfo ldapUser = LDAPUtil.getLDAPUserInfoByContact( contact);
		 	System.out.println( " ldapUser "+ ldapUser );
		 	contact.setFacebookSn(ldapUser.facebook);
			contact.setYmSn(ldapUser.ym);
			contact.setSkypeSn(ldapUser.skype);
			contact.setMySpaceSn(ldapUser.mySpace);
			contact.setTwitterSn(ldapUser.twitter);
			contact.setMsnSn(ldapUser.msn);
			contact.setIcqSn(ldapUser.gtalk);
			contact.setAimSn(ldapUser.sip);
			contact.setJabberSn(ldapUser.vhf);
			super.onAfterCreate(contact);
		
			
		 	}
		 	System.out.println(" ##### end  MyContactListener.onAfterCreate : contact"+ contact );
		 
		
	 }
	 public void   onBeforeUpdate(Contact contact) throws ModelListenerException 
		{
			
				System.out.println(" #####  KALEEM START MyContactListener.onBeforeUpdate : Contact"+ contact );
				if(  contact.getAimSn()=="" || contact.getAimSn()==null )LDAPUtil.beforeUpdateContact(contact );
				//LDAPUtil.updateContact(contact,true );
				super.onBeforeUpdate(contact);
				System.out.println(" #####   END MyContactListener.onBeforeUpdate : Contact"+ contact );
		}
	 public void   onAfterUpdate(Contact contact) throws ModelListenerException 
	{
		
			System.out.println(" #####   START MyContactListener.onAfterUpdate : contact.getPrefixId("+ contact.getPrefixId() );
			try{
				int prefix = contact.getPrefixId();
				if( prefix>0 ){
			String prefixName =ListTypeServiceUtil.getListType(contact.getPrefixId()).getName();
			
			System.out.println(" ##### prefix"+ prefix+ "prefixName "+ prefixName );
				}
				LDAPUtil.updateContact(contact,false );
			}catch(Exception e){ e.printStackTrace(); }
			
			System.out.println(" #####   END MyContactListener.onAfterUpdate : ");
	}

	
}