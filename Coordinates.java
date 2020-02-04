package first;

public class Coordinates {
	
	private int x;
	private int y;
	
	public Coordinates(int a, int b) {
		x = a;
		y = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isEqual(Coordinates x) {
		
		if(this.getX() != x.getX()) {
			return false;
		}
		if(this.getY() != x.getY()) {
			return false;
		}
		return true;
	}

}
