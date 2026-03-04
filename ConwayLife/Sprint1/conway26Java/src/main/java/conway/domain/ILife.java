package main.java.conway.domain;

public interface ILife { //Entità che coordina l'evoluzione del gioco applicando le regole di Conway
    void nextGeneration(); //Calcola e applica lo stato della generazione successiva

    boolean isAlive(int row, int col); //Verifica lo stato della cella in posizione (row, col)

    void setStatus(int row, int col, boolean alive); //Imposta lo stato di una cella identificata da (row, col)
    
    public ICell getCell(int row, int col); //Restituisce il riferimento alla cella in posizione (row, col)
    
    public int getRows(); //Restituisce il numero di righe della griglia gestita
    
    public int getCols(); //Restituisce il numero di colonne della griglia gestita
    
    public IGrid getGrid(); //Restituisce il riferimento alla griglia gestita

	public int countNeighborsLive(int row, int col); //Conta le celle vive adiacenti alla cella in posizione (row, col)
}
