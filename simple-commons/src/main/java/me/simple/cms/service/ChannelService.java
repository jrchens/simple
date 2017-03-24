package me.simple.cms.service;

import java.util.List;

import me.simple.commons.entity.Channel;
import me.simple.commons.entity.ChannelNode;
import me.simple.commons.entity.Pagination;

public interface ChannelService {

    Channel saveChannel(Channel channel);

    int removeChannel(String channelName);

    /**
     * just update channel viewname
     * 
     * @param channel
     * @return
     */
    int updateChannel(Channel channel);

    boolean checkChannelExists(String channelName);

    Channel getChannel(String channelName);

    /**
     * query by viewname or channelName
     * 
     * @param channel
     * @param pagination
     * @return
     */
    List<Channel> queryChannel(Channel channel, Pagination<Channel> pagination);

    
    List<Channel> queryChannelChildren(String parentName);

    List<ChannelNode> queryUserChannelChain(String owner, String channelName);
    List<ChannelNode> queryUserChannelTree(String owner, String channelName);

}