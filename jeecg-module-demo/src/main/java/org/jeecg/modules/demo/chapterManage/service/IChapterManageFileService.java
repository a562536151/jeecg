package org.jeecg.modules.demo.chapterManage.service;

import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 用章管理附件
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */
public interface IChapterManageFileService extends IService<ChapterManageFile> {

	public List<ChapterManageFile> selectByMainId(String mainId);
}
