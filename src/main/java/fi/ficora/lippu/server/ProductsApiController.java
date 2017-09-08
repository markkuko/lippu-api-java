package fi.ficora.lippu.server;

import org.threeten.bp.OffsetDateTime;
import fi.ficora.lippu.model.ProductQueryResponse;

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
public class ProductsApiController implements ProductsApi {
    private final ObjectMapper objectMapper;

    public ProductsApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<ProductQueryResponse> products(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Kielivalinta kuten on määritelty [RFC 2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4). Käytetään vastausviestin tekstuaalisten arvojen lokalisointiin." ,required=true) @RequestHeader(value="Accept-Language", required=true) String acceptLanguage,
        @ApiParam(value = "Päivämäärä, jolle validit tuotteet tulisi palauttaa. Mikäli parametria ei anneta, niin päivämääräksi oletetaan tämän hetkinen päivä." ) @PathVariable("date") OffsetDateTime date,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!

        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<ProductQueryResponse>(objectMapper.readValue("\"[{\"products\":[{\"productType\":\"PRODUCT-SINGLE-TICKET\",\"contract\":\"CONTRACT-AB123CD\",\"name\":\"Kertalippu\",\"description\":\"Kertalipun voimassaolo alkaa ostohetkestä. Viimeinen voimassaoloaika tulostuu lippuun. Liikennevälineeseen saa nousta voimassaoloaikana ja jatkaa perille asti, vaikka lippuun merkitty voimassaoloaika päättyisi matkan aikana.\",\"validFrom\":\"2017-08-22T07:20:04.474Z\",\"validTo\":\"2018-08-23T07:20:04.474Z\",\"suitablePassengerCategories\":[\"Adult\",\"Child\",\"Youth\",\"Senior\"]},{\"productType\":\"PRODUCT-SEASON-TICKET\",\"contract\":\"CONTRACT-AB123CD\",\"name\":\"Kuukausilippu\",\"description\":\"Kuukausilippu on matkustajan käytössä voimassaoloaikana rajoittamattomasti määritellyllä vuorovälillä. Lippu on henkilökohtainen ja henkilöllisyys on todistettava tarvittaessa.\",\"validFrom\":\"2017-08-22T07:00:00.000Z\",\"validTo\":\"2018-08-23T07:04:00.000Z\",\"suitablePassengerCategories\":[\"Adult\",\"Youth\"]}],\"passengerCategories\":[\"Adult\",\"Child\",\"Youth\",\"Senior\"]}]\"", ProductQueryResponse.class), HttpStatus.OK);
        }


        if (accept != null && accept.contains("application/xml")) {
            return new ResponseEntity<ProductQueryResponse>(objectMapper.readValue("<null>null  <passengerCategories>aeiou</passengerCategories></null>", ProductQueryResponse.class), HttpStatus.OK);
        }

        return new ResponseEntity<ProductQueryResponse>(HttpStatus.OK);
    }

}
