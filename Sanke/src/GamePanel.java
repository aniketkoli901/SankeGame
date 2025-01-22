import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int screen_width=600;
	static final int screen_height=600;
	static final int unit_Size=25;
	static final int game_unit=(screen_width*screen_height)/unit_Size;
	static final int delay=75;
	final int x[]=new int[unit_Size];
	final int y[]=new int[unit_Size];
	int bodyParts=6;
	int appleEaten;
	int appleX;
	int appleY;
	char direction ='R';
	boolean Runing = false;
	Timer timer;
	Random random;
	private int width_Size;

	GamePanel(){
		random =new Random();
		this.setPreferredSize(new Dimension(screen_width,screen_height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MykeyAdapter());
		Stargame();
	}
	public void Stargame() {
		NewApple();
		Runing=true;
		timer= new Timer(delay,this);
		timer.start();
		
		
		
		
	}
	public void PaintComponet(Graphics g) {
		super.paintComponent(g);
		
	}
	public void draw(Graphics g) {
		if(Runing) {
			for(int i=0;i<screen_height/unit_Size;i++) {
				g.drawLine(i*unit_Size, 0, i*unit_Size,screen_height);
				g.drawLine(0, i*unit_Size, i*screen_width,i*unit_Size);
			}
			g.setColor(Color.red);
			g.fillOval(appleX,appleY,WIDTH,HEIGHT);
			
			for(int i=0;i<bodyParts;i++) {
				if(i==0) {
					g.setColor(Color.YELLOW);
					g.fillRect(x[i],y[i], unit_Size, unit_Size);
				}else {
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i], y[i],unit_Size ,unit_Size);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,75));
			FontMetrics metrics=getFontMetrics(g.getFont());
			g.drawString("Score :"+appleEaten,(screen_width-metrics.stringWidth("Score :"+appleEaten))/2,g.getFont().getSize());
			
		}
		else {
			gameOver(g);
		}
		
	}
	public void NewApple() {
		appleX=random.nextInt((int)(screen_width/unit_Size))*unit_Size;
		appleY=random.nextInt((int)(screen_height/unit_Size))*unit_Size;
	}
	
	public void Move() {
		for(int i=bodyParts;i>0;i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0]=y[0]-unit_Size;
			break;
		case 'D':
			y[0]=y[0]+unit_Size;
			break;
		case 'L':
			y[0]=y[0]-unit_Size;
			break;
		case 'R':
			y[0]=y[0]+unit_Size;
			break;
		}
		
	} 
	public void CheckApple() {
		
		if((x[0]==appleX)&&(y[0]==appleY)) {
			bodyParts++;
			appleEaten++;
			NewApple();
		}
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Game Over",(screen_width-metrics.stringWidth("Game Over"))/2,screen_height);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(Runing) {
			Move();
			CheckApple();
			CheckCollision();	
		}
		repaint();
		
	}
	
	private void CheckCollision() {
		for(int i=bodyParts;i>0;i--) {
			if((x[0]==x[i]&&(y[0]==y[i]))) {
				Runing=false;
			}
		}
		if(x[0]<0) {
			Runing=false;
		}
		if(x[0]>screen_width) {
			Runing=false;
		}
		
		if(y[0]<0) {
			Runing=false;
		}
		if(y[0]>screen_height) {
			Runing=false;
		}
		if(!Runing) {
			timer.stop();
		}
		
	}

	public class MykeyAdapter extends KeyAdapter{
		public void KeyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R') {
					direction='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction!='L') {
					direction='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction!='D') {
					direction='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='U') {
					direction='D';
				}
				break;
			}
		}
		
	}

}
