package com.qtc.ecommerce_demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // @Configuration: 这是Spring配置类
    // CorsConfig: 类名，表示跨域配置
    // implements WebMvcConfigurer: 实现WebMvcConfigurer接口
    @Override
    public void addCorsMappings(CorsRegistry registry) {//CorsRegistry registry- Spring提供的跨域注册器
        // 实现WebMvcConfigurer接口的方法
        // 用于配置CORS跨域规则
        registry.addMapping("/**")
                // 匹配所有路径，所有请求都应用这个跨域规则
                // "/**" 表示所有URL
                //如果本地安装前后端 可以直接使用http://localhost:8081
                .allowedOriginPatterns(//在qtc的主机运行后端、其他主机运行前端的情况下 允许以下主机ip访问我的ip,且需要在对方主机上的前端代码里把base_url改为qtc的主机地址
                        "http://localhost:[*]",
                        "http://127.0.0.1:[*]",
                        "http://192.168.*.*:[*]",
                        "http://192.168.43.6:[*]", //  qtc与lxy连接热点minomax后 lxy的主机地址
                        "http://192.168.43.7:[*]", //  qtc与lxy连接热点minomax后 qtc的主机地址
                        "http://10.163.*.*:[*]",
                        "http://10.91.243.22:[*]",  // qtc与grx连接热点minomax后 qtc的主机地址
                        "http://10.91.243.244:[*]", // qtc与grx连接热点minomax后 grx的主机地址

                        // 如果需要，可以加整个子网
                        "http://10.91.243.*:[*]",   // 子网
                        "null"// file://协议
                        // 定义哪些域名/IP可以访问本后端
                        // [*] 表示匹配任意端口
                        // *.* 表示匹配任意IP段
                        // null 允许文件协议（本地HTML文件）
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 前端可以使用的HTTP方法
                // OPTIONS 是CORS预检请求必须的：预检请求是浏览器在发送"复杂"跨域请求前，自动发送的一个 OPTIONS请求，用于询问服务器是否允许这个跨域请求。
                .allowedHeaders("*")
                // 允许所有请求头
                // * 通配符表示所有
                .allowCredentials(true)
                // 允许发送Cookie、认证头等信息 允许携带凭证
                .maxAge(3600);
        // 预检请求结果缓存3600秒（1小时）
        // 在此期间内相同的请求不需要再次预检
    }

   /* @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // ✅ 正确的模式写法
        config.addAllowedOriginPattern("http://192.168.*.*:[*]");
        config.addAllowedOriginPattern("http://127.0.0.1:*");
        config.addAllowedOrigin("null"); // 显式允许 origin 为 null 的情况

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    */
}