package conway.io;

import io.javalin.websocket.WsContext;
import main.java.conway.domain.IGrid;
import main.java.conway.domain.IOutDev;

public class IOController implements IOutDev {
	
	WsContext ctx;
	
	public void setContext( WsContext wsContext ) {
		this.ctx = wsContext;
	}

	@Override
	public void display(String msg) {
		if( ctx != null ) {
			ctx.send(msg);
		}

	}

	@Override
	public void displayCell(IGrid grid, int x, int y) {
		if ( ctx != null ) {
			int alive = grid.getCell(x, y).isAlive() ? 0 : 1;
			ctx.send("cell("+x+","+y+","+alive+")");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayGrid(IGrid grid) {
		if ( ctx != null ) {
			for (int x = 0; x < grid.getCols(); x++) {
				for (int y = 0; y < grid.getRows(); y++) {
					displayCell(grid, x, y);
				}
			}
		}

	}

}
