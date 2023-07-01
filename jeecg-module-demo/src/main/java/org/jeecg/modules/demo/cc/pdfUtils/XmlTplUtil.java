package org.jeecg.modules.demo.cc.pdfUtils;

import com.aspose.words.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @description  word动态填充工具类
 **/
@Component
@Slf4j
public class XmlTplUtil {


    @Value("${pdf.docxPath}")
    private String docxPath;

    private static XmlTplUtil tplm = null;

    private Configuration cfg = null;

    @Autowired
    private XmlTplUtil(@Value("${pdf.docxPath}") String docxPath) {
        this.docxPath = docxPath;
        cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File(docxPath));
        } catch (Exception e) {
            log.error("xmlUtil填充word失败",e);
        }
    }

    private Template getTemplate(String name) throws IOException {
        if (tplm == null) {
            tplm = new XmlTplUtil(docxPath);
        }
        Template template = tplm.cfg.getTemplate(name,"utf-8");
        return template;
    }

    /**
     * @param templatefile 模板文件
     * @param param 需要填充的内容
     * @param out 填充完成输出的文件
     * @throws IOException
     * @throws TemplateException
     */
    public void process(String templatefile, Map param, Writer out) throws IOException, TemplateException {
        XmlTplUtil xmlTplUtil = new XmlTplUtil(docxPath);
        // 获取模板
        Template template = xmlTplUtil.getTemplate(templatefile);
        template.setOutputEncoding("utf-8");
        // 合并数据
        try (Writer writer = out) {
            template.process(param, writer);

        } catch (IOException | TemplateException e) {
            log.error("xmlUtil填充word失败", e);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}