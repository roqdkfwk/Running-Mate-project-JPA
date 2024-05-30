package com.suseok.run.model.service;

import java.util.List;

import com.suseok.run.model.dto.Group;

public interface GroupService {

	//그룹 찾기
	List<Group> search(String con);
	
	Group selectById(int groupId);
	
	List<Group> selectAll();

	//그룹 가입
	boolean join(int groupId, String userId);

	//그룹 탈퇴
	boolean exit(int groupId, String userId);

	boolean insert(Group group, String userId);

	boolean update(Group group, String userId);

	boolean kickOut(int groupId, String userId, int memberId);


}
