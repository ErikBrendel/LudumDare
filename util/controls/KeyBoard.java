package util.controls;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{
	private static boolean[] keys = new boolean[512];
	
	public static boolean isKeyDown(int keyCode){
		if(keyCode>=0 && keyCode<keys.length)return keys[keyCode];
		else return false;
	}
	
	public static void setAllReleased(){
		for(int i = 0; i<keys.length; i++){
			keys[i] = false;
		}
	}
	
	public static void setReleased(int i){
		keys[i] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();
		if(keyCode>=0 && keyCode<keys.length){
			keys[keyCode] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode>=0 && keyCode<keys.length){
			keys[keyCode] = false;
		}
		
	}

	
	
	//unnoetig
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	
}
