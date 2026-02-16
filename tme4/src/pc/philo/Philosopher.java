package pc.philo;

import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable {
	private Fork left;
	private Fork right;

	public Philosopher(Fork left, Fork right) {
		this.left = left;
		this.right = right;
	}

	public void run() {
		// TODO
		while (true) {
			this.think();
			if (ThreadLocalRandom.current().nextInt(0, 10) % 2 == 0) {
				this.left.acquire();
				System.out.println(Thread.currentThread().getName() + " has one fork");
				this.right.acquire();
				System.out.println(Thread.currentThread().getName() + " has one fork");
				this.eat();
				this.left.release();
				this.right.release();
			}
		}
		// System.out.println(Thread.currentThread().getName() + " has one fork");
	}

	private void eat() {
		System.out.println(Thread.currentThread().getName() + " is eating");
	}

	private void think() {
		System.out.println(Thread.currentThread().getName() + " is thinking");
	}
}
