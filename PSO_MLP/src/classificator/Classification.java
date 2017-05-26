package classificator;

import java.util.Random;

import main.Particle;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public final class Classification {

	private String base3 = "keystroke_71features.arff";
	private static Instances dataAll = null;
	private String baseCurrent = base3;
	public int seed;
	private MultilayerPerceptron classifier;

	public int getLength() {
		return dataAll.numAttributes() - 1;
	}

	public ResultClassification getFitnessClafissation(Particle particle) throws Exception {
		classifier.setLearningRate(particle.getPosition()[0]);
		classifier.setMomentum(particle.getPosition()[1]);
		classifier.setTrainingTime((int) particle.getPosition()[2]);
		return classification(dataAll);
	}

	private ResultClassification classification(Instances data) throws Exception {

		if (data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}
		

		data.randomize(new Random(seed));

		RemovePercentage percentageData = new RemovePercentage();
		percentageData.setInputFormat(data);
		percentageData.setOptions(Utils.splitOptions("-P 90"));
		Instances dataTest = Filter.useFilter(data, percentageData);

		percentageData.setOptions(Utils.splitOptions("-V -P 90"));
		Instances dataTrain = Filter.useFilter(data, percentageData);

		classifier.buildClassifier(dataTrain);
		Evaluation eval = new Evaluation(dataTrain);
		eval.evaluateModel(classifier, dataTest);

		double avgFAR = 0, avgFRR = 0;
		for (int i = 0; i < data.numClasses(); i++) {
			avgFAR += eval.falsePositiveRate(i);
			avgFRR += eval.falseNegativeRate(i);
		}
		avgFAR = avgFAR / (data.numClasses());
		avgFAR *= 100;
		avgFRR = avgFRR / (data.numClasses());
		avgFRR *= 100;

		ResultClassification classification = new ResultClassification();
		classification.setFAR(avgFAR);
		classification.setFRR(avgFRR);
		classification.setPctCorrect(eval.pctCorrect());
		classification.setErrorMLP(((MultilayerPerceptron) classifier).getM_error());

		return classification;

	}

	public Classification(int seed) throws Exception {
		if (dataAll == null) {
			dataAll = new DataSource(baseCurrent).getDataSet();
		}
		seed = new Random().nextInt();
		classifier = new MultilayerPerceptron();
		classifier.setValidationThreshold(20);
		classifier.setValidationSetSize(30);
	}

	public int getSeed() {
		return seed;
	}

}
