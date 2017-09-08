package fi.ficora.lippu.server;

import fi.ficora.lippu.model.ReservationRequest;
import fi.ficora.lippu.model.ReservationResponse;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.validation.constraints.*;
import javax.validation.Valid;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-05T15:09:31.417+03:00")

@Controller
public class ReservationApiController implements ReservationApi {
    private final ObjectMapper objectMapper;

    public ReservationApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<ReservationResponse> reservation(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Varauksen tiedot" ,required=true )  @Valid @RequestBody ReservationRequest body,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!

        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<ReservationResponse>(objectMapper.readValue("\"[{\"caseId\":\"FIRMA/12345/2017\",\"confirmedReservations\":[{\"reservationData\":\"InRyYXZlbCI6IFsNCiAgICB7DQogICAgICAidHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgIm\",\"ticketPayload\":\"dGlja2V0IHBheWxvYWQ=\",\"validFrom\":\"2017-08-23T07:00:00\",\"validTo\":\"2017-08-23T08:00:00\"},{\"reservationData\":\"XXXXXXXXXXXXXXXXXdHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgIm\",\"ticketPayload\":\"dGlja2V0IHBheWxvYWQgMQ==\",\"validFrom\":\"2017-08-23T07:00:00\",\"validTo\":\"2017-08-23T08:00:00\"},{\"reservationData\":\"YYYYYYYYYYYYYYYYYYB7DQogICAgICAidHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgIm\",\"ticketPayload\":\"dGlja2V0IHBheWxvYWQgMg==\",\"validFrom\":\"2017-08-23T07:00:00\",\"validTo\":\"2017-08-23T08:00:00\"}]}]\"", ReservationResponse.class), HttpStatus.OK);
        }


        if (accept != null && accept.contains("application/xml")) {
            return new ResponseEntity<ReservationResponse>(objectMapper.readValue("<null>  <caseId>aeiou</caseId></null>", ReservationResponse.class), HttpStatus.OK);
        }

        return new ResponseEntity<ReservationResponse>(HttpStatus.OK);
    }

    public ResponseEntity<Boolean> reservationDelete(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Luodun varauksen tapaustunniste, jonka perusteella varaus poistetaan",required=true ) @PathVariable("caseId") String caseId,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!

        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<Boolean>(objectMapper.readValue("true", Boolean.class), HttpStatus.OK);
        }


        if (accept != null && accept.contains("application/xml")) {
            return new ResponseEntity<Boolean>(objectMapper.readValue("true", Boolean.class), HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }

}
