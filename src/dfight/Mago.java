package dfight;


public class Mago extends Heroi {
    
    static int cont=0;
    public Mago(String n) {
        super(n, 1, 10, 7, 80);//(nome, nivel, atq, def, hp)//
        
    }
    public void Habilidade1(Monstros M1,Monstros M2){
        Jogo.addTexto("Heroi   Trovão(AAiii AAiii)");
        if(HLevel[0]<=3){
        M1.setHp(M1.getHp()-this.Atq);
        M2.setHp(M2.getHp()-this.Atq);
        if(gerador.nextInt(4)==0){
            H[0] = 1;
        }
        }
        else{
            M1.setHp(M1.getHp()-(int)(this.Atq*1.2));
            M2.setHp(M2.getHp()-(int)(this.Atq*1.2));
            if(gerador.nextInt(3)==0){
                H[0] = 1;
        }
        }
    }
    public void Habilidade2(Monstros M){
        Jogo.addTexto("Heroi   Ladrão Espiritual");
        if(HLevel[1]<=3){
        M.setHp(M.getHp()-this.Atq);
        this.setHp(this.getHp()+(int)(this.Atq*0.5));
        }
        else{
            M.setHp(M.getHp()-(int)(this.Atq*1.2));
            this.setHp(this.getHp()+(int)(this.Atq*0.7));
        }
    }
    public void Habilidade3(Monstros M){
        if(HLevel[2]<=3){
            Jogo.addTexto("Heroi   Prisão Mental(Trabalho)");
            if(M instanceof Boss)
                M.setStun(2);
            else
                M.setStun(3);
        }
        else{
            if(M instanceof Boss)
                M.setStun(3);
            else
                M.setStun(4);
        }
    }
    public void Passiva(){
        cont++;
        if(Nivel<6){
        if(cont==4){
            H[3]=1;
            Jogo.addTexto("Heroi   Meditação Ativa! (Passiva)");
            cont = 0;
        }
        }
        else{
            if(cont==3){
            H[3]=1;
            Jogo.addTexto("Heroi   Meditação Ativa! (Passiva)");
            cont = 0;
        }
        }
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
        if(HUsosTotal[2]!=4)
            HUsosTotal[2]++;
    }
}