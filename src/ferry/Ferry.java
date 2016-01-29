/*
 * La clase ferry es la clase principal, la clase barco es el hilo.
 */
package ferry;
/**
 *
 * @author jflopez
 */
import java.util.concurrent.Semaphore;
import java.util.*;
class missem {
    public static Semaphore orde = new Semaphore (1);
    public static Semaphore oriz = new Semaphore (1);    
}
class tiempo extends Thread {
    public static boolean tiempo = true;
    public static int dinero = 0;
    @Override
    public void run() {
        try {
            sleep(60000);
            tiempo = false;
        } catch (Exception e) {
        }
    }
}
class orillaiz extends Thread {
    public static int acumiz = 1;
    Window w = new Window("Orilla izquierda");
    @Override
    public void run() {
        try {
            while(tiempo.tiempo){
                Random rndiz = new Random();
                int piz;
                
                piz = rndiz.nextInt(10);
                acumiz = acumiz + piz;
                System.out.println("Esperando en orilla izquierda "+acumiz);
                w.write("Esperando en orilla izquierda "+acumiz);
                sleep(1500);
            }           
        } catch (Exception e) {
        }
    }
}

class orillade extends Thread {
    public static int acumde = 1;
    Window w = new Window("Orilla derecha");
    @Override
    public void run() {
        try {
            while(tiempo.tiempo){
                Random rndde = new Random();
                int pde;
                
                pde = rndde.nextInt(10);
                acumde = acumde + pde;
                System.out.println("Esperando en orilla derecha "+acumde);
                w.write("Esperando en orilla derecha "+acumde);
                sleep(1350);               
            }          
        } catch (Exception e) {
        }
    }
}

class barco extends Thread {
    Window w = new Window("Barco");
    @Override
    public void run(){
        try {
            while(tiempo.tiempo) {//El barco siempre esta moviendose
                //Personas suben al barco en orilla derecha;                              
                System.out.println("El barco llega a la orilla derecha y espera dos segundos");
                w.write("El barco llega a la orilla derecha y espera dos segundos");
                //sleep(2000);                
                missem.orde.acquire(); 
                if (orillade.acumde<=50) {
                    System.out.println("Suben " + orillade.acumde + " pasajeros de la orilla derecha.");
                    w.write("Suben " + orillade.acumde + " pasajeros de la orilla derecha.");
                    tiempo.dinero = tiempo.dinero + (orillade.acumde*2);
                    orillade.acumde = 0;
                }
                else {
                    orillade.acumde = orillade.acumde - 50;
                    System.out.println("Suben 50 pasajeros de la orilla derecha, se quedan en tierra " + orillade.acumde + " pasajeros.");
                    w.write("Suben 50 pasajeros de la orilla derecha, se quedan en tierra " + orillade.acumde + " pasajeros.");
                    tiempo.dinero = tiempo.dinero + (100);
                }
                missem.orde.release();
                
                //Viaja
                System.out.println("Viajando(5s)");
                w.write("Viajando(5s)");
                sleep(5000);
                
                
                //Las personas suben al barco de la orilla izquierda                                
                System.out.println("El barco llega a la orilla izquierda y espera dos segundos");    
                w.write("El barco llega a la orilla izquierda y espera dos segundos");
                //sleep(2000);
                missem.oriz.acquire();
                if (orillaiz.acumiz<=50) {
                    System.out.println("Suben " + orillaiz.acumiz + " pasajeros de la orilla izquierda");  
                    w.write("Suben " + orillaiz.acumiz + " pasajeros de la orilla izquierda");
                    tiempo.dinero = tiempo.dinero + (orillaiz.acumiz*2);
                    orillaiz.acumiz = 0;
                }
                else {
                    orillaiz.acumiz = orillaiz.acumiz - 50;
                    System.out.println("Suben 50 pasajeros de la orilla izquierda, se quedan en tierra " + orillaiz.acumiz + " pasajeros.");        
                    w.write("Suben 50 pasajeros de la orilla izquierda, se quedan en tierra " + orillaiz.acumiz + " pasajeros.");
                    tiempo.dinero = tiempo.dinero + (100);
                }
                missem.oriz.release();
                
                //Viaja
                System.out.println("Viajando(5s)");
                w.write("Viajando(5s)");
                sleep(5000);
            }
        } catch (Exception e) {
        }
        System.out.println("Dinero recaudado en un minuto: "+tiempo.dinero);
        w.write("Dinero recaudado en un minuto: "+tiempo.dinero);
    }
}

public class Ferry {
    public static void main(String[] args) {
        System.out.println("Lanzando personas de las orillas");
        
        orillade od = new orillade();
        od.start();
        orillaiz oi = new orillaiz();
        oi.start();
        
        System.out.println("Lanzando barco");
        barco bar = new barco();
        bar.start();
        
        tiempo t = new tiempo();
        t.start();
        
    }
}