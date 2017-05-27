package main;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Particle implements Comparable<Particle> {

	private double pBest[], gBest[], position[], velocity[];
	private double errorMLP, fitness, fitnesspBest, fitnessgBest, FAR, FRR, gBestFAR, gBestFRR;
	private int size;

	public Particle(int size) {
		this.size = size;
		this.pBest = new double[size];
		this.gBest = new double[size];
		//Position[0] = LearningRate, Position[1] = Momentum, Position[2] = TrainingTime
		this.position = new double[size];
		this.velocity = new double[size];
		this.fitness = 0;
		initialize();
	}

	private void initialize() {
		for (int i = 0; i < this.size; i++) {
			if (i == 2)
				this.position[i] = ThreadLocalRandom.current().nextInt(1, 5000 + 1);
			else
				this.position[i] = Math.random();
		}
		
		System.out.println("fim");
	}

	public double[] getpBest() {
		return pBest;
	}

	public void setpBest(double pBest[]) {
		this.pBest = pBest;
	}

	public double[] getgBest() {
		return gBest;
	}

	public void setgBest(double gBest[]) {
		this.gBest = gBest;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity[]) {
		this.velocity = velocity;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getSize() {
		return size;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double position[]) {
		this.position = position;
	}

	public int compareTo(Particle particle) {
		if (this.fitness < particle.getFitness())
			return -1;
		else if (this.fitness > particle.getFitness())
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return Arrays.toString(position);
	}

	public double getFitnessgBest() {
		return fitnessgBest;
	}

	public void setFitnessgBest(double fitnessgBest) {
		this.fitnessgBest = fitnessgBest;
	}

	public double getFitnesspBest() {
		return fitnesspBest;
	}

	public void setFitnesspBest(double fitnesspBest) {
		this.fitnesspBest = fitnesspBest;
	}

	public double getFAR() {
		return FAR;
	}

	public void setFAR(double fAR) {
		FAR = fAR;
	}

	public double getFRR() {
		return FRR;
	}

	public void setFRR(double fRR) {
		FRR = fRR;
	}

	public double getgBestFAR() {
		return gBestFAR;
	}

	public void setgBestFAR(double gBestFAR) {
		this.gBestFAR = gBestFAR;
	}

	public double getgBestFRR() {
		return gBestFRR;
	}

	public void setgBestFRR(double gBestFRR) {
		this.gBestFRR = gBestFRR;
	}

	public double getLearningRate() {
		return position[0];
	}

	public double getMomentum() {
		return position[1];
	}

	public double geTrainingTime() {
		return position[2];
	}

	public double getErrorMLP() {
		return errorMLP;
	}

	public void setErrorMLP(double errorMLP) {
		this.errorMLP = errorMLP;
	}

}
