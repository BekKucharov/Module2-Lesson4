package uz.pdp.springlesson4restsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.payload.OutcomeDto;
import uz.pdp.springlesson4restsecurity.service.OutcomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {
    @Autowired
    OutcomeService outcomeService;

    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest request){
        return ResponseEntity.ok(outcomeService.getAll(request));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request){
        ApiResponse one = outcomeService.getOne(id, request);
        return ResponseEntity.status(one.isSuccess()?200:404).body(one);
    }
    @PostMapping
    public HttpEntity<?> add(@RequestBody OutcomeDto outcomeDto, HttpServletRequest request){
        ApiResponse add = outcomeService.add(outcomeDto, request);
        return ResponseEntity.status(add.isSuccess()?201:409).body(add);
    }
}
