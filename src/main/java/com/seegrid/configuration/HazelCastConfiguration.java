

package com.seegrid.configuration;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
This class contains the hazelcast configuration
starts the server at port 5701 and tries to starts till 5721 if some of those ports
are occupied. Creates an LRU Cache that never expires and it is shared across all the instances
for high availability.
 */
public class HazelCastConfiguration {
    @Bean
    public HazelcastInstance hazelCastInstance(){
        Config config = new Config();
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);//start at 5701
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();//join the network of clusters
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig()
                .addMember("localhost").setEnabled(true);//add all instances of localhost as cluster
        config.setInstanceName("hazelcast-instance")        // hazel case instance name
                .addMapConfig(
                        new MapConfig()                     // create map
                                .setName("deck")//in memory map containing deck
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(-1));     // cache will be available until it will remove manually. less then 0 means never expired.
        return Hazelcast.newHazelcastInstance(config);

    }
}


