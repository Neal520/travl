package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.mapper.CategoryMapper;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.utils.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public String findAllCategory() throws JsonProcessingException {
        //定义返回的json
        String jsonData = null;
        Jedis jedis = null;//如果jedis访问不了，会发生异常
        try {
            //1.从redis缓存数据库取
            jsonData = (String) redisTemplate.opsForValue().get("categoryList");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断有效性，是否空
        if(jsonData==null || "".equals(jsonData)) {
            //为空，去数据库获取泛型集合分类数据
            List<Category> categoryList = categoryMapper.findAllCategory();
            //将集合对象转换json
            jsonData = new ObjectMapper().writeValueAsString(categoryList);
            try {
                //将json写入redis缓存数据库中
                redisTemplate.opsForValue().set("categoryList", jsonData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonData;
    }

}
