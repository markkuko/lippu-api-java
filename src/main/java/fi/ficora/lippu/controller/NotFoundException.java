package fi.ficora.lippu.controller;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-09-29T16:37:02.463+03:00")

public class NotFoundException extends ApiException {
    private final int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
