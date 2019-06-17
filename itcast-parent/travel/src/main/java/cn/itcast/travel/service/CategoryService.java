package cn.itcast.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CategoryService {
    String findAllCategory() throws JsonProcessingException;
}
