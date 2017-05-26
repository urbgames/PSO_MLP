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
	private int limiteThread = 4, countThreadCurrent = 0, countParticleProcessed = 0;
	private int maxInteration, currentInteration = 0;
	private long startTime;
	private ExcelGenerator excelGenerator;
	private int seed = new Random().nextInt();
	private ThreadManager threadManager = new ThreadManager(this);

	public PSO(int sizePopulation, int maxInteration2, int experiment) throws Exception {

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
		countParticleProcessed++;
		updateParticle();
	}

	public synchronized void updateParticle() throws Exception {
		if (countParticleProcessed < population.size() && countThreadCurrent < limiteThread) {
			currentIndexParticleUpdate++;
			countThreadCurrent++;
			System.out.println(currentIndexParticleUpdate);
			threadManager.updateParticleThread(population, currentIndexParticleUpdate, seed);
		} else if (countThreadCurrent == 0) {
			if (currentInteration < maxInteration) {
				Update.updatePopulation(population);
				long totalTime = System.currentTimeMillis() - startTime;
				ParticleToExcel.updateExcelByGeneration(excelGenerator, population, currentInteration, totalTime, seed);
				currentIndexParticleUpdate = 0;
				currentInteration++;
				System.out.println("Interação:" + currentInteration);
			} else {
				excelGenerator.saveFile();
				System.out.println("Fim do algoritmo");
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int sizePopulation = 10, maxInteration = 20, repetition = 10;
		new PSO(sizePopulation, maxInteration, 1);
	}

}
