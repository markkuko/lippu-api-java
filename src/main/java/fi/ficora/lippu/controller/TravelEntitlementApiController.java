package fi.ficora.lippu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.ApiError;
import fi.ficora.lippu.domain.model.ReservationDeleteResponse;
import fi.ficora.lippu.domain.model.TravelEntitlement;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.exception.ResourceNotFoundException;
import fi.ficora.lippu.service.IReservationService;
import fi.ficora.lippu.util.ApiErrorUtil;
import fi.ficora.lippu.util.ConversionUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

@Controller
public class TravelEntitlementApiController implements TravelEntitlementApi {
    private final ObjectMapper objectMapper;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private MessageSource messageSource;
    private static final Logger log = LoggerFactory.getLogger(TravelEntitlementApiController.class);
    public TravelEntitlementApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
    public ResponseEntity<?> travelEntitlementActivate(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId, @ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId, @ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator, @ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization, @ApiParam(value = "A identifier for the travel entitlement",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        try {
            ReservationItem item = reservationService.activateReservationItem(
                    travelEntitlementId);
            TravelEntitlement entitlement = ConversionUtil.
                    reservationItemToTravelEntitlement(item);
            return new ResponseEntity<TravelEntitlement>(entitlement, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.info("TravelEntitlement activate threw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse404(
                    messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
        } catch (NotAuthorizedException e) {
            log.info("TravelEntitlement activate threw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse403(
                    messageSource.getMessage("http.error.message.403",null, Locale.ENGLISH));
        }
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
    public ResponseEntity<?> travelEntitlementDelete(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "An identifier for a travel entitlement that will be cancelled.",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        try {
            int response = reservationService.deleteReservationItem(travelEntitlementId);
            if (response == Constants.RESULT_CODE_SUCCESS) {
                ReservationDeleteResponse deleteResponse = new ReservationDeleteResponse();
                deleteResponse.cancellation(Boolean.TRUE);
                return new ResponseEntity<ReservationDeleteResponse>(deleteResponse, HttpStatus.OK);
            } else {
                return ApiErrorUtil.generateErrorResponse404(
                        messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
            }
        } catch (ResourceNotFoundException e) {
            log.info("TravelEntitlement delete threw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse404(
                    messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
        } catch (NotAuthorizedException e) {
            log.info("Travel Entitlement delete threw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse403(
                    messageSource.getMessage("http.error.message.403",null, Locale.ENGLISH));
        }
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
    public ResponseEntity<?> travelEntitlementStatus(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "A identifier for the travel entitlement.",required=true) @PathVariable("travelEntitlementId") String travelEntitlementId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        try {
            ReservationItem item = reservationService.getReservationItem(travelEntitlementId);
            if(item == null || !item.isConfirmed()) {
                log.info("Reservation item is null or is not confirmed {} ");
                return ApiErrorUtil.generateErrorResponse404(
                        messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
            }
            TravelEntitlement entitlement = ConversionUtil.
                    reservationItemToTravelEntitlement(item);
            return new ResponseEntity<TravelEntitlement>(entitlement, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.info("TravelEntitlement delete threw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse404(
                    messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));

        } catch (NotAuthorizedException e) {
            log.info("Reservation delete throw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse403(
                    messageSource.getMessage("http.error.message.403",null, Locale.ENGLISH));

        }
    }

}
