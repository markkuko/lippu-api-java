package fi.ficora.lippu.server;

import fi.ficora.lippu.model.AccountId;
import fi.ficora.lippu.model.AuthenticationRequest;

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
public class AuthApiController implements AuthApi {
    private final ObjectMapper objectMapper;

    public AuthApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<String> commitAuth(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Autentikaation validointiin käytettävä syöteviesti." ,required=true )  @Valid @RequestBody AuthenticationRequest body,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!

        if (accept != null && accept.contains("application/json")) {
            return new ResponseEntity<String>(objectMapper.readValue("{ }", String.class), HttpStatus.OK);
        }


        if (accept != null && accept.contains("application/xml")) {
            return new ResponseEntity<String>(objectMapper.readValue("<null></null>", String.class), HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    public ResponseEntity<Void> initAuth(@ApiParam(value = "Yksilöivä viestitunniste, jota käytetään virhetilanteiden selvityksessä. Jokainen viestiä käsittelevä järjestelmä luo oman messageId-arvonsa" ,required=true) @RequestHeader(value="X-Message-Id", required=true) String xMessageId,
        @ApiParam(value = "Yksilöivä viestiketjutunniste, jota käytetään virhetilanteiden selvityksessä. Arvon määrittelee alkuperäinen kutsujataho ja se säilytetään läpi käsittelyprosessin." ,required=true) @RequestHeader(value="X-Transaction-Id", required=true) String xTransactionId,
        @ApiParam(value = "Tekstuaalinen tunniste siitä, että mikä järjestelmä on alunperin kutsun luonut. Käytetään virhetilanteiden selvityksessä." ,required=true) @RequestHeader(value="X-Initiator", required=true) String xInitiator,
        @ApiParam(value = "Yritystunnus, jolle autentikaatioprosessi aloitetaan" ,required=true )  @Valid @RequestBody AccountId body,
        @RequestHeader(value = "Accept", required = false) String accept) throws Exception {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
