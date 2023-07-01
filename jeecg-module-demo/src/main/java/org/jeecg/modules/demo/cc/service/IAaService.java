package org.jeecg.modules.demo.cc.service;

import org.jeecg.modules.demo.cc.entity.Aa;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;

import java.util.List;


/**
 * @author : [${USER}]
 * @version : [v1.0]
 * @createTime : [${DATE} ${TIME}]
 */
public interface IAaService extends IService<Aa> {
    List<Aa> listWithTree();

}
