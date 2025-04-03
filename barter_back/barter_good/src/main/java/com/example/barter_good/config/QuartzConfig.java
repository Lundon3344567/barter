package com.example.barter_good.config;

import com.example.barter_good.common.aop.RedisTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chengke
 * @version 1.0
 * @className QuartzConfig
 * @description 定时任务配置类
 * @date 2021/11/23 9:44
 */
@Configuration
public class QuartzConfig {

    private static final String LIKE_TASK_IDENTITY = "LikeTaskQuartz";

    @Bean
    public JobDetail quartzDetail(){
        return JobBuilder.newJob(RedisTask.class).withIdentity(LIKE_TASK_IDENTITY).storeDurably().build();
    }

    @Bean
    public SimpleTrigger quartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withIntervalInHours(1).repeatForever();

        return TriggerBuilder.newTrigger().forJob(quartzDetail()).withIdentity(LIKE_TASK_IDENTITY).withSchedule(scheduleBuilder).build();
    }

}