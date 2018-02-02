package PayasoCar;

import java.util.Random;
import java.util.Scanner;


// Andrés Valera y Juan Carlos Medina.

public class Payaso implements Runnable{
	static Coche car = new Coche();
	private static int tiempo;
	private static Scanner leer;
	private static int num;
	private static int puerta1In=0;
	private static int puerta2In=0;
	private static int puerta3In=0;
	private static int puerta4In=0;
	private static boolean isExecuted=true;
    
	public static void main(String[] args) throws InterruptedException {
		leer = new Scanner(System.in);
		
		//Creo el objeto de payaso
		Payaso h1 = new Payaso();
		
		//Creo cada hilo y le asigno payaso
		Thread hA = new Thread(h1);
		Thread hB = new Thread(h1);
		Thread hC = new Thread(h1);
		Thread hD = new Thread(h1);
		
		//Le pongo nombre a los hilos
		hA.setName("puerta 1");
		hB.setName("puerta 2");
		hC.setName("puerta 3");
		hD.setName("puerta 4");
		
		//Pregunto al usuario si quiere priorizar una puerta
		System.out.println("¿Quieres que entren los 70 payasos de una puerta primero?");
		System.out.println("1. SI -- 2. NO");
		num = leer.nextInt();
		
		//Si el usuario ha elegido si entra al if
		if(num == 1) {
			//Pregunto al usuario que puerta quiere priorizar
			System.out.println("¿Por que puerta quieres que entren primero?");
			System.out.println("Elige la puerta 1, 2, 3 ó 4?");
			num = leer.nextInt();
			
			//Compruebo la eleccion e inicio los hilos en consecuencia
			switch(num) {
				case 1:
					hA.start();
					hA.join();
					hB.start();
					hC.start();
					hD.start();
					break;
				case 2:
					hB.start();
					hB.join();
					hA.start();
					hC.start();
					hD.start();
					break;
				case 3:
					hC.start();
					hC.join();
					hA.start();
					hB.start();
					hD.start();
					break;
				case 4:
					hD.start();
					hD.join();
					hA.start();
					hB.start();
					hC.start();
					break;	
			}
		}else { //Si el usuario elige cualquier otra opcion inicio todos los hilos a la vez
			hA.start();
			hB.start();
			hC.start();
			hD.start();
		}
		
		// Se ejecuta hasta que todos los hilos hayan terminado y cuando se terminan todos muestra resultados finales.
		while(isExecuted){
		if(hA.getState()==Thread.State.TERMINATED && hB.getState()==Thread.State.TERMINATED && hC.getState()==Thread.State.TERMINATED && hD.getState()==Thread.State.TERMINATED) {	
			System.out.println("La capacidad máxima del mini cooper era: "+car.getCapacidad());
			if(puerta1In==0) {
				puerta1In=70;
			} else if(puerta2In==0) {
				puerta2In=70;
			} else if(puerta3In==0) {
				puerta3In=70;
			} else if(puerta4In==0) {
				puerta4In=70;
			}
			System.out.println("Por la puerta 1 han pasado "+puerta1In+" payasos.");
			System.out.println("Por la puerta 2 han pasado "+puerta2In+" payasos.");
			System.out.println("Por la puerta 3 han pasado "+puerta3In+" payasos.");
			System.out.println("Por la puerta 4 han pasado "+puerta4In+" payasos.");
			isExecuted=false;
		}
		}
		
	}
	
	@Override
	public void run() {
		
		//Loop que controla un maximo de 70 payasos
		for(int i=1;i<=70;i++) {
			//Llamo al metodo de añadir payaso
			
			anyadirPayaso(i);
			
			
			//Le digo al hilo cuanto tiempo tiene que esperar
			try{
				//Genero el tiempo aleatoriamente y se lo asigno
				tiempo = randTime();
				Thread.sleep(tiempo);
			}catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//<SECCIÓN CRITICA>
	//Metodo que accede a la cantidad de payasos y los aumenta de manera sincronizada
	private synchronized void anyadirPayaso(int num) {
		//Compruebo que aún caben payasos
		if(car.getCantidad() < car.getCapacidad()) {
			//Meto el payaso en el coche
			car.meterPayaso();
			
			//Genero el numero aleatorio que mostrara una frase u otra
			int varRandom=randFrase();
			String[] text ={"¡Uy, Un payaso!", "¡Aiba, Otro más!", "¡Vaya! ¿De donde salió este?"};
			
			//Muestro los datos de la inserción
			System.out.println(text[varRandom]);
			System.out.println("El payaso entro por la "+Thread.currentThread().getName()+", hay "+car.getCantidad()+" payasos. Entro en "+((float)tiempo/1000)+" segundos!");
			System.out.println("Han entrado "+num+" payasos por esta puerta\n");
			
		} else {
			// Guardo el numero de payasos que han entrado por cada puerta
			if(Thread.currentThread().getName()=="puerta 1" && puerta1In==0) {
				puerta1In = num-1;
			} else if(Thread.currentThread().getName()=="puerta 2" && puerta2In==0) {
				puerta2In = num-1;
			} else if(Thread.currentThread().getName()=="puerta 3" && puerta3In==0) {
				puerta3In = num-1;
			} else if(Thread.currentThread().getName()=="puerta 4" && puerta4In==0) {
				puerta4In = num-1;
			}
			
			int varRandom2=randFrase2();
			String[] text2 ={"¡Ops, ya no caben mas payasos!", "¡Aiba, otro fuera!", "¡Vaya! Mala suerte..", "Otra vez será :("};
			System.out.println(text2[varRandom2]);
			System.out.println("El payaso no pasó por la "+Thread.currentThread().getName()+", hay "+car.getCantidad()+" payasos. Tardaron "+((float)tiempo/1000)+" segundos en echarlo!");
			System.out.println("Han intentado entrar "+num+" payasos por esta puerta\n");
			
		}
		
		
		
		
		
	}
	//</SECCIÓN CRITICA>
	
	//Metodos que generan los números aleatorios necesarios
	
	private int randTime(){
	    Random rand = new Random();
	    
	    int randomNum = rand.nextInt((300 - 100) + 1) + 100;
	    
	    return randomNum;
	}
	
	private int randFrase(){
	    Random rand = new Random();
	    
	    int randomNum = rand.nextInt((2 - 0)+1);
	    
	    return randomNum;
	}
	
	private int randFrase2(){
	    Random rand = new Random();
	    
	    int randomNum = rand.nextInt((3 - 0)+1);
	    
	    return randomNum;
	}
}
