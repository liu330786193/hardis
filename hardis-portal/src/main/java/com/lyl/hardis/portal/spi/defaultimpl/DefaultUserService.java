package com.lyl.hardis.portal.spi.defaultimpl;

import com.google.common.collect.Lists;
import com.lyl.hardis.portal.entity.bo.UserInfo;
import com.lyl.hardis.portal.spi.UserService;

import java.util.Arrays;
import java.util.List;

public class DefaultUserService implements UserService {
    @Override
    public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
        return Arrays.asList(assembleDefaultUser());
    }

    @Override
    public UserInfo findByUserId(String userId) {
        if (userId.equals("hardis")){
            return assembleDefaultUser();
        }
        return null;
    }

    @Override
    public List<UserInfo> findByUserIds(List<String> userIds) {
        if (userIds.contains("hardis")){
            return Lists.newArrayList(assembleDefaultUser());
        }
        return null;
    }

    private UserInfo assembleDefaultUser() {
        UserInfo defaultUser = new UserInfo();
        defaultUser.setUserId("apollo");
        defaultUser.setName("apollo");
        defaultUser.setEmail("apollo@acme.com");

        return defaultUser;
    }
}
