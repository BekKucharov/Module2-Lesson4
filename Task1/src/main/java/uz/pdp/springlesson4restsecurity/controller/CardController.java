package uz.pdp.springlesson4restsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springlesson4restsecurity.entity.Card;
import uz.pdp.springlesson4restsecurity.payload.ApiResponse;
import uz.pdp.springlesson4restsecurity.payload.CardDto;
import uz.pdp.springlesson4restsecurity.security.JwtProvider;
import uz.pdp.springlesson4restsecurity.service.CardService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardService service;

    @PostMapping
    public HttpEntity<ApiResponse> add(@RequestBody CardDto dto, HttpServletRequest request){
        ApiResponse add = service.add(dto, request);
        return ResponseEntity.status(add.isSuccess()?201:409).body(add);
    }
    @GetMapping
    public HttpEntity<?> getAll(HttpServletRequest request){
        return ResponseEntity.ok(service.getCard(request));
    }
    @GetMapping("/{id}")
    public HttpEntity<Card> getOne(@PathVariable Integer id, HttpServletRequest request){
        return ResponseEntity.ok(service.getCard(id, request));
    }
    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> edit(@PathVariable Integer id, @RequestBody CardDto cardDto, HttpServletRequest request){
        ApiResponse edit = service.edit(id, cardDto, request);
        return ResponseEntity.status(edit.isSuccess() ? 202 : 409).body(edit);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> delete(@PathVariable Integer id, HttpServletRequest request){
        ApiResponse delete = service.delete(id, request);
        return ResponseEntity.status(delete.isSuccess()?200:404).body(delete);
    }
}
