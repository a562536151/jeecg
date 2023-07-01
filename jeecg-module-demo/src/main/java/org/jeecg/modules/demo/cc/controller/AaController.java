package org.jeecg.modules.demo.cc.controller;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.aspose.words.ControlChar;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.cc.entity.Aa;
import org.jeecg.modules.demo.cc.entity.CategoryEntity;
import org.jeecg.modules.demo.cc.mapper.AaMapper;
import org.jeecg.modules.demo.cc.pdfUtils.DocToPdf;
import org.jeecg.modules.demo.cc.pdfUtils.DocxToXmlConverter;
import org.jeecg.modules.demo.cc.pdfUtils.XmlToDocx;
import org.jeecg.modules.demo.cc.service.IAaService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.cc.service.impl.AaServiceImpl;
import org.jeecg.modules.demo.cc.vo.AaPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @Description: 销售管理
 * @Author: jeecg-boot
 * @Date:   2023-05-01
 * @Version: V1.0
 */
@Api(tags="销售管理")
@RestController
@RequestMapping("/cc/aa")
@Slf4j
public class AaController extends JeecgController<Aa, IAaService> {
	@Autowired
	private IAaService aaService;

	@Autowired
	private AaMapper aaMapper;

	 @Autowired
	 private AaServiceImpl serviceimpl;
	@Autowired
	private DocToPdf docToPdf;

	 @Autowired
	 private DocxToXmlConverter docxToXmlConverter;

	 @Autowired
	 private XmlToDocx xmlToDocx;

	 @Value("${pdf.docxPath}")
	 private  String docxPath;

	 @Value("${pdf.licensePath}")
	 private String licesePath;

	 @Value("${pdf.docxTempPath}")
	 private String tempPath;




	 @RequestMapping("/list/tree")
	 public List<Aa> list(){
		 List<Aa> categoryEntities = aaService.listWithTree();

		 return categoryEntities;
	 }
	
