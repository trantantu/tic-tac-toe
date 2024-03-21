/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
/**
 *
 * @author Acer
 */
public class TicTacToe extends javax.swing.JFrame {
    JButton [][] Button;
    Timer Time;
    Integer Second,Delay = 15;
    JTextArea History;
    boolean isXturn = true, isPvP = true;
    int Count_Game = 1, Count_Turn = 0;
    int[] TurnPlay;
    char[][] Board;
    String ChooseXorY = " ";
    JScrollPane Scroll;
    JFrame Notification = null;
    
     static class MovePos {
        int Row, Col;
    };
     public void GeneratedBoard() {

        Panel.setLayout(new GridLayout(3, 3));

        Second = Delay;

        Time = new Timer(1000, (ActionEvent e) -> {
            Second--;
            String temp = Second.toString();
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            Count_Time.setText(temp + " giây");
            if (Second == 0) {
                if (isXturn) {
                    WinGame("O");
                } else {
                    WinGame("X");
                }
            }
        });

        History = new JTextArea();
        History.setBackground(Color.white);
        History.setEditable(false);
        History.setFont(new Font("Cascadia Code", 0, 16));
        Scroll = new JScrollPane(History);
        Scroll.setBounds(532, 80, 350, 300);

        TurnPlay = new int[9];
        Board = new char[3][3];
        Button = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i][j] = ' ';
                Button[i][j] = new JButton(" ");
                Button[i][j].setFont(new Font("Antique", 1, 120));
                Button[i][j].setBackground(new Color(204, 255, 204));
                Button[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 102, 51), 2));
                Panel.add(Button[i][j]);
                int a = i, b = j;
                Button[a][b].addActionListener((ActionEvent e) -> {
                    if (" ".equals(Button[a][b].getText())) {
                        if (" ".equals(ChooseXorY) && !isPvP) {
                            JOptionPane.showMessageDialog(Notification, "Vui lòng chọn quân cờ ?",
                                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                        } else {
                            PlaySound("Press_BT");
                            Move(a, b);
                            if (isPvP) {
                                Change_Turns();
                            }
                            isXturn = !isXturn;
                            if (!isPvP) {
                                if (Count_Turn <= 8) {
                                    Move(ComputerMove(Board).Row, ComputerMove(Board).Col);
                                    isXturn = !isXturn;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public void PlaySound(String TypeSound) {
        String URL = null;
        if ("Press_BT".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tictactoe/Sound_BT.wav";
        }
        if ("Win".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tictactoe/Sound_Win.wav";
        }
        if ("Lose".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tictactoe/Sound_Lose.wav";
        }
        if ("Draw".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tictactoe/Sound_Draw.wav";
        }
        if ("Welcome".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tictactoe/Sound_Welcome.wav";
        }
        try {
            URL soundbyte = new File(URL).toURI().toURL();
            java.applet.AudioClip Sound = java.applet.Applet.newAudioClip(soundbyte);
            Sound.play();
        } catch (MalformedURLException ex) {
        }
    }
    
    public String CheckWin(){
        for(int i=0;i<3;i++){
            if(!" ".equals(Button[i][0].getText())&& (Button[i][0].getText().equals(Button[i][1].getText())) && (Button[i][0].getText().equals(Button[i][2].getText())))
                return Button[i][0].getText();
            if(!" ".equals(Button[0][i].getText())&& (Button[0][i].getText().equals(Button[1][i].getText())) && (Button[0][i].getText().equals(Button[2][i].getText())))
                return Button[0][i].getText();
        }
        if(!" ".equals(Button[0][0].getText())&& (Button[0][0].getText().equals(Button[1][1].getText()))&&(Button[0][0].getText().equals(Button[2][2].getText())) )
            return Button[0][0].getText();
        if(!" ".equals(Button[0][2].getText())&& (Button[0][2].getText().equals(Button[1][1].getText()))&&(Button[0][2].getText().equals(Button[2][0].getText())) )
            return Button[0][2].getText();
        return "N";
        
            }
    
    public void Change_Turns() {
        if (isXturn) {
            Current_Turn.setText("O");
            Current_Turn.setForeground(Color.RED);
        } else {
            Current_Turn.setText("X");
            Current_Turn.setForeground(Color.BLUE);
        }
    }
    
    
    public void WinGame(String N) {

        String Winner = N;

        if (!"N".equals(Winner) || Count_Turn == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button[i][j].setEnabled(false);
                }
            }

            HighlightWin();

            History.setText(History.getText()
                    + " ======= Ván " + Count_Game + " =======\n");
            if (isPvP) {
                History.setText(History.getText() + "  Chế độ chơi:\n    Chơi với người\n");
            } else {
                History.setText(History.getText() + "  Chế độ chơi:\n    Chơi với máy\n");
            }
            if (!"N".equals(Winner)) {
                History.setText(History.getText() + "  " + Winner + " Thắng\n");
            } else {
                History.setText(History.getText() + "  Hòa\n");
            }
            History.setText(History.getText() + "  Thế cờ:\n"
                    + "     -------------\n"
                    + "     | " + Button[0][0].getText() + " | " + Button[0][1].getText() + " | " + Button[0][2].getText() + " |\n"
                    + "     -------------\n"
                    + "     | " + Button[1][0].getText() + " | " + Button[1][1].getText() + " | " + Button[1][2].getText() + " |\n"
                    + "     -------------\n"
                    + "     | " + Button[2][0].getText() + " | " + Button[2][1].getText() + " | " + Button[2][2].getText() + " |\n"
                    + "     -------------\n");
            if ("O".equals(Winner) && Second == 0) {
                History.setText(History.getText() + "  X quá thời gian\n");
            }
            if ("X".equals(Winner) && Second == 0) {
                History.setText(History.getText() + "  O quá thời gian\n");
            }

            Count_Game++;
            Time.stop();
            if (!"N".equals(Winner)) {
                if (!isPvP) {
                    if (ChooseXorY == null ? Winner == null : ChooseXorY.equals(Winner)) {
                        Winner = "Bạn đã thắng";
                        PlaySound("Win");
                    } else {
                        Winner = "Máy đã thắng";
                        PlaySound("Lose");
                    }
                } else {
                    Winner = Winner + " Thắng";
                    PlaySound("Win");
                }

            } else {
                Winner = "Hòa";
                PlaySound("Draw");
            }
            int m = JOptionPane.showConfirmDialog(this, Winner + ". Bạn có muốn tiếp tục không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }
    
    public void MakeNewGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button[i][j].setEnabled(true);
                Button[i][j].setText(" ");
                Board[i][j] = ' ';
            }
        }
        isXturn = true;
        Count_Time.setText(Delay + " giây");
        Time.stop();
        Second = Delay;
        TurnPlay = new int[9];
        Count_Turn = 0;
        ChooseXorY = " ";
        X.setBackground(Color.white);
        O.setBackground(Color.white);
    }
    
    public void HighlightWin() {

        for (int i = 0; i < 3; i++) {
            if (!" ".equals(Button[i][0].getText()) && (Button[i][0].getText() == null ? Button[i][1].getText() == null : Button[i][0].getText().equals(Button[i][1].getText())) && (Button[i][1].getText() == null ? Button[i][2].getText() == null : Button[i][1].getText().equals(Button[i][2].getText()))) {
                Button[i][0].setEnabled(true);
                Button[i][1].setEnabled(true);
                Button[i][2].setEnabled(true);
            }
            if (!" ".equals(Button[0][i].getText()) && Button[0][i].getText().equals(Button[1][i].getText()) && Button[1][i].getText().equals(Button[2][i].getText())) {
                Button[0][i].setEnabled(true);
                Button[1][i].setEnabled(true);
                Button[2][i].setEnabled(true);
            }
        }

        if (!" ".equals(Button[0][0].getText()) && (Button[0][0].getText() == null ? Button[1][1].getText() == null : Button[0][0].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][2].getText() == null : Button[1][1].getText().equals(Button[2][2].getText()))) {
            Button[0][0].setEnabled(true);
            Button[1][1].setEnabled(true);
            Button[2][2].setEnabled(true);
        }

        if (!" ".equals(Button[0][2].getText()) && (Button[0][2].getText() == null ? Button[1][1].getText() == null : Button[0][2].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][0].getText() == null : Button[1][1].getText().equals(Button[2][0].getText()))) {
            Button[0][2].setEnabled(true);
            Button[1][1].setEnabled(true);
            Button[2][0].setEnabled(true);
        }

    }
    
    
    public void Move(int x, int y) {
        if (isXturn) {
            Button[x][y].setText("X");
            Button[x][y].setForeground(Color.BLUE);
            TurnPlay[Count_Turn] = x * 10 + y;
            Board[x][y] = 'X';
        } else {
            Button[x][y].setText("O");
            Button[x][y].setForeground(Color.RED);
            TurnPlay[Count_Turn] = x * 10 + y;
            Board[x][y] = 'O';
        }
        Second = Delay;
        Time.start();
        Count_Time.setText(Delay + " giây");
        Count_Turn++;
        WinGame(CheckWin());
    }

    
    
    public char CheckWinnerInMinimax(char[][] board) {

        if (board[0][0] != ' ' && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
            return board[0][0];
        } else if (board[1][0] != ' ' && board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
            return board[1][0];
        } else if (board[2][0] != ' ' && board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
            return board[2][0];
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
            return board[0][0];
        } else if (board[0][1] != ' ' && board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
            return board[0][1];
        } else if (board[0][2] != ' ' && board[0][2] == board[1][2] && board[1][2] == board[2][2]) {
            return board[0][2];
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        } else if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == ' ') {
                    return ' ';
                }
            }
        }

        return 'I';
    }

    public MovePos ComputerMove(char[][] Board) {

        MovePos BestMove = new MovePos();
        char Letter;

        if ("O".equals(ChooseXorY)) {
            Letter = 'X';
        } else {
            Letter = 'O';
        }

        int BestMoveScore = -1000000000;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (Board[x][y] == ' ') {
                    Board[x][y] = Letter;
                    int CurrentMoveScore = Minimax(Board, Letter, 0, false);
                    Board[x][y] = ' ';
                    if (CurrentMoveScore > BestMoveScore) {
                        BestMoveScore = CurrentMoveScore;
                        BestMove.Row = x;
                        BestMove.Col = y;
                    }
                }
            }
        }
        return BestMove;
    }

    public int Minimax(char[][] Board, char Letter, int Depth, boolean isMaximum) {

        char Result = CheckWinnerInMinimax(Board);

        if (Letter == 'X') {
            if (Result == 'X') {
                return 10 - Depth;
            } else if (Result == 'O') {
                return Depth - 10;
            }
        } else {
            if (Result == 'O') {
                return 10 - Depth;
            } else if (Result == 'X') {
                return Depth - 10;
            }
        }
        if (Result == 'I') {
            return 0;
        }

        if (isMaximum) {
            int BestMoveScoreMax = -1000000;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (Board[x][y] == ' ') {
                        Board[x][y] = Letter;
                        BestMoveScoreMax = Math.max(BestMoveScoreMax, Minimax(Board, Letter, Depth + 1, !isMaximum));
                        Board[x][y] = ' ';
                    }
                }
            }
            return BestMoveScoreMax;
        } else {
            int BestMoveScoreMin = 1000000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Board[i][j] == ' ') {
                        if (Letter == 'O') {
                            Board[i][j] = 'X';
                        } else {
                            Board[i][j] = 'O';
                        }
                        BestMoveScoreMin = Math.min(BestMoveScoreMin, Minimax(Board, Letter, Depth + 1, !isMaximum));
                        Board[i][j] = ' ';
                    }
                }
            }
            return BestMoveScoreMin;
        }
    }
    
    
    
    
    /**
     * Creates new form TicTacToe
     */
    public TicTacToe() {
        initComponents();
        this.setTitle("Tic Tac Toe");
        this.setIconImage(new ImageIcon("./src/main/java/com/mycompany/tictactoe/Icon.png").getImage());
        this.getContentPane().setBackground(new Color(204, 255, 204));
        this.setLocationRelativeTo(null);
       Label_ChooseXorO.setVisible(false);
        X.setVisible(false);
        O.setVisible(false);
      
        GeneratedBoard();
        PlaySound("Welcome");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TimeRemain = new javax.swing.JLabel();
        Count_Time = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Label_Current_Turn = new javax.swing.JLabel();
        Current_Turn = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Label_GameMode = new javax.swing.JLabel();
        GameModePVP = new javax.swing.JButton();
        GameModePVC = new javax.swing.JButton();
        Label_ChooseXorO = new javax.swing.JLabel();
        X = new javax.swing.JButton();
        O = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        NewGame = new javax.swing.JButton();
        ShowHistory = new javax.swing.JButton();
        Exit_Button = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        Continue = new javax.swing.JButton();
        Undo = new javax.swing.JButton();
        Panel = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        Custom = new javax.swing.JMenu();
        SetTime = new javax.swing.JMenuItem();
        Exit_Menu = new javax.swing.JMenuItem();
        About = new javax.swing.JMenu();
        Rule = new javax.swing.JMenuItem();

        jLabel1.setFont(new java.awt.Font("Modern No. 20", 1, 80)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Tic Tac Toe");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 204));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 5));

        Title.setFont(new java.awt.Font("Ravie", 1, 70)); // NOI18N
        Title.setForeground(new java.awt.Color(0, 102, 51));
        Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Title.setText("Tic Tac Toe");
        Title.setAlignmentX(0.5F);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Title, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 4));

        TimeRemain.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        TimeRemain.setText("Thời gian còn lại");

        Count_Time.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Count_Time.setForeground(new java.awt.Color(255, 51, 51));
        Count_Time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Count_Time.setText("15 giây");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeRemain, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(Count_Time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TimeRemain, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Count_Time)
                .addGap(27, 27, 27))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 4));

        Label_Current_Turn.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Label_Current_Turn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Current_Turn.setText("Lượt đi");

        Current_Turn.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        Current_Turn.setForeground(new java.awt.Color(255, 51, 51));
        Current_Turn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Current_Turn.setText("X");
        Current_Turn.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Label_Current_Turn, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(Current_Turn, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(Label_Current_Turn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Current_Turn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 4));

        Label_GameMode.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Label_GameMode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_GameMode.setText("Chế độ chơi");

        GameModePVP.setBackground(new java.awt.Color(153, 255, 102));
        GameModePVP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        GameModePVP.setText("Người");
        GameModePVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameModePVPActionPerformed(evt);
            }
        });

        GameModePVC.setBackground(new java.awt.Color(153, 255, 102));
        GameModePVC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        GameModePVC.setText("Máy");
        GameModePVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameModePVCActionPerformed(evt);
            }
        });

        Label_ChooseXorO.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Label_ChooseXorO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_ChooseXorO.setText("Chọn quân");

        X.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        X.setForeground(new java.awt.Color(255, 0, 51));
        X.setText("X");
        X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XActionPerformed(evt);
            }
        });

        O.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        O.setForeground(new java.awt.Color(51, 51, 255));
        O.setText("O");
        O.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_GameMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Label_ChooseXorO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(GameModePVP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GameModePVC, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(X, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(O, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGap(14, 14, 14))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_GameMode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GameModePVP, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(GameModePVC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Label_ChooseXorO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(O, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(X, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 255, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 4));

        NewGame.setBackground(new java.awt.Color(153, 255, 102));
        NewGame.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NewGame.setText("Ván mới");
        NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewGameActionPerformed(evt);
            }
        });

        ShowHistory.setBackground(new java.awt.Color(153, 255, 102));
        ShowHistory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ShowHistory.setText("Lịch sử");
        ShowHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHistoryActionPerformed(evt);
            }
        });

        Exit_Button.setBackground(new java.awt.Color(153, 255, 102));
        Exit_Button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Exit_Button.setText("Thoát");
        Exit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_ButtonActionPerformed(evt);
            }
        });

        Stop.setBackground(new java.awt.Color(153, 255, 102));
        Stop.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Stop.setText("Dừng lại");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        Continue.setBackground(new java.awt.Color(153, 255, 102));
        Continue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Continue.setText("Tiếp tục");
        Continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueActionPerformed(evt);
            }
        });

        Undo.setBackground(new java.awt.Color(153, 255, 102));
        Undo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Undo.setText("Quay lại");
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Continue, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Exit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ShowHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NewGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Stop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Continue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ShowHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Undo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Exit_Button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel.setBackground(new java.awt.Color(0, 102, 51));
        Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 51), 2));
        Panel.setAlignmentX(0.0F);
        Panel.setAlignmentY(0.0F);
        Panel.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        Custom.setText("Tùy chỉnh");

        SetTime.setText("Thời gian");
        SetTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetTimeActionPerformed(evt);
            }
        });
        Custom.add(SetTime);

        Exit_Menu.setText("Thoát");
        Exit_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_MenuActionPerformed(evt);
            }
        });
        Custom.add(Exit_Menu);

        MenuBar.add(Custom);

        About.setText("Thông tin");

        Rule.setText("Luật chơi");
        Rule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RuleActionPerformed(evt);
            }
        });
        About.add(Rule);

        MenuBar.add(About);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SetTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetTimeActionPerformed
    if (Count_Turn > 0 && "N".equals(CheckWin())) {
            JOptionPane.showMessageDialog(this, "Trận đấu vẫn đang diễn ra. Bạn không thể thay đổi thời gian giữa lượt trong lúc này",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            String Setdelay;
            Setdelay = JOptionPane.showInputDialog("Vui lòng nhập thời gian chờ:", 15);
            if (Setdelay != null) {
                if (Setdelay.matches("-?\\d+(\\.\\d+)?")) { //-?(int)(.
                    Delay = Integer.valueOf(Setdelay);
                    Second = Delay;
                    Count_Time.setText(Delay + " giây");
                }else
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập thời gian là chữ số!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_SetTimeActionPerformed

    private void XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XActionPerformed
    if (" ".equals(ChooseXorY)) {
            O.setBackground(Color.gray);
            ChooseXorY = "X";
            Current_Turn.setText("X");
            Current_Turn.setForeground(Color.BLUE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_XActionPerformed

    private void Exit_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_MenuActionPerformed
    if (Count_Turn > 0 && "N".equals(CheckWin())) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn thoát không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else
            System.exit(0);            
    }//GEN-LAST:event_Exit_MenuActionPerformed

    private void GameModePVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GameModePVPActionPerformed
if (!isPvP) {
            boolean Confirm = true;
            if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
                int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn đổi chế độ chơi và chơi ván mới không ?",
                        "Thông báo", JOptionPane.YES_NO_OPTION);
                if (m == JOptionPane.NO_OPTION) {
                    Confirm = false;
                }
            }
            if (Confirm) {
                isPvP = !isPvP;
                Label_ChooseXorO.setVisible(false);
                X.setVisible(false);
                O.setVisible(false);
                GameModePVP.setBackground(new Color(159, 168, 218));
                GameModePVC.setBackground(Color.gray);
                MakeNewGame();
            }
        }
             // TODO add your handling code here:
    }//GEN-LAST:event_GameModePVPActionPerformed

    private void GameModePVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GameModePVCActionPerformed
    if (isPvP) {
            boolean Confirm = true;
            if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
                int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn đổi chế độ chơi và chơi ván mới không ?",
                        "Thông báo", JOptionPane.YES_NO_OPTION);
                if (m == JOptionPane.NO_OPTION) {
                    Confirm = false;
                }
            }
            if (Confirm) {
                Label_ChooseXorO.setVisible(true);
                X.setVisible(true);
                O.setVisible(true);
                isPvP = !isPvP;
                GameModePVC.setBackground(new Color(159, 168, 218));
                GameModePVP.setBackground(Color.gray);
                MakeNewGame();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_GameModePVCActionPerformed

    private void OActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OActionPerformed
     if (" ".equals(ChooseXorY)) {
            X.setBackground(Color.gray);
            ChooseXorY = "O";
            Random generator = new Random();
            Move(generator.nextInt(3), generator.nextInt(3));
            Current_Turn.setText("O");
            Current_Turn.setForeground(Color.RED);
            isXturn = !isXturn;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_OActionPerformed

    private void ShowHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHistoryActionPerformed
    JDialog d = new JDialog(this, "Lịch sử");
        d.add(Scroll);
        d.setSize(350, 300);
        d.setBounds(200, 200, 275, 400);
        d.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_ShowHistoryActionPerformed

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
     boolean Confirm = true;
        if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn < 9 && Second != 0) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn tạo ván mới không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.NO_OPTION) {
                Confirm = false;
            }
        }
        if (Confirm) {
            MakeNewGame();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_NewGameActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
    Time.stop();        // TODO add your handling code here:
    }//GEN-LAST:event_StopActionPerformed

    private void ContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueActionPerformed
