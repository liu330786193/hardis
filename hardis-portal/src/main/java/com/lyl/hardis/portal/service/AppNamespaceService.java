package com.lyl.hardis.portal.service;

import com.lyl.hardis.common.entity.AppNamespace;
import com.lyl.hardis.common.exception.BadRequestException;
import com.lyl.hardis.core.ConfigConsts;
import com.lyl.hardis.core.enums.ConfigFileFormat;
import com.lyl.hardis.portal.repository.AppNamespaceRepository;
import com.lyl.hardis.portal.spi.UserInfoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AppNamespaceService {

    @Autowired
    private UserInfoHolder userInfoHolder;
    @Autowired
    private AppNamespaceRepository appNamespaceRepository;

    @Transactional
    public void createDefaultAppNamespace(String appId) {
        if (!isAppNamespaceNameUnique(appId, ConfigConsts.NAMESPACE_APPLICATION)) {
            throw new BadRequestException(String.format("App already has application namespace. AppId = %s", appId));
        }

        AppNamespace appNs = new AppNamespace();
        appNs.setAppId(appId);
        appNs.setName(ConfigConsts.NAMESPACE_APPLICATION);
        appNs.setComment("default app namespace");
        appNs.setFormat(ConfigFileFormat.Properties.getValue());
        String userId = userInfoHolder.getUser().getUserId();
        appNs.setDataChangeCreatedBy(userId);
        appNs.setDataChangeLastModifiedBy(userId);

        appNamespaceRepository.save(appNs);
    }

    public boolean isAppNamespaceNameUnique(String appId, String namespaceName) {
        Objects.requireNonNull(appId, "AppId must not be null");
        Objects.requireNonNull(namespaceName, "Namespace must not be null");
        return Objects.isNull(appNamespaceRepository.findByAppIdAndName(appId, namespaceName));
    }

}
