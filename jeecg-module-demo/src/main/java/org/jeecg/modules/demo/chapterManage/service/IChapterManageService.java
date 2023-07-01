package org.jeecg.modules.demo.chapterManage.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManage;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用章管理
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */
public interface IChapterManageService extends IService<ChapterManage> {


	public String queryChapterNumber(String processInstanceId);


	String queryDepartmentByCode(String orgCode);

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(ChapterManage chapterManage,List<ChapterManageFile> chapterManageFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(ChapterManage chapterManage,List<ChapterManageFile> chapterManageFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
