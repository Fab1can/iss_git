package main.java.conway.domain;

public interface ICell { //Entità con stato vivo/morto

	void setStatus(boolean alive); // Imposta esplicitamente lo stato della cella
	
	boolean isAlive(); //Restituisce true se la cella è viva, false altrimenti
	
	void switchCellState(); //Inverte lo stato corrente della cella (viva ↔ morta)
	
}
