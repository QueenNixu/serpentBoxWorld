package serpent;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class serpent {

	private int xPosHead = 10;
	private int yPosHead = 6;
	private int length = 4;
	private int xPosTail = 7;
	private int yPosTail = 6;
	private String Direction = "Right";
	private List<String> tailDir = new ArrayList<String>(Arrays.asList("Right","Right","Right"));
	
	public serpent() {
		
	}
	
	public int getXHead() {
		return xPosHead;
	}
	public void setXHead(int newX) {
		xPosHead = newX;
	}
	
	public int getYHead() {
		return yPosHead;
	}
	public void setYHead(int newY) {
		yPosHead = newY;
	}
	
	public int getXTail() {
		return xPosTail;
	}
	public void setXTail(int newX) {
		xPosTail = newX;
	}
	
	public int getYTail() {
		return yPosTail;
	}
	public void setYTail(int newY) {
		yPosTail = newY;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int le) {
		length = le;
	}
	
	public String getDir() {
		return Direction;
	}
	public void setDir(String dir) {
		Direction = dir;
	}
	
	public String getTailDir() {
		String Aux = tailDir.get(0);
		//tailDir.remove(0);
		return Aux;
	}
	
	public void removelistZero() {
		tailDir.remove(0);
	}
	
	public void addListLast(String dir) {
		tailDir.add(dir);
	}
	
	public void addListFirst(String dir) {
		tailDir.add(0, dir);
	}
	
	public void printList() {
		for(int i = 0 ; i < tailDir.size() ; i++) {
			System.out.print(tailDir.get(i)+"\n");
		}
	}
	
}
