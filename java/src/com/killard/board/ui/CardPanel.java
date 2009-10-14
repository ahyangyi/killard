package com.killard.board.ui;

import com.killard.board.card.Card;
import com.killard.board.ui.event.CardSkillListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CardPanel extends JPanel {

    private Card card;

    private PlayerPanel playerPanel;

    private JButton skillBtn;

    public CardPanel(Card card, PlayerPanel playerPanel) {
        this.card = card;
        this.playerPanel = playerPanel;
        this.init();

    }

    private void init() {

        this.removeAll();
        skillBtn = new JButton("Skill");
        skillBtn.addActionListener(new CardSkillListener(playerPanel, card));

        setPreferredSize(new Dimension(100, 120));
        setLayout(null);
        if (card != null) {
            //add(new JLabel("A:" + card.getHealthChange()), BorderLayout.NORTH);
            //add(new JLabel(card.getName()), BorderLayout.CENTER);

            add(skillBtn);
            skillBtn.setBounds(20,80,60,30);
        }
    }

    public void setCard(Card card) {
        this.card = card;
        this.init();
        this.repaint();
    }

   
    public void addComp(Component comp, int x, int y, int width, int height){
         this.init();
        this.add(comp);
        comp.setBounds(x, y, width, height);

    }

    public void removeComp(Component comp){
        this.remove(comp);
    }

    public void paint(Graphics g) {
        try {
            g.setColor(new Color(100,200,100));
            //g.clearRect(0,0, this.getWidth(), this.getHeight());
            g.fillRect(0,0, this.getWidth(), this.getHeight());
            for(Component comp:this.getComponents()){
                comp.repaint();
            }
            if (card != null) {
                File file = new File("java/pic" + File.separator + card.getClass().getSimpleName() + ".jpg");
                System.out.println(file.getAbsoluteFile());
                Image img = ImageIO.read(file);
                g.drawImage(img, 0, 0, null);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void begin() {
        skillBtn.setEnabled(true);
        revalidate();
    }

    public void end() {
        skillBtn.setEnabled(false);
        revalidate();
    }
}
