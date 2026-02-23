package pc.philo;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
	
	private ReentrantLock lock;
	int id;
	private static int num = 0;
	
	public Fork() {
		this.lock = new ReentrantLock();
		this.id = num;
		num++;
	}
	
	public void acquire () {
		// TODO
		lock.lock();
    }
	
	
	public void release () {
		// TODO
		lock.unlock();
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getNum() {
		return num;
	}
}
