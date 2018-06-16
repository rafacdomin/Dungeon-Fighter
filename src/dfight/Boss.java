
package dfight;

import java.awt.Image;
import javax.swing.ImageIcon;


public class Boss extends Monstros{
    protected static String Nome;
    protected static int Nivel,Atq, Def, DefTotal, Hp,HpTotal;
    public Boss(ImageIcon i){
        super("Oh! Destruidor.",9,i);
    }
}
