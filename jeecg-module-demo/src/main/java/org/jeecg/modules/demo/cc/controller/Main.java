package org.jeecg.modules.demo.cc.controller;

import com.aspose.words.ControlChar;
import com.aspose.words.Document;
import com.aspose.words.PageSetup;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        // 加载Word文档
        Document doc = new Document("C:\\Users\\1\\Desktop\\works\\7777.docx");

        // 获取文档的页数
        int pageCount = doc.getPageCount();

        // 指定要删除的页码（假设要删除第3页）
        int pageIndexToDelete = 2;

        // 确保要删除的页码在文档范围内
        if (pageIndexToDelete >= 0 && pageIndexToDelete < pageCount) {
            // 删除指定的页面
            PageSetup pageSetup = doc.getSections().get(0).getPageSetup();
            pageSetup.setRestartPageNumbering(true);
            pageSetup.setPageStartingNumber(pageIndexToDelete + 2);
            doc.getSections().get(0).getBody().getFirstParagraph().remove();

            // 保存修改后的文档
            doc.save("C:\\Users\\1\\Desktop\\works\\8888.docx");
        } else {
            System.out.println("页码 " + (pageIndexToDelete + 1) + " 不在文档范围内。");
        }
    }





    public static String addNewLineBeforeIncreasingNumbers(String str) {
        int count = 1;
        StringBuilder sb = new StringBuilder();
        String lineBreak = ControlChar.LINE_BREAK;
        for (int i = 0; i < str.length(); i++) {
            if (Character.getNumericValue(str.charAt(i)) == count) {
                sb.append(lineBreak);
                count++;
            }
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    }


