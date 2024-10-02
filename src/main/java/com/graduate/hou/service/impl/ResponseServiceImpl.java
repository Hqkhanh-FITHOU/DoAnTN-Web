package com.graduate.hou.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.graduate.hou.dto.ResponseDTO;
import com.graduate.hou.entity.Response;
import com.graduate.hou.entity.Review;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.ResponseRepository;
import com.graduate.hou.repository.ReviewRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.ResponseService;

@Service
public class ResponseServiceImpl implements ResponseService{

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Response> getAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public Response creatResponse(ResponseDTO responseDTO) {
        Response response = new Response();
        Optional<Review> reviewOptional = reviewRepository.findById(responseDTO.getReviewId());
        Optional<User> userOptional = usersRepository.findById(responseDTO.getUserId());
        response.setReponseContent(responseDTO.getReponseContent());
        response.setUser(userOptional.get());
        response.setReview(reviewOptional.get());
        return responseRepository.save(response);
    }

    @Override
    public Response updatResponse(Long id, ResponseDTO responseDTO) {
        Optional<Response> responseOptional = responseRepository.findById(responseDTO.getReponseId());
        Response response = responseOptional.get();
        Optional<Review> reviewOptional = reviewRepository.findById(responseDTO.getReviewId());
        Optional<User> userOptional = usersRepository.findById(responseDTO.getUserId());
        response.setReponseContent(responseDTO.getReponseContent());
        response.setUser(userOptional.get());
        response.setReview(reviewOptional.get());
        return responseRepository.save(response);
    }

    @Override
    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }

}
