package org.jeecg.modules.demo.cc.controller;

import com.aspose.words.*;

public class WordSectionIdentification {
    public static void identifySections(String filePath) throws Exception {
        Document doc = new Document(filePath);

        NodeCollection<Section> sections = doc.getSections();
        for (int i = 0; i < sections.getCount(); i++) {
            Section section = (Section) sections.get(i);
            System.out.println("Section " + (i + 1) + " Content:");
            System.out.println(section.getBody().getText());
        }
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\1\\Desktop\\works\\科学技术进步奖推荐书.docx";  // Word文件路径
        int startSectionIndex = 1;  // 起始节的索引（从0开始）
        int endSectionIndex = 2;  // 结束节的索引（从0开始）

        try {
          //  identifySections(filePath);
           //deleteSectionContent(filePath, startSectionIndex, endSectionIndex);
            Document doc = new Document(filePath);
            int sectionIndexToRemove = 2; // 要删除的节的索引，从0开始计数
            if (doc.getSections().getCount() > sectionIndexToRemove) {
                doc.getSections().removeAt(sectionIndexToRemove);
            }
            doc.save(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteSectionContent(String filePath, int startSectionIndex, int endSectionIndex) throws Exception {
        Document doc = new Document(filePath);

        NodeCollection<Section> sections = doc.getSections();
        if (startSectionIndex >= 0 && startSectionIndex < sections.getCount() && endSectionIndex >= startSectionIndex && endSectionIndex < sections.getCount()) {
            for (int i = startSectionIndex + 1; i < endSectionIndex; i++) {
                Section section = (Section) sections.get(i);
                section.removeAllChildren();
            }
        }

        doc.save(filePath);
    }
}