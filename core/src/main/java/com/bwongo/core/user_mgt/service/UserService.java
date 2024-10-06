package com.bwongo.core.user_mgt.service;

import com.bwongo.commons.exceptions.model.ExceptionType;
import com.bwongo.commons.text.StringRegExUtil;
import com.bwongo.commons.utils.Validate;
import com.bwongo.core.base.model.dto.response.PageResponseDto;
import com.bwongo.core.base.model.enums.ApprovalStatusEnum;
import com.bwongo.core.base.model.enums.UserTypeEnum;
import com.bwongo.core.base.repository.TCountryRepository;
import com.bwongo.core.base.service.AuditService;
import com.bwongo.core.user_mgt.models.dto.request.*;
import com.bwongo.core.user_mgt.models.dto.response.UserApprovalResponseDto;
import com.bwongo.core.user_mgt.models.dto.response.UserResponseDto;
import com.bwongo.core.user_mgt.models.jpa.TPreviousPassword;
import com.bwongo.core.user_mgt.models.jpa.TUser;
import com.bwongo.core.user_mgt.models.jpa.TUserApproval;
import com.bwongo.core.user_mgt.repository.*;
import com.bwongo.core.user_mgt.service.dto.UserDtoService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.bwongo.core.base.utils.BaseUtils.pageToDto;
import static com.bwongo.core.base.utils.EnumValidation.isApprovalStatus;
import static com.bwongo.core.base.utils.EnumValidation.isUserType;
import static com.bwongo.core.merchant_mgt.utils.MerchantMsgConstants.MERCHANT_GROUP;
import static com.bwongo.core.user_mgt.utils.UserMgtUtils.checkThatUserIsAssignable;
import static com.bwongo.core.user_mgt.utils.UserMsgConstants.*;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/15/24
 * @LocalTime 2:17 PM
 **/
@RequiredArgsConstructor
@Service
public class UserService {

    private static final String PASSWORD = "password";
    private static final String USERTYPE = "userType";

    private final TUserRepository userRepository;
    private final TUserGroupRepository userGroupRepository;
    private final TCountryRepository countryRepository;
    private final TUserApprovalRepository userApprovalRepository;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoService userDtoService;
    private final TPreviousPasswordRepository previousPasswordRepository;
    private final TGroupAuthorityRepository groupAuthorityRepository;

    @Transactional
    public UserResponseDto addUser(UserRequestDto userRequestDto) {

        userRequestDto.validate();

        var existingUserUsername = userRepository.findByEmail(userRequestDto.email());
        Validate.isTrue(existingUserUsername.isEmpty(), ExceptionType.BAD_REQUEST ,USERNAME_TAKEN, userRequestDto.email());

        var existingUserGroup = userGroupRepository.findTUserGroupByName("ADMIN_GROUP");
        final var userGroup = existingUserGroup.get();

        var user = userDtoService.dtoToTUser(userRequestDto);
        user.setAccountExpired(Boolean.FALSE);
        user.setAccountLocked(Boolean.FALSE);
        user.setApproved(Boolean.FALSE);
        user.setDeleted(Boolean.FALSE);
        user.setInitialPasswordReset(Boolean.FALSE);
        user.setNonVerifiedEmail(Boolean.TRUE);
        user.setUserGroup(userGroup);

        auditService.stampLongEntity(user);
        var savedUser = userRepository.save(user);

        //initiate user approval
        var userApproval = new TUserApproval();
        userApproval.setUser(user);
        userApproval.setStatus(ApprovalStatusEnum.PENDING);
        auditService.stampAuditedEntity(userApproval);

        userApprovalRepository.save(userApproval);

        return userDtoService.userToDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {

        userUpdateRequestDto.validate();
        var userId = userUpdateRequestDto.userId();
        var email = userUpdateRequestDto.email();
        var userType = UserTypeEnum.valueOf(userUpdateRequestDto.userType());
        var existingUserEmail = userRepository.findByEmail(userUpdateRequestDto.email());

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, String.format(USER_DOES_NOT_EXIST, userId));
        var user = existingUser.get();

        if(!user.getEmail().equals(email))
            Validate.isTrue(existingUserEmail.isEmpty(), ExceptionType.BAD_REQUEST, EMAIL_ALREADY_TAKEN, email);

        var existingUserGroup = userGroupRepository.findTUserGroupByName("ADMIN_GROUP");
        final var userGroup = existingUserGroup.get();

        Validate.isTrue(!userGroup.getName().equals(MERCHANT_GROUP), ExceptionType.BAD_REQUEST, INVALID_USER_GROUP);

        user.setUserGroup(userGroup);
        user.setEmail(email);
        user.setUserType(userType);
        user.setFirstName(userUpdateRequestDto.firstName());
        user.setSecondName(userUpdateRequestDto.secondName());

        auditService.stampLongEntity(user);

        return userDtoService.userToDto(userRepository.save(user));
    }

