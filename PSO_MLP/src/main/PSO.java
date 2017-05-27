package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import excelGenerator.ExcelGenerator;
import factory.FactoryParticle;
import thread.ThreadManager;

public class PSO implements Observer {

	private List<Particle> population;
	private int currentIndexParticleUpdate = 0;
	private int limiteThread = Runtime.getRuntime().availableProcessors(), countThreadCurrent = 0;
	private int maxInteration, currentInteration = 0;
	private long startTime;
	private ExcelGenerator excelGenerator;
	private int seed = new Random().nextInt();
	private ThreadManager threadManager = new ThreadManager(this);
	private int maxRepetition = 0, currentRepetition = 0;

	public PSO(int sizePopulation, int maxInteration2, int maxRepetition, int... repetition) throws Exception {
		this.maxRepetition = maxRepetition;

		if (repetition.length == 0)
			this.currentRepetition = 0;
		else
			this.currentRepetition = repetition[0];

		excelGenerator = new ExcelGenerator("ExperimentoPSO" + this.currentRepetition);
		ParticleToExcel.createLabelExcel(excelGenerator);
		FactoryParticle factoryParticle = FactoryParticle.getInstace();
		maxInteration = maxInteration2;
		population = new ArrayList<>();

		for (int i = 0; i < sizePopulation; i++)
			population.add(factoryParticle.factoryParticle());

		startTime = System.currentTimeMillis();
		updateParticle();
	}

	public void update(Observable arg0, Object arg1) {
		try {
			if (arg1 == "Classified")
				finishUpdate();
			else if (arg1 == "Created")
				updateParticle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void finishUpdate() throws Exception {
		countThreadCurrent--;
		updateParticle();
	}

	public synchronized void updateParticle() throws Exception {
		System.out.println("Thread atuando: " + countThreadCurrent);
		if (currentIndexParticleUpdate < population.size() && countThreadCurrent < limiteThread) {
			currentIndexParticleUpdate++;
			countThreadCurrent++;
			System.out.println("Index particle: " + (currentIndexParticleUpdate - 1));
			threadManager.updateParticleThread(population, currentIndexParticleUpdate - 1, seed);

		} else if (countThreadCurrent == 0) {
			if (currentInteration < maxInteration) {
				Update.updatePopulation(population);
				long totalTime = System.currentTimeMillis() - startTime;
				ParticleToExcel.updateExcelByGeneration(excelGenerator, population, currentInteration, totalTime, seed);
				startTime = System.currentTimeMillis();
				currentIndexParticleUpdate = 0;
				currentInteration++;
				System.out.println("Interação:" + currentInteration);
				updateParticle();
			} else {
				excelGenerator.saveFile();
				this.currentRepetition++;
				if (this.currentRepetition < this.maxInteration)
					new PSO(population.size(), maxInteration, maxRepetition, this.currentRepetition);
				System.out.println("Fim do algoritmo");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int sizePopulation = 10, maxInteration = 10, repetition = 1;
		new PSO(sizePopulation, maxInteration, repetition);
	}

}
