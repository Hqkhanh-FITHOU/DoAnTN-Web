package com.graduate.hou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.graduate.hou.entity.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long>{

}
