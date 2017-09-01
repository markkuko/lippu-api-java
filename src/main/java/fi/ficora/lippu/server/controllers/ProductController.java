package fi.ficora.lippu.server.controllers;

import fi.ficora.lippu.query.ProductQueryResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @RequestMapping(method= RequestMethod.GET)
    public ProductQueryResponse availableProducts() {
        return new ProductQueryResponse();
    }
}
