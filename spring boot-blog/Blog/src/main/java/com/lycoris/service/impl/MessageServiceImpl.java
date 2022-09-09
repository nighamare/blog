package com.lycoris.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Message;
import com.lycoris.dto.MessageInfo;
import com.lycoris.mapper.MessageMapper;
import com.lycoris.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 16:02
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Override
    public void insertSelective(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public void updateByPrimaryKeySelective(Message message) {
        messageMapper.updateByPrimaryKeySelective(message);

    }

    @Override
    public void deleteByIds(Long[] longs) {
        for (Long aLong : longs) {
            messageMapper.deleteByPrimaryKey(aLong);
        }

    }

    @Override
    public void deleteById(Long aLong) {
        messageMapper.deleteByPrimaryKey(aLong);
    }

    @Override
    public PageInfo<Message> selectByPageSelective(int pageNum, int pageSize, Message message) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(messageMapper.selectAll(message));
    }

    @Override
    public Message selectById(Long aLong) {

        return messageMapper.selectByPrimaryKey(aLong);
    }

    @Override
    public Message selectOneByCondition(Message message) {
        return null;
    }


    @Override
    public List<MessageInfo> selectAllMessageInfo(MessageInfo messageInfo) {
        return messageMapper.selectAllByMessageInfo(messageInfo);
    }

    @Override
    public PageInfo<MessageInfo> getListByPage(int pageNum, int pageSize, Message message) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(messageMapper.selectAllByPage(message));
    }

    @Override
    public int selectCountByStatus(Integer status) {
        return messageMapper.selectCountByStatus(status);
    }
}
