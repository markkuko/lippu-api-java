package fi.ficora.lippu.server;

import fi.ficora.lippu.model.AvailabilityRequest;
import fi.ficora.lippu.model.AvailabilityResponse;

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
public class AvailabilityApiController implements AvailabilityApi {
    private final ObjectMapper objectMapper;

    public AvailabilityApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<AvailabilityResponse> availability(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Kielivalinta kuten on määritelty [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Käytetään vastausviestin tekstuaalisten arvojen lokalisointiin." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage,
        @ApiParam(value = "Saatavuuskyselyn hakuehdot" ,required=true )  @Valid @RequestBody AvailabilityRequest body,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!

        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<AvailabilityResponse>(objectMapper.readValue("\"[{\"contract\":\"CONTRACT-AB123CD\",\"travel\":{\"productType\":\"PRODUCT-SINGLE-TICKET\",\"dateTime\":\"2017-08-23T07:20:04.474Z\",\"from\":{\"lat\":60.2,\"lon\":24.8,\"description\":\"Kauppatori, Mikkeli\"},\"to\":{\"id\":\"MATKA:181342\",\"description\":\"Lahti Matkakeskus C\"}},\"availability\":[{\"reservationData\":\"InRyYXZlbCI6IFsNCiAgICB7DQogICAgICAidHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgImRlc2NyaXB0aW9uIjogIkthdXB1bmdpbnRhbG8sIExhaHRpIg0KICAgICAgfSwNCiAgICAgICJ0byI6IHsNCiAgICAgICAgImxhdCI6IDYxLjQsDQogICAgICAgICJsb24iOiAyMC40LA0KICAgICAgICAiZGVzY3JpcHRpb24iOiAiSGVpbm9sYSINCiAgICAgIH0sDQogICAgICAicHJvZHVjdFR5cGUiOiAiUFJPRFVDVC1TSU5HTEUtVElDS0VUIiwNCiAgICAgICJkYXRlVGltZSI6ICIyMDE3LTA4LTIzVDA3OjIwOjA0LjQ3NFoiDQogICAgfQ==\",\"validTo\":\"2017-08-22T13:04:04.534Z\",\"fare\":{\"currency\":\"EUR\",\"amount\":13,\"vatPercent\":10},\"transport\":{\"operator\":\"Liikennöitsijä OY AB (Y-1234567)\",\"vehicleInfo\":\"Linja-auto Mikkeli-Lahti\",\"seatInfo\":\"Istumapaikka 45\"},\"applicableForPassengers\":[\"Adult\"]},{\"reservationData\":\"XXXXXXXXIFsNCiAgICB7DQogICAgICAidHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgImRlc2NyaXB0aW9uIjogIkthdXB1bmdpbnRhbG8sIExhaHRpIg0KICAgICAgfSwNCiAgICAgICJ0byI6IHsNCiAgICAgICAgImxhdCI6IDYxLjQsDQogICAgICAgICJsb24iOiAyMC40LA0KICAgICAgICAiZGVzY3JpcHRpb24iOiAiSGVpbm9sYSINCiAgICAgIH0sDQogICAgICAicHJvZHVjdFR5cGUiOiAiUFJPRFVDVC1TSU5HTEUtVElDS0VUIiwNCiAgICAgICJkYXRlVGltZSI6ICIyMDE3LTA4LTIzVDA3OjIwOjA0LjQ3NFoiDQogICAgfQ==\",\"validTo\":\"2017-08-22T13:04:04.534Z\",\"fare\":{\"currency\":\"EUR\",\"amount\":13,\"vatPercent\":10},\"transport\":{\"operator\":\"Liikennöitsijä OY AB (Y-1234567)\",\"vehicleInfo\":\"Linja-auto Mikkeli-Lahti\",\"seatInfo\":\"Istumapaikka 46\"},\"applicableForPassengers\":[\"Adult\"]},{\"reservationData\":\"YYYYYYYYYIFsNCiAgICB7DQogICAgICAidHJhdmVsVHlwZSI6ICJDb29yZGluYXRlQmFzZWRUcmF2ZWwiLA0KICAgICAgImZyb20iOiB7DQogICAgICAgICJsYXQiOiA2MC4yLA0KICAgICAgICAibG9uIjogMjQuOCwNCiAgICAgICAgImRlc2NyaXB0aW9uIjogIkthdXB1bmdpbnRhbG8sIExhaHRpIg0KICAgICAgfSwNCiAgICAgICJ0byI6IHsNCiAgICAgICAgImxhdCI6IDYxLjQsDQogICAgICAgICJsb24iOiAyMC40LA0KICAgICAgICAiZGVzY3JpcHRpb24iOiAiSGVpbm9sYSINCiAgICAgIH0sDQogICAgICAicHJvZHVjdFR5cGUiOiAiUFJPRFVDVC1TSU5HTEUtVElDS0VUIiwNCiAgICAgICJkYXRlVGltZSI6ICIyMDE3LTA4LTIzVDA3OjIwOjA0LjQ3NFoiDQogICAgfQ==\",\"validTo\":\"2017-08-22T13:04:04.534Z\",\"fare\":{\"currency\":\"EUR\",\"amount\":7.5,\"vatPercent\":10},\"transport\":{\"operator\":\"Liikennöitsijä OY AB (Y-1234567)\",\"vehicleInfo\":\"Linja-auto Mikkeli-Lahti\",\"seatInfo\":\"Istumapaikka 47\"},\"applicableForPassengers\":[\"Child\"],\"extraServices\":[{\"title\":\"Lastenistuin\",\"description\":\"Erillinen lasten turvaistuin matkalle\",\"extraServiceReservationData\":\"EXTRA-CHILD-SEAT-115311\",\"fare\":{\"currency\":\"EUR\",\"amount\":4,\"vatPercent\":10}}]}]}]\"", AvailabilityResponse.class), HttpStatus.OK);
        }


        if (accept != null && accept.contains("application/xml")) {
            return new ResponseEntity<AvailabilityResponse>(objectMapper.readValue("<null>  <contract>aeiou</contract>null</null>", AvailabilityResponse.class), HttpStatus.OK);
        }

        return new ResponseEntity<AvailabilityResponse>(HttpStatus.OK);
    }

}
