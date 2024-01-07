package org.main;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Quiz implements ActionListener {

    List<Answers> QUESTIONER_LIST = questionsFromExcel();

    char guess;
    char answer;
    int index = 0;
    int correctGuesses = 0;
    int totalQuestions = QUESTIONER_LIST.size();
    int result;
    int seconds = 60;

    JFrame frame = new JFrame();
    JTextField textField = new JTextField();
    JTextArea textArea = new JTextArea();
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    JButton buttonNext = new JButton();
    JLabel answerLabelA = new JLabel();
    JLabel answerLabelB = new JLabel();
    JLabel answerLabelC = new JLabel();
    JLabel answerLabelD = new JLabel();
    JLabel timerLabel = new JLabel();
    JLabel secondsLeft = new JLabel();
    JTextField numberOfCorrect = new JTextField();
    JTextField percentage = new JTextField();


    public Quiz() throws IOException {
        setFrame();
        setTextFiled();
        setTextArea();
        setButtonA();
        setButtonB();
        setButtonC();
        setButtonD();
        setAnswerLabelA();
        setAnswerLabelB();
        setAnswerLabelC();
        setAnswerLabelD();
        setSecondsLeft();
        setTimerLabel();
        setNumberOfCorrect();
        setPercentage();
        setButtonNext();

        frame.add(textField);
        frame.add(textArea);
        frame.add(buttonA);
        frame.add(buttonB);
        frame.add(buttonC);
        frame.add(buttonD);
        frame.add(buttonNext);
        frame.add(answerLabelA);
        frame.add(answerLabelB);
        frame.add(answerLabelC);
        frame.add(answerLabelD);
        frame.add(secondsLeft);
        frame.add(timerLabel);
        frame.setVisible(true);

        continueQuiz();
    }


    public void continueQuiz() {
        buttonNext.setEnabled(false);

        if (index >= totalQuestions) {
            result();
        } else {
            textField.setText("Question " + index + 1);
            textArea.setText(QUESTIONER_LIST.get(index).getQuestion());
            answerLabelA.setText(QUESTIONER_LIST.get(index).getFirstAnswer());
            answerLabelB.setText(QUESTIONER_LIST.get(index).getSecondAnswer());
            answerLabelC.setText(QUESTIONER_LIST.get(index).getThirdAnswer());
            answerLabelD.setText(QUESTIONER_LIST.get(index).getFourthAnswer());
        }
        if (index == totalQuestions - 1) {
            buttonNext.setText("Finish");
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        enableButtons(false);
        buttonNext.setEnabled(true);

        if (actionEvent.getSource() == buttonA) {
            if (QUESTIONER_LIST.get(index).getFirstAnswer()
                    .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
                correctGuesses++;
            }
        }
        if (actionEvent.getSource() == buttonB) {
            if (QUESTIONER_LIST.get(index).getSecondAnswer()
                    .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
                correctGuesses++;
            }
        }
        if (actionEvent.getSource() == buttonC) {
            if (QUESTIONER_LIST.get(index).getThirdAnswer()
                    .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
                correctGuesses++;
            }
        }
        if (actionEvent.getSource() == buttonD) {
            if (QUESTIONER_LIST.get(index).getFourthAnswer()
                    .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
                correctGuesses++;
            }
        }
        highlightWrongAnswers();
        nextQuestion(actionEvent);
    }

    public void nextQuestion(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonNext) {
            answerLabelA.setForeground(new Color(255, 255, 255));
            answerLabelB.setForeground(new Color(255, 255, 255));
            answerLabelC.setForeground(new Color(255, 255, 255));
            answerLabelD.setForeground(new Color(255, 255, 255));

            enableButtons(true);
            index++;
            continueQuiz();
        }
    }

    public void result() {
        enableButtons(false);
        buttonNext.setEnabled(false);

        result = (int)((correctGuesses / (double)totalQuestions) * 100);

        textField.setText("Results");
        textArea.setText("");
        answerLabelA.setText("");
        answerLabelB.setText("");
        answerLabelC.setText("");
        answerLabelD.setText("");

        numberOfCorrect.setText(correctGuesses + " / " + totalQuestions);
        percentage.setText(result + "%");
        frame.add(percentage);
        frame.add(numberOfCorrect);
    }

    private void enableButtons(boolean b) {
        buttonA.setEnabled(b);
        buttonB.setEnabled(b);
        buttonC.setEnabled(b);
        buttonD.setEnabled(b);
    }

    private void highlightWrongAnswers() {
        enableButtons(false);

        if (!QUESTIONER_LIST.get(index).getFirstAnswer()
                .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
            answerLabelA.setForeground(new Color(255, 0, 0));
        } else {
            answerLabelA.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getSecondAnswer()
                .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
            answerLabelB.setForeground(new Color(255, 0, 0));
        } else {
            answerLabelB.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getThirdAnswer()
                .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
            answerLabelC.setForeground(new Color(255, 0, 0));
        } else {
            answerLabelC.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getFourthAnswer()
                .equals(QUESTIONER_LIST.get(index).getCorrectAnswer())) {
            answerLabelD.setForeground(new Color(255, 0, 0));
        } else {
            answerLabelD.setForeground(new Color(0, 255, 13));
        }
    }

    private void setPercentage() {
        percentage.setBounds(225, 325, 200, 100);
        percentage.setBackground(new Color(25, 25, 25));
        percentage.setForeground(new Color(25, 255, 0));
        percentage.setFont(new Font("Ink Free", Font.ITALIC, 50));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);
    }

    private void setNumberOfCorrect() {
        numberOfCorrect.setBounds(225, 225, 200, 100);
        numberOfCorrect.setBackground(new Color(25, 25, 25));
        numberOfCorrect.setForeground(new Color(25, 255, 0));
        numberOfCorrect.setFont(new Font("MV Boli", Font.BOLD, 50));
        numberOfCorrect.setBorder(BorderFactory.createBevelBorder(1));
        numberOfCorrect.setHorizontalAlignment(JTextField.CENTER);
        numberOfCorrect.setEditable(false);

    }

    private void setTimerLabel() {
        timerLabel.setBounds(535, 475, 100, 25);
        timerLabel.setBackground(new Color(50, 50, 50));
        timerLabel.setForeground(new Color(255, 0, 0));
        timerLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        timerLabel.setText("Timer");
    }

    private void setSecondsLeft() {
        secondsLeft.setBounds(535, 510, 100, 100);
        secondsLeft.setBackground(new Color(25, 25, 25));
        secondsLeft.setForeground(new Color(255, 0, 0));
        secondsLeft.setFont(new Font("Ink Free", Font.ITALIC, 60));
        secondsLeft.setBorder(BorderFactory.createBevelBorder(1));
        secondsLeft.setOpaque(true);
        secondsLeft.setHorizontalAlignment(JTextField.CENTER);
        secondsLeft.setText(String.valueOf(seconds));
    }

    private void setAnswerLabelA() {
        answerLabelA.setBounds(125, 100, 500, 100);
        answerLabelA.setBackground(new Color(50, 50, 50));
        answerLabelA.setForeground(new Color(255, 255, 255));
        answerLabelA.setFont(new Font("MV Boli", Font.PLAIN, 35));
    }

    private void setAnswerLabelB() {
        answerLabelB.setBounds(125, 200, 500, 100);
        answerLabelB.setBackground(new Color(50, 50, 50));
        answerLabelB.setForeground(new Color(255, 255, 255));
        answerLabelB.setFont(new Font("MV Boli", Font.PLAIN, 35));
    }

    private void setAnswerLabelC() {
        answerLabelC.setBounds(125, 300, 500, 100);
        answerLabelC.setBackground(new Color(50, 50, 50));
        answerLabelC.setForeground(new Color(255, 255, 255));
        answerLabelC.setFont(new Font("MV Boli", Font.PLAIN, 35));
    }

    private void setAnswerLabelD() {
        answerLabelD.setBounds(125, 400, 500, 100);
        answerLabelD.setBackground(new Color(50, 50, 50));
        answerLabelD.setForeground(new Color(255, 255, 255));
        answerLabelD.setFont(new Font("MV Boli", Font.PLAIN, 35));
    }

    private void setButtonA() {
        buttonA.setBounds(0, 100, 100, 100);
        buttonA.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonA.setFocusable(false);
        buttonA.setText("A");
        buttonA.addActionListener(this);
    }

    private void setButtonB() {
        buttonB.setBounds(0, 200, 100, 100);
        buttonB.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.setText("B");
        buttonB.addActionListener(this);
    }

    private void setButtonC() {
        buttonC.setBounds(0, 300, 100, 100);
        buttonC.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.setText("C");
        buttonC.addActionListener(this);
    }

    private void setButtonD() {
        buttonD.setBounds(0, 400, 100, 100);
        buttonD.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonD.setFocusable(false);
        buttonD.setText("D");
        buttonD.addActionListener(this);
    }

    private void setButtonNext() {
        buttonNext.setBounds(290, 520, 150, 35);
        buttonD.setFocusable(false);
        buttonNext.setFont(new Font("MV Boli", Font.ITALIC, 35));
        buttonNext.setText("Next");
        buttonNext.addActionListener(this);
    }

    private void setTextArea() {
        textArea.setBounds(0, 50, 650, 50);
        textArea.setBackground(new Color(25, 25, 25, 255));
        textArea.setForeground(new Color(0, 111, 255));
        textArea.setFont(new Font("MV Boli", Font.BOLD, 30));
        textArea.setEditable(false);
    }

    private void setTextFiled() {
        textField.setBounds(0, 0, 650, 50);
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(255, 255, 255));
        textField.setFont(new Font("Ink Free", Font.BOLD, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setText("Lets start the Quiz");
    }

    private void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(null);
        frame.setResizable(false);
    }

    public List<Answers> questionsFromExcel() throws IOException {
        XSSFSheet sheet = getSheet();
        HashMap<String, Answers> questionerMap = new HashMap<>();

        int rowsAmount = sheet.getPhysicalNumberOfRows();
        List<Answers> questionerList = new ArrayList<>();
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
        return questionerList;
    }

    private static XSSFSheet getSheet() throws IOException {
        File exelFile = new File("C:\\Questions.xlsx");
        FileInputStream filestream = new FileInputStream(exelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(filestream);
        return workbook.getSheet("Questions");
    }

}
