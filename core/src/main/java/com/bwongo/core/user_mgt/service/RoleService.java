package com.bwongo.core.user_mgt.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.user_mgt.models.dto.request.UserGroupRequestDto;
import com.bwongo.core.user_mgt.models.dto.response.GroupAuthorityResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.PermissionResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.RoleResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.core.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.core.user_mgt.repository.TGroupAuthorityRepository;
import com.bwongo.core.user_mgt.repository.TPermissionRepository;
import com.bwongo.core.user_mgt.repository.TRoleRepository;
import com.bwongo.core.user_mgt.repository.TUserGroupRepository;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bwongo.core.user_mgt.utils.UserMgtUtils.checkThatPermissionRoleIsAssignable;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/13/24
 * @LocalTime 1:45 PM
 **/
@Service
@RequiredArgsConstructor
public class RoleService {

    private final TGroupAuthorityRepository groupAuthorityRepository;
    private final TRoleRepository roleRepository;
    private final AuditService auditService;
    private final TUserGroupRepository userGroupRepository;
    private final TPermissionRepository permissionRepository;
    private final UserDtoService userDtoService;
    private static final String USER_GROUP_SUFFIX = "_GROUP";


    public UserGroupResponseDto addUserGroup(UserGroupRequestDto userGroupDto) {

        userGroupDto.validate();
        var existingUserGroup = userGroupRepository.findTUserGroupByName(userGroupDto.name()+USER_GROUP_SUFFIX);
        Validate.isTrue(existingUserGroup.isEmpty(), ExceptionType.BAD_REQUEST, USER_GROUP_ALREADY_EXISTS, userGroupDto.name()+USER_GROUP_SUFFIX);

        var userGroup = userDtoService.dtoToTUserGroup(userGroupDto);
        userGroup.setName(userGroupDto.name()+USER_GROUP_SUFFIX);

        auditService.stampLongEntity(userGroup);

        return userDtoService.userGroupToDto(userGroupRepository.save(userGroup));
    }

    public List<UserGroupResponseDto> getAllUserGroups() {
        return userGroupRepository.findAll().stream()
                .map(userDtoService::userGroupToDto)
                .collect(Collectors.toList());
    }

    public List<GroupAuthorityResponseDto> getUserGroupAuthorities(Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        return groupAuthorityRepository.findByUserGroup(existingUserGroup.get()).stream()
                .map(userDtoService::groupAuthorityToDto)
                .collect(Collectors.toList());
    }

    public GroupAuthorityResponseDto assignPermissionToUserGroup(String permissionName, Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        var existingPermission = permissionRepository.findByName(permissionName);
        Validate.isPresent(existingPermission, PERMISSION_NAME_DOES_NOT_EXIST, permissionName);
        checkThatPermissionRoleIsAssignable(existingPermission.get());

        var existingGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(existingUserGroup.get(), existingPermission.get());
        Validate.isTrue(existingGroupAuthority.isEmpty(), ExceptionType.BAD_REQUEST, PERMISSION_ALREADY_ASSIGNED_TO_USER_GROUP, permissionName, existingUserGroup.get().getName());

        var groupAuthority = new TGroupAuthority();
        groupAuthority.setUserGroup(existingUserGroup.get());
        groupAuthority.setPermission(existingPermission.get());

        auditService.stampLongEntity(groupAuthority);

        return userDtoService.groupAuthorityToDto(groupAuthorityRepository.save(groupAuthority));
    }

    public boolean unAssignPermissionFromUserGroup(String permissionName, Long userGroupId) {

        var existingUserGroup = userGroupRepository.findById(userGroupId);
        Validate.isPresent(existingUserGroup, USER_GROUP_DOES_NOT_EXIST, userGroupId);

        var existingPermission = permissionRepository.findByName(permissionName);
        Validate.isPresent(existingPermission, PERMISSION_NAME_DOES_NOT_EXIST, permissionName);

        var existingGroupAuthority = groupAuthorityRepository.findByUserGroupAndPermission(existingUserGroup.get(), existingPermission.get());
        Validate.isTrue(existingGroupAuthority.isPresent(), ExceptionType.BAD_REQUEST, PERMISSION_NOT_ASSIGNED_TO_USER_GROUP, permissionName, existingUserGroup.get().getName());

        groupAuthorityRepository.delete(existingGroupAuthority.get());
        return Boolean.TRUE;
    }

    public List<PermissionResponseDto> getAllPermissionsByRoleName(String roleName) {

        var existingRole = roleRepository.findByName(roleName);
        Validate.isPresent(existingRole, ROLE_NAME_DOES_NOT_EXIST, roleName);

        return permissionRepository.findAllByRole(existingRole.get()).stream()
                .map(userDtoService::permissionToDto)
                .collect(Collectors.toList());
    }

    public List<RoleResponseDto> getAllRoles(){
        return roleRepository.findAll().stream()
                .map(userDtoService::roleToDto)
                .collect(Collectors.toList());
    }

    public PermissionResponseDto deactivatePermission(Long id) {
        var existing = permissionRepository.findById(id);
        Validate.isPresent(existing, PERMISSION_DOES_NOT_EXIST, id);
        Validate.isTrue(existing.get().getIsAssignable(), ExceptionType.BAD_REQUEST, PERMISSION_IS_ALREADY_IN_ACTIVE, id);

        var permission = existing.get();
        permission.setIsAssignable(Boolean.FALSE);
        var result = permissionRepository.save(permission);

        return userDtoService.permissionToDto(result);
    }

    public PermissionResponseDto activatePermission(Long id) {

        var existing = permissionRepository.findById(id);
        Validate.isPresent(existing, PERMISSION_DOES_NOT_EXIST, id);
        Validate.isTrue(!existing.get().getIsAssignable(), ExceptionType.BAD_REQUEST, PERMISSION_IS_ALREADY_ACTIVE, id);

        var permission = existing.get();
        permission.setIsAssignable(Boolean.TRUE);
        var result = permissionRepository.save(permission);

        return userDtoService.permissionToDto(result);
    }

}
