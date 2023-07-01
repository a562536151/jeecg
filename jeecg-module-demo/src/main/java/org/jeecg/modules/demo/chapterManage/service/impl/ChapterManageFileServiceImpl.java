package org.jeecg.modules.demo.chapterManage.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import org.jeecg.modules.demo.chapterManage.mapper.ChapterManageFileMapper;
import org.jeecg.modules.demo.chapterManage.service.IChapterManageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangjian
 * @date 2023/05/08
 */
@Service
public class ChapterManageFileServiceImpl extends ServiceImpl<ChapterManageFileMapper, ChapterManageFile> implements IChapterManageFileService {

    @Autowired
    private ChapterManageFileMapper chapterManageFileMapper;

    @Override
    public List<ChapterManageFile> selectByMainId(String mainId) {
        return chapterManageFileMapper.selectByMainId(mainId);
    }

}



