//package com.suseok.run.model.service;
//
//import java.util.List;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import com.suseok.run.model.dao.RankDao;
//import com.suseok.run.model.dao.UserDao;
//import com.suseok.run.model.entity.Post;
//import com.suseok.run.model.entity.UserRankRecord;
//
//@Service
//@RequiredArgsConstructor
//public class RankServiceImpl implements RankService {
//
//	// TODO RankReposity
//	private final RankDao rankDao;
//	private final UserDao userDao;
//
//	@Override
//	public List<UserRankRecord> selectAllOrderBy(String con) {
//		List<UserRankRecord> users = rankDao.selectAllOrderBy(con);
//		for (int i = 0; i < users.size(); i++) {
//			UserRankRecord user = users.get(i);
//			user.setUserName(userDao.selectBySeq(user.getUserSeq()).getUserName());
//			user.setUserNick(userDao.selectBySeq(user.getUserSeq()).getUserNick());
//			user.setUserId(userDao.selectBySeq(user.getUserSeq()).getUserId());
//
//		}
//		return users;
//	}
//
//	@Override
//	public List<UserRankRecord> selectAllOrderBy(String con, String userId) {
//		List<UserRankRecord> users = rankDao.selectAllOrderBy(con);
//		List<UserRankRecord> rivals = rankDao.selectAllRivalOrderBy(con, userId);
//		for (int i = 0; i < users.size(); i++) {
//			UserRankRecord user = users.get(i);
//			user.setUserName(userDao.selectBySeq(user.getUserSeq()).getUserName());
//			user.setUserNick(userDao.selectBySeq(user.getUserSeq()).getUserNick());
//			user.setUserId(userDao.selectBySeq(user.getUserSeq()).getUserId());
//			for (int j = 0; j < rivals.size(); j++) {
//				if (rivals.get(j).getUserSeq() == user.getUserSeq()) {
//					user.setMyRival(true);
//				}
//			}
//
//		}
//		return users;
//	}
//
//	@Override
//	public List<UserRankRecord> selectAllMemberOrderBy(String con, int groupId) {
//		List<UserRankRecord> users = rankDao.selectAllMemberOrderBy(con, groupId);
//		for (int i = 0; i < users.size(); i++) {
//			UserRankRecord user = users.get(i);
//			user.setUserName(userDao.selectBySeq(user.getUserSeq()).getUserName());
//			user.setUserNick(userDao.selectBySeq(user.getUserSeq()).getUserNick());
//			user.setUserId(userDao.selectBySeq(user.getUserSeq()).getUserId());
//		}
//		return users;
//	}
//
//	@Override
//	public List<Post> selectGroupsOrderBy(String con) {
//		return rankDao.selectGroupsOrderBy(con);
//	}
//
//	@Override
//	public List<Post> selectMyGroupsOrderBy(String con, String userId) {
//		return rankDao.selectMyGroupsOrderBy(con, userId);
//	}
//
//	@Override
//	public UserRankRecord selectRival(String userId, String rivalId) {
//		return rankDao.selectRival(userId, rivalId);
//	}
//
//	@Override
//	public UserRankRecord selectByUser(String userId) {
//		return rankDao.selectByUserId(userId);
//	}
//
//	@Override
//	public boolean updateRankRecord(UserRankRecord record, int userSeq) {
//
//		return rankDao.updateRankRecord(record);
//	}
//}
