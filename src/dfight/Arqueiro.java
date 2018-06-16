package dfight;


public class Arqueiro extends Heroi {

    public Arqueiro(String n) {
        super(n, 1, 9, 9, 70);//(nome, nivel, atq, def, hp)//
    }
    public void Habilidade1(Monstros M1,Monstros M2){
        Jogo.addTexto("Heroi   Reflexos Apurados");
        if(HLevel[1]<=3)
            this.Def+= 3;
        else
            this.Def+=5;
        H[0] = 4;
    }
    public void Habilidade2(Monstros M){
        Jogo.addTexto("Heroi   Flecha Pesada (Pesadãããoo)");
        this.Atacar(M);
        if(HLevel[1]<=3)
            M.setStun(1);
        else
            M.setStun(gerador.nextInt(2)+1);
        
    }
    public void Habilidade3(Monstros M){
        Jogo.addTexto("Heroi   Flecha Envenenada(Venenosa eeeehh)");
        this.Atacar(M);
        M.setVeneno(1);
        if(HLevel[2]<=3)
            H[2]=5;
        else
            H[2]=8;
    }
    public void Passiva(){
        
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
            HUsosTotal[1]+=2;
            HUsos[1]=2;
        }
        else{
            if(HUsosTotal[1]!=4)
                HUsosTotal[1]++;
        }
    }
    public void upHab3(){
        HLevel[2]++;
        if(HUsosTotal[2]==0){
            HUsosTotal[2]=1;
            HUsos[2]=HUsosTotal[2];
        }
        else
        if(HUsosTotal[2]!=3)
            HUsosTotal[2]++;
    }
}