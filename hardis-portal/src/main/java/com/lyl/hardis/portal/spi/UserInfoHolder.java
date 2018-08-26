package com.lyl.hardis.portal.spi;

import com.lyl.hardis.portal.entity.bo.UserInfo;

/**
 * Get access to the user's information,
 * different companies should have a different implementation
 */
public interface UserInfoHolder {

  UserInfo getUser();

}
