package main;

import java.util.ArrayList;
import java.util.List;

import classificator.Classification;
import excelGenerator.ExcelGenerator;
import factory.FactoryParticle;

public class PSO {

	private List<Particle> population;
	private int currentIndexParticleUpdate = 0;
	private int limiteThread = 4, countThreadCurrent = 0, countParticleProcessed = 0;
	private Classification classifier;
	private int maxInteration, currentInteration = 0;
	private long startTime;
	private ExcelGenerator excelGenerator;

	public PSO(int sizePopulation, int maxInteration2, int experiment, Classification classifier) throws Exception {

		this.classifier = classifier;
		excelGenerator = new ExcelGenerator("ExperimentoPSO" + experiment);
		ParticleToExcel.createLabelExcel(excelGenerator);
		FactoryParticle factoryParticle = FactoryParticle.getInstace();
		maxInteration = maxInteration2;
		population = new ArrayList<>();

		for (int i = 0; i < sizePopulation; i++)
			population.add(factoryParticle.factoryParticle());

		startTime = System.currentTimeMillis();
		updateParticle();
	}

	public synchronized void finishUpdate() throws Exception {
		countThreadCurrent--;
		countParticleProcessed++;
		updateParticle();
	}

	public synchronized void updateParticle() throws Exception {

		if (countParticleProcessed < population.size())
			while (countThreadCurrent < limiteThread) {
				countThreadCurrent++;
				new Thread(new Runnable() {
					public void run() {
						try {
							Update.updateParticle(population.get(currentIndexParticleUpdate), classifier);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
				currentIndexParticleUpdate++;
			}

		else if (countThreadCurrent == 0) {
			if (currentInteration < maxInteration) {
				Update.updatePopulation(population);
				long totalTime = System.currentTimeMillis() - startTime;
				ParticleToExcel.updateExcelByGeneration(excelGenerator, population, currentInteration, totalTime,
						classifier.getSeed());
				currentIndexParticleUpdate = 0;
				currentInteration++;
			} else {
				excelGenerator.saveFile();
			}
		}

	}

	public static void main(String[] args) throws Exception {

		int sizePopulation = 10, maxInteration = 100, repetition = 10;
		new PSO(sizePopulation, maxInteration, 1, new Classification());

	}

}
