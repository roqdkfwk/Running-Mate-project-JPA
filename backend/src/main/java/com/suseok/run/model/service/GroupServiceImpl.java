package com.suseok.run.model.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.suseok.run.model.dao.GroupDao;
import com.suseok.run.model.dao.RankDao;
import com.suseok.run.model.dto.Group;
import com.suseok.run.model.dto.User;
import com.suseok.run.model.dto.UserRankRecord;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	// TODO GroupReposity
	private final GroupDao groupDao;
	private final RankDao rankDao;
	private final UserService userService;

	@Override
	public List<Group> search(String con) {
		return groupDao.search(con);
	}

	@Override
	public boolean insert(Group group, String userId) {
		User user = userService.selectById(userId);
		group.setGroupAdmin(user.getUserSeq());
	
		return groupDao.insert(group);
	}

	@Override
	public boolean join(int groupId, String userId) {
		// 자격이 되는 사람만가능
		Group group = groupDao.selectById(groupId);
		UserRankRecord urr = rankDao.selectByUserId(userId);

		if (urr.getFrequency() != 0 && urr.getFrequency() > group.getConFrequency())
			return false;
		if (urr.getHighestPace() != 0 && urr.getHighestPace() > group.getConPace())
			return false;
		if (urr.getTotalDistance() != 0 && urr.getTotalDistance() < group.getConTotalDistance())
			return false;

		return groupDao.join(groupId, userId);
	}

	@Override
	public boolean exit(int groupId, String userId) {
		return groupDao.exit(groupId, userId);
	}

	@Override
	public boolean update(Group group, String userId) {
		User user = userService.selectById(userId);
		if (group.getGroupAdmin() == user.getUserSeq())
			return groupDao.update(group);
		return false;
	}

	@Override
	public boolean kickOut(int groupId, String userId, int memberId) {
		User user = userService.selectById(userId);
		Group group = groupDao.selectById(groupId);
		if (group.getGroupAdmin() == user.getUserSeq())
			return groupDao.deleteMember(groupId, memberId);
		return false;
	}

	@Override
	public Group selectById(int groupId) {
		return groupDao.selectById(groupId);
	}

	@Override
	public List<Group> selectAll() {
		return groupDao.selectAll();
	}
}
