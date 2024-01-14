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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Quiz implements ActionListener {

    List<Answers> QUESTIONER_LIST = questionsFromExcel();
    int index = 0;
    int correctGuesses = 0;
    int totalQuestions = QUESTIONER_LIST.size();
    int result;
    int seconds = 60;

    JFrame frame = new JFrame();
    JTextField textField = new JTextField();
    JTextArea questionArea = new JTextArea();
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();
    JButton buttonNext = new JButton();
    JButton buttonStartAgain = new JButton();
    JTextArea answerAreaA = new JTextArea();
    JTextArea answerAreAB = new JTextArea();
    JTextArea answerAreaC = new JTextArea();
    JTextArea answerAreaD = new JTextArea();
    JTextArea explanationArea = new JTextArea();
    JLabel timerLabel = new JLabel();
    JLabel secondsLeft = new JLabel();
    JTextField numberOfCorrect = new JTextField();
    JTextField percentage = new JTextField();

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            seconds--;
            secondsLeft.setText(String.valueOf(seconds));
            if (seconds <= 0) {
                highlightWrongAnswers();

                buttonNext.setEnabled(true);
            }
        }
    });


    public Quiz() throws IOException {
        setFrame();
        setTextFiled();
        setQuestionArea();
        setButtonA();
        setButtonB();
        setButtonC();
        setButtonD();
        setAnswerA();
        setAnswerB();
        setAnswerC();
        setAnswerD();
        setExplanationArea();
        setSecondsLeft();
        setTimerLabel();
        setNumberOfCorrect();
        setPercentage();
        setButtonNext();
        setButtonStartAgain();

        frame.add(textField);
        frame.add(questionArea);
        frame.add(buttonA);
        frame.add(buttonB);
        frame.add(buttonC);
        frame.add(buttonD);
        frame.add(buttonNext);
        frame.add(answerAreaA);
        frame.add(answerAreAB);
        frame.add(answerAreaC);
        frame.add(answerAreaD);
        frame.add(secondsLeft);
        frame.add(timerLabel);
        frame.add(explanationArea);
        frame.add(buttonStartAgain);
        frame.setVisible(true);

        continueQuiz();
    }


    public void continueQuiz() {
        buttonNext.setEnabled(false);
        timer.start();
        seconds = 60;

        if (index >= totalQuestions) {
            frame.remove(explanationArea);
            result();
        } else {
            textField.setText("Question " + (index + 1));
            questionArea.setText(QUESTIONER_LIST.get(index).getQuestion());
            answerAreaA.setText(QUESTIONER_LIST.get(index).getFirstAnswer());
            answerAreAB.setText(QUESTIONER_LIST.get(index).getSecondAnswer());
            answerAreaC.setText(QUESTIONER_LIST.get(index).getThirdAnswer());
            answerAreaD.setText(QUESTIONER_LIST.get(index).getFourthAnswer());
        }
        if (index == totalQuestions - 1) {
            buttonNext.setText("Finish");
        }
        explanationArea.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonStartAgain) {
            startAgain();

        } else {
            enableButtons(false);
            buttonNext.setEnabled(true);

            if (actionEvent.getSource() == buttonA) {
                if (QUESTIONER_LIST.get(index).getFirstAnswer()
                        .equals(QUESTIONER_LIST.get(index).getDescription())) {
                    correctGuesses++;
                }
            }
            if (actionEvent.getSource() == buttonB) {
                if (QUESTIONER_LIST.get(index).getSecondAnswer()
                        .equals(QUESTIONER_LIST.get(index).getDescription())) {
                    correctGuesses++;
                }
            }
            if (actionEvent.getSource() == buttonC) {
                if (QUESTIONER_LIST.get(index).getThirdAnswer()
                        .equals(QUESTIONER_LIST.get(index).getDescription())) {
                    correctGuesses++;
                }
            }
            if (actionEvent.getSource() == buttonD) {
                if (QUESTIONER_LIST.get(index).getFourthAnswer()
                        .equals(QUESTIONER_LIST.get(index).getDescription())) {
                    correctGuesses++;
                }
            }
            highlightWrongAnswers();
            explanationArea.setText(QUESTIONER_LIST.get(index).getExplanation());
            explanationArea.setVisible(true);
            nextQuestion(actionEvent);
        }
    }

    private void startAgain() {
        frame.remove(percentage);
        frame.remove(numberOfCorrect);
        frame.add(explanationArea);
        index = 0;
        correctGuesses = 0;
        buttonStartAgain.setVisible(false);
        buttonNext.setText("Next");
        enableButtons(true);

        Collections.shuffle(QUESTIONER_LIST);
        continueQuiz();
    }

    public void nextQuestion(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonNext) {
            answerAreaA.setForeground(new Color(255, 255, 255));
            answerAreAB.setForeground(new Color(255, 255, 255));
            answerAreaC.setForeground(new Color(255, 255, 255));
            answerAreaD.setForeground(new Color(255, 255, 255));

            enableButtons(true);
            index++;
            continueQuiz();
        }
    }

    public void result() {
        timer.stop();
        enableButtons(false);
        buttonNext.setEnabled(false);

        result = (int) ((correctGuesses / (double) totalQuestions) * 100);

        textField.setText("Results");
        questionArea.setText("");
        answerAreaA.setText("");
        answerAreAB.setText("");
        answerAreaC.setText("");
        answerAreaD.setText("");
        setCorrectGuesses();
        frame.add(percentage);
        frame.add(numberOfCorrect);
        buttonStartAgain.setVisible(true);
    }

    private void setCorrectGuesses() {
        numberOfCorrect.setText(correctGuesses + " / " + totalQuestions);
        percentage.setText(result + "%");

        if (result < 70) {
            percentage.setForeground(new Color(149, 15, 15));
        }
    }

    private void enableButtons(boolean b) {
        buttonA.setEnabled(b);
        buttonB.setEnabled(b);
        buttonC.setEnabled(b);
        buttonD.setEnabled(b);
    }

    private void highlightWrongAnswers() {
        enableButtons(false);
        timer.stop();

        if (!QUESTIONER_LIST.get(index).getFirstAnswer()
                .equals(QUESTIONER_LIST.get(index).getDescription())) {
            answerAreaA.setForeground(new Color(255, 0, 0));
        } else {
            answerAreaA.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getSecondAnswer()
                .equals(QUESTIONER_LIST.get(index).getDescription())) {
            answerAreAB.setForeground(new Color(255, 0, 0));
        } else {
            answerAreAB.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getThirdAnswer()
                .equals(QUESTIONER_LIST.get(index).getDescription())) {
            answerAreaC.setForeground(new Color(255, 0, 0));
        } else {
            answerAreaC.setForeground(new Color(0, 255, 13));
        }
        if (!QUESTIONER_LIST.get(index).getFourthAnswer()
                .equals(QUESTIONER_LIST.get(index).getDescription())) {
            answerAreaD.setForeground(new Color(255, 0, 0));
        } else {
            answerAreaD.setForeground(new Color(0, 255, 13));
        }
    }

    private void setPercentage() {
        percentage.setBounds(425, 425, 200, 100);
        percentage.setBackground(new Color(25, 25, 25));
        percentage.setForeground(new Color(25, 255, 0));
        percentage.setFont(new Font("Ink Free", Font.ITALIC, 50));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);
    }

    private void setNumberOfCorrect() {
        numberOfCorrect.setBounds(425, 325, 200, 100);
        numberOfCorrect.setBackground(new Color(25, 25, 25));
        numberOfCorrect.setForeground(new Color(255, 255, 255));
        numberOfCorrect.setFont(new Font("MV Boli", Font.BOLD, 50));
        numberOfCorrect.setBorder(BorderFactory.createBevelBorder(1));
        numberOfCorrect.setHorizontalAlignment(JTextField.CENTER);
        numberOfCorrect.setEditable(false);

    }

    private void setTimerLabel() {
        timerLabel.setBounds(935, 775, 100, 25);
        timerLabel.setBackground(new Color(50, 50, 50));
        timerLabel.setForeground(new Color(255, 0, 0));
        timerLabel.setFont(new Font("MV Boli", Font.PLAIN, 25));
        timerLabel.setText("Timer");
    }

    private void setSecondsLeft() {
        secondsLeft.setBounds(935, 810, 100, 100);
        secondsLeft.setBackground(new Color(25, 25, 25));
        secondsLeft.setForeground(new Color(255, 0, 0));
        secondsLeft.setFont(new Font("Ink Free", Font.ITALIC, 60));
        secondsLeft.setBorder(BorderFactory.createBevelBorder(1));
        secondsLeft.setOpaque(true);
        secondsLeft.setHorizontalAlignment(JTextField.CENTER);
        secondsLeft.setText(String.valueOf(seconds));
    }

    private void setAnswerA() {
        answerAreaA.setBounds(125, 270, 890, 70);
        answerAreaA.setBackground(new Color(50, 50, 50));
        answerAreaA.setForeground(new Color(255, 255, 255));
        answerAreaA.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        answerAreaA.setEditable(false);
        answerAreaA.setLineWrap(true);
        answerAreaA.setWrapStyleWord(true);

    }

    private void setAnswerB() {
        answerAreAB.setBounds(125, 370, 890, 70);
        answerAreAB.setBackground(new Color(110, 102, 102));
        answerAreAB.setForeground(new Color(255, 255, 255));
        answerAreAB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        answerAreAB.setEditable(false);
        answerAreAB.setLineWrap(true);
        answerAreAB.setWrapStyleWord(true);
    }

    private void setAnswerC() {
        answerAreaC.setBounds(125, 470, 890, 70);
        answerAreaC.setBackground(new Color(50, 50, 50));
        answerAreaC.setForeground(new Color(255, 255, 255));
        answerAreaC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        answerAreaC.setEditable(false);
        answerAreaC.setLineWrap(true);
        answerAreaC.setWrapStyleWord(true);
    }

    private void setAnswerD() {
        answerAreaD.setBounds(125, 570, 890, 70);
        answerAreaD.setBackground(new Color(110, 102, 102));
        answerAreaD.setForeground(new Color(255, 255, 255));
        answerAreaD.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        answerAreaD.setEditable(false);
        answerAreaD.setLineWrap(true);
        answerAreaD.setWrapStyleWord(true);
    }

    private void setExplanationArea() {
        explanationArea.setBounds(15, 650, 920, 150);
        explanationArea.setBackground(new Color(255, 255, 255, 255));
        explanationArea.setForeground(new Color(1, 11, 23));
        explanationArea.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        explanationArea.setEditable(false);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);

    }

    private void setButtonA() {
        buttonA.setBounds(0, 250, 100, 100);
        buttonA.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonA.setFocusable(false);
        buttonA.setText("A");
        buttonA.addActionListener(this);
    }

    private void setButtonB() {
        buttonB.setBounds(0, 350, 100, 100);
        buttonB.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.setText("B");
        buttonB.addActionListener(this);
    }

    private void setButtonC() {
        buttonC.setBounds(0, 450, 100, 100);
        buttonC.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.setText("C");
        buttonC.addActionListener(this);
    }

    private void setButtonD() {
        buttonD.setBounds(0, 550, 100, 100);
        buttonD.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonD.setFocusable(false);
        buttonD.setText("D");
        buttonD.addActionListener(this);
    }

    private void setButtonNext() {
        buttonNext.setBounds(390, 820, 150, 35);
        buttonNext.setFocusable(false);
        buttonNext.setFont(new Font("MV Boli", Font.ITALIC, 35));
        buttonNext.setText("Next");
        buttonNext.addActionListener(this);
    }

    private void setButtonStartAgain() {
        buttonStartAgain.setBounds(120, 820, 250, 35);
        buttonStartAgain.setFocusable(false);
        buttonStartAgain.setFont(new Font("MV Boli", Font.ITALIC, 30));
        buttonStartAgain.setText("Start Again");
        buttonStartAgain.addActionListener(this);
        buttonStartAgain.setVisible(false);
        buttonStartAgain.addActionListener(this);
    }

    private void setQuestionArea() {
        questionArea.setBounds(0, 50, 1050, 200);
        questionArea.setBackground(new Color(255, 255, 255, 255));
        questionArea.setForeground(new Color(1, 11, 23));
        questionArea.setFont(new Font("Times New Roman", Font.BOLD, 20));
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
    }

    private void setTextFiled() {
        textField.setBounds(0, 0, 1050, 50);
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(255, 255, 255));
        textField.setFont(new Font("Ink Free", Font.BOLD, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
    }

    private void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 950);
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
        Collections.shuffle(questionerList);
        return questionerList;
    }

    private static XSSFSheet getSheet() throws IOException {
        File exelFile = new File("C:\\ECBA mock test.xlsx");
        FileInputStream filestream = new FileInputStream(exelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(filestream);
        return workbook.getSheet("For development");
    }

}
