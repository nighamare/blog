package com.lycoris.service;

import com.github.pagehelper.PageInfo;
import com.lycoris.domain.Message;
import com.lycoris.dto.MessageInfo;

import java.util.List;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/22 16:01
 */
public interface MessageService extends BasicService<Message,Long> {
    List<MessageInfo> selectAllMessageInfo(MessageInfo messageInfo);

    PageInfo<MessageInfo> getListByPage(int pageNum, int pageSize, Message message);

    int selectCountByStatus(Integer status);

}
