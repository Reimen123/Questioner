package org.main;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionerExcelReader {

    public static void main(String[] args) throws IOException {
        File exelFile = new File("C:\\Questions.xlsx");
        FileInputStream filestream = new FileInputStream(exelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(filestream);
        XSSFSheet sheet = workbook.getSheet("Questions");
        List<Answers> questionerList = new ArrayList<>();

        int rowsAmount = sheet.getPhysicalNumberOfRows();
        for (int row = 1; row < rowsAmount; row++) {
            Answers answers = new Answers();
            answers.setQuestion(sheet.getRow(row).getCell(2).toString());
            answers.setCategory(sheet.getRow(row).getCell(1).toString());
            answers.setFirstAnswer(sheet.getRow(row).getCell(3).toString());
            answers.setSecondAnswer(sheet.getRow(row).getCell(4).toString());
            answers.setThirdAnswer(sheet.getRow(row).getCell(5).toString());
            answers.setFourthAnswer(sheet.getRow(row).getCell(6).toString());
            answers.setCorrectAnswer(sheet.getRow(row).getCell(7).toString());
            answers.setDescription(sheet.getRow(row).getCell(8).toString());
            answers.setExplanation(sheet.getRow(row).getCell(9).toString());
            questionerList.add(answers);
        }

        System.out.println(questionerList);

        Quiz quiz = new Quiz();

    }
}
