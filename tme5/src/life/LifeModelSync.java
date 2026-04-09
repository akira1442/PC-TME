package life;

public class LifeModelSync extends LifeModel {

	public LifeModelSync(int rows, int cols) {
		super(rows, cols);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public synchronized void updateFrom(LifeModel mcopy) {
		// TODO Auto-generated method stub
		super.updateFrom(mcopy);
		
	}
	
	@Override
	public synchronized void refreshCurrent() {
		super.refreshCurrent();
	}
	
	@Override
	public synchronized void updateNext(int startRow, int endRow) {
		super.updateNext(startRow, endRow);
	}
}
