package mcp.mcphub.useful.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MercadoLivreMcpController {

    @GetMapping
    public String getMercadoLivreMcp() {
        return "Mercado Livre Mcp";
    }
}
