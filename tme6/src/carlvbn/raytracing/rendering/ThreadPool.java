package carlvbn.raytracing.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;


public class ThreadPool implements Executor {
	
	private List<Thread> workers = new ArrayList<>();
	private BlockingQueue<Runnable> tasks;
	
	public ThreadPool(int size, int nbThread) {
		this.tasks = new ArrayBlockingQueue<>(size);
		this.workers = new ArrayList<>();
		for (int i = 0; i < nbThread; i++) {
			Thread worker = new Thread( ()->{
				try {
					while(true) {
						Runnable task = tasks.take();
						task.run();
					}
				}catch(InterruptedException e) {
					return;
				}
			});
			worker.start();
			workers.add(worker);
		}
		
	}
	
	@Override
	public void execute(Runnable task) {
		try {
			tasks.put(task);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
