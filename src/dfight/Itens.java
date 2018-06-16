package dfight;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author aluno
 */
public class Itens {
    protected String Nome;
    protected ImageIcon ItIcon,ItIconDes;
    protected int Qnt,Tipo;
    
    public Itens(String n, ImageIcon icon, int q, int t,ImageIcon des){
        Nome = n;
        ItIcon = icon;
        Qnt = q;
        Tipo = t;
        ItIconDes = des;
    }
    
    public void usarItem(){
        switch(this.Nome){
            case "PocaoHp":
                if(this.Qnt>0){
                    this.Qnt--;
                    
                    Heroi.Hp=Heroi.HpTotal;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboupverde.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
            case "PocaoHab":
                if(this.Qnt>0){
                    this.Qnt--;
                    Heroi.HUsos=Heroi.HUsosTotal;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acabouphab.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
                
            case "BombaFumaça":
                if(this.Qnt>0){
                    this.Qnt--;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboubombadegas.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
            case "Presente":
                if(this.Qnt>0){
                    this.Qnt--;
                    Jogo.mobs[Jogo.le][Jogo.le2].setStun(5);
                    Jogo.mobs[Jogo.le][Jogo.le2+1].setStun(5);
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboupresente.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
            case "Cafe":
                if(this.Qnt>0){
                    this.Qnt--;
                    Jogo.Cafe = 1;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboucafe.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
            case "tnt":
                if(this.Qnt>0){
                    this.Qnt--;
                    Jogo.mobs[Jogo.le][Jogo.le2].Hp*=0.60;
                    Jogo.mobs[Jogo.le][Jogo.le2+1].Hp*=0.60;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboudinamite.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                    
                }
                break;
                
            case "Pena":
                if(this.Qnt>0){
                    this.Qnt--;
                    Jogo.addTexto("Heroi   Ressuscitado! (Pena Gasta)");
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboupena.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                }
                break;
                
            case "Mapa":
                if(this.Qnt>0){
                    this.Qnt--;
                    if(this.Qnt==0){
                        ItIcon = new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/Botoes/acaboupena.png")).getImage().getScaledInstance(135, 140, Image.SCALE_DEFAULT));
                    }
                    
                }
                break;
        }
        }
    }