    public UserResponseDto getUserById(Long id) {
        return userDtoService.userToDto(getUser(id));
    }

    public UserResponseDto reAssignUserToGroup(Long groupId, Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        checkThatUserIsAssignable(existingUser.get());

        var existingUserGroup = userGroupRepository.findById(groupId);
        Validate.isPresent(existingUser, USER_GROUP_DOES_NOT_EXIST, groupId);
        final var userGroup = existingUserGroup.get();

        Validate.isTrue(groupId == userGroup.getId(), ExceptionType.BAD_REQUEST, String.format(USER_ALREADY_ASSIGNED_TO_USER_GROUP, groupId));

        existingUser.get().setUserGroup(existingUserGroup.get());
        auditService.stampLongEntity(existingUser.get());

        return userDtoService.userToDto(existingUser.get());
    }

    public Long getNumberOfUsersByType(String userType) {
        Validate.isTrue(isUserType(userType), ExceptionType.BAD_REQUEST, VALID_USER_TYPE);
        UserTypeEnum userTypeEnum = UserTypeEnum.valueOf(userType);
        return userRepository.countByUserType(userTypeEnum);
    }

    public PageResponseDto getAll(Pageable pageable) {

        var userPage = userRepository.findAll(pageable);
        var userList = userPage.stream()
                .map(userDtoService::userToDto)
                .toList();

        return pageToDto(userPage, userList);
    }

    @Transactional
    public UserApprovalResponseDto approveUser(UserApprovalRequestDto userApprovalRequestDto) {

        userApprovalRequestDto.validate();
        var existingApproval = userApprovalRepository.findById(userApprovalRequestDto.approvalId());
        Validate.isPresent(existingApproval, USER_APPROVAL_NOT_FOUND, userApprovalRequestDto.approvalId());
        final var userApproval = existingApproval.get();
        var approvalEnum = ApprovalStatusEnum.valueOf(userApprovalRequestDto.status());

        Validate.isTrue(!approvalEnum.equals(ApprovalStatusEnum.PENDING), ExceptionType.BAD_REQUEST, PENDING_INVALID_APPROVAL);
        Validate.isTrue(!userApproval.getStatus().equals(approvalEnum), ExceptionType.BAD_REQUEST, INVALID_APPROVAL, approvalEnum.name());

        userApproval.setStatus(approvalEnum);
        auditService.stampAuditedEntity(userApproval);
        var savedUserApproval = userApprovalRepository.save(userApproval);

        final var user = savedUserApproval.getUser();

        if(approvalEnum.equals(ApprovalStatusEnum.APPROVED)) {
            user.setApproved(Boolean.TRUE);
            user.setAccountExpired(Boolean.FALSE);
            user.setAccountLocked(Boolean.FALSE);
            user.setCredentialExpired(Boolean.FALSE);
            user.setDeleted(Boolean.FALSE);
            user.setApprovedBy(auditService.getLoggedInUser().getId());
            auditService.stampLongEntity(user);
            userRepository.save(user);
        }

        return userDtoService.userApprovalToDto(savedUserApproval);
    }

