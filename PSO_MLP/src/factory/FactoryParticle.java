package factory;

import main.Particle;

public class FactoryParticle {

	private static volatile FactoryParticle instance;
	private static int lengthGenes;

	public static FactoryParticle getInstace() throws Exception {

		if (instance == null) {
			synchronized (FactoryParticle.class) {
				if (instance == null) {
					lengthGenes = 3;
					instance = new FactoryParticle();
				}
			}
		}
		return instance;
	}

	public Particle factoryParticle() {
		return new Particle(lengthGenes);
	}

}
