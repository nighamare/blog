package com.lycoris.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/26 15:02
 */
@Mapper
public interface CronMapper {
    @Select("select cron from cron limit 1")
    String getCron();
    @Select("update cron set cron='0 0/30 * * * ?' cron where id= 1 ")
    void updateCron();
}
