package fi.ficora.lippu.controller;

import fi.ficora.lippu.domain.model.AvailabilityRequest;
import fi.ficora.lippu.domain.model.AvailabilityResponse;
import fi.ficora.lippu.domain.model.TravelAvailability;
import fi.ficora.lippu.service.AuthService;
import fi.ficora.lippu.service.AvailabilityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

@Controller
public class AvailabilityApiController implements AvailabilityApi {
    private final ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private AvailabilityService availabilityService;

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    public AvailabilityApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Trip availaibility inquiry", notes = "API to inquire trip availabilities from transport operator.", response = AvailabilityResponse.class, tags={ "availability", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns free transport service capability with given search terms.", response = AvailabilityResponse.class) })
    @RequestMapping(value = "/availability",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml" },
            method = RequestMethod.POST)
    public ResponseEntity<AvailabilityResponse> availability(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will add its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId, @ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId, @ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator, @ApiParam(value = "Language value like specified in [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Value is used to localize response values." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage, @ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="Authorization", required=true) String authorization, @ApiParam(value = "Availability search terms" ,required=true )  @Valid @RequestBody AvailabilityRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        log.info("Operation: commitAuth; Transaction: {}; Message: {}; Initator: {}",
                xTransactionId, xMessageId, xInitiator);
            // @todo Replace stub implementation
        AvailabilityResponse response = availabilityService.addAvailability(body);
        if(response != null) {
            return new ResponseEntity<AvailabilityResponse>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<AvailabilityResponse>(HttpStatus.BAD_REQUEST);
        }

    }

}
