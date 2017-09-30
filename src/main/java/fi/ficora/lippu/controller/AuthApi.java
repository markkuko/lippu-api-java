/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.0-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package fi.ficora.lippu.controller;

import fi.ficora.lippu.domain.model.AccountId;
import fi.ficora.lippu.domain.model.ApiError;
import fi.ficora.lippu.domain.model.AuthenticationInitResponse;
import fi.ficora.lippu.domain.model.AuthenticationRequest;
import fi.ficora.lippu.domain.model.AuthenticationResponse;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

@Api(value = "auth", description = "the auth API")
public interface AuthApi {

    @ApiOperation(value = "Creates a valid authentication token", notes = "", response = AuthenticationResponse.class, tags={ "login", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "[JWT](https://jwt.io/) authentication token for making authorized requests.", response = AuthenticationResponse.class),
        @ApiResponse(code = 403, message = "Authentication failed", response = ApiError.class),
        @ApiResponse(code = 405, message = "Input error", response = ApiError.class) })
    @RequestMapping(value = "/auth/commit",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    default ResponseEntity<?> commitAuth(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Input for validating authentication request." ,required=true )  @Valid @RequestBody AuthenticationRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!
        return new ResponseEntity<AuthenticationResponse>(HttpStatus.OK);
    }


    @ApiOperation(value = "Initiates authentication process", notes = "", response = AuthenticationInitResponse.class, tags={ "login", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Authentication initialization response. Response includes server side nonce value for the authentication process.", response = AuthenticationInitResponse.class),
        @ApiResponse(code = 403, message = "Company account does not exists or valid authentication token could not be generated.", response = ApiError.class),
        @ApiResponse(code = 405, message = "Invalid company account.", response = ApiError.class) })
    @RequestMapping(value = "/auth/init",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    default ResponseEntity<?> initAuth(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Company account identification" ,required=true )  @Valid @RequestBody AccountId body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!
        return new ResponseEntity<AuthenticationInitResponse>(HttpStatus.OK);
    }

}
