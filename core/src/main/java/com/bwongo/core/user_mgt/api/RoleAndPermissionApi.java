package com.bwongo.core.user_mgt.api;

import com.bwongo.core.user_mgt.models.dto.request.UserGroupRequestDto;
import com.bwongo.core.user_mgt.models.dto.response.GroupAuthorityResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.PermissionResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.RoleResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserGroupResponseDto;
import com.bwongo.core.user_mgt.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Date 3/13/24
 * @LocalTime 2:57 PM
 **/
@Tag(name = "Role, Permission and user group Api", description = "Manages user roles, permissions and user groups with the corresponding group authorities")
@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RoleAndPermissionApi {

    private final RoleService roleService;

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "roles", produces = APPLICATION_JSON)
    public List<RoleResponseDto> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/activate/{id}", produces = APPLICATION_JSON)
    public PermissionResponseDto activatePermission(@PathVariable("id") Long id){
        return roleService.activatePermission(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/de-activate/{id}", produces = APPLICATION_JSON)
    public PermissionResponseDto deactivatePermission(@PathVariable("id") Long id){
        return roleService.deactivatePermission(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/assign-to-group", produces = APPLICATION_JSON)
    public GroupAuthorityResponseDto assignPermissionToUserGroup(@RequestParam("permissionName") String permissionName,
                                                                 @RequestParam("userGroupId") Long userGroupId){
        return roleService.assignPermissionToUserGroup(permissionName, userGroupId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.UPDATE')")
    @PutMapping(path = "permission/un-assign-to-group", produces = APPLICATION_JSON)
    public boolean unAssignPermissionFromUserGroup(@RequestParam("permissionName") String permissionName,
                                                   @RequestParam("userGroupId") Long userGroupId){
        return roleService.unAssignPermissionFromUserGroup(permissionName, userGroupId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "groups", produces = APPLICATION_JSON)
    public List<UserGroupResponseDto> getAllUserGroups(){
        return roleService.getAllUserGroups();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.WRITE')")
    @PostMapping(path = "group")
    public UserGroupResponseDto addUserGroup(@RequestBody UserGroupRequestDto userGroupDto){
        return roleService.addUserGroup(userGroupDto);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "group/{userGroupId}/authorities", produces = APPLICATION_JSON)
    public List<GroupAuthorityResponseDto> getUserGroupAuthorities(@PathVariable("userGroupId") Long userGroupId){
        return roleService.getUserGroupAuthorities(userGroupId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ROLE.READ')")
    @GetMapping(path = "permissions/{roleName}", produces = APPLICATION_JSON)
    public List<PermissionResponseDto> getPermissionByRoleName(@PathVariable("roleName") String roleName){
        return roleService.getAllPermissionsByRoleName(roleName);
    }
}
