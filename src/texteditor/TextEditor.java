/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class TextEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AppFrame af=new AppFrame();
        af.setVisible(true);
        af.setTitle("TextEditor");
        af.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
        
    }
    
}
