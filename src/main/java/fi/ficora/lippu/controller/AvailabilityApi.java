/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.0-SNAPSHOT).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package fi.ficora.lippu.controller;

import fi.ficora.lippu.domain.model.AvailabilityRequest;
import fi.ficora.lippu.domain.model.AvailabilityResponse;

import fi.ficora.lippu.domain.model.Travel;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.OffsetDateTime;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

@Api(value = "availability", description = "the availability API")
public interface AvailabilityApi {

    @ApiOperation(value = "Trip availaibility inquiry", notes = "API to inquire trip availabilities from transport operator.", response = AvailabilityResponse.class, tags={ "availability", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Returns free transport service capability with given search terms.", response = AvailabilityResponse.class) })
    @RequestMapping(value = "/availability",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    default ResponseEntity<AvailabilityResponse> availability(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will add its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message related to same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Language value like specified in [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Value is used to localize response values." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="Authorization", required=true) String authorization,@ApiParam(value = "Availability search terms" ,required=true )  @Valid @RequestBody AvailabilityRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // @todo Replace stub implementation
        AvailabilityResponse response = new AvailabilityResponse();
        response.setContract("CONTRACT ID");
        Travel travel = new Travel();
        travel.setProductType("PRODUCT ID");
        travel.setServiceId("ASDF");
        travel.setDateTime(OffsetDateTime.now());
        response.setTravel(travel);
        System.out.println("Testi:" + body.toString());
        return new ResponseEntity<AvailabilityResponse>(response, HttpStatus.OK);
    }

}