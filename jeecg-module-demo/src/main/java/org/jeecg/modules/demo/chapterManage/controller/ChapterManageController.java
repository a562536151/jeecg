/*
package org.jeecg.modules.demo.chapterManage.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.demo.precast.entity.ProjectPreCast;
import org.jeecg.modules.demo.projecthead.entity.ProjectProjectHead;
import org.jeecg.modules.demo.projecthead.service.impl.ProjectProjectHeadServiceImpl;
import org.jeecg.modules.demo.utils.ChapterApplyNumberUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManageFile;
import org.jeecg.modules.demo.chapterManage.entity.ChapterManage;
import org.jeecg.modules.demo.chapterManage.vo.ChapterManagePage;
import org.jeecg.modules.demo.chapterManage.service.IChapterManageService;
import org.jeecg.modules.demo.chapterManage.service.IChapterManageFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 */
/**
 * @Description: 用章管理
 * @Author: jeecg-boot
 * @Date:   2023-05-04
 * @Version: V1.0
 *//*

@Api(tags="用章管理")
@RestController
@RequestMapping("/chapterManage/chapterManage")
@Slf4j
public class ChapterManageController {
	@Autowired
	private IChapterManageService chapterManageService;
	@Autowired
	private IChapterManageFileService chapterManageFileService;

	@Autowired
	private ChapterApplyNumberUtil chapterApplyNumberUtil;
	@Autowired
	private ProjectProjectHeadServiceImpl projectProjectHeadService;
	*/
/**
	 * 分页列表查询
	 *
	 * @param chapterManage
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 *//*

	@AutoLog(value = "用章管理-分页列表查询")
	@ApiOperation(value="用章管理-分页列表查询", notes="用章管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ChapterManage chapterManage,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ChapterManage> queryWrapper = QueryGenerator.initQueryWrapper(chapterManage, req.getParameterMap());
		if (chapterManage.getProjectHeadId() != null) {
			queryWrapper.eq("PROJECT_HEAD_ID", chapterManage.getProjectHeadId());
		}else{
			return Result.error("无法查询到用章信息");
		}
		Page<ChapterManage> page = new Page<ChapterManage>(pageNo, pageSize);
		IPage<ChapterManage> pageList = chapterManageService.page(page, queryWrapper);
		return Result.OK(pageList);
	}



	 @AutoLog(value = "用章管理-分页列表查询")
	 @ApiOperation(value="用章管理-分页列表查询", notes="用章管理-分页列表查询")
	 @GetMapping(value = "/listAll")
	 public Result<?> queryPageListAll(ChapterManage chapterManage,
									@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									HttpServletRequest req) {
		 QueryWrapper<ChapterManage> queryWrapper = QueryGenerator.initQueryWrapper(chapterManage, req.getParameterMap());
		 Page<ChapterManage> page = new Page<ChapterManage>(pageNo, pageSize);
		 IPage<ChapterManage> pageList = chapterManageService.page(page, queryWrapper);
		 return Result.OK(pageList);
	 }


	 @AutoLog(value = "用章管理-查询主表信息")
	 @ApiOperation(value="用章管理-查询主表信息", notes="用章管理-查询主表信息")
	 @PostMapping(value = "/queryProjectHead")
	 public Result<?> queryProjectHead(@RequestBody ChapterManagePage chapterManagePage) {
		 ProjectProjectHead  projectProjectHead = projectProjectHeadService.getById(chapterManagePage.getProjectHeadId());

		 return Result.OK("获取成功！",projectProjectHead);
	 }
	
	*/
/**
	 *   添加
	 *
	 * @param chapterManagePage
	 * @return
	 *//*

	@AutoLog(value = "用章管理-添加")
	@ApiOperation(value="用章管理-添加", notes="用章管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ChapterManagePage chapterManagePage) {
		ChapterManage chapterManage = new ChapterManage();

		chapterManagePage.setIzDeleted("0");
		BeanUtils.copyProperties(chapterManagePage, chapterManage);
		if(CollectionUtils.isNotEmpty(chapterManagePage.getChapterManageFileList())){
			chapterManagePage.getChapterManageFileList().stream().forEach(item -> item.setIzDeleted("0"));
		}
		String code = chapterManage.getSysOrgCode();
		if(!StringUtils.isEmpty(code)){
			if (code.length()<10){
				chapterManage.setApplyDepartment(chapterManageService.queryDepartmentByCode(code));
			}else {
				chapterManage.setApplyDepartment(chapterManageService.queryDepartmentByCode(code.substring(0, 10)));
			}
		}
		chapterManageService.saveMain(chapterManage, chapterManagePage.getChapterManageFileList());
		return Result.OK("添加成功！",chapterManage);
	}





	 @PostMapping(value = "/queryMainId")
	 public Result<?> queryMainId(@RequestParam(name="id",required=true) String id) {
		 ProjectProjectHead projectProjectHead =null;
		 String mainId = null;
		 ChapterManage chapterManage = chapterManageService.getById(id);
		 if(!ObjectUtils.isEmpty(chapterManage)){
			 projectProjectHead = projectProjectHeadService.getById(chapterManage.getProjectHeadId());
		 }
		 if(!ObjectUtils.isEmpty(projectProjectHead)){
			 mainId = projectProjectHead.getId();
		 }
		 return Result.OK("申请成功！",mainId);
	 }




	 @AutoLog(value = "用章管理-流程查询")
	 @ApiOperation(value="用章管理-流程查询", notes="用章管理-流程查询")
	 @GetMapping(value = "/queryProcess")
	 public Result<?> queryProcess(@RequestParam(name="businessKey",required=true) String businessKey) {
		 String projectHeadId = null;
		ChapterManage chapterManage = chapterManageService.getById(businessKey);
		if(!ObjectUtils.isEmpty(chapterManage)) {
			projectHeadId = chapterManage.getProjectHeadId();
		}else {
			return Result.error("业务id不存在");
		}
		 ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		 RuntimeService runtimeService = processEngine.getRuntimeService();
		 List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
				 .processDefinitionKey("process1684828083045")
				 .active()
				 .list();
		 List<String> businessKeys = new ArrayList<>();
		 for(ProcessInstance processInstance : processInstances){
			 String key = processInstance.getBusinessKey();
			 businessKeys.add(key);
		 }

		 for(String key : businessKeys){
			 ChapterManage chapterManages = chapterManageService.getById(key);
			 if(ObjectUtils.isNotEmpty(chapterManages)&&chapterManages.getProjectHeadId().equals(projectHeadId)){
				 return Result.error(500,"当前流程正在运行");
			 }
		 }

		 return Result.OK().success("当前无流程在运行");
	 }


	 //审批获取用章号
	 @PostMapping(value = "/addChapterNumber")
	 public Result<?> addChapterNumber() {
		 String number = chapterApplyNumberUtil.generateCode();

		 if(StringUtils.isEmpty(number)){
			 return Result.error("用章申请号为空或达到最大");
		 }

		 return Result.OK("申请成功！",number);
	 }



	
	*/
