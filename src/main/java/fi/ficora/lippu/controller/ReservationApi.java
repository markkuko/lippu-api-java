/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.0-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package fi.ficora.lippu.controller;

import fi.ficora.lippu.domain.model.ApiError;
import fi.ficora.lippu.domain.model.ReservationDeleteResponse;
import fi.ficora.lippu.domain.model.ReservationRequest;
import fi.ficora.lippu.domain.model.ReservationResponse;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-30T10:16:24.190+03:00")

@Api(value = "reservation", description = "the reservation API")
public interface ReservationApi {

    @ApiOperation(value = "Travel reservation", notes = "An interface that allows operators to make travel reservations from a transport service.", response = ReservationResponse.class, tags={ "reservation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns the ticket-payloads generated by the transport service and the corresponding booking data.", response = ReservationResponse.class),
        @ApiResponse(code = 403, message = "Invalid authentication token", response = ApiError.class) })
    @RequestMapping(value = "/reservation",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    default ResponseEntity<ReservationResponse> reservation(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Language value like specified in [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Value is used to localize response values." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "Reservation information" ,required=true )  @Valid @RequestBody ReservationRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!
        return new ResponseEntity<ReservationResponse>(HttpStatus.OK);
    }


    @ApiOperation(value = "Cancelletion of transport reservation", notes = "", response = ReservationDeleteResponse.class, tags={ "reservation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns an acknowledgment of a successful cancellation", response = ReservationDeleteResponse.class),
        @ApiResponse(code = 403, message = "Case id does not exists or invalid authentication token", response = ApiError.class),
        @ApiResponse(code = 404, message = "No valid booking with given case identifier.", response = ApiError.class) })
    @RequestMapping(value = "/reservation/{caseId}",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.DELETE)
    default ResponseEntity<?> reservationDelete(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "A case identifier for a reservation that will be removed",required=true ) @PathVariable("caseId") String caseId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!
        return new ResponseEntity<ReservationDeleteResponse>(HttpStatus.OK);
    }

}
