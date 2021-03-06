package com.java.application;

import com.java.filters.AuthFilter;
import com.java.filters.Filters;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@EnableZuulProxy
public class FalshSaleZuulStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FalshSaleZuulStartApplication.class);
    }

    @Bean
    public Filters filters(){
        return new Filters();
    }

    @Bean
    public AuthFilter authFilter(){
        return new AuthFilter();
    }

    /**
     * Initializing Redisson
     */
    @Bean
    public RBloomFilter<Integer> rBloomFilter() throws IOException {
        Config conf = new Config();
        //conf = Config.fromYAML(RedissonConfiguration.class.getClassLoader().getResource("redisson-config.yml"));
        conf.useSentinelServers().addSentinelAddress("redis://192.168.100.128:26379")
                .addSentinelAddress("redis://192.168.100.128:26380")
                .addSentinelAddress("redis://192.168.100.128:26381")
                .setMasterName("mymaster")
//                .setCheckSentinelsList(false)
                .setReadMode(ReadMode.SLAVE);
        Redisson redisson = (Redisson) Redisson.create(conf);
        RBloomFilter<Integer> rBloomFilter = redisson.getBloomFilter("saleIds");
        return rBloomFilter;
    }

}