    public boolean deleteUserAccount(Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        final var user = existingUser.get();

        user.setAccountExpired(Boolean.TRUE);
        user.setAccountLocked(Boolean.TRUE);
        user.setDeleted(Boolean.TRUE);
        user.setApproved(Boolean.FALSE);

        auditService.stampLongEntity(user);
        userRepository.save(user);

        return Boolean.TRUE;
    }

    public boolean suspendUserAccount(Long userId) {

        var existingUser = userRepository.findById(userId);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, userId);
        final var user = existingUser.get();

        Validate.isTrue(!user.isAccountLocked(), ExceptionType.BAD_REQUEST, USER_ACCOUNT_IS_ALREADY_LOCKED);

        user.setAccountLocked(Boolean.TRUE);
        auditService.stampLongEntity(user);
        userRepository.save(user);

        return Boolean.TRUE;
    }

    public PageResponseDto getUserApprovals(String status, Pageable pageable) {

        Validate.isTrue(isApprovalStatus(status), ExceptionType.BAD_REQUEST, INVALID_APPROVAL_STATUS);
        var approvalEnum = ApprovalStatusEnum.valueOf(status);

        var approvalPage = userApprovalRepository.findAllByStatus(approvalEnum, pageable);
        var approvalList = approvalPage.stream()
                .map(userDtoService::userApprovalToDto)
                .toList();

        return pageToDto(approvalPage, approvalList);
    }

    private TUser getUser(Long id){
        var existingUser = userRepository.findById(id);
        Validate.isPresent(existingUser, USER_DOES_NOT_EXIST, id);

        var user = new TUser();
        if(existingUser.isPresent())
            user = existingUser.get();

        return user;
    }

    @Transactional
    public boolean changePassword(ChangePasswordRequestDto changePasswordRequestDto){

        changePasswordRequestDto.validate();
        var existingUser = userRepository.findById(auditService.getLoggedInUser().getId());
        var user = existingUser.get();

        var newPassword = changePasswordRequestDto.newPassword();
        var oldPassword = changePasswordRequestDto.oldPassword();
        var oldEncryptedPassword = user.getPassword();

        Validate.isTrue(passwordEncoder.matches(oldPassword, oldEncryptedPassword), ExceptionType.BAD_REQUEST, OLD_PASSWORDS_DONT_MATCH);
        Validate.isTrue(!newPassword.equals(oldPassword), ExceptionType.BAD_REQUEST, OLD_NEW_SAME_PASSWORD);

        var previousPasswords = previousPasswordRepository.findAllByUser(user);

        previousPasswords.forEach(previousPassword -> Validate.isTrue(!(passwordEncoder.matches(newPassword, previousPassword.getPreviousPassword())) ,
                ExceptionType.BAD_REQUEST,
                PASSWORD_USED_BEFORE));

        var optionalMaxPasswordCount = previousPasswords.stream().mapToInt(TPreviousPassword::getPasswordChangeCount).max();
        int maxPasswordCount = 0;

        if(optionalMaxPasswordCount.isPresent())
            maxPasswordCount = optionalMaxPasswordCount.getAsInt();

        var previousPassword = userDtoService.dtoToTPreviousPassword(changePasswordRequestDto);
        previousPassword.setPreviousPassword(oldEncryptedPassword);
        previousPassword.setPasswordChangeCount(maxPasswordCount + 1);
        previousPassword.setUser(user);

        user.setPassword(passwordEncoder.encode(newPassword));
        auditService.stampLongEntity(user);
        userRepository.save(user);

        auditService.stampLongEntity(previousPassword);
        previousPasswordRepository.save(previousPassword);

        return Boolean.TRUE;
    }

}
