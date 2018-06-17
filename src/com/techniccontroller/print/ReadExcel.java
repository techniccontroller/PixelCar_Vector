package com.techniccontroller.print;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel implements Serializable{

	private String inputFile;
	private static ArrayList<Objekt> objektpool;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
		objektpool = new ArrayList<Objekt>();
	}

	public ArrayList<Objekt> read() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			for (int j = 0; j < sheet.getRows(); j++) {

				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					if (i == 0 && cell.getType() != CellType.EMPTY && j != 0) {
						
						objektpool.add(new Objekt());
						objektpool.get(objektpool.size() - 1).setName(
								cell.getContents());
						objektpool.get(objektpool.size() - 1).setCode(
								new ArrayList<int[]>());
						break;
					} else if (i == 0 && cell.getType() != CellType.EMPTY
							&& j == 0) {
						
						objektpool.add(new Objekt());
						objektpool.get(0).setName(cell.getContents());
						objektpool.get(0).setCode(new ArrayList<int[]>());

						break;
					}
					if (i > 0) {
						
						objektpool
								.get(objektpool.size() - 1)
								.getCode()
								.get(objektpool.get(objektpool.size() - 1)
										.getCode().size() - 1)[i - 1] = Integer
								.parseInt(cell.getContents());

					}
					if (i == 0 && cell.getType() == CellType.EMPTY) {
						objektpool.get(objektpool.size() - 1).getCode()
								.add(new int[5]);
					}
				}

			}
			for (int u = 0; u < objektpool.size(); u++) {
				System.out.println("Letter " + objektpool.get(u).getName()
						+ ":");
				for (int i = 0; i < objektpool.get(u).getCode().size(); i++) {
					for (int e = 0; e < objektpool.get(u).getCode().get(0).length; e++) {
						System.out.print("\t"
								+ objektpool.get(u).getCode().get(i)[e]);
					}
					System.out.println();
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
		return objektpool;
	}
	public ArrayList<Objekt> getObjekts(){
		return objektpool;
	}

}