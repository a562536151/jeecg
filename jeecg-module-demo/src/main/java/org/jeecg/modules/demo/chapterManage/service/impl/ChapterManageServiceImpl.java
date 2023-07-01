package org.jeecg.modules.demo.chapterManage.service.impl;

import org.jeecg.modules.demo.chapterManage.entity.ChapterManage;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import org.jeecg.modules.demo.chapterManage.mapper.ChapterManageFileMapper;
import org.jeecg.modules.demo.chapterManage.mapper.ChapterManageMapper;
import org.jeecg.modules.demo.chapterManage.service.IChapterManageService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 用章管理
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 */
@Service
public class ChapterManageServiceImpl extends ServiceImpl<ChapterManageMapper, ChapterManage> implements IChapterManageService {

	@Autowired
	private ChapterManageMapper chapterManageMapper;
	@Autowired
	private ChapterManageFileMapper chapterManageFileMapper;

	@Override
	public String queryChapterNumber(String processInstanceId) {
		return 	chapterManageMapper.queryChapterNumber(processInstanceId);
	}

	@Override
	public String queryDepartmentByCode(String orgCode) {
		return chapterManageMapper.queryDepartmentByCode(orgCode);
	}

	@Override
	@Transactional
	public void saveMain(ChapterManage chapterManage, List<ChapterManageFile> chapterManageFileList) {
		chapterManageMapper.insert(chapterManage);
		if(chapterManageFileList!=null && chapterManageFileList.size()>0) {
			for(ChapterManageFile entity:chapterManageFileList) {
				//外键设置
				entity.setChapterId(chapterManage.getId());
				chapterManageFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(ChapterManage chapterManage,List<ChapterManageFile> chapterManageFileList) {
		chapterManageMapper.updateById(chapterManage);
		
		//1.先删除子表数据
		chapterManageFileMapper.deleteByMainId(chapterManage.getId());
		
		//2.子表数据重新插入
		if(chapterManageFileList!=null && chapterManageFileList.size()>0) {
			for(ChapterManageFile entity:chapterManageFileList) {
				//外键设置
				entity.setChapterId(chapterManage.getId());
				chapterManageFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		chapterManageFileMapper.deleteByMainId(id);
		chapterManageMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			chapterManageFileMapper.deleteByMainId(id.toString());
			chapterManageMapper.deleteById(id.toString());
		}
	}
	
}
