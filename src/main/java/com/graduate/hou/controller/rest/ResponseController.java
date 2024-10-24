package com.graduate.hou.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.hou.dto.request.ResponseDTO;
import com.graduate.hou.entity.Response;
import com.graduate.hou.service.impl.ResponseServiceImpl;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/response")
public class ResponseController {
    @Autowired
    private ResponseServiceImpl responseService;

    @PostMapping("/new")
    public ResponseEntity<Response> createNewResponse(@RequestBody ResponseDTO responseDTO) {
        Response response = responseService.creatResponse(responseDTO);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Response>> getAllResponse(){
        List<Response> responses = responseService.getAllResponses();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateResponse (@PathVariable Long id, @RequestBody ResponseDTO responseDTO){
        Response response = responseService.updatResponse(id,responseDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    String deleteResponse(@PathVariable Long id){
        responseService.deleteResponse(id);
        return "Xóa thành công";
    }
}
