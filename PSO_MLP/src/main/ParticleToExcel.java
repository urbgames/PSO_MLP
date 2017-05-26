package main;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import excelGenerator.ExcelGenerator;

public class ParticleToExcel {

	public static void updateExcelByGeneration(ExcelGenerator excelGenerator,List<Particle> population, int interation, long totalTime, int seed) throws Exception{
		int row = 0;
		excelGenerator.insertCellInfo(interation + 1, row++, interation, Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getFitnessgBest(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getgBestFAR(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getgBestFRR(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getLearningRate(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getMomentum(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).getErrorMLP(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, population.get(0).geTrainingTime(), Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, seed, Cell.CELL_TYPE_NUMERIC);
		excelGenerator.insertCellInfo(interation + 1, row++, totalTime, Cell.CELL_TYPE_NUMERIC);
	}
	
	public static void createLabelExcel(ExcelGenerator excelGenerator){
		int row = 0;
		excelGenerator.insertCellInfo(0, row++, "Interaction", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "gBestFitness(%)", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "FAR(%)", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "FRR(%)", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "LearningRate", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "Momentum", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "ErrorMLP", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "TrainingTime", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "Seed", Cell.CELL_TYPE_STRING);
		excelGenerator.insertCellInfo(0, row++, "Time(ms)", Cell.CELL_TYPE_STRING);
	}
	
}
