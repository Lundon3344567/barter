package com.example.barter_authority.config;

import com.example.barter_authority.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author chengke
 * @version 1.0
 * @className WebSocketConfig
 * @description WebSocket配置类
 * @date 2021/12/11 19:29
 */

//开启STOMP协议来传输基于代理的消息
@EnableAsync
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("{tokenHead}")
    private String tokenHead;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 添加endpoint，网页可以通过websocket连接上服务（配置websocket的服务地址），可以指定是否使用socketJS
     * @param registry registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //setAllowedOrigins("*")：允许跨域
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

                //判断是否为连接，如果是，需要获取token，并且设置用户对象
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    if(!StringUtils.isEmpty(token)){
                        String authToken = token.substring(tokenHead.length());
                        String username = JwtUtil.getUserName(authToken);
                        //token中存在用户名
                        if(!StringUtils.isEmpty(username)){
                            //登录
                            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                            //验证token是否有效，有效则重新设置用户对象
                            if(jwtUtil.validateToken(authToken,userDetails)){
                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     * @param registry registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //配置代理域，配置代理目的地前缀为/queue，可以在配置域上向客户端推送消息
        registry.enableSimpleBroker("/queue");

        //推送消息前缀，第一个是点对点，第二个是广播   客户端订阅地址的前缀信息
        //registry.enableSimpleBroker("/user/","/topic/");
//        registry.setUserDestinationPrefix("/user");
//
//        registry.setApplicationDestinationPrefixes("/app");
    }
}