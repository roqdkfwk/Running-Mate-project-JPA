package com.suseok.run.model.dao;

import java.util.List;

import com.suseok.run.model.dto.Group;

public interface GroupDao {
	// CRUD : insert, selectAll, update, delete

	boolean insert(Group group);

	List<Group> selectAll();

	Group selectById(int groupId);

	boolean update(Group group);

	int delete(int groupId);
	
	List<Group> search(String groupName);

	boolean join(int groupId, String userId);

	boolean exit(int groupId, String userId);

	boolean deleteMember(int groupId, int memberId);
}
