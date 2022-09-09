package com.lycoris.mapper;

import com.lycoris.domain.Message;
import com.lycoris.dto.MessageInfo;

import java.util.List;

/**
* @author DELL
* @description 针对表【message(消息信息表)】的数据库操作Mapper
* @createDate 2022-08-17 15:28:40
* @Entity com.lycoris.domain.Message
*/
public interface MessageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    List<Message> selectAll(Message message);

    List<MessageInfo> selectAllByMessageInfo(MessageInfo messageInfo);


    List<MessageInfo> selectAllByPage(Message message);

    int selectCountByStatus(Integer status);
}
