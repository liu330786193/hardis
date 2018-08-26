package com.lyl.hardis.portal.spi;

import com.lyl.hardis.portal.entity.bo.UserInfo;

import java.util.List;

public interface UserService {
    List<UserInfo> searchUsers(String keyword, int offset, int limit);

    UserInfo findByUserId(String userId);

    List<UserInfo> findByUserIds(List<String> userIds);
}
