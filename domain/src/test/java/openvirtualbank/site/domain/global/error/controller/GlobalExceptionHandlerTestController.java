package openvirtualbank.site.domain.global.error.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import openvirtualbank.site.domain.global.error.dto.RequestDto;

@RestController
@RequestMapping("/test")
public class GlobalExceptionHandlerTestController {
    @PostMapping("/valid")
    public void testValid(@RequestBody @Valid RequestDto dto) {
    }

    @PostMapping("/missing-body")
    public void testMissingBody(@RequestBody RequestDto dto) {
    }

    @GetMapping("/missing-param")
    public void testMissingParam(@RequestParam String name) {
    }
}
