package uz.pdp.springlesson4restsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.service.IncomeService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/income")
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest request){
        return ResponseEntity.ok(incomeService.getAll(request));
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id, HttpServletRequest request){
        ApiResponse one = incomeService.getOne(id, request);
        return ResponseEntity.status(one.isSuccess()?200:404).body(one);
    }
}
