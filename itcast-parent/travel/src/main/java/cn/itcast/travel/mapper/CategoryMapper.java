package cn.itcast.travel.mapper;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> findAllCategory();
}