/**
	 *  编辑
	 *
	 * @param chapterManagePage
	 * @return
	 *//*

	@AutoLog(value = "用章管理-编辑")
	@ApiOperation(value="用章管理-编辑", notes="用章管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ChapterManagePage chapterManagePage) {
		ChapterManage chapterManage = new ChapterManage();
		BeanUtils.copyProperties(chapterManagePage, chapterManage);
		ChapterManage chapterManageEntity = chapterManageService.getById(chapterManage.getId());
		if(chapterManageEntity==null) {
			return Result.error("未找到对应数据");
		}
		if(CollectionUtils.isNotEmpty(chapterManagePage.getChapterManageFileList())){
			chapterManagePage.getChapterManageFileList().stream().forEach(item -> item.setIzDeleted("0"));
		}
		chapterManageService.updateMain(chapterManage, chapterManagePage.getChapterManageFileList());
		return Result.OK("编辑成功!");
	}
	
	*/
/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 *//*

	@AutoLog(value = "用章管理-通过id删除")
	@ApiOperation(value="用章管理-通过id删除", notes="用章管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ChapterManage entity = chapterManageService.getById(id);
		if (entity == null) {
			return Result.error("该记录不存在或已被删除！");
		}
		chapterManageService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	*/
/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 *//*

	@AutoLog(value = "用章管理-批量删除")
	@ApiOperation(value="用章管理-批量删除", notes="用章管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.chapterManageService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	*/
/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 *//*

	@AutoLog(value = "用章管理-通过id查询")
	@ApiOperation(value="用章管理-通过id查询", notes="用章管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ChapterManage chapterManage = chapterManageService.getById(id);
		if(chapterManage==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(chapterManage);

	}
	
	*/
/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 *//*

	@AutoLog(value = "用章管理附件通过主表ID查询")
	@ApiOperation(value="用章管理附件主表ID查询", notes="用章管理附件-通主表ID查询")
	@GetMapping(value = "/queryChapterManageFileByMainId")
	public Result<?> queryChapterManageFileListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ChapterManageFile> chapterManageFileList = chapterManageFileService.selectByMainId(id);
		return Result.OK(chapterManageFileList);
	}

    */
/**
    * 导出excel
    *
    * @param request
    * @param chapterManage
    *//*

    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChapterManage chapterManage) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ChapterManage> queryWrapper = QueryGenerator.initQueryWrapper(chapterManage, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ChapterManage> queryList = chapterManageService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ChapterManage> chapterManageList = new ArrayList<ChapterManage>();
      if(oConvertUtils.isEmpty(selections)) {
          chapterManageList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          chapterManageList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ChapterManagePage> pageList = new ArrayList<ChapterManagePage>();
      for (ChapterManage main : chapterManageList) {
          ChapterManagePage vo = new ChapterManagePage();
          BeanUtils.copyProperties(main, vo);
          List<ChapterManageFile> chapterManageFileList = chapterManageFileService.selectByMainId(main.getId());
          vo.setChapterManageFileList(chapterManageFileList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "用章管理列表");
      mv.addObject(NormalExcelConstants.CLASS, ChapterManagePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("用章管理数据", "导出人:"+sysUser.getRealname(), "用章管理"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    */
/**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    *//*

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ChapterManagePage> list = ExcelImportUtil.importExcel(file.getInputStream(), ChapterManagePage.class, params);
              for (ChapterManagePage page : list) {
                  ChapterManage po = new ChapterManage();
                  BeanUtils.copyProperties(page, po);
                  chapterManageService.saveMain(po, page.getChapterManageFileList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
*/
