package com.lyl.hardis.portal.controller;

import com.google.common.collect.Sets;
import com.lyl.hardis.common.entity.App;
import com.lyl.hardis.common.exception.BadRequestException;
import com.lyl.hardis.common.utils.InputValidator;
import com.lyl.hardis.common.utils.RequestPrecondition;
import com.lyl.hardis.portal.entity.model.AppModel;
import com.lyl.hardis.portal.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class Controller {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<App> findApps(@RequestParam(value = "appIds", required = false) String appIds) {
        if (StringUtils.isEmpty(appIds)) {
            return appService.findAll();
        } else {
            return appService.findByAppIds(Sets.newHashSet(appIds.split(",")));
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public App create(@RequestBody AppModel appModel) {
        App app = transformToApp(appModel);
        App createdApp = appService.
    }

    private App transformToApp(AppModel appModel){
        String appId = appModel.getAppId();
        String appName = appModel.getName();
        String ownerName = appModel.getOwnerName();
        String orgId = appModel.getOrgId();
        String orgName = appModel.getOrgName();
        RequestPrecondition.checkArgumentsNotEmpty(appId, appName, ownerName, orgId, orgName);
        if (!InputValidator.isValidClusterNamespace(appModel.getAppId())) {
            throw new BadRequestException(
                    String.format("AppId格式错误: %s", InputValidator.INVALID_CLUSTER_NAMESPACE_MESSAGE));
        }

        return App.builder()
                .appId(appId)
                .name(appName)
                .ownerName(ownerName)
                .orgId(orgId)
                .orgName(orgName)
                .build();
    }

}
