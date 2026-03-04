package main.java.conway.domain;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Life implements ILife{
	
	public static ILife CreateLife(int nr, int nc) {
	   return new Life(nr,nc);   
   }
	
	private final int rows;
    private final int cols;
    
    // Due matrici distinte
    private IGrid gridA;
    private IGrid gridB;
    
 // Un riferimento che punta sempre alla griglia che contiene lo stato attuale
    private IGrid currentGrid;
    private IGrid nextGrid;
    
   public static ILife CreateGameRules() {
	   return new Life(5, 5); 
	   // Dimensioni di default, possono essere 
	   //lette da un file di configurazione o passate come parametri
   }

    // Costruttore che crea una griglia vuota di dimensioni specifiche
    public Life(int rows, int cols) {
    	this.rows = rows;
    	this.cols = cols;
        this.gridA = new Grid(rows, cols);
        this.gridB = new Grid(rows, cols);
        this.currentGrid = this.gridA;
        this.nextGrid = this.gridB;
    }

    // Calcola la generazione successiva applicando le 4 regole di Conway
    public void nextGeneration() {
    	// Applichiamo le regole leggendo da currentGrid e scrivendo in nextGrid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int neighbors = countNeighborsLive(r, c);
                boolean isAlive = currentGrid.isAlive(r, c);
                //apply rules
                if (isAlive) {
                    nextGrid.setStatus(r, c, neighbors == 2 || neighbors == 3);
                } else {
                    nextGrid.setStatus(r, c, neighbors == 3);
                }
            }
        }

        // --- IL PING-PONG ---
        // Scambiamo i riferimenti: ciò che era 'next' diventa 'current'
        IGrid temp = currentGrid;
        currentGrid      = nextGrid;
        nextGrid         = temp;
        // Nota: non abbiamo creato nuovi oggetti, abbiamo solo spostato i puntatori
    }

	@Override
	public boolean isAlive(int row, int col) {
		return this.currentGrid.isAlive(row, col);
	}

	@Override
	public int getRows() {
 		return this.currentGrid.getRows();
	}

	@Override
	public int getCols() {
 		return this.currentGrid.getCols();
	}

	@Override
	public void setStatus(int row, int col, boolean alive) {
		this.currentGrid.setStatus(row, col, alive);
	}

	@Override
	public ICell getCell(int row, int col) {
		return this.currentGrid.getCell(row, col);
	}

	@Override
	public IGrid getGrid() {
		return this.currentGrid;
	}
	
	@Override
    public int countNeighborsLive(int row, int col) {
		int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int neighborRow = row + i;
                int neighborCol = col + j;
                
                if (neighborRow >= 0 && neighborRow < rows && 
                    neighborCol >= 0 && neighborCol < cols) {
                    
                    if (getCell(neighborRow,neighborCol).isAlive()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
	
	
	
	
}
