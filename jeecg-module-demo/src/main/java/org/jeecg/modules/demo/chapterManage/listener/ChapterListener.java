/*
package org.jeecg.modules.demo.chapterManage.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManage;
import org.jeecg.modules.demo.chapterManage.service.IChapterManageService;
import org.jeecg.modules.demo.projecthead.entity.ProjectProjectHead;
import org.jeecg.modules.demo.projecthead.service.IProjectProjectHeadService;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ChapterListener implements ExecutionListener {


    public static IProjectProjectHeadService projectProjectHeadService;
    public static IChapterManageService chapterManageService;
    static {
        chapterManageService = SpringContextUtils.getBean(IChapterManageService.class);
        projectProjectHeadService = SpringContextUtils.getBean(IProjectProjectHeadService.class);
    }
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        log.info("用章申请单流程监听开始");
        String changeId = delegateExecution.getProcessBusinessKey();
        ChapterManage chapterManages = chapterManageService.getOne(
                new LambdaQueryWrapper<ChapterManage>().eq(ChapterManage::getId, changeId)
                        .eq(ChapterManage::getIzDeleted, "0"));

        ProjectProjectHead projectProjectHead =  projectProjectHeadService.getOne(
                new LambdaQueryWrapper<ProjectProjectHead>().eq(ProjectProjectHead::getId, chapterManages.getProjectHeadId()));

        if(StringUtils.isBlank(projectProjectHead.getChapterAuditStatus())){
            projectProjectHead.setChapterAuditStatus(delegateExecution.getProcessInstanceId());
        }else {
            projectProjectHead.setChapterAuditStatus(projectProjectHead.getAuditStatus()+","+delegateExecution.getProcessInstanceId());
        }




        projectProjectHeadService.updateById(projectProjectHead);

        log.info("用章申请单流程监听结束");

    }
}
*/
