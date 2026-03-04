package main.java.conway.domain;

public class Grid implements IGrid{

	private int rows;
	private int cols;
	private ICell[][] grid;
	
	public Grid(int rows, int cols) {
		this.rows=rows;
		this.cols=cols;
		this.grid=new ICell[rows][cols];
		for (int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				this.grid[i][j]=new Cell();
			}
		}
	}

	@Override
	public int getRows() {
		return this.rows;
	}

	@Override
	public int getCols() {
		return this.cols;
	}

	@Override
	public boolean isAlive(int row, int col) {
		return this.grid[row][col].isAlive();
	}

	@Override
	public void setStatus(int row, int col, boolean status) {
		this.grid[row][col].setStatus(status);
		
	}

	@Override
	public ICell getCell(int row, int col) {
		return this.grid[row][col];
	}

	@Override
	public void reset() {
		for (int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				this.grid[i][j].setStatus(false);
			}
		}
	}
}
