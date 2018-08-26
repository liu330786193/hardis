package com.lyl.hardis.portal.repository;

import com.lyl.hardis.common.entity.AppNamespace;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AppNamespaceRepository extends PagingAndSortingRepository<AppNamespace, Long> {

  AppNamespace findByAppIdAndName(String appId, String namespaceName);

  AppNamespace findByName(String namespaceName);

  AppNamespace findByNameAndIsPublic(String namespaceName, boolean isPublic);

  List<AppNamespace> findByIsPublicTrue();

  @Modifying
  @Query("UPDATE AppNamespace SET IsDeleted=1,DataChange_LastModifiedBy=?2 WHERE AppId=?1")
  int batchDeleteByAppId(String appId, String operator);

  @Modifying
  @Query("UPDATE AppNamespace SET IsDeleted=1,DataChange_LastModifiedBy = ?3 WHERE AppId=?1 and Name = ?2")
  int delete(String appId, String namespaceName, String operator);
}
