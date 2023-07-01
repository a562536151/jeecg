package org.jeecg.modules.demo.TechHiExport.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.TechHiExport.entity.TechHiExport;
import org.jeecg.modules.demo.TechHiExport.service.ITechHiExportService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
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
 * @Description: 科学技术导出历史记录
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Api(tags="科学技术导出历史记录")
@RestController
@RequestMapping("/TechHiExport/techHiExport")
@Slf4j
public class TechHiExportController extends JeecgController<TechHiExport, ITechHiExportService> {
	@Autowired
	private ITechHiExportService techHiExportService;
	
	/**
	 * 分页列表查询
	 *
	 * @param techHiExport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "科学技术导出历史记录-分页列表查询")
	@ApiOperation(value="科学技术导出历史记录-分页列表查询", notes="科学技术导出历史记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TechHiExport>> queryPageList(TechHiExport techHiExport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TechHiExport> queryWrapper = QueryGenerator.initQueryWrapper(techHiExport, req.getParameterMap());
		Page<TechHiExport> page = new Page<TechHiExport>(pageNo, pageSize);
		IPage<TechHiExport> pageList = techHiExportService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param techHiExport
	 * @return
	 */
	@AutoLog(value = "科学技术导出历史记录-添加")
	@ApiOperation(value="科学技术导出历史记录-添加", notes="科学技术导出历史记录-添加")
	@RequiresPermissions("TechHiExport:tech_hi_export:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TechHiExport techHiExport) {
		techHiExportService.save(techHiExport);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param techHiExport
	 * @return
	 */
	@AutoLog(value = "科学技术导出历史记录-编辑")
	@ApiOperation(value="科学技术导出历史记录-编辑", notes="科学技术导出历史记录-编辑")
	@RequiresPermissions("TechHiExport:tech_hi_export:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TechHiExport techHiExport) {
		techHiExportService.updateById(techHiExport);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "科学技术导出历史记录-通过id删除")
	@ApiOperation(value="科学技术导出历史记录-通过id删除", notes="科学技术导出历史记录-通过id删除")
	@RequiresPermissions("TechHiExport:tech_hi_export:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		techHiExportService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "科学技术导出历史记录-批量删除")
	@ApiOperation(value="科学技术导出历史记录-批量删除", notes="科学技术导出历史记录-批量删除")
	@RequiresPermissions("TechHiExport:tech_hi_export:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.techHiExportService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "科学技术导出历史记录-通过id查询")
	@ApiOperation(value="科学技术导出历史记录-通过id查询", notes="科学技术导出历史记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TechHiExport> queryById(@RequestParam(name="id",required=true) String id) {
		TechHiExport techHiExport = techHiExportService.getById(id);
		if(techHiExport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(techHiExport);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param techHiExport
    */
    @RequiresPermissions("TechHiExport:tech_hi_export:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TechHiExport techHiExport) {
        return super.exportXls(request, techHiExport, TechHiExport.class, "科学技术导出历史记录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("TechHiExport:tech_hi_export:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TechHiExport.class);
    }

}
