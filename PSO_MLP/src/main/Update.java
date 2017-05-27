package main;

import java.util.Collections;
import java.util.List;

import classificator.Classification;
import classificator.ResultClassification;

public class Update {

	private final static double VELOCITYCOEFFICIENT = 0.1;

	public static void updateParticle(Particle particle, Classification classifier) throws Exception {

		double weight = 0.7;
		double pVelocities[] = new double[particle.getSize()];
		double pPositions[] = new double[particle.getSize()];
		for (int i = 0; i < particle.getSize(); i++) {
			pVelocities[i] = weight * particle.getVelocity()[i]
					+ VELOCITYCOEFFICIENT * Math.random() * (particle.getpBest()[i] - particle.getPosition()[i])
					+ VELOCITYCOEFFICIENT * Math.random() * (particle.getgBest()[i] - particle.getPosition()[i]);

			double position = particle.getPosition()[i] + pVelocities[i];

			position = Math.abs(position);
			if (i == 0 || i == 1) {
				if (position > 1)
					position = 1;
				else if (position < 0)
					position = 0;
			} else {
				if (position > 10000)
					position = 10000;
				else if (position < 100)
					position = 100;
			}

			pPositions[i] = position;
		}

		particle.setVelocity(pVelocities);
		particle.setPosition(pPositions);

		ResultClassification resultClassification = Fitness.getClassification(particle, classifier);
		particle.setFitness(resultClassification.getErrorMLP());
		particle.setFAR(resultClassification.getFAR());
		particle.setFRR(resultClassification.getFRR());
		particle.setErrorMLP(resultClassification.getErrorMLP());
		particle.setPctCorrect(resultClassification.getPctCorrect());

		if (particle.getFitnesspBest() > particle.getFitness()) {
			particle.setpBest(pPositions);
			particle.setFitnesspBest(particle.getFitness());
			particle.setFAR(particle.getFAR());
			particle.setFRR(particle.getFRR());
			particle.setPctCorrect(particle.getPctCorrect());
		}

	}

	public static void updatePopulation(List<Particle> population) {
		Collections.sort(population);
		if (population.get(0).getFitnessgBest() > population.get(0).getFitness()
				|| population.get(0).getFitnessgBest() == 0)
			for (int i = 0; i < population.size(); i++) {
				population.get(i).setgBest(population.get(0).getPosition());
				population.get(i).setFitnessgBest(population.get(0).getFitness());
				population.get(i).setgBestFAR(population.get(0).getFAR());
				population.get(i).setgBestFRR(population.get(0).getFRR());
				population.get(i).setPctCorrectgBest(population.get(0).getPctCorrect());
			}
	}

}
