package com.bwongo.core.user_mgt.service.dto;

import com.bwongo.core.base.model.dto.response.AddressResponseDto;
import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.base.model.jpa.TAddress;
import com.bwongo.core.base.service.dto.BaseDtoService;
import com.bwongo.core.user_mgt.models.dto.request.ChangePasswordRequestDto;
import com.bwongo.core.user_mgt.models.dto.request.MerchantUserRequestDto;
import com.bwongo.core.user_mgt.models.dto.request.UserGroupRequestDto;
import com.bwongo.core.user_mgt.models.dto.request.UserRequestDto;
import com.bwongo.core.user_mgt.models.dto.response.*;
import com.bwongo.core.user_mgt.models.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 6:00 PM
 **/
@Service
@RequiredArgsConstructor
public class UserDtoService {

    private final BaseDtoService baseDtoService;
    private final PasswordEncoder passwordEncoder;

    public UserGroupResponseDto userGroupToDto(TUserGroup userGroup){

        if(userGroup == null)
            return null;


        return new UserGroupResponseDto(
                userGroup.getId(),
                userGroup.getCreatedOn(),
                userGroup.getModifiedOn(),
                userGroup.getName(),
                userGroup.getNote()
        );
    }

    public TUserGroup dtoToTUserGroup(UserGroupRequestDto userGroupRequestDto){

        if(userGroupRequestDto == null)
            return null;


        var userGroup = new TUserGroup();
        userGroup.setName(userGroupRequestDto.name());
        userGroup.setNote(userGroupRequestDto.note());

        return userGroup;
    }

    public RoleResponseDto roleToDto(TRole role){

        if(role == null){
            return null;
        }

        return new RoleResponseDto(
                role.getId(),
                role.getCreatedOn(),
                role.getModifiedOn(),
                role.getName(),
                role.getNote()
        );
    }

    public PermissionResponseDto permissionToDto(TPermission permission){

        if(permission == null){
            return null;
        }

        return new PermissionResponseDto(
                permission.getId(),
                permission.getCreatedOn(),
                permission.getModifiedOn(),
                roleToDto(permission.getRole()),
                permission.getName(),
                permission.getIsAssignable()
        );
    }

    public GroupAuthorityResponseDto groupAuthorityToDto(TGroupAuthority groupAuthority){

        if(groupAuthority == null){
            return null;
        }

        return new GroupAuthorityResponseDto(
                groupAuthority.getId(),
                groupAuthority.getCreatedOn(),
                groupAuthority.getModifiedOn(),
                userGroupToDto(groupAuthority.getUserGroup()),
                permissionToDto(groupAuthority.getPermission())
        );
    }
    public TPreviousPassword dtoToTPreviousPassword(ChangePasswordRequestDto changePasswordRequestDto){

        if(changePasswordRequestDto == null){
            return null;
        }

        var previousPassword = new TPreviousPassword();
        previousPassword.setPreviousPassword(changePasswordRequestDto.oldPassword());

        return previousPassword;
    }

    public UserGroupResponseDto mapTUserGroupToUserGroupResponseDto(TUserGroup userGroup){

        if(userGroup == null){
            return null;
        }

        return new UserGroupResponseDto(
                userGroup.getId(),
                userGroup.getCreatedOn(),
                userGroup.getModifiedOn(),
                userGroup.getName(),
                userGroup.getNote()
        );
    }

    public TUser dtoToMerchantUser(MerchantUserRequestDto userRequestDto){

        if(userRequestDto == null){
            return null;
        }

        return TUser.builder()
                .email(userRequestDto.email())
                .password(passwordEncoder.encode(userRequestDto.password()))
                .secondName(userRequestDto.secondName())
                .firstName(userRequestDto.firstName())
                .build();
    }

    public TUser dtoToTUser(UserRequestDto userRequestDto){

        if(userRequestDto == null){
            return null;
        }

        return TUser.builder()
                .email(userRequestDto.email())
                .password(passwordEncoder.encode(userRequestDto.password()))
                .secondName(userRequestDto.secondName())
                .firstName(userRequestDto.firstName())
                .userType(UserTypeEnum.valueOf(userRequestDto.userType()))
                .build();
    }

    public UserResponseDto userToDto(TUser user){

        if(user == null){
            return null;
        }

        return new UserResponseDto(
                user.getId(),
                user.getCreatedOn(),
                user.getModifiedOn(),
                user.getFirstName(),
                user.getSecondName(),
                user.getEmail(),
                user.isAccountLocked(),
                user.isAccountExpired(),
                user.isCredentialExpired(),
                user.isApproved(),
                userGroupToDto(user.getUserGroup()),
                user.getDeleted(),
                user.getApprovedBy(),
                user.getUserType(),
                user.getNonVerifiedEmail(),
                user.getImagePath(),
                user.getMerchantId()
        );
    }

    public UserApprovalResponseDto userApprovalToDto(TUserApproval userApproval){

        if(userApproval == null){
            return null;
        }

        return new UserApprovalResponseDto(
                userApproval.getId(),
                userApproval.getCreatedOn(),
                userApproval.getModifiedOn(),
                userToDto(userApproval.getCreatedBy()),
                userToDto(userApproval.getModifiedBy()),
                userToDto(userApproval.getUser()),
                userApproval.getStatus()
        );
    }

    public AddressResponseDto addressToDto(TAddress address){

        if(address == null){
            return null;
        }

        return new AddressResponseDto(
                address.getId(),
                address.getCreatedOn(),
                address.getModifiedOn(),
                userToDto(address.getCreatedBy()),
                userToDto(address.getModifiedBy()),
                address.getStreet(),
                address.getAddressDescription(),
                address.getTownOrVillage(),
                baseDtoService.countryToDto(address.getCountry()),
                address.getLatitudeCoordinate(),
                address.getLongitudeCoordinate()
        );
    }
}
