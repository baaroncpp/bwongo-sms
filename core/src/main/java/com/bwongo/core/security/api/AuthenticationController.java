package com.bwongo.core.security.api;

import com.bwongo.core.security.model.dto.AuthenticationRequestDto;
import com.bwongo.core.security.model.dto.AuthenticationResponseDto;
import com.bwongo.core.security.model.dto.RefreshTokenRequestDto;
import com.bwongo.core.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;

/**
 * @Author bkaaron
 * @Project soft-life-saver-sacco
 * @Date 3/10/24
 * @LocalTime 6:07 PM
 **/
@Tag(name = "Authentication", description = "Manage user authentication, access token and refresh token")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "authenticate", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDto));
    }

    @PostMapping(path = "refresh-token", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequestDto));
    }

    @GetMapping(path = "validate/merchant/code/{code}/secret-key/{secretKey}")
    public ResponseEntity<Boolean> validateMerchantCodeAndSecretKey(@PathVariable("code") String merchantCode,
                                                                      @PathVariable("secretKey") String secretKey){
        return ResponseEntity.ok(authenticationService.validateMerchantSecretKey(merchantCode, secretKey));
    }

}
