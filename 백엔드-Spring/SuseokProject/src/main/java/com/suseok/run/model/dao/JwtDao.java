package com.suseok.run.model.dao;

import com.suseok.run.model.dto.JwtToken;

public interface JwtDao {
	
	boolean insert(JwtToken jwtToken);

	boolean deleteRefreshToken(String userId);
}
