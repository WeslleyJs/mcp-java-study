package mcp.mcphub.useful.controller;

import mcp.mcphub.useful.McpServerUsefulToolsApplication;
import mcp.mcphub.useful.model.bo.WeatherResponse;
import mcp.mcphub.useful.service.WeatherService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")  // Prefixo para todos os endpoints deste controlador
public class WeatherMcpController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ToolCallback toUpperCaseTool;

    @GetMapping("/")
    public ResponseEntity<?> helloWorld() {
        return ResponseEntity.ok().body("Hello World");
    }

    @PostMapping("/toUpperCase")
    public String convertToUpperCase(@RequestBody String input) {
        McpServerUsefulToolsApplication.TextInput textInput = new McpServerUsefulToolsApplication.TextInput(input);
        return (String) toUpperCaseTool.call(String.valueOf(textInput));
    }

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        try {
            WeatherResponse response = weatherService.getWeather(city);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}