package cn.itcast.travel.controller;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("category")
public class CategoryController {
@Autowired
    private CategoryService categoryService;
@RequestMapping("findAllCategory")
    @ResponseBody
    public String findAllCategory() throws JsonProcessingException {
    //调用业务逻辑层获取分类列表的json数据
    String jsonData =null;
    //定义json转换对象
    ObjectMapper objectMapper = new ObjectMapper();
    //定义返回数据对象
    ResultInfo resultInfo = null;
    try {
        jsonData = categoryService.findAllCategory();
    } catch (Exception e) {
        e.printStackTrace();
        resultInfo = new ResultInfo(false,null,"服务器正忙。。");
        jsonData = objectMapper.writeValueAsString(resultInfo);
    }
    return jsonData;
}
}
