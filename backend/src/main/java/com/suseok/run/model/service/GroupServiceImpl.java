package com.suseok.run.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suseok.run.model.dao.GroupDao;
import com.suseok.run.model.dao.RankDao;
import com.suseok.run.model.dao.UserDao;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.dto.UserRankRecord;

@Service
public class GroupServiceImpl implements GroupService {

	// TODO GroupReposity
	@Autowired
	GroupDao gd;

	@Autowired
	RankDao rd;

	@Autowired
	UserService us;

	@Override
	public List<Group> search(String con) {
		return gd.search(con);
	}

	@Override
	public boolean insert(Group group, String userId) {
		User user = us.selectById(userId);
		group.setGroupAdmin(user.getUserSeq());
	
		return gd.insert(group);
	}

	@Override
	public boolean join(int groupId, String userId) {
		// 자격이 되는 사람만가능
		Group group = gd.selectById(groupId);
		UserRankRecord urr = rd.selectByUserId(userId);

		if (urr.getFrequency() != 0 && urr.getFrequency() > group.getConFrequency())
			return false;
		if (urr.getHighestPace() != 0 && urr.getHighestPace() > group.getConPace())
			return false;
		if (urr.getTotalDistance() != 0 && urr.getTotalDistance() < group.getConTotalDistance())
			return false;

		return gd.join(groupId, userId);
	}

	@Override
	public boolean exit(int groupId, String userId) {
		return gd.exit(groupId, userId);
	}

	@Override
	public boolean update(Group group, String userId) {
		User user = us.selectById(userId);
		if (group.getGroupAdmin() == user.getUserSeq())
			return gd.update(group);
		return false;
	}

	@Override
	public boolean kickOut(int groupId, String userId, int memberId) {
		User user = us.selectById(userId);
		Group group = gd.selectById(groupId);
		if (group.getGroupAdmin() == user.getUserSeq())
			return gd.deleteMember(groupId, memberId);
		return false;
	}

	@Override
	public Group selectById(int groupId) {
		return gd.selectById(groupId);
	}

	@Override
	public List<Group> selectAll() {
		return gd.selectAll();
	}

}
