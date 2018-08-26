package com.lyl.hardis.portal.service;

import com.google.common.collect.Lists;
import com.lyl.hardis.common.entity.App;
import com.lyl.hardis.common.exception.BadRequestException;
import com.lyl.hardis.portal.entity.bo.UserInfo;
import com.lyl.hardis.portal.repository.AppRepository;
import com.lyl.hardis.portal.spi.UserInfoHolder;
import com.lyl.hardis.portal.spi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class AppService {

    @Autowired
    private AppRepository appRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoHolder userInfoHolder;
    @Autowired
    private AppNamespaceService appNamespaceService;
    @Autowired
    private RoleInitializationService roleInitializationService;

    public List<App> findAll() {
        Iterable<App> apps = appRepository.findAll();
        if (apps == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList((apps));
    }

    public List<App> findByAppIds(Set<String> appIds) {
        return appRepository.findByAppIdIn(appIds);
    }

    public App createAppInLocal(App app) {
        String appId = app.getAppId();
        App managedApp = appRepository.findByAppId(appId);

        if (managedApp != null) {
            throw new BadRequestException(String.format("App already exists. AppId = %s", appId));
        }

        UserInfo owner = userService.findByUserId(app.getOwnerName());
        if (owner == null) {
            throw new BadRequestException("Application's owner not exist.");
        }
        app.setOwnerEmail(owner.getEmail());

        String operator = userInfoHolder.getUser().getUserId();
        app.setDataChangeCreatedBy(operator);
        app.setDataChangeLastModifiedBy(operator);

        App createdApp = appRepository.save(app);

        appNamespaceService.createDefaultAppNamespace(appId);
        roleInitializationService.initAppRoles(createdApp);

        Tracer.logEvent(TracerEventType.CREATE_APP, appId);

        return createdApp;
    }
}
