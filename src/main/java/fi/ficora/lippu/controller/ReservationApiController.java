package fi.ficora.lippu.controller;

import fi.ficora.lippu.config.Constants;
import fi.ficora.lippu.domain.ReservationItem;
import fi.ficora.lippu.domain.model.*;
import fi.ficora.lippu.exception.NotAuthorizedException;
import fi.ficora.lippu.exception.NotValidReservationRequest;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-11T13:35:10.864+03:00")

@Controller
public class ReservationApiController implements ReservationApi {
    private final ObjectMapper objectMapper;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger log = LoggerFactory.getLogger(ReservationApiController.class);
    public ReservationApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "Travel reservation", notes = "An interface that allows operators to make travel reservations from a transport service.", response = ReservationResponse.class, tags={ "reservation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the ticket-payloads generated by the transport service and the corresponding booking data.", response = ReservationResponse.class) })
    @RequestMapping(value = "/reservation",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml" },
            method = RequestMethod.POST)
    public ResponseEntity<?> reservation(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "Language value like specified in [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Value is used to localize response values." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "Reservation information" ,required=true )  @Valid @RequestBody ReservationRequest body, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {

        try {
            ReservationResponse response = new ReservationResponse();
            String caseId = "";
            List<ReservationItem> items = reservationService.confirmReservation(
                    body.getReservations());
            for (ReservationItem item : items) {
                if (caseId.equals("")) {
                    caseId = item.getCaseId();
                }
                response.addConfirmedReservationsItem(
                        ConversionUtil.
                                reservationItemToConfirmedReservations(item));
            }
            response.setCaseId(caseId);
            return new ResponseEntity<ReservationResponse>(response, HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            log.info("Reservation throw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse403(
                    messageSource.getMessage("http.error.message.403",null, Locale.ENGLISH));
        } catch (ResourceNotFoundException e) {
            log.info("Reservation throw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse404(
                    messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
        } catch (NotValidReservationRequest e) {
            log.info("Reservation throw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse400(
                    messageSource.getMessage("http.error.message.400",null, Locale.ENGLISH));
        }

    }


    @ApiOperation(value = "Cancelletion of transport reservation", notes = "", response = Boolean.class, tags={ "reservation", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns an acknowledgment of a successful cancellation", response = Boolean.class),
            @ApiResponse(code = 404, message = "No valid booking with given case identifier.") })
    @RequestMapping(value = "/reservation/{caseId}",
            produces = { "application/json", "application/xml" },
            consumes = { "application/json", "application/xml" },
            method = RequestMethod.DELETE)
    public ResponseEntity<?> reservationDelete(@ApiParam(value = "Unique message identification that is used for error situations. Every client implementation will create its own message ids." ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,@ApiParam(value = "Unique transaction identification that is used for error situations. Value is generated by original requestor and used in every message in the whole request chain related to the same ticket transaction." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,@ApiParam(value = "Text indentification of original requestor. Value is use for error situations." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,@ApiParam(value = "[JWT](https://jwt.io/) authentication token for authorization requests." ,required=true) @RequestHeader(value="X-Authorization", required=true) String xAuthorization,@ApiParam(value = "A case identifier for a reservation that will be removed",required=true) @PathVariable("caseId") String caseId, @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        try {
            int response = reservationService.delete(caseId);
            if (response == Constants.RESULT_CODE_SUCCESS) {
                ReservationDeleteResponse deleteResponse = new ReservationDeleteResponse();
                deleteResponse.cancellation(Boolean.TRUE);
                return new ResponseEntity<ReservationDeleteResponse>(deleteResponse, HttpStatus.OK);
            } else {
                return ApiErrorUtil.generateErrorResponse404(
                        messageSource.getMessage("http.error.message.404",null, Locale.ENGLISH));
            }
        } catch (NotAuthorizedException e) {
            log.info("Reservation delete throw {} ", e.getMessage());
            return ApiErrorUtil.generateErrorResponse403(
                    messageSource.getMessage("http.error.message.403",null, Locale.ENGLISH));
        }
    }
}
