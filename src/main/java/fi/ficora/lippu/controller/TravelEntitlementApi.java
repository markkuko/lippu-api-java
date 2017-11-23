/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.0-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package fi.ficora.lippu.controller;

import fi.ficora.lippu.domain.model.ApiError;
import fi.ficora.lippu.domain.model.ReservationDeleteResponse;
import fi.ficora.lippu.domain.model.TravelEntitlement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

@Api(value = "travelEntitlement", description = "the travelEntitlement API")
public interface TravelEntitlementApi {

    Logger log = LoggerFactory.getLogger(TravelEntitlementApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Activate a travel entitlement identified by the travelEntitlementId.", nickname = "travelEntitlementActivate", notes = "", response = TravelEntitlement.class, tags={ "travelEntitlement", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns status of travel entitlement after activation.", response = TravelEntitlement.class),
        @ApiResponse(code = 403, message = "Access denied or invalid authentication token.", response = ApiError.class),
        @ApiResponse(code = 404, message = "No valid travel entitlement found with the given id.", response = ApiError.class) })
    @RequestMapping(value = "/travelEntitlement/{travelEntitlementId}/activate",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    default ResponseEntity<TravelEntitlement> travelEntitlementActivate(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "A identifier for the travel entitlement",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        return new ResponseEntity<TravelEntitlement>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Cancelletion of one travel entitlement from a reservation.", nickname = "travelEntitlementDelete", notes = "", response = ReservationDeleteResponse.class, tags={ "travelEntitlement", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an acknowledgment of a successful cancellation of the travel entitlement.", response = ReservationDeleteResponse.class),
        @ApiResponse(code = 403, message = "Access denied or invalid authentication token.", response = ApiError.class),
        @ApiResponse(code = 404, message = "No valid travel entitlement found with the given id.", response = ApiError.class) })
    @RequestMapping(value = "/travelEntitlement/{travelEntitlementId}",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    default ResponseEntity<ReservationDeleteResponse> travelEntitlementDelete(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "An identifier for a travel entitlement that will be cancelled.",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        return new ResponseEntity<ReservationDeleteResponse>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Retrieves the status of the travel entitlement.", nickname = "travelEntitlementStatus", notes = "", response = TravelEntitlement.class, tags={ "travelEntitlement", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns status of the travel entitlement.", response = TravelEntitlement.class),
        @ApiResponse(code = 403, message = "Access denied or invalid authentication token.", response = ApiError.class),
        @ApiResponse(code = 404, message = "No valid travel entitlement found with the given id.", response = ApiError.class) })
    @RequestMapping(value = "/travelEntitlement/{travelEntitlementId}",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.GET)
    default ResponseEntity<TravelEntitlement> travelEntitlementStatus(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "A identifier for the travel entitlement.",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        return new ResponseEntity<TravelEntitlement>(HttpStatus.NOT_IMPLEMENTED);
    }

}
