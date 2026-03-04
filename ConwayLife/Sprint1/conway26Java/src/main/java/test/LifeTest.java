package main.java.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.conway.domain.*;


public class LifeTest {
	
	private ILife l;
	public static final int rowsSize = 5;
	public static final int colsSize = 5;

	@Before
	public void setup() {
		System.out.println("LifeTest | setup");
		l = new Life(rowsSize, colsSize);
	}

	@After
	public void down() {
		System.out.println("LifeTest | down");
	}
	
	@Test
	public void testNextGeneration() {
		System.out.println("LifeTest | testNextGeneration");
		l.setStatus(1, 1, true);
		l.nextGeneration();
		assertFalse(l.isAlive(1, 1));
	}
	
	@Test
	public void testIsAlive1() {
		System.out.println("LifeTest | testIsAlive1");
		assertFalse(l.isAlive(0, 0));
	}
	
	@Test
	public void testIsAlive2() {
		System.out.println("LifeTest | testIsAlive2");
		l.setStatus(0, 0, true);
		assertTrue(l.isAlive(0, 0));
		l.setStatus(0, 0, false);
	}
	
	@Test
	public void testSetGetCellCorretto() {
		System.out.println("LifeTest | testSetGetCellCorretto");
		l.setStatus(0, 0, true);
		assertTrue(l.isAlive(0, 0));
		l.setStatus(0, 0, false);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetCellErrato1() {
		System.out.println("LifeTest | testGetCellErrato1");
	    l.isAlive(-1, -1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetCellErrato2() {
		System.out.println("LifeTest | testGetCellErrato2");
	    l.isAlive(rowsSize, colsSize);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetCellErrato1() {
		System.out.println("LifeTest | testSetCellErrato1");
	    l.setStatus(-1, -1, true);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetCellErrato2() {
		System.out.println("LifeTest | testSetCellErrato2");
	    l.setStatus(rowsSize, colsSize, true);
	}
	
	@Test
	public void testGetRows() {
		System.out.println("LifeTest | testGetRows");
		assertTrue(l.getRows()==rowsSize);
	}
	
	@Test
	public void testGetCols() {
		System.out.println("LifeTest | testGetCols");
		assertTrue(l.getCols()==colsSize);
	}
	
	@Test
	public void testGetGrid() {
		System.out.println("LifeTest | testGetGrid");
		IGrid grid = l.getGrid();
		assertTrue(grid.getRows()==rowsSize);
		assertTrue(grid.getCols()==colsSize);
	}
	
	@Test
	public void testCountNeighborsLive() {
		System.out.println("LifeTest | testCountNeighborsLive");
		l.setStatus(1, 1, true);
		l.setStatus(1, 2, true);
		l.setStatus(1, 3, true);
		int count = l.countNeighborsLive(2, 2);
		assertTrue(count == 3);
		l.getGrid().reset();
	}
}
