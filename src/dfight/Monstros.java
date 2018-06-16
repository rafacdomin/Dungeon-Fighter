
package dfight;

import static java.lang.Math.*;
import java.util.Random;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.ImageIcon;

public class Monstros{
    private Random gerador = new Random();
    protected String Nome;
    protected int Nivel,Atq, Def, DefTotal, Hp,HpTotal,Veneno,Stun;
    protected ImageIcon MobIcon;
    
    public Monstros(String n, int l, ImageIcon i) {
        if(!(this instanceof Boss)){
            Atq = 3+l*2;
            Def =DefTotal= 2+l*3;
            Hp = HpTotal = 40+20*l;
        }
        else{
            Atq = 20;
            Def =DefTotal= 20;
            Hp = HpTotal = 300;
            
        }
        MobIcon = i;
        Nome = n;
        Nivel = l;
        Veneno = 0;
        Stun = 0;
    }

    public String getNome() {
        return Nome;
    }

    public int getDefTotal() {
        return DefTotal;
    }

    public void setVeneno(int Veneno) {
        this.Veneno = Veneno;
    }
    
    public int getNivel() {
        return Nivel;
    }

    public int getAtq() {
        return Atq;
    }

    public int getDef() {
        return Def;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int Hp) {
        this.Hp = Hp;
    }
    public void setStun(int Stun) {
        this.Stun = Stun;
    }
    public void Ven(){
        this.Hp -= Heroi.H[2];
    }
    
    public void Atacar(Heroi H){
        
        int Dano = (gerador.nextInt(4)+1)*this.Atq - (gerador.nextInt(2)+1)*H.getDef();
        //JOptionPane.showMessageDialog(null, "Mob: "+Nome+"   Dano(Antes do multiplicador): "+Dano,"Atq",JOptionPane.WARNING_MESSAGE);
        if(Dano>0){
            int Dano2;
            if(H.Nivel<6)
                Dano2 = (int)(Dano*0.7*0.3);
            else
                Dano2 = (int)(Dano*0.7*0.4);
            if(H instanceof Guerreiro && H.H[2]>0){
                Dano*=0.49;  // 0.7*0.7 = 0.49
                Jogo.addTexto(""+Nome+"  "+Dano+" Dano (Reduzido)");
                H.setHp(H.getHp()-Dano);
            }
            else{
            Dano*=0.7;
            Jogo.addTexto(""+Nome+"  "+Dano+" Dano");
            H.setHp(H.getHp()-Dano);
            }
            if(H instanceof Guerreiro){
                Jogo.addTexto(""+Nome+"  "+Dano2+" Dano Refletido (Espinho)");
                this.Hp -= Dano2;
            }
        }
        if(Dano>=-4&&Dano<=0){
            Jogo.addTexto(""+Nome+" Fez Nada");
        }
        if(Dano<-4){
            Jogo.addTexto(""+Nome+" "+(int)(abs(Dano*H.getDef()*0.1*0.8))+" Dano em si");
            this.Hp += Dano*H.getDef()*0.1*0.8;
        }
    }

    public void setDef(int Def) {
        this.Def = Def;
    }
}
