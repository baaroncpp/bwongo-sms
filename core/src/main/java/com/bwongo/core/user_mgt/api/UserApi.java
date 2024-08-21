package com.bwongo.core.user_mgt.api;

import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.user_mgt.models.dto.request.*;
import com.bwongo.core.user_mgt.models.dto.response.UserApprovalResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.core.user_mgt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Date 3/16/24
 * @LocalTime 3:53 AM
 **/
@Tag(name = "User Api", description = "Manages user CRUD operations")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PostMapping(path = "/change-password", produces = APPLICATION_JSON)
    public Boolean changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto){
        return userService.changePassword(changePasswordRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON)
    public UserResponseDto get(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public UserResponseDto add(@RequestBody UserRequestDto userDto) {
        return userService.addUser(userDto);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PutMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public UserResponseDto update(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateUser(userUpdateRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.DELETE','ADMIN_ROLE.DELETE')")
    @DeleteMapping(path="/delete", produces = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean delete(Long id) {
        return userService.deleteUserAccount(id);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path="pageable", produces = APPLICATION_JSON)
    public PageResponseDto getAllUsers(@RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size) {
        var pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return userService.getAll(pageable);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    @GetMapping(path="/count/{user_type}", produces = APPLICATION_JSON)
    public Long getNumberOfUsersByType(@PathVariable("user_type") String userType) {
        return userService.getNumberOfUsersByType(userType);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "re_assign/group", produces = APPLICATION_JSON)
    public UserResponseDto reAssignUserToGroup(@RequestParam(name = "groupId") Long groupId,
                                               @RequestParam(name = "userId") Long userId){
        return userService.reAssignUserToGroup(groupId, userId);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.WRITE','ADMIN_ROLE.WRITE')")
    @PostMapping(path = "approve", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserApprovalResponseDto approveUser(@RequestBody UserApprovalRequestDto userApprovalDto){
        return userService.approveUser(userApprovalDto);
    }

    @PreAuthorize("hasAnyAuthority('USER_ROLE.UPDATE','ADMIN_ROLE.UPDATE')")
    @PatchMapping(path = "suspend/{id}", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    public boolean suspendUserAccount(@PathVariable("id") Long id){
        return userService.suspendUserAccount(id);
    }

    @GetMapping(path = "approvals", produces = APPLICATION_JSON)
    @PreAuthorize("hasAnyAuthority('USER_ROLE.READ','ADMIN_ROLE.READ')")
    public PageResponseDto getUserApprovals(@RequestParam(name = "page") int page,
                                                          @RequestParam(name = "size") int size,
                                                          @RequestParam(name = "status") String status){
        var pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());
        return userService.getUserApprovals(status, pageable);
    }
}