	/**
	 * 分页列表查询
	 *
	 * @param aa
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "销售管理-分页列表查询")
	@ApiOperation(value="销售管理-分页列表查询", notes="销售管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<List<Aa>> queryPageList(Aa aa,
										   AaPage aaPage,
										   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										   HttpServletRequest req) throws Exception {
		String ww = "读朱自清的荷塘月色，品读鲁迅的从百草园到三味书屋，看梁实秋的雅舍!一篇篇经典散文，想起来就都可不理。这是独处的妙处，我且受用这无边的荷香月色好了。  曲曲折折的荷塘上面，弥望旳是田田的叶子。叶子出水很高，像亭亭旳舞女旳裙。层层的叶子中间，零星地点缀着些白花，有袅娜(niǎo,nuó)地开着旳，有羞涩地打着朵儿旳;正如一粒粒的明珠，又如碧天里的星星，又如刚出浴的美人。微风过处，送来缕缕清香，仿佛远处高楼上渺茫的歌声似的。这时候叶子与花也有一丝的颤动，像闪电般，霎时传过荷塘的那边去了。叶子本是肩并肩密密地挨着，这便宛然有了一道凝碧的波痕。叶子底下是脉脉(mò)的流水，遮住了，不能见一些颜色;而叶子却更见风致了。  月光如流水一般，静静地泻在这一片叶子和花上。薄薄的青雾浮起在荷塘里。叶子和花仿佛在牛乳中洗过一样;又像笼着轻纱的梦。虽然是满月，天上却有一层淡淡的云，所以不能朗照;但我以为这恰是到了好处——酣眠固不可少，小睡也别有风味的。月光是隔了树照过来的，高处丛生的灌木，落下参差的斑驳的黑影，峭楞楞如鬼一般;弯弯的杨柳的稀疏的倩影，却又像是画在荷叶上。塘中的月色并不均匀;但光与影有着和谐的旋律，如梵婀(ē)玲(英语violin小提琴的译音)上奏着的名曲。  荷塘的四面，远远近近，高高低低都是树，而杨柳最多。这些树将一片荷塘重重围住;只在小路一旁，漏着几段空隙，像是特为月光留下的。树色一例是阴阴的，乍看像一团烟雾;但杨柳的丰姿，便在烟雾里也辨得出。树梢上隐隐约约的是一带远山，只有些大意罢了。树缝里也漏着一两点路灯光，没精打采的，是渴睡人的眼。这时候最热闹的，要数树上的蝉声与水里的蛙声;但热闹是它们的，我什么也没有。  2、鲁迅《从百草园到三味书屋》片段  不必说碧绿的菜畦，光滑的石井栏，高大的皂荚树，紫红的桑椹;也不必说鸣蝉在树叶里长吟，肥胖的黄蜂伏在菜花上，轻捷的叫天子(云雀)忽然从草间直窜向云霄里去了。单是周围的短短的泥墙根一带，就有无限趣味。油蛉在这里低唱，蟋蟀们在这里弹琴。翻开断砖来，有时会遇见蜈蚣;还有斑蝥，倘若用手指按住它的脊梁，便会拍的一声，从后窍喷出一阵烟雾。何首乌藤和木莲藤缠络着，木莲有莲房一般的果实，何首乌有拥肿的根。有人说，何首乌根是有象人形的，吃了便可以成仙，我于是常常拔它起来，牵连不断地拔起来，也曾因此弄坏了泥墙，却从来没有见过有一块根象人样。如果不怕刺，还可以摘到覆盆子，象小珊瑚珠攒成的小球，又酸又甜，色味都比桑椹要好得远。  3、陈从周《说园》片段  园有静观、动观之分，这一点我们在造园之先，首要考虑。何谓静观，就是园中予游者多驻足的观赏点;动观就是要有较长的游览线。二者说来，小园应以静观为主，动观为辅，庭院专主静观。大园则以动观为主，静观为辅。前者如苏州网师园，后者则苏州拙政园差可似之。人们进入网师园宜坐宜留之建筑多，绕池一周，有槛前细数游鱼，有亭中待月迎风，而轩外花影移墙，峰峦当窗，宛然如画，静中生趣。至于拙政园径缘池转，廊引人随，与“日午画船桥下过，衣香人影太匆匆”的瘦西湖相仿佛，妙在移步换影，这是动观。立意在先，文循意出。动静之分，有关园林性质与园林面积大小。象上海正在建造的盆景园，则宜以静观为主，即为一例。  中国园林是由建筑、山水、花木等组合而成的一个综合艺术品，富有诗情画意。叠山理水要造成“虽由人作，宛自天开”的境界。山与水的关系究竟如何呢?简言之，模山范水，用局部之景而非缩小(网师园水池仿虎丘白莲池，极妙)，处理原则悉符画本。山贵有脉，水贵有源，脉源贯通，全园生动。我曾经用“水随山转，山因水活”与“溪水因山成曲折，山蹊随地作低平”来说明山水之间的关系，也就是从真山真水中所得到的启示。明末清初叠山家张南垣主张用平冈小陂、陵阜陂阪，也就是要使园林山水接近自然。如果我们能初步理解这个道理，就不至于离自然太远，多少能呈现水石交融的美妙境界。  4、梁实秋《雅舍》片段  “雅舍”最宜月夜——地势较高，得月较先。看山头吐月，红盘乍涌，一霎间，清光四射，天空皎洁，四野无声，微闻犬吠，坐客无不悄然!舍前有两株梨树，等到月升中天，清光从树间筛洒而下，地下阴影斑斓，此时尤为幽绝。直到兴阑人散，归房就寝，月光仍然逼进窗来，助我凄凉。细雨蒙蒙之际，“雅舍”亦复有趣。推窗展望，俨然米氏章法，若云若雾，一片弥漫。但若大雨滂沱，我就又惶悚不安了，屋顶浓印到处都有，起初如碗大，俄而扩大如盆，继则滴水乃不绝，终乃屋顶灰泥突然崩裂，如奇葩初绽，砉然一声而泥水下注，此刻满室狼藉，抢救无及。此种经验，已数见不鲜。";
		String input = "AA 1.BB 2.CC 3.DD 4.dasdasdasd";
		String output = addNewLineBeforeIncreasingNumbers(input);
		String textS ="#这是第一行#这是第二行#这是第三行";
		Map<String,Object> map = new HashMap<>();
		map.put("transcationNoticeNo","11");
		map.put("supplierName","11");
		map.put("materialInformation","11");
		map.put("supplierOffer","11");
		map.put("inputa","ccc");
		map.put("inputb",ww);
		map.put("input",textS);
		docToPdf.DocToPdf(map,"template.docx");
		List<Aa> entity =  serviceimpl.listWithTree();
		return Result.OK(entity);
	}


	 public static String addNewLineBeforeIncreasingNumbers(String str) {
		 int count = 1;
		 StringBuilder sb = new StringBuilder();
		 for (int i = 0; i < str.length(); i++) {
			 if (Character.getNumericValue(str.charAt(i)) == count) {
				 sb.append("#");
				 count++;
			 }
			 sb.append(str.charAt(i));
		 }
		 return sb.toString();
	 }
	
	/**
	 *   添加
	 *
	 * @param aa
	 * @return
	 */
	@AutoLog(value = "销售管理-添加")
	@ApiOperation(value="销售管理-添加", notes="销售管理-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Aa aa) {
		serviceimpl.addCategory(aa);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param aa
	 * @return
	 */
	@AutoLog(value = "销售管理-编辑")
	@ApiOperation(value="销售管理-编辑", notes="销售管理-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Aa aa) {

		aa.setUpdateBy(null);
		aa.setUpdateTime(null);
		aaMapper.updateData(aa);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "销售管理-通过id删除")
	@ApiOperation(value="销售管理-通过id删除", notes="销售管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		serviceimpl.deleteCategory(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "销售管理-批量删除")
	@ApiOperation(value="销售管理-批量删除", notes="销售管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.aaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "销售管理-通过id查询")
	@ApiOperation(value="销售管理-通过id查询", notes="销售管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Aa> queryById(@RequestParam(name="id",required=true) String id) {
		Aa aa = aaService.getById(id);
		if(aa==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(aa);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param aa
    */
    @RequiresPermissions("cc:aa:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Aa aa) {
        return super.exportXls(request, aa, Aa.class, "销售管理");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("cc:aa:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Aa.class);
    }




}
