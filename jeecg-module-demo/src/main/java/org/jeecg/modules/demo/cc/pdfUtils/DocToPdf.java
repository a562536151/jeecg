package org.jeecg.modules.demo.cc.pdfUtils;

import com.aspose.words.Document;
import com.aspose.words.Shape;
import com.aspose.words.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.poi.xwpf.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.aspose.words.Section;
import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


//word转pdf核心代码



@Slf4j
@Component
public class DocToPdf {

    @Autowired
    private DocxToXmlConverter docxToXmlConverter;

    @Autowired
    private XmlToDocx xmlToDocx;

    @Value("${pdf.docxPath}")
    private  String docxPath;

    @Value("${pdf.licensePath}")
    private String licesePath;

    @Value("${pdf.licensePaths}")
    private String licensePaths;

    @Value("${pdf.docxTempPath}")
    private String tempPath;


    public boolean getLicense() {
        try (InputStream is = new FileInputStream(licesePath)) {
            License aposeLic = new License();
            aposeLic.setLicense(is);
            return true;
        } catch (Exception e) {
            log.error("获取aswords的凭证错误", e);
            return false;
        }
    }







    public String DocToPdf(Map<String,Object> programFile,String documentDocNames) throws Exception {
        File newFile = null;
        String documentDocName = null;
        String docx = null;
        String xml = null;
        try {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            if (!getLicense()) {
                return null;
            }
            //对文件进行拷贝生成副本，后续操作在副本上进行
            File oldFile = new File(docxPath + documentDocNames);
            String paths = docxPath + documentDocNames.replace(".docx", "") + "副本.docx";
            newFile = new File(paths);
            // 创建文件输入流和输出流，进行文件拷贝
            try (FileInputStream fis = new FileInputStream(oldFile);
                 FileOutputStream fos = new FileOutputStream(newFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                log.info("docx文件拷贝成功");
            }


            documentDocName = newFile.getName();

            programFile.put("docxPath", docxPath);
            programFile.put("tempPath", tempPath);

            try {
                //把指定路径word转化成xml,并且对word里面表格内容进行填充
                docxToXmlConverter.docxToXml(programFile, documentDocName);
                log.info("word模板表格渲染成功");
            } catch (Exception e) {
                log.error("word模板表格渲染失败");
            }


            String documentXmlName = "document.xml";
            String programmeFilePath = docxPath;
            programFile.put("fileUrl","xxxxxxxxxxxx11.pdf");
            String fileUrl = (String) programFile.get("fileUrl");
            fileUrl = fileUrl.replace("*", "");
            String templateUUId = documentDocName + UUID.randomUUID().toString();
            xml = docxPath + templateUUId + ".xml";
            xmlToDocx.toDocx(documentXmlName, docxPath + documentDocName, xml, docxPath + templateUUId + ".docx", programFile);
            log.info("docx写入xml成功");
            docx = docxPath + templateUUId + ".docx";
            String pdf = programmeFilePath + File.separator + fileUrl;



            com.aspose.words.Document docs = new com.aspose.words.Document(docx);
            // 获取文档中所有的Paragraph节点
            NodeCollection<Paragraph> paragraphs = docs.getChildNodes(NodeType.PARAGRAPH, true);
            for (Paragraph para : paragraphs) {
                if (para.getText().contains("#")) {
                    // 拆分段落文本内容
                    String[] parts = para.getText().split("#");
                    // 清空原有的Runs
                    para.getRuns().clear();
                    // 添加第一个文本部分到段落中，同时设置Runs的样式属性
                    Run firstRun = new Run(docs, parts[0]);
                    firstRun.getFont().setName("方正小标宋_GBK");
                    firstRun.getFont().setSize(11);
                    firstRun.getFont().setColor(Color.BLACK);
                    para.getRuns().add(firstRun);

                    // 遍历剩余的文本部分，每个部分添加到段落中，并在后面添加换行符
                    for (int i = 1; i < parts.length; i++) {
                        Run run = new Run(docs, ControlChar.LINE_BREAK + parts[i]);
                        run.getFont().setName("方正小标宋_GBK");
                        run.getFont().setSize(11);
                        run.getFont().setColor(Color.BLACK);
                        para.getRuns().add(run);
                    }
                }
            }
            docs.save(docx);

            String watermarkImage = "C:\\Users\\1\\Desktop\\workTppdf\\111.png";

            Document doc = GenerateWaterMark(docx, watermarkImage);


            try (FileOutputStream os = new FileOutputStream(new File(pdf))) {
                String[] fonts = new String[]{"C:\\data"};
                FontSettings.setFontsFolders(fonts, true);
                doc.save(os, SaveFormat.PDF);
                String firstDocPath = "C:\\Users\\1\\Desktop\\testWord\\1111.docx";
                String secondDocPath = "C:\\Users\\1\\Desktop\\testWord\\八.docx";
                String thirdDocPath = "C:\\Users\\1\\Desktop\\testWord\\2222.docx";
                //第四页直接删除
                deleteWord(firstDocPath,4);
                //在第三页建立一个空白页插入
                addDocxAfterDocx(firstDocPath, thirdDocPath,3);
                //第四页直接删除
                int page = 12;
                int page2 = 11;
                deleteWord(firstDocPath,page-1);
                //在第三页建立一个空白页插入
                addDocxAfterDocx(firstDocPath, secondDocPath,page2-1);
                //String mergedDocPath = "C:\\Users\\1\\Desktop\\works\\6666.docx";
                //    mergeWordDocuments(firstDocPath, secondDocPath, mergedDocPath);
                String firstFilePath  = "C:\\Users\\1\\Desktop\\works\\2222.pdf";   // 源PDF文件路径
                String secondFilePath  = "C:\\Users\\1\\Desktop\\works\\1111.pdf";   // 目标PDF文件路径
                String outputFilePath  = "C:\\Users\\1\\Desktop\\works\\3333.pdf";
              // addPdfAfterPdf(firstFilePath, secondFilePath, outputFilePath);
                delete(xml, docx, docxPath + documentDocName);

            }
            return pdf;
        } catch (Exception e) {
            log.error(documentDocNames + "转pdf异常", e);
            delete(xml, docx, docxPath + documentDocName);
            return null;
        }
    }

    @NotNull
    private static Document GenerateWaterMark(String docx, String watermarkImage) throws Exception {
        Document doc = new Document(docx);

        // 创建水印形状
        Shape watermark = new Shape(doc, ShapeType.IMAGE);
        watermark.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
        watermark.setRelativeVerticalPosition(RelativeVerticalPosition.PAGE);
        watermark.setHorizontalAlignment(HorizontalAlignment.CENTER);
        watermark.setVerticalAlignment(VerticalAlignment.CENTER);
        watermark.setWidth(doc.getFirstSection().getPageSetup().getPageWidth());
        watermark.setHeight(doc.getFirstSection().getPageSetup().getPageHeight());
        watermark.setWrapType(WrapType.NONE);

// 设置水印图片
        watermark.getImageData().setImage(watermarkImage);

// 添加水印到每一页的页眉
        for (Section section : doc.getSections()) {
            HeaderFooter header = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.HEADER_PRIMARY);
            if (header == null) {
                header = new HeaderFooter(section.getDocument(), HeaderFooterType.HEADER_PRIMARY);
                section.getHeadersFooters().add(header);
            }
            Paragraph watermarkPara = new Paragraph(doc);
            watermarkPara.appendChild(watermark.deepClone(true));
            header.appendChild(watermarkPara);
        }
        return doc;
    }


