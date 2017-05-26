package thread;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import classificator.Classification;
import main.Particle;
import main.Update;

public class ThreadManager extends Observable {

	public ThreadManager(Observer observer) {
		addObserver(observer);
	}

	public void updateParticleThread(List<Particle> population, int currentIndexParticleUpdate, int seed) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Update.updateParticle(population.get(currentIndexParticleUpdate), new Classification(seed));
					setChanged();
					notifyObservers("Classified");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		setChanged();
		notifyObservers("Created");
	}

}
