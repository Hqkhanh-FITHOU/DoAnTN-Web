package com.graduate.hou.service;

import java.util.List;

import com.graduate.hou.dto.request.ResponseDTO;
import com.graduate.hou.entity.Response;

public interface ResponseService {

    List<Response> getAllResponses();

    Response creatResponse(ResponseDTO responseDTO);

    Response updatResponse(Long id, ResponseDTO responseDTO);

    void deleteResponse(Long id);
}
