package fi.ficora.lippu.controller;

import fi.ficora.lippu.repository.AuthRepository;
import fi.ficora.lippu.repository.ClientRepository;
import fi.ficora.lippu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.ficora.lippu.domain.Client;
import javax.validation.Valid;
import fi.ficora.lippu.domain.model.AccountId;
import fi.ficora.lippu.domain.model.AuthenticationInitResponse;
import fi.ficora.lippu.domain.model.AuthenticationRequest;
import fi.ficora.lippu.domain.model.AuthenticationResponse;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

@Controller
public class AuthApiController implements AuthApi {
    private final ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    public AuthApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // final Logger logger = LoggerFactory.getLogger(AuthApi.getClass());
    @ApiOperation(value = "Creates a valid authentication token", notes = "", response = AuthenticationResponse.class, tags={ "login", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "[JWT](https://jwt.io/) authentication token for making authorized requests.", response = AuthenticationResponse.class),
        @ApiResponse(code = 403, message = "Authentication failed"),
        @ApiResponse(code = 405, message = "Input error") })
    @RequestMapping(value = "/auth/commit",
        produces = { "application/json", "application/xml" },
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> commitAuth(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will add its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Input for validating authentication request." ,required=true )  @Valid @RequestBody AuthenticationRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // @todo Replace stub implementation
        log.info("Operation: commitAuth; Transaction: {}; Message: {}; Initator: {}", xTransactionId, xMessageId, xInitiator);

            String token = authService.verifyAuthentication(body.getData(),
                    body.getCnonce(),
                    body.getPubKeyId(), body.getAlg().toString());
            if(token != null) {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setToken(token);
                return new ResponseEntity<AuthenticationResponse>(response,HttpStatus.OK);
            } else {
                log.info("Operation: commitAuth; token is null {}, returning forbidden.", xInitiator);
                return new ResponseEntity<AuthenticationResponse>(HttpStatus.FORBIDDEN);
            }

    }

    @ApiOperation(value = "Initiates authentication process", notes = "", response = AuthenticationInitResponse.class, tags={ "login", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication initialization response. Response includes controller side nonce value for the authentication process.", response = AuthenticationInitResponse.class),
            @ApiResponse(code = 403, message = "Company account does not exists or valid authentication token could not be generated."),
            @ApiResponse(code = 405, message = "Invalid company account.") })
    @RequestMapping(value = "/auth/init",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml" },
            method = RequestMethod.POST)
    public ResponseEntity<AuthenticationInitResponse> initAuth(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will add its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Company account identification" ,required=true )  @Valid @RequestBody AccountId body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        if(body.getAccount() != null ) {
            String nonce = authService.generateNonce(body.getAccount());
            if (nonce != null) {
                AuthenticationInitResponse response = new AuthenticationInitResponse();
                response.setNonce(nonce);
                return new ResponseEntity<AuthenticationInitResponse>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<AuthenticationInitResponse>(HttpStatus.FORBIDDEN);

    }

}