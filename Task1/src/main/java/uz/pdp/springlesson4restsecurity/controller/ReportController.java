package uz.pdp.springlesson4restsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springlesson4restsecurity.payload.LoginDto;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.ok("Report Sent");
    }
    @PostMapping("/test")
    public HttpEntity<?> add(@RequestBody LoginDto dto){
        System.out.println(dto);
        return ResponseEntity.ok(dto);
    }
}
