
package service;

import interfaces.AtualizavelTela;

public class CronometroJogo extends Thread{
    
    private int segundos;
    private volatile boolean rodando;
    private AtualizavelTela tela;
    
    public CronometroJogo (AtualizavelTela tela){
        this.tela = tela;
        this.segundos = 0;
        this.rodando = true;
    }

@Override
public void run(){
    while(rodando){
        try{
            Thread.sleep(1000);
            segundos++;
            tela.atualizarTempo(segundos);
        }catch(InterruptedException e){
            System.out.println("Cronometro interrompido: " + e.getMessage());
        rodando = false;
            }
        }
    }
public void parar (){
    rodando = false;
}
public int getSegundos(){
    return segundos;
}
}