Time.start();        // TODO add your handling code here:
    }//GEN-LAST:event_ContinueActionPerformed

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
     if(isPvP)
        {
            if ((Count_Turn != 9 )&&(Count_Turn !=0)) {
           
                    Count_Turn--;
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].setText(" ");
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].updateUI();
                    
            }
            if(isXturn){
            Current_Turn.setText("O");
            Current_Turn.setForeground(Color.RED);
            }
            else
            {
               Current_Turn.setText("X");
            Current_Turn.setForeground(Color.BLUE); 
            }
            isXturn = !isXturn;
        }   
    else
         for (int i = 0; i < 2; i++) {
            if (Count_Turn != 9) {
                if ((Count_Turn > 0 && "X".equals(ChooseXorY)) || (Count_Turn > 1 && "O".equals(ChooseXorY))) {
                    Count_Turn--;
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].setText(" ");
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].updateUI();
                }
            }
        }// TODO add your handling code here:
     Second = Delay;
                    Time.start();
                    Count_Time.setText(Delay + " giây");
    }//GEN-LAST:event_UndoActionPerformed

    private void Exit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_ButtonActionPerformed
            // TODO add your handling code here:
               if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn thoát không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else
            System.exit(0);
    }//GEN-LAST:event_Exit_ButtonActionPerformed

    private void RuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RuleActionPerformed
            // TODO add your handling code here:
             JOptionPane.showConfirmDialog(this, """
                                            Tic Tac Toe l\u00e0 m\u1ed9t tr\u00f2 ch\u01a1i tr\u00ean b\u00e0n c\u1edd \u0111\u01a1n gi\u1ea3n.
                                             - C\u00f3 2 ch\u1ebf \u0111\u1ed9 ch\u01a1i: Ch\u01a1i v\u1edbi ng\u01b0\u1eddi v\u00e0 ch\u01a1i v\u1edbi m\u00e1y.
                                             - Tr\u00f2 ch\u01a1i \u0111\u01b0\u1ee3c ch\u01a1i tr\u00ean m\u1ed9t b\u1ea3ng g\u1ed3m 9 \u00f4 \u0111\u01b0\u1ee3c chia th\u00e0nh 3 h\u00e0ng v\u00e0 3 c\u1ed9t.
                                             - C\u00e1c ng\u01b0\u1eddi ch\u01a1i l\u1ea7n l\u01b0\u1ee3t \u0111\u00e1nh X ho\u1eb7c O v\u00e0o \u00f4 tr\u1ed1ng, qu\u00e2n X \u0111\u00e1nh tr\u01b0\u1edbc.
                                             - M\u1ed7i l\u01b0\u1ee3t ch\u01a1i ng\u01b0\u1eddi ch\u01a1i c\u00f3 15 gi\u00e2y \u0111\u1ec3 suy ngh\u0129.
                                             - H\u1ebft 15 gi\u00e2y v\u1eabn ch\u01b0a \u0111\u00e1nh n\u01b0\u1edbc ti\u1ebfp theo ng\u01b0\u1eddi ch\u01a1i s\u1ebd thua.
                                             - B\u1ea1n c\u00f3 th\u1ec3 thay \u0111\u1ed5i th\u1eddi gian gi\u1eefa c\u00e1c l\u01b0\u1ee3t trong ph\u1ea7n t\u00f9y ch\u1ec9nh.
                                             - Ng\u01b0\u1eddi ch\u01a1i n\u00e0o t\u1ea1o \u0111\u01b0\u1ee3c 3 k\u00ed t\u1ef1 gi\u1ed1ng nhau tr\u00ean c\u00f9ng m\u1ed9t h\u00e0ng ngang, d\u1ecdc, ch\u00e9o l\u00e0 ng\u01b0\u1eddi th\u1eafng cu\u1ed9c.
                                             - N\u1ebfu \u0111\u00e1nh h\u1ebft b\u00e0n c\u1edd kh\u00f4ng c\u00f3 ng\u01b0\u1eddi chi\u00ean th\u1eafng th\u00ec tr\u00f2 ch\u01a1i s\u1ebd k\u1ebft th\u00fac v\u1edbi k\u1ebft qu\u1ea3 h\u00f2a.
                                            """, "Luật Chơi",
                JOptionPane.CLOSED_OPTION);
    }//GEN-LAST:event_RuleActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu About;
    private javax.swing.JButton Continue;
    private javax.swing.JLabel Count_Time;
    private javax.swing.JLabel Current_Turn;
    private javax.swing.JMenu Custom;
    private javax.swing.JButton Exit_Button;
    private javax.swing.JMenuItem Exit_Menu;
    private javax.swing.JButton GameModePVC;
    private javax.swing.JButton GameModePVP;
    private javax.swing.JLabel Label_ChooseXorO;
    private javax.swing.JLabel Label_Current_Turn;
    private javax.swing.JLabel Label_GameMode;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JButton NewGame;
    private javax.swing.JButton O;
    private javax.swing.JPanel Panel;
    private javax.swing.JMenuItem Rule;
    private javax.swing.JMenuItem SetTime;
    private javax.swing.JButton ShowHistory;
    private javax.swing.JButton Stop;
    private javax.swing.JLabel TimeRemain;
    private javax.swing.JLabel Title;
    private javax.swing.JButton Undo;
    private javax.swing.JButton X;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
