package main.java.conway.domain;

public interface IGrid { //Entità che rappresenta un insieme ordinato e bidimensionale di celle e gestisce le operazioni a loro correlate
	public int getRows(); //Restituisce il numero di righe della griglia
	public int getCols(); //Restituisce il numero di colonne della griglia
	public boolean isAlive(int row, int col); //Verifica se la cella in posizione (row, col) è viva
	public void setStatus(int row, int col, boolean status); //Aggiorna lo stato della cella in posizione (row, col)
	public ICell getCell(int row, int col); //Restituisce il riferimento alla cella in posizione (row, col)
    public void reset(); //Modifica lo stato di tutte le celle della griglia portandole a morta
}
