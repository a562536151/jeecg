package org.jeecg.modules.demo.TechDataClassify.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.TechDataClassify.entity.TechDataClassify;
import org.jeecg.modules.demo.TechDataClassify.service.ITechDataClassifyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.TechDataClassify.vo.TechClassifyXlsx;
import org.jeecg.modules.demo.TechDataClassify.vo.TechDataClassifyPage;
import org.jeecg.modules.demo.TechHiExport.entity.TechHiExport;
import org.jeecg.modules.demo.TechHiExport.service.ITechHiExportService;
import org.jeecg.modules.demo.cc.entity.Aa;
import org.jeecg.modules.demo.cc.vo.AaPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 科学技术-多级学科
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Api(tags="科学技术-多级学科")
@RestController
@RequestMapping("/TechDataClassify/TechDataClassify")
@Slf4j
public class TechDataClassifyController extends JeecgController<TechDataClassify, ITechDataClassifyService> {
	@Autowired
	private ITechDataClassifyService TechDataClassifyService;
	@Autowired
	private ITechHiExportService techHiExportService;
	
	/**
	 * 分页列表查询
	 *
	 * @param TechDataClassify
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-分页列表查询")
	@ApiOperation(value="科学技术-多级学科-分页列表查询", notes="科学技术-多级学科-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TechDataClassify>> queryPageList(TechDataClassify TechDataClassify,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TechDataClassify> queryWrapper = QueryGenerator.initQueryWrapper(TechDataClassify, req.getParameterMap());
		queryWrapper.eq("iz_deleted","0");
		Page<TechDataClassify> page = new Page<TechDataClassify>(pageNo, pageSize);
		IPage<TechDataClassify> pageList = TechDataClassifyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}




	 @AutoLog(value = "科学技术-多级学科-树形查询")
	 @ApiOperation(value="科学技术-多级学科-树形查询", notes="科学技术-多级学科-树形查询")
	 @GetMapping(value = "/queryTreeList")
	 public Result<List<TechDataClassify>> queryTreeList(TechDataClassify TechDataClassify,
										   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										   HttpServletRequest req)  {
		 List<TechDataClassify> entity =  TechDataClassifyService.listWithTree(TechDataClassify.getName());
		 if(CollectionUtils.isEmpty(entity)){
			 return Result.error("未找到数据");
		 }
		 return Result.OK(entity);
	 }



/*	 @ApiOperation(value="科学技术-多级学科-111", notes="科学技术-多级学科-111")
	 @GetMapping(value = "/queryTreesList")
	 public Result<List<TechDataClassify>> queryTreesList(TechDataClassify TechDataClassify,
														 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
														 HttpServletRequest req)  {
		 List<TechDataClassify> entity =  TechDataClassifyService.listWithTree();
		 List<List<TechDataClassify>>   treePathList = TechDataClassifyService.getTreePathList(entity);



		 if(CollectionUtils.isEmpty(entity)){
			 return Result.error("未找到数据");
		 }
		 return Result.OK(entity);
	 }*/
	
	/**
	 *   添加
	 *
	 * @param TechDataClassify
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-添加")
	@ApiOperation(value="科学技术-多级学科-添加", notes="科学技术-多级学科-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TechDataClassify TechDataClassify) {
		TechDataClassifyService.addCategory(TechDataClassify);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param TechDataClassify
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-编辑")
	@ApiOperation(value="科学技术-多级学科-编辑", notes="科学技术-多级学科-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TechDataClassify TechDataClassify) {
		TechDataClassifyService.updateCategory(TechDataClassify);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-通过id删除")
	@ApiOperation(value="科学技术-多级学科-通过id删除", notes="科学技术-多级学科-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		TechDataClassifyService.deleteCategory(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-批量删除")
	@ApiOperation(value="科学技术-多级学科-批量删除", notes="科学技术-多级学科-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		List<String> idsList = Arrays.asList(ids.split(","));
		for(String id : idsList){
			TechDataClassifyService.deleteCategory(id);
		}
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "科学技术-多级学科-通过id查询")
	@ApiOperation(value="科学技术-多级学科-通过id查询", notes="科学技术-多级学科-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TechDataClassify> queryById(@RequestParam(name="id",required=true) String id) {
		TechDataClassify TechDataClassify = TechDataClassifyService.getById(id);
		if(TechDataClassify==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(TechDataClassify);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param TechDataClassify
    */
	@AutoLog(value = "科学技术-多级学科-导出excel")
	@ApiOperation(value="科学技术-多级学科-导出excel", notes="科学技术-多级学科-导出excel")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TechDataClassify TechDataClassify) {
		List<TechDataClassify> entity =  TechDataClassifyService.listWithTree(TechDataClassify.getName());
		List<List<TechDataClassify>>   treePathList = TechDataClassifyService.getTreePathList(entity);
		List<TechClassifyXlsx> techClassifyXlsxList = treePathList.stream().map(item ->{
			TechClassifyXlsx classifyXlsx = new TechClassifyXlsx();
			if(item.size()>=1) {
				classifyXlsx.setNameFirst(item.get(0).getName());
				classifyXlsx.setNameFirstCode(item.get(0).getCode());
			}
			if(item.size()>=2) {
				classifyXlsx.setNameSecond(item.get(1).getName());
				classifyXlsx.setNameFirstCode(item.get(1).getCode());
			}
			if(item.size()>=3) {
				classifyXlsx.setNameThird(item.get(2).getName());
				classifyXlsx.setNameThirdCode(item.get(2).getCode());
			}
			return classifyXlsx;
		}).collect(Collectors.toList());

		// Step.1 组装查询条件查询数据
		QueryWrapper<TechDataClassify> queryWrapper = QueryGenerator.initQueryWrapper(TechDataClassify, request.getParameterMap());
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		TechHiExport techHiExport = new TechHiExport();
		techHiExport.setCreateTime(new Date());
		if(sysUser!=null) {
			techHiExport.setExportPerson(Optional.ofNullable(sysUser.getRealname()).orElse(""));
		}
		techHiExport.setFileName("科学技术-多级学科");
		techHiExportService.save(techHiExport);

		//Step.2 获取导出数据
		List<TechDataClassify> queryList = TechDataClassifyService.list(queryWrapper);
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<TechDataClassify> TechDataClassifyList = new ArrayList<TechDataClassify>();
		if(oConvertUtils.isEmpty(selections)) {
			TechDataClassifyList = queryList;
		}else {
			List<String> selectionList = Arrays.asList(selections.split(","));
			TechDataClassifyList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		}

/*		// Step.3 组装pageList
		List<TechDataClassifyPage> pageList = new ArrayList<TechDataClassifyPage>();
		for (TechDataClassify main : TechDataClassifyList) {
			TechDataClassifyPage vo = new TechDataClassifyPage();
			BeanUtils.copyProperties(main, vo);
			pageList.add(vo);
		}*/

		// Step.4 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		mv.addObject(NormalExcelConstants.FILE_NAME, "科学技术-多级学科");
		mv.addObject(NormalExcelConstants.CLASS, TechClassifyXlsx.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("科学技术-多级学科", "导出人:"+"sysUser.getRealname()", "科学技术-多级学科"));
		mv.addObject(NormalExcelConstants.DATA_LIST, techClassifyXlsxList);
		return mv;
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */

    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TechDataClassify.class);
    }

}
