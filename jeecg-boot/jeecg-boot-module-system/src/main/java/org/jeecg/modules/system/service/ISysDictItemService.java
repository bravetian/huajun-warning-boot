package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

import org.jeecg.modules.system.entity.SysDictItem;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author zhangweijian
 * @since 2018-12-28
 */
public interface ISysDictItemService extends IService<SysDictItem> {
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
