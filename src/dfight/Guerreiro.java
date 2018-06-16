package dfight;


public class Guerreiro extends Heroi {

    public Guerreiro(String n) {
        super(n, 1, 7, 8, 100);//(nome, nivel, atq, def, hp)//
    }
    public void Habilidade1(Monstros M1,Monstros M2){
        Jogo.addTexto("Heroi   Grito de Guerra(Demaciaa!!)");
        if(HLevel[0]<4){
        M1.setDef(M1.getDef()-2);
        M2.setDef(M1.getDef()-2);
        }
        else{
            M1.setDef(M1.getDef()-3);
            M2.setDef(M1.getDef()-3);
        }
        H[0] = 5;
    }
    public void Habilidade2(Monstros M){
        Jogo.addTexto("Heroi   Fúria(Tô mt P*****)");
        if(HLevel[1]<4)
            this.Atq+=2;
        else
            this.Atq+=4;
        H[1] = 5;
    }
    public void Habilidade3(Monstros M){
        Jogo.addTexto("Heroi   Pele de Ferro(Duro como pedra)");
        if(HLevel[2]<4)
            H[2] = 6;
        else
            H[2] = 8;
    }
    public void Passiva(){
        if(Nivel<6)
            this.Hp+=2;
        else
            this.Hp+=4;
        if(Hp>HpTotal)
            Hp=HpTotal;
    }
    public void upHab1(){
        HLevel[0]++;
        if(HUsosTotal[0]==0){
            HUsosTotal[0]=1;
            HUsos[0]=HUsosTotal[0];
        }
        else
        if(HUsosTotal[0]!=3)
            HUsosTotal[0]++;
    }
    public void upHab2(){
        HLevel[1]++;
        if(HUsosTotal[1]==0){
            HUsosTotal[1]=1;
            HUsos[1]=HUsosTotal[1];
        }
        else
        if(HUsosTotal[1]!=3)
           HUsosTotal[1]++;
    }
    public void upHab3(){
        HLevel[2]++;
        if(HUsosTotal[2]==0){
            HUsosTotal[2]=2;
            HUsos[2]=HUsosTotal[2];
        }
        else{
            if(HUsosTotal[0]!=4)
                HUsosTotal[2]++;
        }
    }
}