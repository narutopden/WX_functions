package com.payment.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.payment.demo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Blue_Sky 7/23/21
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where openid = #{openid}")
    User findByOpenId(@Param("openid") String openId);

    @Insert("INSERT INTO `wechatPay`.`user`( `openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`) " +
            "VALUES (#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveUser(User user);
}
