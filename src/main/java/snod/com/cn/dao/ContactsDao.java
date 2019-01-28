package snod.com.cn.dao;

import java.util.List;

import snod.com.cn.entity.ContactsInfo;

public interface ContactsDao {

	void addContacts(ContactsInfo contactsInfo);

	List<ContactsInfo> queryContacts(int userId);

}
