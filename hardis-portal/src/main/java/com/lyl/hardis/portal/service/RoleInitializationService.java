package com.lyl.hardis.portal.service;


import com.lyl.hardis.common.entity.App;

public interface RoleInitializationService {

  void initAppRoles(App app);

  void initNamespaceRoles(String appId, String namespaceName, String operator);

  void initNamespaceEnvRoles(String appId, String namespaceName, String operator);

  void initNamespaceSpecificEnvRoles(String appId, String namespaceName, String env, String operator);

}
