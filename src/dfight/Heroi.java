
package dfight;

import static java.lang.Math.abs;
import java.util.Random;


public abstract class Heroi{
    protected Random gerador = new Random();
    protected int Elixir;
    protected static int H[] = new int[4],HLevel[] = new int[4], HUsos[] = new int[3], HUsosTotal[] = new int[3];
    protected String Nome;
    protected static int Nivel,Atq, AtqTotal, Def,DefTotal, Hp,HpTotal, PS, PSTotal,PH;
    public Heroi(String n, int l, int a, int d, int h) {
        Nome = n;
        Nivel = l;
        Atq =AtqTotal= a;
        Def =DefTotal= d;
        Hp = h;
        HpTotal = h;
        H[0]=H[1]=H[2]=0;
        HUsos[0] = HUsosTotal[0] = HLevel[0] = 0;
        HUsos[1] = HUsosTotal[1] = HLevel[1] = 0;
        HUsos[2] = HUsosTotal[2] = HLevel[2] = 0 ;
        PS = PSTotal = 0;
        PH=1;
    }

    public static int getDefTotal() {
        return DefTotal;
    }

    public static int getAtqTotal() {
        return AtqTotal;
    }

    public static int[] getHUsosTotal() {
        return HUsosTotal;
    }

    public static int getNivel() {
        return Nivel;
    }

    public static int getAtq() {
        return Atq;
    }
    
    public String getNome() {
        return Nome;
    }

    public static int getHpTotal() {
        return HpTotal;
    }

    public static int getDef() {
        return Def;
    }

    public static int getHp() {
        return Hp;
    }
    
    public static void setAtq(int Atq) {
        Heroi.Atq = Atq;
    }

    public static void setDef(int Def) {
        Heroi.Def = Def;
    }

    public static void setHp(int Hp) {
        Heroi.Hp = Hp;
    }

    public static void LevelUp(){
        PS+=2;
        PSTotal+=2;
        PH++;
        Nivel++;
    }
    
    public static void setHpTotal(int HpTotal) {
        Heroi.HpTotal = HpTotal;
    }
    
    public void usarElixir(){
        Hp += 20;
        Elixir -= 1;
    }
    public void setElixir(int n){
        Elixir = Elixir + n;
    }
    public int getElixir(){
        return Elixir;
    }
    public void Atacar(Monstros M){
        int Dano;
        Dano = (int) ((gerador.nextInt(3)+1)*this.Atq - (gerador.nextInt(2)+1)*M.getDef());
        if(Dano>0){
            M.setHp(M.getHp()-(int)(Dano*0.7));
            Jogo.addTexto("Heroi "+"  "+((int)(Dano*0.7))+"  Dano em "+M.Nome);
        }
        if(Dano>=-3&&Dano<=0){
            Jogo.addTexto("Heroi  Fez nada");
        }
        if(Dano<-3){
            this.Hp += Dano*M.getDef()*0.1;
            Jogo.addTexto("Heroi "+"  "+(abs((int)(Dano*M.getDef()*0.1)))+"  Dano em si");
        }
    }
    public String getclasse(){
        if (this instanceof Guerreiro){
            return "Guerreiro";
        }
        else if (this instanceof Arqueiro){
            return "Arqueiro";
        }
        else if (this instanceof Mago){
            return "Mago";
        }else
            return null;
    }
    public abstract void Habilidade1(Monstros M1,Monstros M2);
    public abstract void Habilidade2(Monstros M);
    public abstract void Habilidade3(Monstros M);
    public abstract void Passiva();
    public abstract void upHab1();
    public abstract void upHab2();
    public abstract void upHab3();
}
