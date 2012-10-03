/**
 * 
 */
package com.sample.util;

/**
 * @author kaleem.mohammed
 *
 */

import java.util.Hashtable;

import java.util.Enumeration;
import java.util.List;

import java.util.ArrayList;
import javax.naming.*;
import javax.naming.directory.*;
import com.sample.util.LDAPUserInfo;

import com.liferay.portal.model.Contact;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ListTypeServiceUtil;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.User;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Phone;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
/*

 * Retrieve several attributes of a particular entry.
 *
 * [equivalent to getattrs.c in Netscape SDK]
 */
public class LDAPUtil 
{
	public static String screenName=null;
	
	public static boolean isPhoneAdded=false;
	
	public static final int listType_business=11006;
	public static final int listType_mobile=11008;
	public static final int listType_thuraya=11012;
	public static final int listType_wave=11010;
	public static final int listType_foodsat=11007;
	
	public static final int addressType_personal=11002;
	public static final int addressType_business=11000;
	public static final int addressType_other=11001;
	
	
	
	
	public static DirContext  getLDAPContext()
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPContext #####################");
		Hashtable env = new Hashtable(5, 0.75f);	
		
		DirContext ctx = null;

		// env.put(LdapContext.CONTROL_FACTORIES,
		// conf.getProperty("ldap.factories.control"));
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://ldap-dev.globalepic.lu:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL,
				"cn=wfp-write,ou=ldapAccess,dc=emergency,dc=lu");
		env.put(Context.SECURITY_CREDENTIALS, "My3CatsOnATree");
		env.put(Context.STATE_FACTORIES, "PersonStateFactory");
		env.put(Context.OBJECT_FACTORIES, "PersonObjectFactory");

		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,
				"ldap://ldap-dev.globalepic.lu:389/dc=emergency,dc=lu");

		// env.put(Context.INITIAL_CONTEXT_FACTORY, Env.INITCTX);
		/* Specify host and port to use for directory service */
		// env.put(Context.PROVIDER_URL, Env.MY_SERVICE);
		try 
		{
			/* get a handle to an Initial DirContext */
			//DirContext ctx = new InitialDirContext(env);	
			System.out.println(" ############## END  LDAPUtil.getLDAPContext #####################");
			ctx=  new InitialDirContext(env);
		}
		catch(Exception e){	e.printStackTrace();}
		
		return ctx;

	}

	public static LDAPUserInfo getLDAPUserInfo()
	{
		
		System.out.println(" ############## START  LDAPUtil.getLDAPUser #####################");
		
		LDAPUserInfo ldapUserInfo = new LDAPUserInfo();
		
		/* get a handle to an Initial DirContext */
		DirContext ctx = getLDAPContext();			
		
		//Attributes attrs = sr.getAttributes();			
		Attributes attrs = getAllAttributes ( ctx );
		
		populateLDAPUser(ldapUserInfo, attrs ) ;
		
		System.out.println(" ############## END  LDAPUtil.getLDAPUser #####################");
		return ldapUserInfo;
	}
	
	public static Attributes getAllAttributes(DirContext ctx)
	{
		System.out.println(" ############## START  LDAPUtil.getAllAttributes ##################### screenName :"+screenName);
		// Specify the search filter
		Attributes attrs = null;
		try
		{
		String FILTER = "(&(objectClass=top) ((uid=" + screenName + ")))";

		// limit returned attributes to those we care about
		String[] attrIDs = { "sn", "cn", "mobile", "postalCode","personalTitle",
				"telephoneNumber", "street", "communicationUri", "o", "c" ,"uid"};

		SearchControls ctls = new SearchControls();
		ctls.setReturningAttributes(attrIDs);
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		System.out.println(" 1");
		// Search for objects using filter and controls
		NamingEnumeration answer = ctx.search(
				"ldap://ldap-dev.globalepic.lu:389/uid=" + screenName
						+ ",ou=users,ou=people,dc=emergency,dc=lu", FILTER,
				ctls);
		System.out.println("2");
		SearchResult sr = (SearchResult) answer.next();
		System.out.println(" 3");
		
		 attrs = sr.getAttributes();
		}
		catch(Exception e) { e.printStackTrace(); }
		
		System.out.println(" ############## END  LDAPUtil.getAllAttributes #####################");
		
		return attrs;
	}
	public static void populateLDAPUser(LDAPUserInfo ldapUserInfo, Attributes attrs ) 
	{	
		System.out.println(" ############## START  LDAPUtil.populateLDAPUser #####################");		
		try
		{
			//screenName = attrs.get("uid").toString();
			ldapUserInfo.surName = attrs.get("sn").toString();
			ldapUserInfo.givenName = attrs.get("cn").toString();
			
			Object mobileObj = attrs.get("mobile");
			Object postalCodeObj = attrs.get("postalCode");
			Object organizationObj = attrs.get("mobile");
			Object streetObj = attrs.get("street");
			Object countryObj = attrs.get("street");
			
			if( mobileObj!=null) ldapUserInfo.mobile = mobileObj.toString();
			if( postalCodeObj!=null) ldapUserInfo.postalCode = postalCodeObj.toString();
			if( organizationObj!=null) ldapUserInfo.organization = organizationObj.toString();
			if( streetObj!=null) ldapUserInfo.street = streetObj.toString();
			if( countryObj!=null) ldapUserInfo.country = countryObj.toString();
						
			System.out.println(" ############## ldapUserInfo  "+ldapUserInfo );
			System.out.println(" ############## ldapUserInfo.surName  "+ldapUserInfo.surName + " communicationUri :"+ attrs.get("communicationUri") );
			
			List<String> commuriList = null;
			
			if( attrs.get("communicationUri")!=null )
			{
				commuriList = getAttributeValueListByName( attrs, "communicationUri" );				
			}			
			for (int j = 0; commuriList!=null&& j < commuriList.size(); j++)
			{
				String temp = commuriList.get(j);	
				System.out.println(" ############## temp "+temp );
				if (temp.indexOf("gtalk") != -1) {
					ldapUserInfo.gtalk = temp.replace("gtalk:chat?jid=","");					
				} else if (temp.indexOf("msnim") != -1) {
					ldapUserInfo.msn = temp.replace("msnim:chat?contact=","");					
				} else if (temp.indexOf("skype") != -1) {
					ldapUserInfo.skype = temp.replace("skype:","");					
				} else if (temp.indexOf("sip") != -1) {
					ldapUserInfo.sip = temp.replace("sip:","");					
				} else if (temp.indexOf("VHF") != -1) {
					ldapUserInfo.vhf = temp.replace("VHFcallsign:","");					
				}
			}
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.populateLDAPUser #####################");
		
	}
	public static List<String> getAttributeValueListByName(Attributes attrs, String attributeName )
	{
		System.out.println(" ############## START  LDAPUtil.getAttributeValueListByName #####################"+attributeName);
		List<String> commuriList = null;	
		int i=0;
		try
		{
			if(attrs.get(attributeName)!=null )
			{
				commuriList = new ArrayList<String>();					
				NamingEnumeration nString = attrs.get(attributeName).getAll();			
				while (nString.hasMore()) 
				{
					commuriList.add(attrs.get(attributeName).get(i).toString());
					nString.next();		
					i=i+1;
				}
			}
		}
		catch(Exception e){ e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.getAttributeValueListByName #####################"+commuriList );
		return commuriList;
	}
	public static void updateContact(Contact  contact)
	{
		
		System.out.println(" ############## START  LDAPUtil.updateContact ##################### screenName: "+screenName );		
		try 
		{
			/* get a handle to an Initial DirContext */
			DirContext ctx = getLDAPContext();			
			ModificationItem[] mods = new ModificationItem[1];		 
			 Attributes attrs = getAllAttributes( ctx );
			 
			 List<String> commuriList = getAttributeValueListByName( attrs ,"communicationUri");
			 List<String> resultList = new ArrayList<String>();	
			
			 //getIcqSn -> gtalk, sip/lync ->aim 
			 Attribute mod0 = new BasicAttribute("communicationUri");
			 int msnflag=0;
			 int skypeflag=0;
			 int lyncflag=0;
			 int gtalkflag=0;
			 int vhfflag=0;
			 if( commuriList!=null)
			 {
				 for (int j = 0; j < commuriList.size(); j++)
				{
					String temp = commuriList.get(j);	
					 System.out.println(" ############## temp : "+temp );
					if (temp.indexOf("gtalk") != -1&& contact.getIcqSn()!=null && contact.getIcqSn()!="") {
						mod0.add("gtalk:chat?jid="+contact.getIcqSn().trim());gtalkflag=1;				
					} else if (temp.indexOf("msnim") != -1&& contact.getMsnSn()!=null && contact.getMsnSn()!="") {
						mod0.add("msnim:chat?contact="+contact.getMsnSn().trim());msnflag=1;
					} else if (temp.indexOf("skype") != -1&& contact.getSkypeSn()!=null && contact.getSkypeSn()!="") {
						mod0.add("skype:"+contact.getSkypeSn().trim());skypeflag=1;	 
					} else if (temp.indexOf("sip") != -1 && contact.getAimSn()!=null && contact.getAimSn()!="") {
						mod0.add("sip:"+contact.getAimSn().trim());lyncflag=1;
					} else if (temp.indexOf("VHF") != -1 && contact.getJabberSn()!=null && contact.getJabberSn()!="") {
						mod0.add("VHFcallsign:"+contact.getJabberSn().trim());vhfflag=1;
					}
				}
			 if( msnflag==0 && contact.getMsnSn()!=null&& contact.getMsnSn()!="" )
			 {
				 mod0.add("msnim:chat?contact="+contact.getMsnSn().trim() );msnflag=1;
			 }
			 if( skypeflag==0 && contact.getSkypeSn()!=null && contact.getSkypeSn()!="")
			 {
				 mod0.add("skype:"+contact.getSkypeSn().trim() );skypeflag=1;
			 }
			 if( gtalkflag==0 && contact.getIcqSn()!=null&& contact.getIcqSn()!="" )
			 {
				 mod0.add("gtalk:chat?jid="+contact.getIcqSn().trim() );gtalkflag=1;	
			 }
			 if( lyncflag==0 && contact.getAimSn()!=null&& contact.getAimSn()!="" )
			 {
				 mod0.add("sip:"+contact.getAimSn().trim() );lyncflag=1;
			 }
			 if( vhfflag==0 && contact.getJabberSn()!=null&& contact.getJabberSn()!="" )
			 {
				 mod0.add("VHFcallsign:"+contact.getJabberSn().trim() );vhfflag=1;
			 }
			 System.out.println(" ############## 3333333");
			 
			
			
			 if( lyncflag>0|| gtalkflag>0|| skypeflag>0|| msnflag>0|| vhfflag>0)
			 {
				 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+screenName +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			 }
			 }
			 
			 System.out.println(" ############## 44444444");
			 //mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);
			// mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);

			
			 
			
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.updateContact #####################");
		
	}
	
	public static List<String> getLDAPPhoneByScreenName( String userName )
	{
		System.out.println(" ############## START  LDAPUtil.getLDAPPhoneByScreenName ##################### userName: "+userName );
		List<String> phoneList = null;
		try 
		{
			DirContext ctx = getLDAPContext();			 
			Attributes attrs = getAllAttributes( ctx );			
			phoneList = getAttributeValueListByName( attrs ,"telephoneNumber");
		}
		catch(Exception e) { e.printStackTrace(); }			 
	   System.out.println(" ############## END  LDAPUtil.getLDAPPhoneByScreenName ##################### userName: "+userName );	
	   
	   return phoneList;
	}
	public static void importPhones(User user )
	{
		System.out.println(" ############## START  LDAPUtil.importLDAPPhones user "+user);
		try
 		{
 			LDAPUtil.screenName=user.getScreenName();
 			
 			//List<Address> addressList = user.getAddresses();
 			//Contact contact = user.getContact(); 
 			//List<Phone> phoneList = user.getPhones();
 			//user.setJobTitle("Web Developer");				
			List<ListType> phoneTypes = ListTypeServiceUtil.getListTypes(Contact.class.getName()
			       + ListTypeConstants.PHONE);
			int phoneTypeId = 0;
			
			// find personal and fax type phones
			for (ListType phoneType : phoneTypes) 
			{
			     String phoneTypeName = phoneType.getName();
			     System.out.println(" phoneTypeName : "+phoneTypeName );
			     if ("personal".equals(phoneTypeName)) { phoneTypeId = phoneType.getListTypeId();   }
			    // if ("personal-fax".equals(phoneTypeName)) { phoneTypeId = phoneType.getListTypeId();     }
			 }	
			List<String> phoneList = getLDAPPhoneByScreenName(user.getScreenName() );
			System.out.println(" phoneList from LDAP : "+ phoneList );
			
			if( phoneList!=null && phoneList.size()>0)
			{
			for( String phn : phoneList )
			{				
				if( phn.indexOf("Office")!=-1 || phn.indexOf("office")!=-1)
				{
					String phnArray [] = phn.split("Office:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("office:");	
					String number = phnArray[1];
					String ext="";
					if( number.indexOf("-")!=-1)
					{
						phnArray = number.split("-");
						ext=phnArray[1];
						number=phnArray[0];
					}
					System.out.println(" phn : "+ phn + ": number : "+number+ " ext :"+ext +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, ext, listType_business, true);		            
				}
				else if( phn.indexOf("WAVE")!=-1|| phn.indexOf("Wave")!=-1)
				{
					String phnArray [] = phn.split("WAVE:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("Wave:");			
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number+ " phoneTypeId :"+ phoneTypeId + "phnArray:"+phnArray ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_wave, true);		            
				}
				else if( phn.indexOf("Foodsat")!=-1|| phn.indexOf("foodsat")!=-1)
				{
					String phnArray [] = phn.split("Foodsat:");
					if( phnArray==null || phnArray.length==0 ) phnArray = phn.split("foodsat:");			
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number+ " phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_foodsat, true);		            
				}
				else if( phn.indexOf("Thuraya")!=-1 || phn.indexOf("thuraya")!=-1)
				{
					String phnArray [] = phn.split("Thuraya:");
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_thuraya, true);		            
				}
				else if( phn.indexOf("Mobile")!=-1 || phn.indexOf("mobile")!=-1 )
				{
					String phnArray [] = phn.split("Mobile:");
					String number = phnArray[1];				
					System.out.println(" phn : "+ phn + ": number : "+number +" phoneTypeId :"+ phoneTypeId ); 
					PhoneLocalServiceUtil.addPhone(user.getUserId(), Contact.class.getName(),  user.getContactId(), number, "", listType_mobile, true);		            
				}
					
				
			}
			
	        System.out.println("Handling bug LPS-17381: user.getContactId() " + user.getContactId());
	        isPhoneAdded = true;    
			}
           
 			System.out.println(" #####  END LDAPUtil.importLDAPPHones  ");
 			//super.exportToLDAP(user);
 		}
 		catch (Exception e)
 		{
 			e.printStackTrace();	
 		}

		
	}
	public static void importAddresses(User user )
	{
		System.out.println(" ############## START  LDAPUtil.importAddresses user "+user);
		try
 		{
			LDAPUserInfo  ldapuser = getLDAPUserInfo();
			System.out.println(ldapuser.street + " ldapuser.street "+ addressType_business );
			AddressLocalServiceUtil.addAddress(user.getUserId(), Address.class.getName(), PortalUtil.getClassNameId(Address.class.getName()), ldapuser.street, "", "", "DUBAI", ldapuser.postalCode, 1, 217, addressType_business, true, true); 
 		}
		catch (Exception e)
 		{
 			e.printStackTrace();	
 		}
		System.out.println(" ############## END  LDAPUtil.importAddresses user "+user);
	}
	public static void exportPhones(List<Phone>  phoneList )
	{		
		System.out.println(" ############## START  LDAPUtil.exportPhones ##################### phoneList: "+phoneList );		
		try 
		{
			/* get a handle to an Initial DirContext */
			DirContext ctx = getLDAPContext();			
			ModificationItem[] mods = new ModificationItem[ 1];		 
			 Attribute mod0 = new BasicAttribute("telephoneNumber");
			//Attributes attrs = getAllAttributes( ctx );
			int i=0;
			for( Phone phone : phoneList)
			 {
				 System.out.println(" ############## phone type Id :"+phone.getTypeId() );
				 switch(phone.getTypeId())
				 {
				 	case listType_business :	mod0.add("Office:"+phone.getNumber().trim()); break;
					case listType_mobile   :	mod0.add("Mobile:"+phone.getNumber().trim()); break;
					case listType_wave :		mod0.add("WAVE:"+phone.getNumber().trim());	  break;
					case listType_thuraya :		mod0.add("Thuraya:"+phone.getNumber().trim()); break;												
					case listType_foodsat :		mod0.add("Foodsat:"+phone.getNumber().trim());break;
				 }
				
				 i++;
			 }			 
			 System.out.println(" ############## mod0 "+mod0 );			
			 if(i>0)
			 {
				 mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
				 ctx.modifyAttributes("ldap://ldap-dev.globalepic.lu:389/uid="+screenName +",ou=users,ou=people,dc=emergency,dc=lu", mods);
			 } 
			 System.out.println(" ############## 44444444");
		}
		catch(Exception e) { e.printStackTrace(); }
		System.out.println(" ############## END  LDAPUtil.updateContact #####################");
		
	}
}
