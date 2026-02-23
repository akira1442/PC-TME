package pc.philo;

public class TestPhilo {

	public static void main (String [] args) {
		final int NB_PHIL = 5;
		Thread [] tPhil = new Thread[NB_PHIL];
		Fork [] tChop = new Fork[NB_PHIL];

		// TODO
		for (int i = 0; i < NB_PHIL; i++) {
			tChop[i] = new Fork();
		}
		
		for (int i = 0; i < NB_PHIL; i++) {
			tPhil[i] = new Thread(new Philosopher(tChop[i%NB_PHIL], tChop[(i+1)%NB_PHIL]));
		}
		
		for (Thread t : tPhil) {
			t.start();
		}
		for (Thread t : tPhil) {
			try {t.join();
			
			}catch (InterruptedException e) {
				System.out.println(e.getMessage()+" coucou");
			}
		}
		System.out.println("Fin du programme");

	}
}