    private static void deleteWord(String filePath,int sectionNum) throws Exception {
        Document docc = new Document(filePath);
        int sectionIndexToRemove = sectionNum; // 要删除的节的索引，从0开始计数
        if (docc.getSections().getCount() > sectionIndexToRemove) {
            docc.getSections().removeAt(sectionIndexToRemove);
        }
        docc.save(filePath);
    }

    private static void addDocxAfterDocx(String firstDocPath, String secondDocPath,int sectionNum) {
        try {
            // 加载第一份Word文档
            Document doc1 = new    Document (firstDocPath);
            // 加载第二份Word文档
            Document  doc2 = new    Document (secondDocPath);
            // 将第二份文档的内容复制到第一份文档的第二页后面
            NodeImporter importer = new NodeImporter(doc2, doc1, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            for (Section section : doc2.getSections()) {
                for (Node node : section.getBody()) {
                    System.out.println("node值为:"+node.getText());
                    Node importedNode = importer.importNode(node, true);
                    doc1.getSections().get(sectionNum).getBody().appendChild(importedNode);
                }
            }
            // 保存结果到新的文档
            doc1.save(firstDocPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Section findSectionByPageIndex(com.aspose.words.Document doc, int pageIndex) {
        for (Section section : doc.getSections()) {
            Body body = section.getBody();
            if (isPageInSection(body, pageIndex)) {
                return section;
            }
        }
        return null;
    }

    private static boolean isPageInSection(Body body, int pageIndex) {
        int pageCount = 0;
        for (Node node : body) {
            if (node instanceof com.aspose.words.Paragraph) {
                Paragraph paragraph = (Paragraph) node;
                if (paragraph.isEndOfSection()) {
                    pageCount++;
                }
            }
            if (pageCount == pageIndex) {
                return true;
            }
        }
        return false;
    }



    private static void mergeWordDocuments(String firstFilePath, String secondFilePath, String outputFilePath) {
        try {
            // 打开第一份 Word 文件
            Document firstDoc = new Document(firstFilePath);

            // 打开第二份 Word 文件
            Document secondDoc = new Document(secondFilePath);

            // 创建一个新的节，并将第二份文档的内容复制到该节中
            Section newSection = (Section) secondDoc.getFirstSection().deepClone(true);

            // 获取第三页的末尾位置
            int pageIndex = 2; // 第三页的索引为2
            int insertIndex = 0;
            NodeCollection paragraphs = firstDoc.getChildNodes(NodeType.PARAGRAPH, true);
            for (Paragraph paragraph : (Iterable<Paragraph>) paragraphs) {
                if (paragraph.getParentSection().getPageSetup().getSectionStart() == SectionStart.NEW_PAGE) {
                    pageIndex++;
                }
                if (pageIndex == 3) {
                    insertIndex = paragraphs.indexOf(paragraph) + 1;
                }
            }

            // 将第二份文档的节插入到第一份文档的第三页之后
            SectionCollection sections = firstDoc.getSections();
            if (sections.getCount() > 0) {
                Section targetSection = sections.get(0);
                targetSection.getParentNode().insertAfter(newSection, targetSection);
            }

            // 保存合并后的 Word 文件
            firstDoc.save(outputFilePath);

            System.out.println("Word 文件合并完成。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static int getPageCount(Section section) {
        int pageCount = 1;
        PageSetup pageSetup = section.getPageSetup();
        if (pageSetup.getSectionStart() == SectionStart.NEW_PAGE || pageSetup.getSectionStart() == SectionStart.NEW_COLUMN) {
            pageCount++;
        }
        return pageCount;
    }







    private String getDefaultFontPath() {
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        String fontPath = null;
        if (osName.contains("linux")) {
            fontPath = "/usr/share/fonts/truetype/";
        } else if (osName.contains("mac")) {
            fontPath = "/Library/Fonts/";
        } else if (osName.contains("windows")) {
            fontPath = "C:/Windows/Fonts/";
        }
        return fontPath;
    }

    public void delete(String xmlPath,String wordPath,String newFile){

        try {
            File file = new File(xmlPath);
            if(file.exists()){
                file.delete();
            }
            File wordFile = new File(wordPath);
            if(wordFile.exists()){
                wordFile.delete();
            }
            File files = new File(newFile);
            if(files.exists()){
                files.delete();
            }
        }catch (Exception e){
            log.error("副本或者xml删除失败",e);
        }

    }

}






