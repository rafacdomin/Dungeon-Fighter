package dfight;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Jogo extends JFrame implements ActionListener {

    private String Nome, cursor, path = null,Hab1,Hab2,Hab3,Hab4,H1,H2,H3;
    private Itens Itens[] = new Itens[16];
    public static int le, le2,Cafe;
    private int Lutas[][] = new int[4][2], Armadilhas[][] = new int[4][2], luta[] = new int[2], e=0, X, Y, Xi, Yi,
            HpUpSoma,HpUpQnt,DefUpSoma,DefUpQnt,AtqUpSoma,AtqUpQnt,TextoBoss=0, roda = 0,counthab = 0, countbolsa, controladorsom = 0, fugir = 0,Aba=0,
            MostrarTexto=1, imgreg = 1, VA[][]= new int[4][2],contluta=0,p=0;
    private JTabbedPane TP = new JTabbedPane();
    private JTextField TF = new JTextField(20);
    private static JTextArea TA,TTrap;
    private Heroi Heroi;
    private JScrollPane SPBolsaLuta;
    private JProgressBar HpHeroiBar,Hpmob1Bar,Hpmob2Bar;
    public static Monstros mobs[][] = new Monstros[5][4];
    private JButton BNovoJogo, BNovoJogo2, BContinue, BDebug, BSair, BSair2, BPause, BVoltar, BMago, BGuerreiro, BArqueiro,
            BAtacar, BHabilidades, BBolsa, BFugir, BConfirma, BCancelar, BProximo, BAnterior, Bmob1, Bmob2, BHpUp, BDefUp, BAtqUp, BAtqDown, BHpDown,
            BDefDown, BH1, BH2, BH3,BMapa[][],BItens[][], BItensLuta[],BUpHab1,BUpHab2,BUpHab3,BBoss,BMostrarTexto,BPular,BTA,BTB,BTC,BTD,BBA,BBB,BBC,BBD,BFA,BFB,BFSim;
    private JPanel PJogo, Historia, JRegras, PMapa, PChar, PDA, PLuta, PRegras, JBoss, PBolsa, PBolsaLuta, PTrap, PBoss,Ptuto;
    private Clip clip;
    private ImageIcon link, fundobolsa;
    private JLabel regra;
    int imgf1 = 0,imgf2 = 0,hp=0;
    
    public Jogo(){
        super("Dungeon Fighter™");
        setSize(1100,650);
        setResizable(false);
        setLocationRelativeTo(null);
        BH1 = new JButton("Usos: "+Heroi.HUsos[0]);
        BH2 = new JButton("Usos: "+Heroi.HUsos[1]);
        BH3 = new JButton("Usos: "+Heroi.HUsos[2]);
        BH1.addActionListener(this);
        BH2.addActionListener(this);
        BH3.addActionListener(this);
        HpUpQnt=DefUpQnt=AtqUpQnt=0;
        Cafe=0;
        for(int i =0;i<4;i++){
            for(int j = 0;j<2;j++){
                VA[i][j]=0;
            }
        }
        
        regra = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/regras1.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        fundobolsa = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/fundobolsa.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
        
        TA = new JTextArea(10,40);
        TA.setText("                        It's Show Time!!");
        TA.setFont(new Font("Arial",Font.TYPE1_FONT,12));
        TA.setEditable(false);
        
        this.getContentPane().setBackground(new Color(169,169,169));
        PDA = new JPanel(new GridLayout(1,1));
        PDA.setBackground(Color.BLACK);
        PDA.setPreferredSize(new Dimension(150,0));
        MonstrosPos();
        Mapa();
        CM();
        Bolsa();
        CriarItens();
        GanhaItem("BombaFumaça");
        GanhaItem("Mapa");
        MenuInicial();
        this.setDefaultCloseOperation(3);
        setVisible(true);
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/icone.png")).getImage()); //Colocar icone do jogo aqui!
    }
    
    public void actionPerformed(ActionEvent in){
        Object o = in.getSource();
        
        // Menu Inicial
        if (o == BNovoJogo){
            Historia();
        }
        if (o == BNovoJogo2){
            p=0;
            Mapa();
            CM();
            MonstrosPos();
            EscolherHeroi();
            playsound("sound/GuardiansInferno.wav");
        }
        if (o == BContinue){
            Jogo();
        }
        if (o == BDebug){
            playsound("sound/GuardiansInferno.wav");
            p=1;
            EscolherHeroi();                
        }
        if (o == BSair){
            System.exit(0);
        }
        if (o == BSair2){
            MenuInicial();     
        }
        if (o == BVoltar){
            MenuInicial2();
        }
        if (o == BPause){
            controladorsom++;
            if(controladorsom % 2 != 0){
                clip.stop();
                BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/mudo.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
            }
            else{
                clip.start();
                BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/som.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
            }
        }
        
        // Escolher Heroi
        if (o == BMago){
            Heroi = new Mago(Nome);
            HpUpSoma = Heroi.HpTotal;
            DefUpSoma = Heroi.DefTotal;
            AtqUpSoma = Heroi.AtqTotal;
            Caracteristicas();
            Mapa();
            Tutorial();
        }
        if (o == BGuerreiro){
            Heroi = new Guerreiro(Nome);
            HpUpSoma = Heroi.HpTotal;
            DefUpSoma = Heroi.DefTotal;
            AtqUpSoma = Heroi.AtqTotal;
            Caracteristicas();
            Mapa();
            Tutorial();
        }
        if (o == BArqueiro){
            Heroi = new Arqueiro(Nome);
            HpUpSoma = Heroi.HpTotal;
            DefUpSoma = Heroi.DefTotal;
            AtqUpSoma = Heroi.AtqTotal;
            Caracteristicas();
            Mapa();
            Tutorial();
        }
        
        // Botoes no Mapa
        for(int i=0;i<5;i++){
            for(int j=0;j<10;j++){
                if (o == BMapa[i][j]){
                    if((i==(X+1)&&j==Y)||(i==(X-1)&&j==Y)||(j==(Y+1)&&i==X)||(j==(Y-1)&&i==X)){
                        Aba=0;
                    if(i==0&&j==0){
                        Fogueira();
                    }
                    if(i!=4)
                        if(Lutas[i][0] == j){
                            BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/MonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                            le=i;
                            le2=0;
                            if(mobs[le][le2].getHp()>0||mobs[le][le2+1].getHp()>0){
                                Object[] options = { "OK"};
                                int message = JOptionPane.showOptionDialog(null, "Hora da batalha!!!","Dungeon Fighter",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
                                if (message == JOptionPane.DEFAULT_OPTION){
                                    this.repaint();
                                    playsound("sound/find.wav");
                                    try { Thread.currentThread().sleep(2500);} catch (Exception e) {e.printStackTrace();}
                                    if(Heroi instanceof Arqueiro){
                                        if(Heroi.Nivel<6){
                                            mobs[le][le2].Stun = 2;
                                            mobs[le][le2+1].Stun = 2;
                                        }
                                        else{
                                            mobs[le][le2].Stun = 3;
                                            mobs[le][le2+1].Stun = 3;
                                        }
                                    }
                                    Luta();
                                }
                                else{
                                    this.repaint();
                                    playsound("sound/find.wav");
                                    try { Thread.currentThread().sleep(2500);} catch (Exception e) {e.printStackTrace();}
                                    if(Heroi instanceof Arqueiro){
                                    if(Heroi.Nivel<6){
                                            mobs[le][le2].Stun = 2;
                                            mobs[le][le2+1].Stun = 2;
                                        }
                                        else{
                                            mobs[le][le2].Stun = 3;
                                            mobs[le][le2+1].Stun = 3;
                                        }
                                    }
                                    Luta();
                                }
                            }
                        }
                    if(i!=4)
                        if(Lutas[i][1] == j){
                            BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/MonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));//mudar para icone de cada monstro
                            le=i;
                            le2=2;
                            if(mobs[le][le2].getHp()>0||mobs[le][le2+1].getHp()>0){
                                Object[] options = { "OK"};
                                int message =JOptionPane.showOptionDialog(null, "Hora da batalha!!!","Dungeon Fighter",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
                                if (message == JOptionPane.DEFAULT_OPTION){
                                    this.repaint();
                                    playsound("sound/find.wav");
                                    try { Thread.currentThread().sleep(2500);} catch (Exception e) {e.printStackTrace();}
                                    if(Heroi instanceof Arqueiro){
                                    if(Heroi.Nivel<6){
                                            mobs[le][le2].Stun = 2;
                                            mobs[le][le2+1].Stun = 2;
                                        }
                                        else{
                                            mobs[le][le2].Stun = 3;
                                            mobs[le][le2+1].Stun = 3;
                                        }
                                    }
                                    Luta();
                                }
                                else{
                                    this.repaint();
                                    playsound("sound/find.wav");
                                    try { Thread.currentThread().sleep(2500);} catch (Exception e) {e.printStackTrace();}
                                    if(Heroi instanceof Arqueiro){
                                    if(Heroi.Nivel<6){
                                            mobs[le][le2].Stun = 2;
                                            mobs[le][le2+1].Stun = 2;
                                        }
                                        else{
                                            mobs[le][le2].Stun = 3;
                                            mobs[le][le2+1].Stun = 3;
                                        }
                                    }
                                    Luta();
                                }
                            }
                        }
                    if(i!=4)
                        if(Armadilhas[i][0] == j&&VA[i][0]==0){
                            le=i;
                            BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/DeusaTrap.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                            Object[] options = { "OK"};
                            int message =JOptionPane.showOptionDialog(null, "It's a Trap!!","Dungeon Fighter",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
                                if (message == JOptionPane.DEFAULT_OPTION){
                                    this.repaint();
                                    Armadilhas();
                                    VA[i][0]=1;
                                }
                                else{
                                    this.repaint();
                                    Armadilhas();
                                    VA[i][0]=1;
                                }
                        }
                    if(i!=4)
                    if(Armadilhas[i][1]==j&&VA[i][1]==0){
                        le=i;
                        BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/DeusaTrap.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                            Object[] options = { "OK"};
                            int message =JOptionPane.showOptionDialog(null, "Sala Bônus!!","Dungeon Fighter",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
                                if (message == JOptionPane.DEFAULT_OPTION){
                                    this.repaint();
                                    Bonus();
                                    VA[i][1]=1;
                                }
                                else{
                                    this.repaint();
                                    Bonus();
                                    VA[i][1]=1;
                                }
                    }
                    if(i==4&&j==9)
                        BossFala();
                        Mover(i,j);
                    }
                }}}
        
        // Botoes Luta
        if (o == BFugir){
            if(Itens[3].Qnt>0){
            Itens[3].usarItem();
            fugir = 1;
            Mover(Xi,Yi);
            Aba=0;
            Jogo();
            TextoBoss=0;
            }
            else
                JOptionPane.showMessageDialog(null, "Sem bombas de fumaça","Fugir",JOptionPane.WARNING_MESSAGE);
        }
        if (o == BAtacar){
            if("Cursoratq".equals(cursor)){
                this.cursorN();
                e=0;
            }
            else{
                this.cursoratq();
                e=1;
            }
        }
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(o==BItens[i][j]){
                    for(int k=0;k<10;k++){
                        if(BItens[i][j].getIcon().equals(Itens[k].ItIcon)||BItens[i][j].getIcon().equals(Itens[k].ItIconDes)){
                            if(((TP.getComponent(0)==PJogo&&Itens[k].Tipo==1)||Itens[k].Tipo==3||((TP.getComponent(0)==PLuta||TP.getComponent(0)==JBoss)&&Itens[k].Tipo==2))&&Itens[k].Qnt>0){
                                Itens[k].usarItem();
                                if(TP.getComponent(0)==PJogo){
                                    if(Itens[k].Nome.equals("Mapa")){
                                        for(int l=0;l<4;l++){
                                        if(Lutas[l][0]==Y||Lutas[l][1]==Y)
                                            BMapa[l][Y].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/MonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                                        if(Armadilhas[l][0]==Y||Armadilhas[l][1]==Y)
                                            BMapa[l][Y].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/DeusaTrap.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                                    }
                                    }
                                    Aba=1;
                                    Jogo();
                                }
                                if(TP.getComponent(0)==PLuta){
                                    Aba = 0;
                                    this.MobsAtq(1);
                                
                                }
                                if(TP.getComponent(0)==JBoss){
                                    Aba = 0;
                                    BossAtq();
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i=0;i<5;i++){
            if(o==BItensLuta[i]){
                 for(int k=0;k<10;k++){
                        if(BItensLuta[i].getIcon().equals(Itens[k].ItIcon)){
                            if((Itens[k].Tipo==2||Itens[k].Tipo==3)&&Itens[k].Qnt>0){
                                Itens[k].usarItem();
                                Aba=0;
                                if(TP.getComponent(0)==PLuta){
                                    Aba = 0;
                                    this.MobsAtq(1);
                                
                                }
                                if(TP.getComponent(0)==JBoss){
                                    Aba = 0;
                                    BossAtq();
                            }
                        }
                 }
            }
        }
        }

        if(o == BBolsa){
            countbolsa++;
            PDA.removeAll();
            Aba=0;
            PDA.setBackground(Color.BLACK);
            PDA.add(SPBolsaLuta);
            
            if(countbolsa%2!=0){
                for(int i=0;i<6;i++){
                    BItensLuta[i].setVisible(true);
            }
                SPBolsaLuta.setVisible(true);
            }
            else{
                for(int i=0;i<6;i++){
                    BItensLuta[i].setVisible(false);
            }
                SPBolsaLuta.setVisible(false);
            }
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
        }
        if (o == BHabilidades){
            counthab++;
            PDA.removeAll();
            JPanel PDA1 = new JPanel(new GridLayout(3,1));
            PDA1.setBackground(Color.BLACK);
            PDA.setBackground(Color.BLACK);
            PDA1.add(BH1);
            PDA1.add(BH2);
            PDA1.add(BH3);
            PDA.add(PDA1);
            Aba=0;if(TP.getComponent(0)==PLuta)
                    Luta();
                if(TP.getComponent(0)==JBoss)
                    JanelaBoss();
            
            if(counthab%2!=0){
                BH1.setVisible(true);
                BH2.setVisible(true);
                BH3.setVisible(true);
            }
            else{
                BH1.setVisible(false);
                BH2.setVisible(false);
                BH3.setVisible(false);
            }
        }
        if (o == BH1){
            if(MostrarTexto==1)
                TA.setText("                        It's Show Time!!");
            if(Heroi.HUsos[0]>0){
                Heroi.HUsos[0]--;
                Heroi.Habilidade1(mobs[le][le2], mobs[le][le2+1]);
                if(TP.getComponent(0)==PLuta)
                    MobsAtq(1);
                if(TP.getComponent(0)==JBoss)
                    BossAtq();
            }
            else if(Heroi instanceof Mago && Heroi.H[3] > 0 && Heroi.HUsosTotal[0]>0){
                Heroi.H[3]=0;
                Heroi.Habilidade1(mobs[le][le2], mobs[le][le2+1]);
                if(TP.getComponent(0)==PLuta)
                    MobsAtq(1);
                if(TP.getComponent(0)==JBoss)
                    BossAtq();
            }
            counthab++;
            BH1.setVisible(false);
            BH2.setVisible(false);
            BH3.setVisible(false);
        }
        
        if (o == BH2){
            if(Heroi.HUsos[1]>0){
                Heroi.HUsos[1]--;
                if(Heroi instanceof Guerreiro){
                    if(MostrarTexto==1)
                        TA.setText("                        It's Show Time!!");
                        Heroi.Habilidade2(new Monstros("",0,new ImageIcon("")));
                        if(TP.getComponent(0)==PLuta)
                    MobsAtq(1);
                if(TP.getComponent(0)==JBoss)
                    BossAtq();
                }
                else
                e=2;
            }
            else if(Heroi instanceof Mago && Heroi.H[3] > 0 && Heroi.HUsosTotal[1]>0){
                Heroi.H[3] = 0;
                e=2;
            }
            counthab++;
            BH1.setVisible(false);
            BH2.setVisible(false);
            BH3.setVisible(false);
        }
        if (o == BH3){
            if(Heroi.HUsos[2]>0){
                Heroi.HUsos[2]--;
                if(Heroi instanceof Guerreiro){
                    if(MostrarTexto==1)
                        TA.setText("                        It's Show Time!!");
                    Heroi.Habilidade3(new Monstros("",0,new ImageIcon("")));
                    if(TP.getComponent(0)==PLuta)
                    MobsAtq(1);
                if(TP.getComponent(0)==JBoss)
                    BossAtq();
                }
                else
                e = 3;
            }
            else if(Heroi instanceof Mago && Heroi.H[3] > 0 && Heroi.HUsosTotal[2]>0){
                Heroi.H[3] = 0;
                e = 3;
            }
            counthab++;
            BH1.setVisible(false);
            BH2.setVisible(false);
            BH3.setVisible(false);
        }
        
        if (o == Bmob1 && e>0){
            this.cursorN();
            if(e==1){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Atacar(mobs[le][le2]);
            }
            if(e==2){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade2(mobs[le][le2]);
            }
            if(e==3){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade3(mobs[le][le2]);
            }
            MobsAtq(1);
            e=0;
        }
        
        if (o == Bmob2 && e>0){
            this.cursorN();
            if(e==1){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Atacar(mobs[le][(le2+1)]);
            }
            if(e==2){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade2(mobs[le][le2+1]);
            }
            if(e==3){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade3(mobs[le][le2+1]);
            }
            MobsAtq(2);
            e=0;
        }
        if(o==BBoss){
            this.cursorN();
            if(e==1){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Atacar(mobs[4][1]);
            }
            if(e==2){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade2(mobs[4][1]);
            }
            if(e==3){
                if(MostrarTexto==1)
                    TA.setText("                        It's Show Time!!");
                Heroi.Habilidade3(mobs[4][1]);
            }
            BossAtq();
            e=0;
        }
        // Botoes Caracteristicas:
        if(o==BHpUp){
            if(Heroi.PS>0){
            HpUpSoma+=10;
            HpUpQnt+=1;
            Heroi.PS--;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            }
        }
        if(o==BDefUp){
            if(Heroi.PS>0){
            DefUpSoma+=1;
            DefUpQnt+=1;
            Heroi.PS--;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            }
        }
        if(o==BAtqUp){
            if(Heroi.PS>0){
            AtqUpSoma+=1;
            AtqUpQnt+=1;
            Heroi.PS--;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            }
            
        }
        if(o==BHpDown){
            if(Heroi.PS<Heroi.PSTotal&&HpUpQnt>0){
            HpUpSoma-=10;
            HpUpQnt-=1;
            Heroi.PS++;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            }
        }
        if(o==BDefDown){
            if(Heroi.PS<Heroi.PSTotal&&DefUpQnt>0){
            DefUpSoma-=1;
            DefUpQnt-=1;
            Heroi.PS++;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            }
        }
        if(o== BAtqDown){
            if(Heroi.PS<Heroi.PSTotal&&AtqUpQnt>0){
            AtqUpSoma-=1;
            AtqUpQnt-=1;
            Heroi.PS++;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            }
        }
        if(o==BConfirma){
            Heroi.HpTotal=HpUpSoma;
            Heroi.DefTotal=DefUpSoma;
            Heroi.AtqTotal=AtqUpSoma;
            HpUpQnt = 0;
            AtqUpQnt = 0;
            DefUpQnt = 0;
            Heroi.PSTotal=Heroi.PS;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
        }
        if(o==BCancelar){
            HpUpSoma=Heroi.HpTotal;
            DefUpSoma=Heroi.DefTotal;
            AtqUpSoma=Heroi.AtqTotal;
            HpUpQnt = 0;
            AtqUpQnt = 0;
            DefUpQnt = 0;
            Heroi.PS=Heroi.PSTotal;
            Aba=1;
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
        }
        if(o==BUpHab1){
            if(Heroi.HLevel[0]==4){
                JOptionPane.showMessageDialog(null, "HABILIDADE JÁ MAXIMIZADA!!","Habilidade",JOptionPane.OK_OPTION);
            }
            else
            if(Heroi.PH>0){
                if(JOptionPane.showConfirmDialog(null, "Aumentar Habilidade?","Habilidade",JOptionPane.YES_NO_OPTION)== 0){
                    Heroi.upHab1();
                    Heroi.PH--;
                    Aba=1;
                    if(TP.getComponent(0)==PJogo)
                        Jogo();
                    if(TP.getComponent(0)==PLuta)
                      Luta();
                    if(TP.getComponent(0)==JBoss)
                       JanelaBoss();
                    if(TP.getComponent(0)==PTrap)
                        Armadilhas();
                }
            }
        }
        if(o==BUpHab2){
            if(Heroi.HLevel[1]==4){
                JOptionPane.showMessageDialog(null, "HABILIDADE JÁ MAXIMIZADA!!","Habilidade",JOptionPane.OK_OPTION);
            }
            else
            if(Heroi.PH>0){
                if(JOptionPane.showConfirmDialog(null, "Aumentar Habilidade?","Habilidade",JOptionPane.YES_NO_OPTION)== 0){
                Heroi.upHab2();
                Heroi.PH--;
                Aba=1;
                if(TP.getComponent(0)==PJogo)
                    Jogo();
                if(TP.getComponent(0)==PLuta)
                    Luta();
                if(TP.getComponent(0)==JBoss)
                    JanelaBoss();
                if(TP.getComponent(0)==PTrap)
                    Armadilhas();
                }
            }
        }
        if(o==BUpHab3){
            if(Heroi.PH>0){
                if(Heroi.HLevel[2]==4){
                JOptionPane.showMessageDialog(null, "HABILIDADE JÁ MAXIMIZADA!!","Habilidade",JOptionPane.OK_OPTION);
            }
            else
                if(JOptionPane.showConfirmDialog(null, "Aumentar Habilidade?","Habilidade",JOptionPane.YES_NO_OPTION)== 0){
                Heroi.upHab3();
                Heroi.PH--;
                Aba=1;
                if(TP.getComponent(0)==PJogo)
                    Jogo();
                if(TP.getComponent(0)==PLuta)
                    Luta();
                if(TP.getComponent(0)==JBoss)
                    JanelaBoss();
                if(TP.getComponent(0)==PTrap)
                    Armadilhas();
                }
            }
        }
        if(o==BMostrarTexto){
            if(MostrarTexto==1){
                MostrarTexto=0;
            }
            else{
                MostrarTexto=1;
            }
        }
        //Regras
        if(o==BProximo){
            if(imgreg==1){
                regra = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/regras2.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
                imgreg++;
                Aba = 2;
                Regras();
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
                
            }
            else if(imgreg==2){
                regra = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/regras3.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
                imgreg++;
                Aba = 2;
                Regras();
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            }
            
        }
        if(o==BAnterior){
            if(imgreg==3){
                regra = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/regras2.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
                imgreg--;
                Aba = 2;
                Regras();
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            }
            else if(imgreg==2){
                regra = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/regras1.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
                imgreg--;
                Aba = 2;
                Regras();
            if(TP.getComponent(0)==PJogo)
                Jogo();
            if(TP.getComponent(0)==PLuta)
                Luta();
            if(TP.getComponent(0)==PTrap)
                Armadilhas();
            if(TP.getComponent(0)==JBoss)
                JanelaBoss();
            }
            
        }
        //Historia
        if( o == BPular){
        this.cursorN();
        playsound("sound/GuardiansInferno.wav");
        EscolherHeroi();
        }
        //Armadilhas
        if(o==BTA){
            if(le==0){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu prêmio:\n\n\n5x Poção de Vida obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                if(hp!=0)
                    Itens[0].Qnt = Itens[0].Qnt + 5;
                else{
                    GanhaItem("PocaoHp");
                    hp=1;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas terei que tirar um pouco do seu HP\n\n\n50% de HP perdido! ","Punição!",JOptionPane.WARNING_MESSAGE);
                Heroi.Hp*=0.5;
            }
            Aba = 0;
            Jogo();
        }
        if(o==BTB){
            if(le==1){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu prêmio:\n\n\n5x Poção de Vida obtida!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                if(hp!=0)
                    Itens[0].Qnt = Itens[0].Qnt + 5;
                else{
                    GanhaItem("PocaoHp");
                    hp = 1;
                }
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas terei que tirar um pouco do seu HP\n\n\n50% de HP perdido! ","Punição!",JOptionPane.WARNING_MESSAGE);
                Heroi.Hp*=0.5;
            }
            Aba = 0;
            Jogo();
        }
        if(o==BTC){
            if(le==2){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu prêmio:\n\n\n2x Dinamite obtida!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("tnt");
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas terei que tirar um pouco do seu HP\n\n\n50% de HP perdido! ","Punição!",JOptionPane.WARNING_MESSAGE);
                Heroi.Hp*=0.5;
            }
            Aba = 0;
            Jogo();
        }
        if(o==BTD){
            if(le==3){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu prêmio:\n\n\n1x Café obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("Cafe");
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas terei que tirar um pouco do seu HP\n\n\n50% de HP perdido! ","Punição!",JOptionPane.WARNING_MESSAGE);
                Heroi.Hp*=0.5;
            }
            Aba = 0;
            Jogo();
        }
        //Bonus
        if(o==BBD){
            if(le==0){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu magnífico prêmio:\n\n\n1x Presente obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("Presente");
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas você acaba de perder um dos melhores itens da Dungeon! ","Você Errou",JOptionPane.WARNING_MESSAGE);
            }
            Aba = 0;
            Jogo();
        }
        if(o==BBC){
            if(le==1){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu magnífico prêmio:\n\n\n1x Pena da Ressurreição obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("Pena");
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas você acaba de perder um dos melhores itens da Dungeon! ","Você Errou",JOptionPane.WARNING_MESSAGE);
            }
            Aba = 0;
            Jogo();
        }
        if(o==BBB){
            if(le==2){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu magnífico prêmio:\n\n\n1x Anel de Poder obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("AnelAtq");
                Heroi.AtqTotal++;
                Heroi.Atq++;
                AtqUpSoma++;
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas você acaba de perder um dos melhores itens da Dungeon! ","Você Errou",JOptionPane.WARNING_MESSAGE);
            }
            Aba = 0;
            Jogo();
        }
        if(o==BBA){
            if(le==3){
                JOptionPane.showMessageDialog(null, "Parabéns, você é bem observador, aqui está o seu magnífico prêmio:\n\n\n1x Anel da Guarda obtido!!! ","Recompensa",JOptionPane.WARNING_MESSAGE);
                GanhaItem("AnelDef");
                Heroi.DefTotal++;
                Heroi.Def++;
                DefUpSoma++;
            }else{
                JOptionPane.showMessageDialog(null, "Ahh, é uma pena, mas você acaba de perder um dos melhores itens da Dungeon! ","Você Errou",JOptionPane.WARNING_MESSAGE);
            }
            Aba = 0;
            Jogo();
        }
        //Boss Fala
        if(o==BFA){
            if(TextoBoss== 2){
                Final1();
            }else if(TextoBoss == 0){
                TextoBoss=2;
                BossFala2();
            }
            if(TextoBoss == 1){
                JanelaBoss();
            }
        }
        if(o==BFB){
            if(TextoBoss == 0){
                TextoBoss=1;
                BossFala2();
            }
            if(TextoBoss== 2){
                Final1();
            }
            
        }
        if(o==BFSim){
            if(TextoBoss == 1){
                JanelaBoss();
            }
            if(TextoBoss== 2){
                Final1();
            }
            if(TextoBoss == 3){
                Final2();
            }
        }
    }
    
    
    private void MenuInicial(){
        getContentPane().removeAll();
        this.cursorN();
        BNovoJogo = new JButton();
        BNovoJogo.setPreferredSize(new Dimension(100,100));
        BNovoJogo.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/novojogobutton.png")));
        BNovoJogo.addActionListener(this);
        BDebug = new JButton();
        BDebug.setPreferredSize(new Dimension(100,100));
        BDebug.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/debugbutton.png")));
        BDebug.addActionListener(this);
        BSair = new JButton();
        BSair.setPreferredSize(new Dimension(100,100));
        BSair.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/sairbutton.png")));
        BSair.addActionListener(this);
        JPanel MenuInicial = new JPanel(new BorderLayout());
        JPanel CentroMI = new JPanel(new GridLayout(3,1));
        CentroMI.setPreferredSize(new Dimension(100,300));
        CentroMI.add(BNovoJogo);
        CentroMI.add(BDebug);
        CentroMI.add(BSair);
        
        JLabel cima = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/cima.jpg")),JLabel.CENTER); //Substituir os labels por imagens!!!!
        cima.setPreferredSize(new Dimension(0,299));
        JLabel baixo = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/baixo.png")),JLabel.CENTER);
        baixo.setPreferredSize(new Dimension(0,101));
        JLabel direita = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/direita.jpg")),JLabel.CENTER);
        direita.setPreferredSize(new Dimension(305,0));
        JLabel esquerda = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/esquerda.jpg")),JLabel.CENTER);
        esquerda.setPreferredSize(new Dimension(310,0));
        
        MenuInicial.add(CentroMI,BorderLayout.CENTER);
        MenuInicial.add(cima,BorderLayout.NORTH);
        MenuInicial.add(baixo,BorderLayout.SOUTH);
        MenuInicial.add(direita,BorderLayout.EAST);
        MenuInicial.add(esquerda,BorderLayout.WEST);
        
        //MenuInicial.setBackground(new Color(0,0,100));   MUDA COR!!!!!! //PODES UTILIZAR AS CONSTANTES DEFINIDAS DA CLASSE Color
        //OU CONSTRUIR A SUA COM new Color(int red,int green,int blue);
        getContentPane().add(MenuInicial);
        playsound("sound/MrBlueSky.wav");
        this.paintComponents(getGraphics());
    }
    
    private void EscolherHeroi(){
        
        getContentPane().removeAll();
        Aba = 0;
        JLabel[] label = new JLabel[5];
        label[0] = new JLabel("Nome: ");
        label[0].setBackground(new Color(169,169,169));
        label[1] = new JLabel("");
        label[1].setPreferredSize(new Dimension(650,0));  
        
        BSair2 = new JButton("SAIR");
        BSair2.setBackground(new Color(156,156,156));
        
        JPanel A = new JPanel(new BorderLayout());
        JPanel B = new JPanel();
        B.setPreferredSize(new Dimension(0,45));
        BMago = new JButton("");
        BMago.addActionListener(this);
        BMago.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/magobutton.png")));
        BMago.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BMago.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/statusmagobutton.png"))); 
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BMago.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/magobutton.png")));
            }  
        });
        BGuerreiro = new JButton("");
        BGuerreiro.addActionListener(this);
        BGuerreiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/guerreirabutton.png")));
        BGuerreiro.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BGuerreiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/statusguerreirabutton.png"))); 
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BGuerreiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/guerreirabutton.png")));
            }  
        });
        BArqueiro = new JButton("");
        BArqueiro.addActionListener(this);
        BArqueiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/arqueirabutton.png")));
        BArqueiro.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BArqueiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/statusarqueirabutton.png"))); 
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BArqueiro.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/arqueirabutton.png")));
            }  
        });
        JPanel MenuHerois = new JPanel(new GridLayout(1,5));
        MenuHerois.add(BMago);
        MenuHerois.add(BGuerreiro);
        MenuHerois.add(BArqueiro);
        BPause = new JButton();
        BPause.setBackground(new Color(156,156,156));
        
        TF.setPreferredSize(new Dimension(0,25));
        
        B.setBackground(new Color(169,169,169));
        B.add(label[0]);
        B.add(TF);
        B.add(label[1]);
        B.add(BPause);
        B.add(BSair2);
        
        if(controladorsom % 2 != 0){ //mudando icone som
            BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/mudo.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        }
        else{
            BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/som.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
        }
        
        JPanel C = new JPanel(new BorderLayout());
        JLabel label6 = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/EscolherHeroi/personagenscima.png")),JLabel.CENTER);
        label6.setPreferredSize(new Dimension(0,160));
        C.add(label6,BorderLayout.NORTH);
        C.add(B,BorderLayout.SOUTH);
        A.add(C,BorderLayout.NORTH);
        A.add(MenuHerois,BorderLayout.CENTER);
        
        getContentPane().add(A);
        TF.setText("");
        TF.requestFocus();
        this.paintComponents(getGraphics());
        BSair2.addActionListener(this);
        BPause.addActionListener(this);
    }
    
    private void Jogo(){
        if(TF.getText().toCharArray().length > 10){
            JOptionPane.showMessageDialog(null, "Digite um Nome com no máximo 10 caracteres!! ","Nome",JOptionPane.WARNING_MESSAGE);
            EscolherHeroi();
        }else if(TF.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Digite um Nome!! ","Nome",JOptionPane.WARNING_MESSAGE);
            EscolherHeroi();
        }
        else{
            TP.removeAll();
            getContentPane().removeAll();
            
            UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
            UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
            
            HpHeroiBar = new JProgressBar(0,Heroi.HpTotal);
            HpHeroiBar.setValue(Heroi.Hp);
            HpHeroiBar.setStringPainted(true);
            HpHeroiBar.setString(""+Heroi.Hp+"/"+Heroi.HpTotal);
            HpHeroiBar.setForeground(Color.GREEN);
            
            
            Nome = TF.getText();
            PJogo = new JPanel(new BorderLayout());
            Caracteristicas();
            Regras();
            PJogo.add(PMapa,BorderLayout.CENTER);
            JLabel label2 = new JLabel("");
            //JLabel label = new JLabel(Nome + "        " + "Hp: " + Heroi.getHp(),null,JLabel.LEFT);
            JLabel label = new JLabel(Nome,null,JLabel.LEFT);
            label.setPreferredSize(new Dimension(120,50));
            label2.setPreferredSize(new Dimension(580,50));
            
            BVoltar = new JButton("MENU");
            BSair = new JButton("SAIR");
            BPause = new JButton();
            if(controladorsom % 2 != 0){ //mudando icone som
                BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/mudo.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
            }
            else{
                BPause.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/som.png")).getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
            }
            
            
            JPanel A = new JPanel();
            A.add(label);
            A.add(HpHeroiBar);
            A.add(label2);
            A.add(BPause);
            A.add(BVoltar);
            A.add(BSair);
            
           
            PJogo.add(A,BorderLayout.NORTH);
            
            TP.add("      Mapa      ",PJogo);
            TP.add("      Char      ",PChar);
            TP.add("      Regras    ",PRegras);
            
            if(Aba==1)
                TP.setSelectedIndex(1);
            else if(Aba==2)
                TP.setSelectedIndex(2);
            else
            playsound("sound/lavendertown.wav");
            
            TP.addMouseListener(new MouseAdapter() {//bagunça do Rafael  
                @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    if(TP.getSelectedComponent()== PChar){ 
                        playsound("sound/Cantina.wav");
                    }
                    if(TP.getSelectedComponent()==PJogo)
                        playsound("sound/lavendertown.wav");
                    if(TP.getSelectedComponent()==PLuta)
                        playsound("sound/B.wav");
                    if(TP.getSelectedComponent()==PTrap)
                        playsound("sound/trap.wav");
                    if(TP.getSelectedComponent()==PBoss)
                        playsound("sound/bossfala.wav");
                    if(TP.getSelectedComponent()==JBoss)
                        playsound("sound/Boss.wav");
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    if(TP.getSelectedComponent()== PChar){ 
                        playsound("sound/Cantina.wav");
                    }
                    if(TP.getSelectedComponent()==PJogo)
                        playsound("sound/lavendertown.wav");
                    if(TP.getSelectedComponent()==PLuta)
                        playsound("sound/B.wav");
                    if(TP.getSelectedComponent()==PTrap)
                        playsound("sound/trap.wav");
                    if(TP.getSelectedComponent()==PBoss)
                        playsound("sound/bossfala.wav");
                    if(TP.getSelectedComponent()==JBoss)
                        playsound("sound/Boss.wav");
                }
                @Override
                public void mouseClicked(MouseEvent arg0){
                    if(TP.getSelectedComponent()== PChar){ 
                        playsound("sound/Cantina.wav");
                    }
                    if(TP.getSelectedComponent()==PJogo)
                        playsound("sound/lavendertown.wav");
                    if(TP.getSelectedComponent()==PLuta)
                        playsound("sound/B.wav");
                    if(TP.getSelectedComponent()==PTrap)
                        playsound("sound/trap.wav");
                    if(TP.getSelectedComponent()==PBoss)
                        playsound("sound/bossfala.wav");
                    if(TP.getSelectedComponent()==JBoss)
                        playsound("sound/Boss.wav");
                }
                @Override
                public void mouseReleased(MouseEvent arg0){
                    if(TP.getSelectedComponent()== PChar){
                        playsound("sound/Cantina.wav");
                    }
                    if(TP.getSelectedComponent()==PJogo)
                        playsound("sound/lavendertown.wav");
                    if(TP.getSelectedComponent()==PLuta)
                        playsound("sound/B.wav");
                    if(TP.getSelectedComponent()==PTrap)
                        playsound("sound/trap.wav");
                    if(TP.getSelectedComponent()==PBoss)
                        playsound("sound/bossfala.wav");
                    if(TP.getSelectedComponent()==JBoss)
                        playsound("sound/Boss.wav");
                }
            });//bagunça do rafael acaba aqui
            getContentPane().add(TP);
            this.paintComponents(getGraphics());    
            BVoltar.addActionListener(this);
            BSair.addActionListener(this);
            BPause.addActionListener(this);
    }
}
    
    private void Mapa(){
        PMapa = new JPanel(new GridLayout(5,10));
        BMapa = new JButton[5][10];
        for(int i=0;i<5;i++){
            for(int j = 0; j<10;j++){
                if(i==0&&j==0){
                    BMapa[i][j] = new JButton(link);
                    Y = 0;
                    X = 0;
                    
                }else if(i==4&&j==9){
                    BMapa[4][9] = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/boss.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                }else{
                    BMapa[i][j] = new JButton("");
                    
                        BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/fundodomapa.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                    
                    if(p!=0){
                        if(i<4){
                        if(Lutas[i][0]==j||Lutas[i][1]==j)
                            BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/MonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                        if(Armadilhas[i][0]==j||Armadilhas[i][1]==j)
                            BMapa[i][j].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/DeusaTrap.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
                        }}
                }BMapa[i][j].addActionListener(this);
                PMapa.add(BMapa[i][j]);
            }
        }
    }
    
    private void MonstrosPos(){
        Random gerador = new Random();
        for(int i=0;i<4;i++){
        do{
            Lutas[i][1] = gerador.nextInt(10); // Gerando as posiçoes das lutas !!
            Lutas[i][0] = gerador.nextInt(10);
            
            Armadilhas[i][0] = gerador.nextInt(10); // Gerando as posiçoes das armadilhas !!
            Armadilhas[i][1] = gerador.nextInt(10);
        }while(Lutas[0][0]==0||Lutas[0][1]==0||Armadilhas[0][0]==0||Armadilhas[0][1]==0||Lutas[i][0]==Lutas[i][1]||Armadilhas[i][0]==Armadilhas[i][1]||Lutas[i][0]==Armadilhas[i][0]||Lutas[i][0]==Armadilhas[i][1]||Lutas[i][1]==Armadilhas[i][0]||Lutas[i][1]==Armadilhas[i][1]);
        }
    }
    
    private void Luta(){
        if(mobs[le][le2].getHp()<=0&&mobs[le][le2+1].getHp()<=0){
            GanhaLuta();
        }
        else if(Heroi.getHp()<=0){
            if(roda == 0){
                playsound("sound/Derrota.wav");
                PerdeLuta();
                roda++;
            }
        }
        else{
            
            TP.removeAll();
            getContentPane().removeAll();

            BH1.setText("Usos: "+Heroi.HUsos[0]);
            BH2.setText("Usos: "+Heroi.HUsos[1]);
            BH3.setText("Usos: "+Heroi.HUsos[2]);
            
            BH1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H1)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            BH2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H2)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            BH3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H3)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            
            BH1.setBackground(new Color(169,169,169));
            BH2.setBackground(new Color(169,169,169));
            BH3.setBackground(new Color(169,169,169));
            
            BH1.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH1.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H1)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            BH2.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH2.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H2)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            BH3.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH3.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H3)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            
            JPanel C = new JPanel(new BorderLayout());
            C.setBackground(Color.BLACK);
            JPanel D = new JPanel();
            D.setBackground(Color.BLACK);

            Bmob1 = new JButton();
            Bmob2 = new JButton();
            Bmob1.addActionListener(this);
            Bmob2.addActionListener(this);
            Bmob1.setPreferredSize(new Dimension(250,340));
            Bmob2.setPreferredSize(new Dimension(250,340));
            Bmob1.setIcon(mobs[le][le2].MobIcon);
            Bmob2.setIcon(mobs[le][le2+1].MobIcon);
            JLabel la1 = new JLabel(" ",null,JLabel.CENTER);
            JLabel la2 = new JLabel(" ",null,JLabel.CENTER);
            JLabel la3 = new JLabel(" ",null,JLabel.LEFT);
            la1.setPreferredSize(new Dimension(30,0));
            la2.setPreferredSize(new Dimension(150,0));
            la3.setPreferredSize(new Dimension(200,0));

            D.add(la1);
            D.add(Bmob1);
            D.add(la2);
            D.add(Bmob2);
            D.add(la3);

            JPanel E = new JPanel(new GridLayout(2,1));
            E.setBackground(Color.BLACK);
            JPanel F = new JPanel();
            F.setBackground(Color.BLACK);

            JLabel[] lab = new JLabel[2];
            lab[0] = new JLabel("");
            lab[1] = new JLabel("",null,JLabel.CENTER);
            lab[0].setPreferredSize(new Dimension(285,0));
            lab[1].setPreferredSize(new Dimension(260,0));

            JLabel n1 = new JLabel("                                 "+mobs[le][le2].Nome);
            JLabel n2 = new JLabel("                         "+mobs[le][le2+1].Nome);
            n1.setForeground(Color.WHITE);
            n2.setForeground(Color.WHITE);
            
            F.add(n1);
            F.add(lab[0]);
            F.add(n2);

            JPanel G = new JPanel();
            G.setBackground(Color.BLACK);
            UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
            UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

            Hpmob1Bar = new JProgressBar(0,mobs[le][le2].HpTotal);
            Hpmob1Bar.setValue(mobs[le][le2].Hp);
            Hpmob1Bar.setStringPainted(true);
            Hpmob1Bar.setString(""+mobs[le][le2].Hp+"/"+mobs[le][le2].HpTotal);
            Hpmob1Bar.setForeground(Color.RED);

            Hpmob2Bar = new JProgressBar(0,mobs[le][le2+1].HpTotal);
            Hpmob2Bar.setValue(mobs[le][le2+1].Hp);
            Hpmob2Bar.setStringPainted(true);
            Hpmob2Bar.setString(""+mobs[le][le2+1].Hp+"/"+mobs[le][le2+1].HpTotal);
            Hpmob2Bar.setForeground(Color.RED);


            G.add(new JLabel("                          ",null,JLabel.CENTER));    
            G.add(Hpmob1Bar);
            G.add(lab[1]);
            G.add(Hpmob2Bar);
            E.add(F);
            E.add(G);

            JLabel la = new JLabel(" ",null,JLabel.CENTER);
            la.setPreferredSize(new Dimension(0,0));
            C.add(la,BorderLayout.SOUTH);
            C.add(E,BorderLayout.NORTH);
            C.add(new JLabel("                ",null,JLabel.CENTER),BorderLayout.WEST);
            C.add(D,BorderLayout.CENTER);
            PLuta = new JPanel(new BorderLayout());
            JLabel label1 = new JLabel("",null,JLabel.CENTER);
            label1.setPreferredSize(new Dimension(150,70));

            JPanel B = new JPanel(new BorderLayout());
            B.setBackground(Color.BLACK);
            BAtacar = new JButton();
            BAtacar.addActionListener(this);
            BHabilidades = new JButton();
            BHabilidades.addActionListener(this);
            BBolsa = new JButton();
            BBolsa.addActionListener(this);
            BFugir = new JButton();
            BFugir.addActionListener(this);
            BAtacar.setPreferredSize(new Dimension(110,70));
            BHabilidades.setPreferredSize(new Dimension(110,70));
            BBolsa.setPreferredSize(new Dimension(110,70));
            BFugir.setPreferredSize(new Dimension(110,70));
            BAtacar.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonatacar.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            BHabilidades.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonhabilidades.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            BBolsa.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonbolsa.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            BFugir.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonfugir.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            
            

            BMostrarTexto = new JButton("Texto");
            BMostrarTexto.addActionListener(this);
            BMostrarTexto.setPreferredSize(new Dimension(20,40));
            
            JScrollPane SP = new JScrollPane(TA);
            JPanel BC = new JPanel(new BorderLayout());
            BC.setBackground(Color.BLACK);
            JLabel[] ls = new JLabel[4];
            for(int i=0;i<4;i++)
            ls[i] = new JLabel("");
            ls[0].setPreferredSize(new Dimension(80,0));
            ls[1].setPreferredSize(new Dimension(0,5));
            ls[2].setPreferredSize(new Dimension(20,0));
            ls[3].setPreferredSize(new Dimension(0,10));
            BC.add(SP,BorderLayout.CENTER);
            BC.add(ls[0],BorderLayout.EAST);
            BC.add(ls[1],BorderLayout.SOUTH);
            BC.add(BMostrarTexto,BorderLayout.WEST);
            BC.add(ls[3],BorderLayout.NORTH);
            B.add(BC,BorderLayout.CENTER);

            JPanel BE = new JPanel(new GridLayout(2,1));
            BE.setBackground(Color.BLACK);
            JPanel BE1 = new JPanel(new BorderLayout());
            BE1.setBackground(Color.BLACK);
            JPanel BE2 = new JPanel(new GridLayout(1,4));
            BE2.setBackground(Color.BLACK);
            HpHeroiBar = new JProgressBar(0,Heroi.HpTotal);
            HpHeroiBar.setValue(Heroi.Hp);
            HpHeroiBar.setStringPainted(true);
            HpHeroiBar.setString(""+Heroi.Hp+"/"+Heroi.HpTotal);
            HpHeroiBar.setForeground(Color.GREEN);

            JLabel be11abel = new JLabel("");
            be11abel.setPreferredSize(new Dimension(0,30));
            JLabel be11abel2 = new JLabel("");
            be11abel2.setPreferredSize(new Dimension(0,30));
            BE1.add(be11abel,BorderLayout.NORTH);
            BE1.add(new JLabel("                      "),BorderLayout.EAST);
            BE1.add(new JLabel("                                                                "),BorderLayout.WEST);
            BE1.add(be11abel2,BorderLayout.SOUTH);
            BE1.add(HpHeroiBar,BorderLayout.CENTER);

            BE2.add(BAtacar);
            BE2.add(BHabilidades);
            BE2.add(BBolsa);
            BE2.add(BFugir);
            BE.add(BE1);
            BE.add(BE2);
            B.add(BE,BorderLayout.EAST);

            JLabel label2 = new JLabel("");
            label2.setPreferredSize(new Dimension(150,0));
            JLabel label3 = new JLabel("");
            label3.setPreferredSize(new Dimension(500,0));

            PLuta.add(PDA,BorderLayout.EAST);
            PLuta.add(C,BorderLayout.CENTER);
            PLuta.add(B,BorderLayout.SOUTH);
            PLuta.setBackground(Color.BLACK);
            TP.add("      Luta      ",PLuta);
            Caracteristicas();
            TP.add("      Char      ",PChar);
            TP.add("      Regras    ",PRegras);
            if(Aba==1)
                TP.setSelectedIndex(1);
            else if(Aba==2)
                TP.setSelectedIndex(2);
            else
                playsound("sound/B.wav");

            getContentPane().add(TP);
            this.paintComponents(getGraphics());
        } 
    }
    
    private void GanhaLuta(){
        playsound("sound/S2.wav");
        Aba=0;
        Cafe=0;
        try { Thread.currentThread().sleep(800);} catch (Exception e) {e.printStackTrace();}
        getContentPane().removeAll();
        TA.setText("                        It's Show Time!!");
        JPanel A = new JPanel(new GridLayout(1,1));
        
        JLabel Vic = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/bglevelup.png")),JLabel.CENTER);
        Vic.setFont(new Font("Serif",Font.BOLD,40));
        
        A.add(Vic);
        
        getContentPane().add(A);
        this.paintComponents(getGraphics());
        Heroi.LevelUp();
        try { Thread.currentThread().sleep(4200);} catch (Exception e) {e.printStackTrace();}
        Jogo();
    }
    
    public void PerdeLuta(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(BItens[i][j].getIcon().equals(Itens[1].ItIcon)){
                    if(Itens[1].Qnt>0){
                        Itens[i].usarItem();
                        Heroi.Hp = Heroi.HpTotal;
                        Luta();
                    }
                }
            }
        }
        if(Heroi.Hp<=0){
        getContentPane().removeAll();
        
        JPanel A = new JPanel(new GridLayout(1,1));
        TA.setText("                        It's Show Time!!");
        JLabel Der = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/TeladeDERROTA.png")),JLabel.CENTER);
        //A.add(new JLabel(""));
        A.add(Der);
        //A.add(new JLabel(""));
        
        getContentPane().add(A);
        this.paintComponents(getGraphics());
        //JOptionPane.showMessageDialog(null, " PERDEU OTÁRIO!!! ","GAME OVER",JOptionPane.WARNING_MESSAGE);
        JOptionPane pane = new JOptionPane("YOU LOSE", JOptionPane.WARNING_MESSAGE);
        JDialog d = pane.createDialog(null, "GAME OVER");
        d.setLocation(575,520);
        d.setVisible(true);
        //try { Thread.currentThread().sleep(30000);} catch (Exception e) {e.printStackTrace();}
        this.dispose();
        clip.stop();
        clip.close();
        Jogo T = new Jogo();
        }
    }
    
    private void Caracteristicas(){
        PChar = new JPanel(new GridLayout(1,1));
        //ImageIcon ImHeroi = new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/ImHeroi.jpg"));
        //ImageIcon ImHeroi = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/groot.gif")).getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT)); //como alterar tamanho imagem!!
        ImageIcon ImHeroi;
        if(Heroi.getclasse().equals("Guerreiro")){
            link= new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/heroguerreira.png")).getImage().getScaledInstance(110,110, Image.SCALE_DEFAULT));
            ImHeroi = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Caracteristicas/guerreirabuttonchar.png")).getImage().getScaledInstance(220, 250, Image.SCALE_DEFAULT));
            Hab1 = "Imagens/Caracteristicas/H1Guerreiro.png";
            Hab2= "Imagens/Caracteristicas/H2Guerreiro.png";
            Hab3 = "Imagens/Caracteristicas/H3Guerreiro.png";
            Hab4 = "Imagens/Caracteristicas/H4Guerreiro.png";
            H1 = "Imagens/Luta/buttongritodeguerra.png";
            H2 = "Imagens/Luta/buttonfuria.png";
            H3 = "Imagens/Luta/buttonpeledeferro.png";
            
        }
        else if(Heroi.getclasse().equals("Mago")){
            link = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/heromago.png")).getImage().getScaledInstance(110,110, Image.SCALE_DEFAULT));
            ImHeroi = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Caracteristicas/magobuttonchar.png")).getImage().getScaledInstance(220, 250, Image.SCALE_DEFAULT));
            Hab1 = "Imagens/Caracteristicas/H1Mago.png";
            Hab2= "Imagens/Caracteristicas/H2Mago.png";
            Hab3 = "Imagens/Caracteristicas/H3Mago.png";
            Hab4 = "Imagens/Caracteristicas/H4Mago.png";
            H1 = "Imagens/Luta/buttontrovao.png";
            H2 = "Imagens/Luta/buttonladraoespiritual.png";
            H3 = "Imagens/Luta/buttonprisaomental.png";
        }
        else{
            link = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/heroarqueira.png")).getImage().getScaledInstance(110,110, Image.SCALE_DEFAULT));
            ImHeroi = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Caracteristicas/arqueirabuttonchar.png")).getImage().getScaledInstance(220, 300, Image.SCALE_DEFAULT));
            Hab1 = "Imagens/Caracteristicas/H1Arqueiro.png";
            Hab2= "Imagens/Caracteristicas/H2Arqueiro.png";
            Hab3 = "Imagens/Caracteristicas/H3Arqueiro.png";
            Hab4 = "Imagens/Caracteristicas/H4Arqueiro.png";
            H1 = "Imagens/Luta/buttonreflexosapurados.png";
            H2 = "Imagens/Luta/buttonflechapesada.png";
            H3 = "Imagens/Luta/buttonflechaenvenenada.png";
        }
        JPanel A = new JPanel(new GridLayout(1,3));
        JPanel B = new JPanel(new BorderLayout());
        JTabbedPane C = new JTabbedPane();
        
        BUpHab1 = new JButton("Usos: "+Heroi.HUsos[0]+"/"+Heroi.HUsosTotal[0]);
        BUpHab2 = new JButton("Usos: "+Heroi.HUsos[1]+"/"+Heroi.HUsosTotal[1]);
        BUpHab3 = new JButton("Usos: "+Heroi.HUsos[2]+"/"+Heroi.HUsosTotal[2]);
        final JButton BUpHab4 = new JButton("Habilidade Passiva");
        BUpHab1.addActionListener(this);
        BUpHab2.addActionListener(this);
        BUpHab3.addActionListener(this);
        
        BUpHab1.setBackground(new Color(169,169,169));
        BUpHab2.setBackground(new Color(169,169,169));
        BUpHab3.setBackground(new Color(169,169,169));
        BUpHab4.setBackground(new Color(169,169,169));
        
        BUpHab1.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab1)));
        BUpHab2.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab2)));
        BUpHab3.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab3)));
        BUpHab4.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab4)));
        
        BUpHab1.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BUpHab1.setIcon(null);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BUpHab1.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab1)));
            }  
        });
        BUpHab2.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BUpHab2.setIcon(null);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BUpHab2.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab2)));            
            }  
        });
        BUpHab3.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BUpHab3.setIcon(null);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BUpHab3.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab3)));
            }  
        });
        BUpHab4.addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {  
                BUpHab4.setIcon(null);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                BUpHab4.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(Hab4)));
            }  
        });
        
        
        JPanel Hab = new JPanel(new GridLayout(4,1));
        Hab.add(BUpHab1);
        Hab.add(BUpHab2);
        Hab.add(BUpHab3);
        Hab.add(BUpHab4);
        
        C.add(Hab,"Habilidades");
        C.add(PBolsa,"Bolsa");
        
        
        
        JPanel BNorte = new JPanel(new GridLayout(1,2));
        BNorte.setPreferredSize(new Dimension(550,260));
        JPanel BNorte1 = new JPanel(new BorderLayout());
        JPanel BNorte2 = new JPanel(new GridLayout(6,1));
        
        BNorte1.setBackground(Color.BLACK);
        BNorte2.setBackground(Color.BLACK);
        JLabel l1 = new JLabel("");
        JLabel l2 = new JLabel("");
        JLabel l3 = new JLabel("");
        JLabel l4 = new JLabel("");
        JLabel l5 = new JLabel(ImHeroi);
        l1.setPreferredSize(new Dimension(0,10));
        l2.setPreferredSize(new Dimension(0,10));
        l3.setPreferredSize(new Dimension(10,0));
        l4.setPreferredSize(new Dimension(10,0));
        l4.setPreferredSize(new Dimension(10,0));
        BNorte1.add(l1,BorderLayout.NORTH);
        BNorte1.add(l2,BorderLayout.SOUTH);
        BNorte1.add(l3,BorderLayout.EAST);
        BNorte1.add(l4,BorderLayout.WEST);
        BNorte1.add(l5,BorderLayout.CENTER);
        
        JLabel JNome = new JLabel("Nome:    "+Nome,null,JLabel.LEFT);
        JLabel JClasse = new JLabel("Classe:  "+ Heroi.getclasse(), null, JLabel.LEFT);
        JLabel JLevel = new JLabel("Level:     "+Heroi.Nivel,null,JLabel.LEFT);
        JLabel JPS = new JLabel("PS:  "+Heroi.PS+"        PH:  "+Heroi.PH,null,JLabel.LEFT);
        
        JNome.setForeground(Color.WHITE);
        JClasse.setForeground(Color.WHITE);
        JLevel.setForeground(Color.WHITE);
        JPS.setForeground(Color.WHITE);
        
        BNorte2.add(new JLabel(null,null,JLabel.LEFT));
        BNorte2.add(JNome);
        BNorte2.add(JClasse);
        BNorte2.add(JLevel);
        BNorte2.add(JPS);
        
        
        BNorte.add(BNorte1);
        BNorte.add(BNorte2);
        B.add(BNorte,BorderLayout.NORTH);
        
        JPanel BCentro = new JPanel(new GridLayout(4,5));
        BCentro.setPreferredSize(new Dimension(550,200));
        BCentro.setBackground(Color.BLACK);
        
        JLabel[] BC = new JLabel[15];
        BC[0] = new JLabel("Hp: ",null,JLabel.CENTER);
        BC[1] = new JLabel(""+Heroi.HpTotal,null,JLabel.CENTER);
        BC[2] = new JLabel(""+(HpUpSoma),null,JLabel.CENTER);
        BC[3] = new JLabel("+",null,JLabel.CENTER);
        BC[4] = new JLabel(""+HpUpQnt,null,JLabel.CENTER);
        BC[5] = new JLabel("Def: ",null,JLabel.CENTER);
        BC[6] = new JLabel(""+Heroi.Def,null,JLabel.CENTER);
        BC[7] = new JLabel(""+(DefUpSoma),null,JLabel.CENTER);
        BC[8] = new JLabel("+",null,JLabel.CENTER);
        BC[9] = new JLabel(""+DefUpQnt,null,JLabel.CENTER);
        BC[10] = new JLabel("Atq: ",null,JLabel.CENTER);
        BC[11] = new JLabel(""+Heroi.Atq,null,JLabel.CENTER);
        BC[12] = new JLabel(""+(AtqUpSoma),null,JLabel.CENTER);
        BC[13] = new JLabel("+",null,JLabel.CENTER);
        BC[14] = new JLabel(""+AtqUpQnt,null,JLabel.CENTER);
        JLabel BC16 = new JLabel("Atributo",null,JLabel.CENTER);
        JLabel BC17 = new JLabel("Atual",null,JLabel.CENTER);
        JLabel BC18 = new JLabel("Soma",null,JLabel.CENTER);
        JLabel BC19 = new JLabel("",null,JLabel.CENTER);
        JLabel BC20 = new JLabel("",null,JLabel.CENTER);
        
        for(int i = 0;i<15;i++)
            BC[i].setForeground(Color.WHITE);
        
        BC16.setForeground(Color.WHITE);
        BC17.setForeground(Color.WHITE);
        BC18.setForeground(Color.WHITE);
        BC19.setForeground(Color.WHITE);
        BC20.setForeground(Color.WHITE);
        
        BC16.setFont(new Font("serif",Font.BOLD,20));
        BC17.setFont(new Font("serif",Font.BOLD,20));
        BC18.setFont(new Font("serif",Font.BOLD,20));
        for(int i=0;i<15;i++)
            BC[i].setFont(new Font("serif",Font.BOLD,15));
        
        JLabel HpLa1 = new JLabel("",null,JLabel.CENTER);
        JLabel HpLa2 = new JLabel("",null,JLabel.CENTER);
        JLabel HpLa3 = new JLabel("",null,JLabel.CENTER);
        JLabel HpLa4 = new JLabel("",null,JLabel.CENTER);
        HpLa1.setPreferredSize(new Dimension(15,10));
        HpLa2.setPreferredSize(new Dimension(15,10));
        HpLa3.setPreferredSize(new Dimension(0,15));
        HpLa4.setPreferredSize(new Dimension(0,15));
        
        JLabel DefLa1 = new JLabel("",null,JLabel.CENTER);
        JLabel DefLa2 = new JLabel("",null,JLabel.CENTER);
        JLabel DefLa3 = new JLabel("",null,JLabel.CENTER);
        JLabel DefLa4 = new JLabel("",null,JLabel.CENTER);
        DefLa1.setPreferredSize(new Dimension(15,10));
        DefLa2.setPreferredSize(new Dimension(15,10));
        DefLa3.setPreferredSize(new Dimension(0,15));
        DefLa4.setPreferredSize(new Dimension(0,15));
        
        JLabel AtqLa1 = new JLabel("",null,JLabel.CENTER);
        JLabel AtqLa2 = new JLabel("",null,JLabel.CENTER);
        JLabel AtqLa3 = new JLabel("",null,JLabel.CENTER);
        JLabel AtqLa4 = new JLabel("",null,JLabel.CENTER);
        AtqLa1.setPreferredSize(new Dimension(15,10));
        AtqLa2.setPreferredSize(new Dimension(15,10));
        AtqLa3.setPreferredSize(new Dimension(0,15));
        AtqLa4.setPreferredSize(new Dimension(0,15));
        
        JPanel BUp1 = new JPanel(new BorderLayout());
        JPanel BUp2 = new JPanel(new BorderLayout());
        JPanel BUp3 = new JPanel(new BorderLayout());
        BUp1.add(HpLa1,BorderLayout.EAST);
        BUp1.add(HpLa2,BorderLayout.WEST);
        BUp1.add(HpLa3,BorderLayout.SOUTH);
        BUp1.add(HpLa4,BorderLayout.NORTH);
        BUp2.add(DefLa1,BorderLayout.EAST);
        BUp2.add(DefLa2,BorderLayout.WEST);
        BUp2.add(DefLa3,BorderLayout.SOUTH);
        BUp2.add(DefLa4,BorderLayout.NORTH);
        BUp3.add(AtqLa1,BorderLayout.EAST);
        BUp3.add(AtqLa2,BorderLayout.WEST);
        BUp3.add(AtqLa3,BorderLayout.SOUTH);
        BUp3.add(AtqLa4,BorderLayout.NORTH);
        BUp1.setBackground(Color.BLACK);
        BUp2.setBackground(Color.BLACK);
        BUp3.setBackground(Color.BLACK);
        
        BHpUp = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mais.png")).getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT)));
        BDefUp = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mais.png")).getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT)));
        BAtqUp = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mais.png")).getImage().getScaledInstance(12, 12, Image.SCALE_DEFAULT)));
        BHpDown = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/menos.png")).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT)));
         BAtqDown = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/menos.png")).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT)));
        BDefDown = new JButton(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/menos.png")).getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT)));
        BHpUp.addActionListener(this);
        BDefUp.addActionListener(this);
        BAtqUp.addActionListener(this);
        BHpDown.addActionListener(this);
        BAtqDown.addActionListener(this);
        BDefDown.addActionListener(this);
        
        BHpUp.setBackground(new Color(156,156,156));
        BDefUp.setBackground(new Color(156,156,156));
        BAtqUp.setBackground(new Color(156,156,156));
        BHpDown.setBackground(new Color(156,156,156));
        BAtqDown.setBackground(new Color(156,156,156));
        BDefDown.setBackground(new Color(156,156,156));
        
        JPanel A1 = new JPanel(new GridLayout(1,2));
        JPanel A2 = new JPanel(new GridLayout(1,2));
        JPanel A3 = new JPanel(new GridLayout(1,2));
        
        A1.add(BHpDown);
        A1.add(BHpUp);
        A2.add(BDefDown);
        A2.add(BDefUp);
        A3.add( BAtqDown);
        A3.add(BAtqUp);
        
        
        
        BUp1.add(A1,BorderLayout.CENTER);
        BUp2.add(A2,BorderLayout.CENTER);
        BUp3.add(A3,BorderLayout.CENTER);
        
        BCentro.add(BC16);
        BCentro.add(BC17);
        BCentro.add(BC18);
        BCentro.add(BC19);
        BCentro.add(BC20);
        for(int i=0;i<15;i++){
            if(i==3){
                BCentro.add(BUp1);
            }
            else if(i==8){
                BCentro.add(BUp2);
            }
            else if(i==13){
                BCentro.add(BUp3);
            }
            else{
            BCentro.add(BC[i]);
            }
        }
        B.add(BCentro,BorderLayout.CENTER);
        JPanel D = new JPanel();
        JLabel l = new JLabel("",null,JLabel.CENTER);
        l.setPreferredSize(new Dimension(200,30));
        BConfirma = new JButton("Confirmar");
        BConfirma.addActionListener(this);
        BCancelar = new JButton("Cancelar");
        BCancelar.addActionListener(this);
        
        BConfirma.setBackground(new Color(156,156,156));
        BCancelar.setBackground(new Color(156,156,156));
        
        D.add(BCancelar);
        D.add(BConfirma);
        D.add(l);
        D.setBackground(Color.BLACK);
        B.add(D,BorderLayout.SOUTH);
        B.setBackground(Color.BLACK);
        A.add(B);
        A.add(C);
        A.setBackground(Color.BLACK);
        PChar.add(A);
    }
    
    private void CM(){
        mobs[0][0] = new Monstros("Aranha",1,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 1/Luta 1/Buttons/buttonmob00.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[0][1] = new Monstros("Alcelope",1,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 1/Luta 1/Buttons/buttonmob01.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[0][2] = new Monstros("Preda",1,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 1/Luta 2/Buttons/buttonmob02.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[0][3] = new Monstros("Patode",1,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 1/Luta 2/Buttons/buttonmob03.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[1][0] = new Monstros("Madeira",2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 2/Luta 3/Buttons/buttonmob10.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[1][1] = new Monstros("Cobranha",2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 2/Luta 3/Buttons/buttonmob11.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[1][2] = new Monstros("Texurso",2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 2/Luta 4/Buttons/buttonmob12.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[1][3] = new Monstros("Trevoseon",2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 2/Luta 4/Buttons/buttonmob13.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[2][0] = new Monstros("Dragoleta",3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 3/Luta 5/Buttons/buttonmob20.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[2][1] = new Monstros("Magoblin",3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 3/Luta 5/Buttons/buttonmob21.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[2][2] = new Monstros("Mostro Conservador",3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 3/Luta 6/Buttons/buttonmob22.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[2][3] = new Monstros("Ogro Sensível",3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 3/Luta 6/Buttons/buttonmob23.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[3][0] = new Monstros("Tubassauro",4,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 4/Luta 7/Buttons/buttonmob30.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[3][1] = new Monstros("Cachorro Doidão",4,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 4/Luta 7/Buttons/buttonmob31.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[3][2] = new Monstros("Morte",4,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 4/Luta 8/Buttons/buttonmob32.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[3][3] = new Monstros("Comensal da Morte",4,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/LUTAS/Nivel 4/Luta 8/Buttons/buttonmob33.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
        mobs[4][1] = new Boss(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Boss/buttonboss.png")).getImage().getScaledInstance(250,340, Image.SCALE_DEFAULT)));
    }
    
    public static void main(String[] args) {
        Jogo T = new Jogo();
    }
    
    private void cursoratq(){
        Toolkit kit = Toolkit.getDefaultToolkit(); 
        cursor = "Cursoratq";
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Cursoratq.png")).getImage();  
        Point point = new Point(0, 0); // 
        //Coordenada do clique em relação à imagem    
        String nameCursor = "Image Cursor";     
        Cursor cursor = kit.createCustomCursor(image , point, nameCursor); 
        //getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        getContentPane().setCursor(cursor);
    }
    
    private void cursorN(){
        Toolkit kit = Toolkit.getDefaultToolkit(); 
        cursor = "Cursor_ST416";
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Cursor_ST416.png")).getImage();  
        Point point = new Point(0, 0); // 
        //Coordenada do clique em relação à imagem    
        String nameCursor = "Image Cursor";     
        Cursor cursor = kit.createCustomCursor(image , point, nameCursor); 
        //getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        getContentPane().setCursor(cursor);
    }
    private void cursorhist(){
        Toolkit kit = Toolkit.getDefaultToolkit(); 
        cursor = "cursorhist";
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/cursorhist.png")).getImage();  
        Point point = new Point(0, 0); // 
        //Coordenada do clique em relação à imagem    
        String nameCursor = "Image Cursor";     
        Cursor cursor = kit.createCustomCursor(image , point, nameCursor); 
        //getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        getContentPane().setCursor(cursor);
    }
    
    public void playsound(String caminho){
        if(caminho.equals(path)){
        }
        else{
            path = caminho;
            if(controladorsom %2 == 0){
                try {
                    if (clip != null) {
                        clip.stop();
                        clip.close();
                    }
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Thread.currentThread().getClass().getResourceAsStream("/"+caminho)));  
                    clip = AudioSystem.getClip();  
                    clip.open(audioInputStream);
                    clip.start();
                    clip.setFramePosition(0);
                    clip.loop(-1);
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                    //whatevers
                }
            }
        }
    }
    
    private void MenuInicial2(){
        getContentPane().removeAll();
        BNovoJogo2 = new JButton("Novo Jogo");
        BNovoJogo2.setPreferredSize(new Dimension(100,100));
        BNovoJogo2.addActionListener(this);
        BNovoJogo2.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/novojogobutton2.jpg")));
        BContinue = new JButton("Continue");
        BContinue.setPreferredSize(new Dimension(100,100));
        BContinue.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/continuebutton.jpg")));
        BContinue.addActionListener(this);
        BDebug = new JButton("Debug");
        BDebug.setPreferredSize(new Dimension(100,100));
        BDebug.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/debugbutton2.jpg")));
        BDebug.addActionListener(this);
        BSair = new JButton("Sair");
        BSair.setPreferredSize(new Dimension(100,100));
        BSair.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/sairbutton2.jpg")));
        BSair.addActionListener(this);
        JPanel MenuInicial = new JPanel(new BorderLayout());
        JPanel CentroMI = new JPanel(new GridLayout(4,1));
        CentroMI.setPreferredSize(new Dimension(100,300));
        CentroMI.add(BNovoJogo2);
        CentroMI.add(BContinue);
        CentroMI.add(BDebug);
        CentroMI.add(BSair);
        
        JLabel cima = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/cima.jpg")),JLabel.CENTER); //Substituir os labels por imagens!!!!
        cima.setPreferredSize(new Dimension(0,299));
        JLabel baixo = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/baixo.png")),JLabel.CENTER);
        baixo.setPreferredSize(new Dimension(0,111));
        JLabel direita = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/direita.jpg")),JLabel.CENTER);
        direita.setPreferredSize(new Dimension(305,0));
        JLabel esquerda = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/MenuInicial/esquerda.jpg")),JLabel.CENTER);
        esquerda.setPreferredSize(new Dimension(310,0));
        
        MenuInicial.add(CentroMI,BorderLayout.CENTER);
        MenuInicial.add(cima,BorderLayout.NORTH);
        MenuInicial.add(baixo,BorderLayout.SOUTH);
        MenuInicial.add(direita,BorderLayout.EAST);
        MenuInicial.add(esquerda,BorderLayout.WEST);
        
        for(int i =0;i<4;i++){
            for(int j = 0;j<2;j++){
                VA[i][j]=0;
            }
        }
        
        getContentPane().add(MenuInicial);
        playsound("sound/MrBlueSky.wav");
        this.paintComponents(getGraphics());
    }
    
    public void Mover(int Xf, int Yf){
        if(X==0&&Y==0){
            BMapa[X][Y].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/fogueira.gif")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
        }
        
        Xi = X;
        Yi = Y;
        X = Xf;
        Y = Yf;
        BMapa[X][Y].setIcon(link);
        if(Xi!=0||Yi!=0)
            BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/fundodomapa.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
        if(fugir == 1){
            if(Xi == 4)
                BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/boss.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));//mudar para icone de cada monstro, fazer um switch
            else
                BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/MonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));//mudar para icone de cada monstro, fazer um switch
        }else{
            if(Xi!=4){
        if(Lutas[Xi][0] == Yi||Lutas[Xi][1] == Yi){
            if(mobs[Xi][0].getHp()<=0||mobs[Xi][1].getHp()<=0)
                BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/XMonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
            if(mobs[Xi][2].getHp()<=0||mobs[Xi][3].getHp()<=0)
                BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/XMonstroSilhueta.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
        }
        if(Armadilhas[Xi][1]==Yi||Armadilhas[Xi][0]==Yi){
            BMapa[Xi][Yi].setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Mapa/XDeusaTrap.png")).getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT)));
        }
        }}
        fugir = 0;
    }
    
    private void MobsAtq(int q){
        Aba=0;
        Luta();
        try { Thread.currentThread().sleep(1000);} catch (Exception e) {e.printStackTrace();}
        if(Cafe==1&&contluta%2==0){
           
        }
        else
        if(!(Heroi.H[0]>0&&Heroi instanceof Mago)){
                if(Heroi.Hp>0){
                if(mobs[le][(le2+q-1)].getHp()>0&&mobs[le][(le2+q-1)].Stun==0){
                    mobs[le][(le2+q-1)].Atacar(Heroi);
                    Luta();
                    try { Thread.currentThread().sleep(1000);} catch (Exception e) {e.printStackTrace();}
                }
                }
                if(Heroi.Hp>0){
                if(q==1){
                    if(mobs[le][le2+1].getHp()>0&&mobs[le][le2+1].Stun==0){
                        mobs[le][le2+1].Atacar(Heroi);
                        Luta();
                    }
                }
                else{
                    if(mobs[le][le2].getHp()>0&&mobs[le][le2].Stun==0){
                        mobs[le][le2].Atacar(Heroi);
                        Luta();
                    }
                }
                }
        }
            if(mobs[le][le2].Veneno>0&&mobs[le][le2].getHp()>0){
                mobs[le][le2].Ven();
                Luta();
            }
            if(mobs[le][le2+1].Veneno>0&&mobs[le][le2+1].getHp()>0){
                mobs[le][le2+1].Ven();
                Luta();
            }
        if(Heroi.H[0]>0){
                Heroi.H[0]--;
                if(Heroi.H[0]==0&&Heroi instanceof Guerreiro){
                    mobs[le][le2+1].setDef(mobs[le][le2+1].getDefTotal());
                    mobs[le][le2].setDef(mobs[le][le2].getDefTotal());
                    
                }
                if(Heroi.H[0]==0&&Heroi instanceof Arqueiro){
                    Heroi.setDef(Heroi.DefTotal);
                }
            }
            if(Heroi.H[1]>0){
                Heroi.H[1]--;
                if(Heroi.H[1]==0&&Heroi instanceof Guerreiro){
                    Heroi.setAtq(Heroi.AtqTotal);
                }
                
            }
            if(Heroi.H[2]>0){
                if(!(Heroi instanceof Arqueiro))
                    Heroi.H[2]--;
            }
            if(mobs[le][le2].Stun>0){
                mobs[le][le2].Stun--;
            }
            if(mobs[le][le2+1].Stun>0){
                mobs[le][le2+1].Stun--;
            }
            Heroi.Passiva();
            contluta++;
            if(mobs[le][le2+1].getHp()>0&&mobs[le][le2].getHp()>0)
                Luta();
                
    }
    
    public void Fogueira(){
        if(JOptionPane.showConfirmDialog(null, "Descansar?","Fogueira",JOptionPane.YES_NO_OPTION)== 0){
            playsound("sound/fogueira.wav");
            try { Thread.currentThread().sleep(3500);} catch (Exception e) {e.printStackTrace();}
            Aba=0;
            Cafe = 0;
            Heroi.Hp = Heroi.HpTotal;
            Heroi.HUsos[0] = Heroi.HUsosTotal[0];
            Heroi.HUsos[1] = Heroi.HUsosTotal[1];
            Heroi.HUsos[2] = Heroi.HUsosTotal[2];
            Heroi.Def = Heroi.DefTotal;
            Heroi.Atq = Heroi.AtqTotal;
            TA.setText("                        It's Show Time!!");
            for(int i=0;i<4;i++){
                for(int j = 0; j<4;j++){
                    if(mobs[i][j].Hp>0){
                        mobs[i][j].Hp=mobs[i][j].HpTotal;
                        mobs[i][j].Stun = 0;
                        mobs[i][j].Veneno = 0;
                    }
                }
            }
            mobs[4][1].Hp=mobs[4][1].HpTotal;
            mobs[4][1].Stun = 0;
            mobs[4][1].Veneno = 0;
            Jogo();
        }
    }
    
    public static void addTexto(String s){
        TA.setText(TA.getText()+"\n   "+s);
    }
    
    public void JanelaBoss(){
        TP.removeAll();
        
        
        if(mobs[4][1].getHp()<=0){
            Final2();
        }
        else if(Heroi.getHp()<=0){
            if(roda == 0){
                playsound("sound/Derrota.wav");
                PerdeLuta();
                roda++;
            }
        }
        else{
            playsound("sound/Boss.wav");
            TP.removeAll();
            getContentPane().removeAll();

            BH1.setText("Usos: "+Heroi.HUsos[0]);
            BH2.setText("Usos: "+Heroi.HUsos[1]);
            BH3.setText("Usos: "+Heroi.HUsos[2]);
            
            BH1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H1)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            BH2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H2)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            BH3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H3)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));
            
            BH1.setBackground(new Color(169,169,169));
            BH2.setBackground(new Color(169,169,169));
            BH3.setBackground(new Color(169,169,169));
            
            BH1.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH1.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH1.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H1)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            BH2.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH2.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH2.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H2)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            BH3.addMouseListener(new MouseAdapter() {  
            @Override  
                public void mouseEntered(MouseEvent arg0) {  
                    BH3.setIcon(null); 
                }  
                @Override
                public void mouseExited(MouseEvent arg0) {  
                    BH3.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource(H3)).getImage().getScaledInstance(175, 175, Image.SCALE_DEFAULT)));                }  
            });
            
            JPanel C = new JPanel(new BorderLayout());
            C.setBackground(Color.BLACK);

            JPanel D = new JPanel();
            D.setBackground(Color.BLACK);

            BBoss = new JButton(mobs[4][1].MobIcon);
            BBoss.addActionListener(this);
            BBoss.setPreferredSize(new Dimension(250,340));
            JLabel la1 = new JLabel(" ",null,JLabel.LEFT);
            JLabel la2 = new JLabel(" ",null,JLabel.CENTER);
            JLabel la3 = new JLabel(" ",null,JLabel.LEFT);
            la1.setPreferredSize(new Dimension(80,0));
            la2.setPreferredSize(new Dimension(150,0));
            la3.setPreferredSize(new Dimension(200,0));

            D.add(la1);
            D.add(BBoss);
//            D.add(la2);

            JPanel E = new JPanel(new GridLayout(2,1));
            E.setBackground(Color.BLACK);

            JPanel F = new JPanel();
            F.setBackground(Color.BLACK);

            JLabel[] lab = new JLabel[2];
            lab[0] = new JLabel("");
            lab[1] = new JLabel("",null,JLabel.CENTER);
            
            lab[1].setPreferredSize(new Dimension(260,0));
            JLabel n1 = new JLabel("                                               "+mobs[4][1].Nome);
            n1.setForeground(Color.WHITE);

            F.add(n1);

            JPanel G = new JPanel();
            G.setBackground(Color.BLACK);
            UIManager.put("ProgressBar.selectionBackground", Color.BLACK);
            UIManager.put("ProgressBar.selectionForeground", Color.BLACK);

            Hpmob1Bar = new JProgressBar(0,mobs[4][1].HpTotal);
            Hpmob1Bar.setValue(mobs[4][1].Hp);
            Hpmob1Bar.setStringPainted(true);
            Hpmob1Bar.setString(""+mobs[4][1].Hp+"/"+mobs[4][1].HpTotal);
            Hpmob1Bar.setForeground(Color.RED);

            lab[0].setPreferredSize(new Dimension(400,0));
            G.add(lab[0]);    
            G.add(Hpmob1Bar);
            G.add(lab[1]);
            E.add(F);
            E.add(G);

            JLabel la = new JLabel(" ",null,JLabel.CENTER);
            la.setPreferredSize(new Dimension(0,0));
            C.add(la,BorderLayout.SOUTH);
            C.add(E,BorderLayout.NORTH);
            C.add(new JLabel("                ",null,JLabel.CENTER),BorderLayout.WEST);
            C.add(D,BorderLayout.CENTER);
            JBoss = new JPanel(new BorderLayout());
            JLabel label1 = new JLabel("",null,JLabel.CENTER);
            label1.setPreferredSize(new Dimension(150,70));

            JPanel B = new JPanel(new BorderLayout());
            B.setBackground(Color.BLACK);
            BAtacar = new JButton();
            BAtacar.addActionListener(this);
            BHabilidades = new JButton();
            BHabilidades.addActionListener(this);
            BBolsa = new JButton();
            BBolsa.addActionListener(this);
            BFugir = new JButton();
            BFugir.addActionListener(this);
            BAtacar.setPreferredSize(new Dimension(110,70));
            BHabilidades.setPreferredSize(new Dimension(110,70));
            BBolsa.setPreferredSize(new Dimension(110,70));
            BFugir.setPreferredSize(new Dimension(110,70));
            BAtacar.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonatacar.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            BHabilidades.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonhabilidades.png")).getImage().getScaledInstance(120, 90, Image.SCALE_DEFAULT)));
            BBolsa.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonbolsa.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
            BFugir.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Luta/buttonfugir.png")).getImage().getScaledInstance(110, 90, Image.SCALE_DEFAULT)));

            BMostrarTexto = new JButton("Texto");
            BMostrarTexto.addActionListener(this);
            BMostrarTexto.setPreferredSize(new Dimension(20,40));
            
            JScrollPane SP = new JScrollPane(TA);
            JPanel BC = new JPanel(new BorderLayout());
            BC.setBackground(Color.BLACK);
            JLabel[] ls = new JLabel[4];
            for(int i=0;i<4;i++)
            ls[i] = new JLabel("");
            ls[0].setPreferredSize(new Dimension(80,0));
            ls[1].setPreferredSize(new Dimension(0,5));
            ls[2].setPreferredSize(new Dimension(20,0));
            ls[3].setPreferredSize(new Dimension(0,10));
            BC.add(SP,BorderLayout.CENTER);
            BC.add(ls[0],BorderLayout.EAST);
            BC.add(ls[1],BorderLayout.SOUTH);
            BC.add(BMostrarTexto,BorderLayout.WEST);
            BC.add(ls[3],BorderLayout.NORTH);
            B.add(BC,BorderLayout.CENTER);

            JPanel BE = new JPanel(new GridLayout(2,1));
            BE.setBackground(Color.BLACK);
            JPanel BE1 = new JPanel(new BorderLayout());
            BE1.setBackground(Color.BLACK);
            JPanel BE2 = new JPanel(new GridLayout(1,4));
            BE2.setBackground(Color.BLACK);
            HpHeroiBar = new JProgressBar(0,Heroi.HpTotal);
            HpHeroiBar.setValue(Heroi.Hp);
            HpHeroiBar.setStringPainted(true);
            HpHeroiBar.setString(""+Heroi.Hp+"/"+Heroi.HpTotal);
            HpHeroiBar.setForeground(Color.GREEN);

            JLabel be11abel = new JLabel("");
            be11abel.setPreferredSize(new Dimension(0,30));
            JLabel be11abel2 = new JLabel("");
            be11abel2.setPreferredSize(new Dimension(0,30));
            BE1.add(be11abel,BorderLayout.NORTH);
            BE1.add(new JLabel("                      "),BorderLayout.EAST);
            BE1.add(new JLabel("                                                                "),BorderLayout.WEST);
            BE1.add(be11abel2,BorderLayout.SOUTH);
            BE1.add(HpHeroiBar,BorderLayout.CENTER);

            BE2.add(BAtacar);
            BE2.add(BHabilidades);
            BE2.add(BBolsa);
            BE2.add(BFugir);
            BE.add(BE1);
            BE.add(BE2);
            B.add(BE,BorderLayout.EAST);

            JLabel label2 = new JLabel("");
            label2.setPreferredSize(new Dimension(150,0));
            JLabel label3 = new JLabel("");
            label3.setPreferredSize(new Dimension(500,0));

            JBoss.add(PDA,BorderLayout.EAST);
            JBoss.add(C,BorderLayout.CENTER);
            JBoss.add(B,BorderLayout.SOUTH);
            
            TP.add("      Boss      ", JBoss);
            Caracteristicas();
            TP.add("      Char      ",PChar);
            TP.add("      Regras    ",PRegras);
            if(Aba==1)
                TP.setSelectedIndex(1);
            else if(Aba==2)
                TP.setSelectedIndex(2);
            else
                playsound("sound/Boss.wav");
            getContentPane().add(TP);
            this.paintComponents(getGraphics());
    }
    }
    
    public void BossAtq(){
        Aba=0;
        JanelaBoss();
        if(Cafe==1&&contluta%2==0){
           
        }
        else
        try { Thread.currentThread().sleep(1000);} catch (Exception e) {e.printStackTrace();}
        if((!(Heroi.H[0]>0&&Heroi instanceof Mago))&&Heroi.Hp>0){
                if(mobs[4][1].getHp()>0&&mobs[4][1].Stun==0){
                    mobs[4][1].Atacar(Heroi);
                    JanelaBoss();
                    try { Thread.currentThread().sleep(1000);} catch (Exception e) {e.printStackTrace();}
                }
        }
            if(mobs[4][1].Veneno>0&&mobs[4][1].getHp()>0){
                mobs[4][1].Ven();
                JanelaBoss();
            }
        if(Heroi.H[0]>0){
                Heroi.H[0]--;
                if(Heroi.H[0]==0&&Heroi instanceof Guerreiro){
                    mobs[4][1].setDef(mobs[le][le2+1].getDefTotal());
                    
                }
                if(Heroi.H[0]==0&&Heroi instanceof Arqueiro){
                    Heroi.setDef(Heroi.DefTotal);
                }
            }
            if(Heroi.H[1]>0){
                Heroi.H[1]--;
                if(Heroi.H[1]==0&&Heroi instanceof Guerreiro){
                    Heroi.setAtq(Heroi.AtqTotal);
                }
                
            }
            if(Heroi.H[2]>0){
                if(!(Heroi instanceof Arqueiro))
                    Heroi.H[2]--;
            }
            if(mobs[4][1].Stun>0){
                mobs[4][1].Stun--;
            }
            Heroi.Passiva();
            contluta++;
            if(mobs[4][1].getHp()>0)
                JanelaBoss();
    }
    
    public void Historia(){
        getContentPane().removeAll();
        this.cursorhist();
        BPular = new JButton("Pular");
        BPular.setPreferredSize(new Dimension(10,40));
        //Pular.setIcon());
        BPular.addActionListener(this);
        JLabel espaço = new JLabel();
        espaço.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço1 = new JLabel();
        espaço1.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco1.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço2 = new JLabel();
        espaço2.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco2.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço3 = new JLabel();
        espaço3.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco3.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço4 = new JLabel();
        espaço4.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco4.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço5 = new JLabel();
        espaço5.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco5.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        Historia = new JPanel(new BorderLayout());
        JPanel botao = new JPanel(new GridLayout(1,1));
        botao.setPreferredSize(new Dimension(10,40));
        botao.add(espaço);
        botao.add(espaço1);
        botao.add(espaço2);
        botao.add(espaço3);
        botao.add(espaço4);
        botao.add(espaço5);
        botao.add(BPular);
        JLabel perg = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/historia.gif")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg.setPreferredSize(new Dimension(1100,620));
        Historia.add(perg);
        Historia.add(botao,BorderLayout.SOUTH);
        getContentPane().add(Historia);
        playsound("sound/narracao.wav");
        this.paintComponents(getGraphics());       
       
    }
    
    public void Armadilhas(){
        TP.removeAll();
        getContentPane().removeAll();
        PTrap = new JPanel(new BorderLayout());
        JLabel IDeusa = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TextoTrap();
        //TTrap.setText("     IT'S A TRAP!!!!");
        JScrollPane SP = new JScrollPane(TTrap);
        PTrap.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        PTrap.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/trapquiz.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IDeusa,BorderLayout.CENTER);
        
        BTA = new JButton(" A");
        BTB = new JButton(" B");
        BTC = new JButton(" C");
        BTD = new JButton(" D");
        BTA.addActionListener(this);
        BTB.addActionListener(this);
        BTC.addActionListener(this);
        BTD.addActionListener(this);
        BTA.setPreferredSize(new Dimension(20,20));
        JPanel PTB = new JPanel(new GridLayout(1,4));
        PTB.add(BTA);
        PTB.add(BTB);
        PTB.add(BTC);
        PTB.add(BTD);
        PTD.add(PTB,BorderLayout.SOUTH);
        
        PTrap.add(PTD,BorderLayout.EAST);
        
        TP.add("ARMADILHA",PTrap);
        Caracteristicas();
        TP.add("  CHAR  ",PChar);
        TP.add("  REGRAS  ",PRegras);
        
        if(Aba==1)
            TP.setSelectedIndex(1);
        else if(Aba==2)
            TP.setSelectedIndex(2);
        else
            playsound("sound/trap.wav");
        
        PTrap.setBackground(new Color(238,154,73));
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    
    public void Bonus(){
        
        TP.removeAll();
        getContentPane().removeAll();
        PTrap = new JPanel(new BorderLayout());
        JLabel IDeusa = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TextoBonus();
        //TTrap.setText("     IT'S A TRAP!!!!");
        JScrollPane SP = new JScrollPane(TTrap);
        PTrap.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        PTrap.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/trapquiz.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IDeusa,BorderLayout.CENTER);
        
        BBA = new JButton(" A");
        BBB = new JButton(" B");
        BBC = new JButton(" C");
        BBD = new JButton(" D");
        BBA.addActionListener(this);
        BBB.addActionListener(this);
        BBC.addActionListener(this);
        BBD.addActionListener(this);
        BBA.setPreferredSize(new Dimension(20,20));
        JPanel PTB = new JPanel(new GridLayout(1,4));
        PTB.add(BBA);
        PTB.add(BBB);
        PTB.add(BBC);
        PTB.add(BBD);
        PTD.add(PTB,BorderLayout.SOUTH);
        
        PTrap.add(PTD,BorderLayout.EAST);
        
        TP.add("ARMADILHA",PTrap);
        Caracteristicas();
        TP.add("  CHAR  ",PChar);
        TP.add("  REGRAS  ",PRegras);
        
        if(Aba==1)
            TP.setSelectedIndex(1);
        else if(Aba==2)
            TP.setSelectedIndex(2);
        else
            playsound("sound/trap.wav");
        
        PTrap.setBackground(new Color(238,154,73));
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    
    public void CriarItens(){
        Itens[0] = new Itens("PocaoHp",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/php.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),5,3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/php.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[1] = new Itens("Pena",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/pena.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),2,0,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/pena.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[2] = new Itens("PocaoHab",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/phab.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),3,3,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/phab.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[3] = new Itens("BombaFumaça",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/bombadegas.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),4,0,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/bombadegas.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[4] = new Itens("Presente",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/presentesgif.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),1,2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/presente.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[5] = new Itens("Cafe",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/cafe.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),1,2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/cafe.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[6] = new Itens("AnelDef",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/anelescudo.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),1,0,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/anelescudo.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[7] = new Itens("AnelAtq",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/anelespada.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),1,0,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/anelespada.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[8] = new Itens("Mapa",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/mapa.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),2,1,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/mapa.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
        Itens[9] = new Itens("tnt",new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/dinamite.png")).getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)),2,2,new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/Descricao/dinamite.png")).getImage().getScaledInstance(130,130, Image.SCALE_DEFAULT)));
    }
    
    public void Bolsa(){
        PBolsa = new JPanel(new GridLayout(4,4));
        BItens = new JButton[4][4];
        for(int i=0;i<4;i++){
            for(int j = 0; j<4;j++){
                BItens[i][j] = new JButton(fundobolsa);
                BItens[i][j].addActionListener(this);
                PBolsa.add(BItens[i][j]);
            }
        }
        PBolsaLuta = new JPanel(new GridLayout(5,1));
        PBolsaLuta.setPreferredSize(new Dimension(100,600));
        SPBolsaLuta = new JScrollPane(PBolsaLuta);
        BItensLuta = new JButton[10];
        for(int i=0;i<10;i++){
            if(i<5){
                BItensLuta[i] = new JButton(fundobolsa);
                BItensLuta[i].addActionListener(this);
                PBolsaLuta.add(BItensLuta[i]);
            }
            else
                BItensLuta[i] = new JButton();
            }
        addDesItens();
    }
    
    public void addDesItens(){
        BItens[0][0].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[0][0].getIcon().equals(Itens[i].ItIcon))
                        BItens[0][0].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[0][0].getIcon().equals(Itens[i].ItIconDes))
                        BItens[0][0].setIcon(Itens[i].ItIcon);
            }  
        });
        
        BItens[0][1].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[0][1].getIcon().equals(Itens[i].ItIcon))
                        BItens[0][1].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[0][1].getIcon().equals(Itens[i].ItIconDes))
                        BItens[0][1].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[0][2].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[0][2].getIcon().equals(Itens[i].ItIcon))
                        BItens[0][2].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[0][2].getIcon().equals(Itens[i].ItIconDes))
                        BItens[0][2].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[0][3].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[0][3].getIcon().equals(Itens[i].ItIcon))
                        BItens[0][3].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[0][3].getIcon().equals(Itens[i].ItIconDes))
                        BItens[0][3].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[1][0].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[1][0].getIcon().equals(Itens[i].ItIcon))
                        BItens[1][0].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[1][0].getIcon().equals(Itens[i].ItIconDes))
                        BItens[1][0].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[1][1].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[1][1].getIcon().equals(Itens[i].ItIcon))
                        BItens[1][1].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[1][1].getIcon().equals(Itens[i].ItIconDes))
                        BItens[1][1].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[1][2].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[1][2].getIcon().equals(Itens[i].ItIcon))
                        BItens[1][2].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[1][2].getIcon().equals(Itens[i].ItIconDes))
                        BItens[1][2].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[1][3].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[1][3].getIcon().equals(Itens[i].ItIcon))
                        BItens[1][3].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[1][3].getIcon().equals(Itens[i].ItIconDes))
                        BItens[1][3].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[2][0].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[2][0].getIcon().equals(Itens[i].ItIcon))
                        BItens[2][0].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[2][0].getIcon().equals(Itens[i].ItIconDes))
                        BItens[2][0].setIcon(Itens[i].ItIcon);
            }  
        });
        BItens[2][1].addMouseListener(new MouseAdapter() {  
        @Override  
            public void mouseEntered(MouseEvent arg0) {
                for(int i=0;i<10;i++)
                    if(BItens[2][1].getIcon().equals(Itens[i].ItIcon))
                        BItens[2][1].setIcon(Itens[i].ItIconDes);
            }  
            @Override
            public void mouseExited(MouseEvent arg0) {  
                for(int i=0;i<10;i++)
                    if(BItens[2][1].getIcon().equals(Itens[i].ItIconDes))
                        BItens[2][1].setIcon(Itens[i].ItIcon);
            }  
        });
    }
    
    public void GanhaItem(String s){
        int c=0; 
        
        for(int i=0;i<10;i++){
            if(Itens[i]!=null&&Itens[i].Nome.equals(s)){
                
                for(int j=0;j<4;j++)
                    for(int k=0;k<4;k++){
                        if(BItens[j][k].getIcon().equals(fundobolsa)&&c==0){
                            BItens[j][k].setIcon(Itens[i].ItIcon);
                            c++;
                        }
                    }
                if(Itens[i].Tipo==3||Itens[i].Tipo==2)
                for(int j=0;j<5;j++)
                    if(BItensLuta[j].getIcon().equals(fundobolsa)&&c==1){
                        c++;
                        BItensLuta[j].setIcon(Itens[i].ItIcon);
                    }
                }
        }
    }	
    
    public void Regras(){
        PRegras = new JPanel(new GridLayout(1,1));
        BProximo = new JButton("Próximo");
        BProximo.setPreferredSize(new Dimension(10,40));
        BProximo.addActionListener(this);
        BAnterior = new JButton("Anterior");
        BAnterior.setPreferredSize(new Dimension(10,40));
        BAnterior.addActionListener(this);
        JLabel espaço = new JLabel();
        espaço.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço1 = new JLabel();
        espaço1.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco1.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço2 = new JLabel();
        espaço2.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco2.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço3 = new JLabel();
        espaço3.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco3.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço4 = new JLabel();
        espaço4.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco4.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
//        JLabel espaço5 = new JLabel();
//        espaço5.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco5.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JRegras = new JPanel(new BorderLayout());
        JPanel botao = new JPanel(new GridLayout(1,1));
        botao.setPreferredSize(new Dimension(10,40));
        botao.add(espaço);
        botao.add(espaço1);
        botao.add(espaço2);
        botao.add(espaço3);
        botao.add(espaço4);
        botao.add(BAnterior);
        botao.add(BProximo);
        regra.setPreferredSize(new Dimension(1100,620));
        JRegras.add(regra);
        JRegras.add(botao,BorderLayout.SOUTH);
        PRegras.add(JRegras);
    }
    public void TextoTrap(){
        if(le==0){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de cair em uma armadilha! \nAgora, vamos a sua pergunta:\n\nQual a função do Reino de Zi?\n\na) Cuidar da defesa e da segurança de Zinouh.\nb) Desenvolver tecnologia e elixires para Zinouh.\nc) Salvar monstros de abusos dos humanos.\nd) Fornecer energia para Zinouh.");
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==1){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de cair em uma armadilha! \nAgora, vamos a sua pergunta:\n\nO que é a Dungeon, local onde estamos?\n\na) Uma caverna lotada de tesouros.\nb) Local onde o mundo foi criado.\nc) Um calabouço de tortura.\nd) Local místico onde todos os habitantes de Kibesa vão para se purificar.");            
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==2){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de cair em uma armadilha! \nAgora, vamos a sua pergunta:\n\nQuem é O Destruidor?\n\na) Um monstro antigo, selado na Dungeon pelos Criadores.\nb) Um humano que se tornou um monstro e agora quer vingança por não ser\naceito pela humanidade.\nc) Um híbrido entre monstro e humano, que fugiu até a Dungeon, treinou com\nos Criadores e os traiu, declarando guerra com os humanos.\nd) Um híbrido entre monstro e Criador, que agora quer dominar o mundo por\nter poder para isso.");            
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==3){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de cair em uma armadilha! \nAgora, vamos a sua pergunta:\n\nO que significa Alquimia?\n\na) Forma de energia capaz de gerar magia.\nb) Energia vital que vem da alma.\nc) Forma de energia artificial criada por humanos para gerar magia.\nd) Ciência que estuda a transformação dos materiais.");
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
    }
    public void TextoBonus(){
        if(le==0){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de achar uma sala bônus! \nAqui você achará os melhores itens da Dungeon, mas as perguntas\ntambém são mais difíceis, preparado?\n\nO que significa Mana?\n\na) Energia vital proveniente da alma.\nb) Gíria para irmã.\nc) Energia vital dos Criadores.\nd) Energia capaz de gerar magia.");
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==1){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de achar uma sala bônus! \nAqui você achará os melhores itens da Dungeon, mas as perguntas\ntambém são mais difíceis, preparado?\n\nQuantos anos se passaram desde o Início do Fim?\n\na) 20.\nb) 200.\nc) 25.\nd) Milhares de anos antes do nascimento de Zinouh.");            
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==2){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de achar uma sala bônus! \nAqui você achará os melhores itens da Dungeon, mas as perguntas\ntambém são mais difíceis, preparado?\n\nO que aconteceu no Início do Fim?\n\na) O vilão destruiu a Dungeon.\nb) Os monstros mais poderosos foram libertados.\nc) A Dungeon foi invadida.\nd) O Herói entrou na Dungeon.");            
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
        if(le==3){
            TTrap.setText("Bem vindo, jovem Herói, você acaba de achar uma sala bônus! \nAqui você achará os melhores itens da Dungeon, mas as perguntas\ntambém são mais difíceis, preparado?\n\nComo são conhecidos os Reinos de Uh, Zi e No, respectivamente?\n\na) “Terra da Magia, Fortaleza Impenetrável e Paraíso da Alquimia”.\nb) “Fortaleza Impenetrável, Terra da Magia e Paraíso da Alquimia”.\nc) “Terra da Magia, Paraíso da Alquimia, Fortaleza Impenetrável”.\nd) “Paraíso da Alquimia, Fortaleza Impenetrável, Terra da Magia”");
            TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        }
    }
    public void BossFala(){
        TP.removeAll();
        getContentPane().removeAll();
        PBoss = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/teladialogoboss.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TextoBoss();
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        PBoss.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        //PTrap.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/trapquiz.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        BFA = new JButton(" Aceitar ");
        BFB = new JButton(" Recusar ");
        
        BFA.addActionListener(this);
        BFB.addActionListener(this);
        
        BFA.setPreferredSize(new Dimension(20,20));
        JPanel PTB = new JPanel(new GridLayout(1,4));
        PTB.add(BFA);
        PTB.add(BFB);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        PBoss.add(PTD,BorderLayout.EAST);
        
        TP.add("  Boss  ",PBoss);
        Caracteristicas();
        TP.add("  Char  ",PChar);
        TP.add("  Regras  ",PRegras);
        
        if(Aba==1)
            TP.setSelectedIndex(1);
        else if(Aba==2)
            TP.setSelectedIndex(2);
        else
            playsound("sound/bossfala.wav");
       
        PBoss.setBackground(new Color(85 ,26,139));
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    public void BossFala2(){
        TP.removeAll();
        getContentPane().removeAll();
        PBoss = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/teladialogoboss.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TextoBoss();
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        PBoss.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        //PTrap.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/trapquiz.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        //BFA = new JButton(" Não ");
        BFSim = new JButton(" Continue ");
        
        //BFA.addActionListener(this);
        BFSim.addActionListener(this);
        
        //BFA.setPreferredSize(new Dimension(20,20));
        JPanel PTB = new JPanel(new GridLayout(1,4));
        PTB.add(BFSim);
        //PTB.add(BFA);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        PBoss.add(PTD,BorderLayout.EAST);
        
        TP.add("  Boss  ",PBoss);
        Caracteristicas();
        TP.add("  Char  ",PChar);
        TP.add("  Regras  ",PRegras);
        
        if(Aba==1)
            TP.setSelectedIndex(1);
        else if(Aba==2)
            TP.setSelectedIndex(2);
        else
            playsound("sound/bossfala.wav");
       
        PBoss.setBackground(new Color(85 ,26,139));
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    public void Tutorial(){
        playsound("sound/tuto1.wav");
        TP.removeAll();
        getContentPane().removeAll();
        Ptuto = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TTrap.setText(" Olá, jovem herói, meu nome é Lady Trap, a deusa das armadilhas, eu tomo \na forma da primeira coisa que você pensa ao ouvir a palavra ‘armadilha’, o que \né estranho, pois eu estou com uma aparência como essa... você é um pouco \nestranho, né? Enfim, vou explicar as regras do meu joguinho: Toda vez que \nvocê me vir, saiba que sua vida pode correr perigo, literalmente, se errar \nminha pergunta, perderá uma quantidade de HP ou, se por acaso, for uma Sala \nBônus, você só terá a ganhar. Que bom, certo? Todas as perguntas serão \nrelativas a história do jogo, então, se você, apressadinho, clicou em ‘pular’ \nno início, saiba que foi intencional, uma longa e chata introdução para \npeneirar os mais fracos. As perguntas serão de múltipla escolha, com apenas \numa correta, no caso da Armadilha. Já nas Salas Bônus, as perguntas serão \num pouco mais difíceis, então espero que realmente tenha prestado atenção \nna história. Caso acerte a pergunta da Sala Bônus, você ganhará um item muito \nbom, afinal, você provou que consegue lidar com os obstáculos à sua frente, \nmesmo sendo apenas uma pergunta. Caso caia numa Armadilha, você  \nperderá HP ao errar. Então vamos a uma pergunta teste:\n\nQual classe você escolheu?\n\n" +
        "a) Guerreiro\n" +
        "b) Arqueiro\n" +
        "c) Mago\n" +
        "d) Classe?? ");
        
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        Ptuto.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        Ptuto.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Tutorial.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        JButton BTutoA = new JButton(" A ");
        JButton BTutoB = new JButton(" B ");
        JButton BTutoC = new JButton(" C ");
        JButton BTutoD = new JButton(" D ");
        
        BTutoA.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if(Heroi.getclasse().equals("Guerreiro"))
                JOptionPane.showMessageDialog(null, "Parabéns! Você lembra de algo que escolheu 5 minutos atrás!","Tutorial",JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "É... já vi que você vai sofrer pela frente","Tutorial",JOptionPane.WARNING_MESSAGE);
            Tutorial2();
        }
        });
        BTutoB.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if(Heroi.getclasse().equals("Arqueiro"))
                JOptionPane.showMessageDialog(null, "Parabéns! Você lembra de algo que escolheu 5 minutos atrás!","Tutorial",JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "É... já vi que você vai sofrer pela frente","Tutorial",JOptionPane.WARNING_MESSAGE);
            Tutorial2();
        }
        });
        BTutoC.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if(Heroi.getclasse().equals("Mago"))
                JOptionPane.showMessageDialog(null, "Parabéns! Você lembra de algo que escolheu 5 minutos atrás!","Tutorial",JOptionPane.WARNING_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "É... já vi que você vai sofrer pela frente","Tutorial",JOptionPane.WARNING_MESSAGE);
            Tutorial2();
        }
        });
        BTutoD.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(null, "É... já vi que você vai sofrer pela frente","Tutorial",JOptionPane.WARNING_MESSAGE);
            Tutorial2();
        }
        });
        JPanel PTB = new JPanel(new GridLayout(1,4));

        PTB.add(BTutoA);
        PTB.add(BTutoB);
        PTB.add(BTutoC);
        PTB.add(BTutoD);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        Ptuto.add(PTD,BorderLayout.EAST);
        
        
        Ptuto.setBackground(new Color(238,154,73));
        
        TP.add(Ptuto);
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    public void Tutorial2(){
        playsound("sound/tuto2.wav");
        TP.removeAll();
        getContentPane().removeAll();
        Ptuto = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TTrap.setText("    De toda forma, acho que deu para entender as Armadilhas e as Salas Bônus.\n" +
        "Agora, falaremos um pouco da Dungeon. São 5 andares, cada andar possui\n" +
        "duas lutas e duas salas que poderão ser Armadilhas ou Sala Bônus, então\n" +
        "tenha cuidado. No local que você começa, terá uma fogueira com\n" +
        "acampamento, você pode sempre voltar até lá para se recuperar totalmente,\n" +
        "sem limite de quantidade, então aproveite! Cada andar tem um nível de\n" +
        "monstros, sendo o primeiro andar com monstros de nível 1, o segundo\n" +
        "andar, monstros de nível dois, assim sucessivamente. Apenas o quinto\n" +
        "andar é especial, não há nada nele, exceto o grande vilão: O Destruidor.\n" +
        "Eu recomendo que não seja apressado e vá direto até o vilão, seria bem \n" +
        "ruim para você, te garanto. \n\n" +
        "   Então, vamos as lutas. Todas as lutas possuem dois inimigos para enfrentar,\n" +
        "exceto o Vilão. Durante a luta, você tem 4 opções: Atacar, onde você\n" +
        "tentará infligir dano no adversário com um golpe básico, Habilidades, onde\n" +
        "você poderá escolher uma das três habilidades, mesmo que você comece\n" +
        "com apenas uma, que sua classe possui para atacar o inimigo ou te deixar\n" +
        "mais forte, Bolsa, onde você poderá usar seus itens coletados durante a\n" +
        "jornada e por fim, Fugir, onde você pode, covardemente, fugir da luta ao\n" +
        "invés de enfrentar com honra seu inimigo, caso tenha uma bomba de fumaça.\n\n" +
        "   Para de fato ficar mais forte, você precisará aumentar seus Pontos de Status,\n" +
        "novos PS são recebidos a cada nível que se passa, você pode usá-los para\n" +
        "aumentar seu Ataque, Defesa e Vida. Além disso, também podem ser usados\n" +
        "para desbloquear novas habilidades e fortalece-las, se assim desejar. Para\n" +
        "que as mudanças sejam permanentes, você precisará descansar na fogueira.\n\n" +
        "   Bom, jovem Herói, isso é tudo, a partir de agora, trace seu caminho sozinho\n" +
        "e descubra todos e segredos e verdades envolvendo esse mundo, afinal, esse\n" +
        "também é o trabalho de um Herói. Boa sorte em sua jornada, espero que você\n" +
        "se torne o Dungeon Fighter que os Criadores tanto esperam de você, a gente\n" +
        "se vê numa Armadilha ou Sala Bônus.\n\n" + 
        "   Ah, antes de ir, lembre-se: nem tudo é o que parece, então tenha muito\n" +
        "cuidado com suas escolhas, afinal, você é o único que pode mudar o nosso\ndestino...\n\n\n\n");
        
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        Ptuto.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        Ptuto.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Tutorial.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        JButton BTProximo = new JButton(" Continuar ");
        
        BTProximo.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Jogo();
        }
        });
        
        BTProximo.setPreferredSize(new Dimension(20,40));
        JPanel PTB = new JPanel(new GridLayout(1,4));

        PTB.add(BTProximo);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        Ptuto.add(PTD,BorderLayout.EAST);
        
        
        Ptuto.setBackground(new Color(238,154,73));
        
        TP.add(Ptuto);
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    public void TextoBoss(){
        if(TextoBoss==0){
            TTrap.setText("     Vejo que conseguiu, enfim, chegar até aqui, Dungeon Fighter. Finalmente \n" +
            "estamos nos conhecendo, eu sou O Destruidor, o Grande Tabu da sua raça, \n" +
            "e uma dádiva para os Monstros. Antes que você venha me enfrentar, eu \n" +
            "preciso te contar uma coisa. A verdade por trás da “paz”, que vocês humanos \n" +
            "se vangloriam. Um dos Criadores me contou a verdade, pois não aguentava \n" +
            "mais ver o que sua criação se tornou. Esse era o Criador do Destino, aquele \n" +
            "que guia a história, desde o início, para seu final. A verdade que ele me \n" +
            "mostrou, chocou não só a mim, como a todos os monstros selados aqui. Há \n" +
            "muito tempo, quando o Reino de Zinouh estava para nascer, foi criado o \n" +
            "“Plano de Pacificação”, proposto por Uh, o Senhor da Magia. Basicamente, \n" +
            "o plano era um acordo entre os Três Grandes Reinos, em que os três se \n" +
            "uniriam para caçar, aprisionar e “pacificar” os monstros de Kibesa. O Senhor \n" +
            "da Magia era poderoso demais, podia modificar memórias, personalidades e\n" +
            "até sentimentos... Ele e seus aprendizes fizeram uma lavagem cerebral em \n" +
            "todos os monstros capturados, fazendo-os achar que trabalhar para os \n" +
            "humanos era uma enorme honra e forma de se redimir por sua natureza \n" +
            "selvagem e violenta. Logo, a maior parte dos monstros de Kibesa já tinham \n" +
            "sido pacificados, aqueles que souberam do plano, logo foram exterminados. \n" +
            "E assim, com todos os monstros sobreviventes trabalhando para vocês, a tão \n" +
            "falada “paz” foi instaurada, com o sangue e a manipulação do meu povo. Ao\n" +
            "descobrir isso, sentimentos horríveis, bem mais fortes do que quando eu fui \n" +
            "perseguido por vocês, nasceram em mim. Ódio. Vingança. Fúria. Tudo isso tomou \n" +
            "conta de mim, então os Criadores tentaram me parar. Tomado por sentimentos \n" +
            "humanos, eu consegui eliminar 3 dos 4 Criadores e absorvi seus poderes. O único \n" +
            "que escapou, após eu destruir sua existência, foi o Criador do Destino. Logo em \n" +
            "seguida, reuni todos aqueles que quiseram me seguir e destruí o selo da Dungeon,\n" +
            "iniciando assim, A Maior Guerra de Todos os Tempos. Por vinte e cinco anos, eu \n" +
            "matei, destruí, amaldiçoei... Então, eu enganei o destino, levei todo o meu exército\n" +
            "para Uh e deixei a Dungeon desprotegida. Eu sabia que o Criador do Destino traria \n" +
            "o Dungeon Fighter até aqui. Isso mesmo, você está sendo manipulado. Não \n" +
            "achou estranho você ter vindo até aqui sem motivo algum? Ou o fato de ter \n" +
            "uma quantidade ínfima de monstros dentro da base secreta do vilão? Os \n" +
            "monstros que ficaram aqui, são os que não quiseram ir para guerra, não \n" +
            "escolheram me seguir, caso você tenha matado algum, você acaba de se tornar \n" +
            "um assassino sujo, que mata inocentes por motivo algum. Mas não se preocupe. \n" +
            "Você é a reencarnação do Criador do Destino, você tem o último poder que falta \n" +
            "para eu completar o meu objetivo: criar um novo mundo. Um mundo de paz, \n" +
            "liberdade, sem conflitos ou ódio, um mundo onde humanos e monstros podem\n" +
            "coexistir, e essa guerra, e nenhuma outra, nunca aconteceram. Para isso, eu preciso \n" +
            "da sua cooperação. Juntos, podemos mudar todo o mundo, apagar toda essa \n" +
            "história sombria e violenta. Você é a última esperança que me resta na \n" +
            "humanidade, mostre que humanos podem ser bons e compreensíveis, por favor... \n" +
            "\n" +
            "Bom, o que me diz, aceita se unir a mim?\n\n\n\n");
        }
        if(TextoBoss==1){
            TTrap.setText("     É uma pena. De verdade. Tudo o que eu queria agora era poder acreditar em \nvocês, humanos, novamente. Acabar com todo esse ódio dentro de mim. \nMas, parece que não será possível... Bom, prepare-se. Pois agora... Irei \nerradicar você e, em seguida, todos os humanos desse mundo. Criarei um \nnovo e utópico mundo, apenas para os monstros!\n\n");
        }
        if(TextoBoss==2){
            TTrap.setText("     Muito Obrigado, Dungeon Fighter, serei eternamente grato a você, por não \n" +
            "me fazer derramar ainda mais sangue. \n    Agora, venha aqui, temos muito a fazer nesse mundo!");
        }
        if(TextoBoss==3){
            TTrap.setText("     Como isso é possível? Como eu, um ser que transcende a tudo o que existe, \n" +
            "pôde ter sido derrotado por um simples humano? Eu tenho o poder de três \n" +
            "dos quatro Criadores, como alguém com o poder de um só conseguiu me \n" +
            "ferir? Isso não faz sentido... \n" +
            "       Eu não posso morrer! Agora, quem vai salvar os monstros? Quem vai \n" +
            "defende-los da maldade humana? E-Eu... Dungeon Fighter, por favor, eu só \n" +
            "te peço isso: use esse poder e crie um novo mundo. Um mundo de paz para \n" +
            "os monstros. É o meu único e último desejo...");
        }
        
    }
    public void Final1(){
        playsound("sound/fim1.wav");
        getContentPane().removeAll();
        this.cursorhist();
        JButton BPróximo = new JButton("Próximo");
        BPróximo.setPreferredSize(new Dimension(10,40));
        //Pular.setIcon());
        JLabel espaço = new JLabel();
        espaço.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço1 = new JLabel();
        espaço1.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco1.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço2 = new JLabel();
        espaço2.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco2.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço3 = new JLabel();
        espaço3.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco3.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço4 = new JLabel();
        espaço4.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco4.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço5 = new JLabel();
        espaço5.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco5.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        Historia = new JPanel(new BorderLayout());
        JPanel botao = new JPanel(new GridLayout(1,1));
        botao.setPreferredSize(new Dimension(10,40));
        botao.add(espaço);
        botao.add(espaço1);
        botao.add(espaço2);
        botao.add(espaço3);
        botao.add(espaço4);
        botao.add(espaço5);
        botao.add(BPróximo);
        JLabel perg1 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/final11.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg1.setPreferredSize(new Dimension(1100,620));
        JLabel perg2 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/final12.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg2.setPreferredSize(new Dimension(1100,620));
        JLabel perg3 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/final13.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg3.setPreferredSize(new Dimension(1100,620));
        JLabel perg4 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/final14.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg4.setPreferredSize(new Dimension(1100,620));
        switch(imgf1){
            case 0 : Historia.add(perg1); break;
            case 1 : Historia.add(perg2); break;
            case 2 : Historia.add(perg3); break;
            case 3 : Historia.add(perg4); break;
        }
        Historia.add(botao,BorderLayout.SOUTH);
        getContentPane().add(Historia);
        //playsound("sound/narracao.wav");
        this.paintComponents(getGraphics());
        
        BPróximo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(imgf1 == 3){
                    Trapfinal1();
                }else if(imgf1==2){
                    imgf1++;
                    Final1();
                }else if(imgf1==1){
                    imgf1++;
                    Final1();
                }else if(imgf1==0){
                    imgf1++;
                    Final1();
                }
            }    
        });
    }
    public void Trapfinal1(){
        TP.removeAll();
        getContentPane().removeAll();
        Ptuto = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TTrap.setText("     Parabéns! Ao escolher se unir ao vilão, você conseguiu o verdadeiro final de \n" +
        "Dungeon Fighter, o Final “Um Novo Mundo de Esperança”! De fato, esse é um \n" +
        "ótimo final, eu nem precisei fazer nada, você resolveu tudo sozinho, parabéns! \n" +
        "Só foi meio clichê né, “E todos viveram felizes para sempre”, mas, todos eles \n" +
        "merecem, como dizem: “A vingança nunca é plena, mata a alma e a envenena”, \n" +
        "certo? Parece que você realmente prestou atenção quando eu disse: “Nem tudo \n" +
        "é o que parece” e “Você é o único que pode mudar o nosso destino”, parabéns! \n" +
        "Ou só é uma boa pessoa e realmente não entendeu, de qualquer forma, agora o \n" +
        "mundo é um lugar melhor, graças a você. \n     Como você é humano, eu sei que você " +
        "jogará de novo e apagará toda a felicidade \ndeles, eu não te julgo, eu mesmo já fiz " +
        "isso várias e várias vezes, como na vez \nantes dessa. Bom, sendo assim, até a " +
        "próxima, num final não tão bom \nquanto esse.\n\n\n\n");
        
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        Ptuto.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        //Ptuto.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Tutorial.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        JButton BTProximo = new JButton(" Continuar ");
        
        BTProximo.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Fim();
        }
        });
        
        BTProximo.setPreferredSize(new Dimension(20,40));
        JPanel PTB = new JPanel(new GridLayout(1,4));

        PTB.add(BTProximo);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        Ptuto.add(PTD,BorderLayout.EAST);
        
        
        Ptuto.setBackground(new Color(238,154,73));
        
        TP.add(Ptuto);
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
    public void Fim(){
        playsound("sound/fim.wav");
        try { Thread.currentThread().sleep(800);} catch (Exception e) {e.printStackTrace();}
        getContentPane().removeAll();
        TA.setText("                        It's Show Time!!");
        JPanel A = new JPanel(new GridLayout(1,1));
        
        JLabel Vic = new JLabel("",new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/TelaTheEnd.png")),JLabel.CENTER);
        Vic.setFont(new Font("Serif",Font.BOLD,40));
        
        A.add(Vic);
        
        getContentPane().add(A);
        this.paintComponents(getGraphics());
        try { Thread.currentThread().sleep(6200);} catch (Exception e) {e.printStackTrace();}
        this.dispose();
        clip.stop();
        clip.close();
        Jogo T = new Jogo();
    }
    public void Final2(){
        imgf2++;
        playsound("sound/bossfala.wav");
        getContentPane().removeAll();
        this.cursorhist();
        JButton BPróximo = new JButton("Próximo");
        BPróximo.setPreferredSize(new Dimension(10,40));
        //Pular.setIcon());
        JLabel espaço = new JLabel();
        espaço.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço1 = new JLabel();
        espaço1.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco1.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));
        JLabel espaço2 = new JLabel();
        espaço2.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco2.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço3 = new JLabel();
        espaço3.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco3.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço4 = new JLabel();
        espaço4.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco4.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        JLabel espaço5 = new JLabel();
        espaço5.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Imagens/Historia/espaco5.png")).getImage().getScaledInstance(158, 40, Image.SCALE_DEFAULT)));;
        Historia = new JPanel(new BorderLayout());
        JPanel botao = new JPanel(new GridLayout(1,1));
        botao.setPreferredSize(new Dimension(10,40));
        botao.add(espaço);
        botao.add(espaço1);
        botao.add(espaço2);
        botao.add(espaço3);
        botao.add(espaço4);
        botao.add(espaço5);
        botao.add(BPróximo);
        JLabel perg1 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/predialogo.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg1.setPreferredSize(new Dimension(1100,620));
        JLabel perg2 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/vilaomorre.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg2.setPreferredSize(new Dimension(1100,620));
        JLabel perg3 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/desejoboss1.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg1.setPreferredSize(new Dimension(1100,620));
        JLabel perg4 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/desejoboss2.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg2.setPreferredSize(new Dimension(1100,620));
        JLabel perg5 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Historia/finalpadrao.png")).getImage().getScaledInstance(1100, 600, Image.SCALE_DEFAULT)));
        perg2.setPreferredSize(new Dimension(1100,620));
        switch(imgf2){
            case 1 : Historia.add(perg1); break;
            case 2 : Historia.add(perg2); break;
            case 3 : Historia.add(perg3); break;
            case 4 : Historia.add(perg4); break;
            case 5 : Historia.add(perg5); break;
            
        }
        Historia.add(botao,BorderLayout.SOUTH);
        getContentPane().add(Historia);
        //playsound("sound/narracao.wav");
        this.paintComponents(getGraphics());
        
        BPróximo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch(imgf2){
                    case 1 : TextoBoss = 3; BossFala2(); break;
                    case 2 : Trapfinal2(); break;
                    case 3 : Final2(); break;
                    case 4 : Fim(); break;
                    case 5 : Fim(); break;
                }
            }    
        });
    }
    public void Trapfinal2(){
        TP.removeAll();
        getContentPane().removeAll();
        Ptuto = new JPanel(new BorderLayout());
        JLabel IBoss = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Telatrap.png")).getImage().getScaledInstance(550, 650, Image.SCALE_DEFAULT)));
        TTrap = new JTextArea(10,40);
        TTrap.setEditable(false);
        TTrap.setText("     Olá, jovem Herói, ou devo lhe chamar de Dungeon Fighter? Você conseguiu. \n" +
        "Matou O Destruidor e absorveu o poder de todos os Criadores para si. Agora \n" +
        "acho que é o momento de eu me apresentar de verdade. Eu sou o Criador \n" +
        "do Destino. Fui eu quem lhe chamou até aqui e apenas você pode me ver. Na \n" +
        "verdade, foi mais uma obrigação do que um chamado. Tudo o que O Destruidor \n" +
        "disse é verdade. Eu escolhi você para ser a minha reencarnação, antes de você \n" +
        "nascer. Por todo esse tempo, eu lhe deixei viver a vida que queria, apenas \n" +
        "observando, de dentro de você, o estado do mundo. Mas ele estava errado em \n" +
        "um aspecto. Quando ele me matou, o mundo se tornou criador de seu próprio \n" +
        "destino. Eu já não mais manipulava as amarras da história. E é por isso que essa \n" +
        "guerra está acontecendo. Então, quando consegui controlar meus poderes de \n" +
        "novo, eu manipulei você para vir até aqui. Estava na hora de acabar de uma vez \n" +
        "por todas com essa guerra. E você conseguiu. Matou a maior ameaça desse \n" +
        "mundo, o ser transcendental que podia fazer o que quiser num piscar de olhos. \n" +
        "Parabéns, você provou ser um verdadeiro Dungeon Fighter. Já que você seguiu \n" +
        "tudo o que eu planejei para você, eu vou te dar uma recompensa. Eu vou deixar \n" +
        "você decidir o destino desse mundo. Você tem o poder para recriar o mundo \n" +
        "como quiser. Use-o da maneira que achar melhor\n\n\n\n");
        
        TTrap.setFont(new Font("Arial",Font.PLAIN,15));
        JScrollPane SP = new JScrollPane(TTrap);
        Ptuto.add(SP,BorderLayout.WEST);
        JLabel nd = new JLabel("");
        nd.setPreferredSize(new Dimension(0,0));
        
        //Ptuto.add(new JLabel(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Tutorial.png")).getImage().getScaledInstance(250, 100, Image.SCALE_DEFAULT))),BorderLayout.NORTH);
        
        JPanel PTD = new JPanel(new BorderLayout());
        PTD.add(IBoss,BorderLayout.CENTER);
        
        JButton BA = new JButton(" Realizar próprio desejo ");
        JButton BB = new JButton(" Realizar desejo do vilão");
        
        BA.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            imgf2 = 4;
            Final2();
        }
        });
        BB.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            imgf2 = 2;
            Final2();
        }
        });
        
        BA.setPreferredSize(new Dimension(20,40));
        JPanel PTB = new JPanel(new GridLayout(1,4));

        PTB.add(BA);
        PTB.add(BB);
        
        PTD.add(PTB,BorderLayout.SOUTH);
        
        Ptuto.add(PTD,BorderLayout.EAST);
        
        
        Ptuto.setBackground(new Color(238,154,73));
        
        TP.add(Ptuto);
        
        getContentPane().add(TP);
        this.paintComponents(getGraphics());
    }
}
