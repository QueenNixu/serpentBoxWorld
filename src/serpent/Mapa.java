package serpent;

import java.awt.EventQueue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;

public class Mapa {

	private static JFrame frame;
	private static JPanel[][] boxes; 
	//private Timer appleTime;
	//private int xAppleBefore;
	//private int yAppleBefore;
	private serpent serp = new serpent();
	private static Timer serpentTimer;
	private static Timer timeTimer;
	private boolean enProceso = false;
	private int xMouse,yMouse;
	private int xAppleToDelete;
	private int yAppleToDelete;
	private PropertyChangeListener listener;
	private static Integer score = 0;
	private JLabel lblLevel;
	private JLabel lblLevelNumber;
	private JLabel lblScore;
	private JLabel lblScoreNumber;
	private JLabel lblMinSeg;
	private static int seg0 = 0;
	private static int seg1 = 0;
	private static int min0 = 0;
	private static int min1 = 0;
	private static Color serpColor = new Color(0, 128, 64);
	private Color fieldColor = new Color(210, 255, 151);
	private Color appleColor = Color.red;
	private int xApple;
	private int yApple;
	private int serpMovementTimer = 300;
	private static int levelSpeed = 1;
	private int[] speedArray = new int[5];
	@SuppressWarnings("unused")
	private static Mapa window;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Mapa window = new Mapa();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		seg0 = 0;
		seg1 = 0;
		min0 = 0;
		min1 = 0;
		score = 0;
		levelSpeed = 1;
		
		frame = new JFrame();
		getFrame().getContentPane().setBackground(new Color(255, 0, 0));
		getFrame().setBounds(350, 100, 500, 490);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		getFrame().setUndecorated(true);
		Border borde = BorderFactory.createLineBorder(Color.BLACK);
		getFrame().getRootPane().setBorder(borde);
		
		speedArray[0] = 100;
		speedArray[1] = 83;
		speedArray[2] = 67;
		speedArray[3] = 50;
		speedArray[4] = 33;
		
		JPanel topBar = new JPanel();
		topBar.setBackground(new Color(0, 0, 0));
		topBar.setBounds(0, 0, 500, 20);
		topBar.addMouseListener(new TopBarMouseListener());
		topBar.addMouseMotionListener(new TopBarMouseListener());
		getFrame().getContentPane().add(topBar);
		topBar.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("X");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblNewLabel.setForeground(new Color(100, 100, 100));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				lblNewLabel.setForeground(new Color(255, 255, 255));
			}
		});
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 0, 0));
		lblNewLabel.setBounds(480, 0, 20, 20);
		topBar.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel scoreBar = new JPanel();
		scoreBar.setBackground(Color.LIGHT_GRAY);
		scoreBar.setBounds(0, 20, 500, 20);
		getFrame().getContentPane().add(scoreBar);
		scoreBar.setLayout(null);
		
		lblScore = new JLabel("Apples:");
		lblScore.setForeground(new Color(0, 0, 0));
		lblScore.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setBounds(0, -1, 72, 20);
		scoreBar.add(lblScore);
		
		lblScoreNumber = new JLabel("0");
		lblScoreNumber.addPropertyChangeListener("text",new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				Integer aux = Integer.parseInt(lblScoreNumber.getText().toString());
				if(aux % 5 == 0 && serpMovementTimer != 100 ) {
						aux = Integer.parseInt(lblLevelNumber.getText().toString())+1;
						
						levelSpeed = aux;
						
						lblLevelNumber.setText(aux.toString());
						stopSerpentMovement();
						serpMovementTimer -= 50;
						startSerpentMovement(levelSpeed);
				}
			}
		});
		lblScoreNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreNumber.setForeground(Color.BLACK);
		lblScoreNumber.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblScoreNumber.setBounds(70, -1, 38, 20);
		scoreBar.add(lblScoreNumber);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.BLACK);
		lblTime.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblTime.setBounds(178, -1, 72, 20);
		scoreBar.add(lblTime);
		
		lblMinSeg = new JLabel(""+min1+min0+":"+seg1+seg0);
		lblMinSeg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinSeg.setForeground(Color.BLACK);
		lblMinSeg.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblMinSeg.setBounds(250, -1, 72, 20);
		scoreBar.add(lblMinSeg);
		
		lblLevel = new JLabel("Level:");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setForeground(Color.BLACK);
		lblLevel.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblLevel.setBounds(401, -1, 72, 20);
		scoreBar.add(lblLevel);
		
		lblLevelNumber = new JLabel("1");
		lblLevelNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevelNumber.setForeground(Color.BLACK);
		lblLevelNumber.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
		lblLevelNumber.setBounds(462, -1, 38, 20);
		scoreBar.add(lblLevelNumber);
		
		JPanel mapPanel = new JPanel();
		mapPanel.setBounds(0, 40, 500, 450);
		getFrame().getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
		
		
		boxes = new JPanel[20][18];
		
		for(int j = 0 ; j < 18 ; j++) {
			
			for(int i = 0 ; i < 20 ; i++) {
				
				JPanel box = new JPanel();
				box.setBackground(fieldColor);
				box.setBounds(25*i, 25*j, 25, 25);
				box.setBorder(borde);
				boxes[i][j] = box;
				mapPanel.add(boxes[i][j]);
				
			}
			
		}
		
		appleSpawn();
		initializeSerpentPos();
		
		getFrame().addKeyListener(new arrowListener());
		
		getFrame().requestFocusInWindow();
		
		
	}
	public   void setApplePos(int x, int y) {
		xAppleToDelete = x;
		yAppleToDelete = y;
		boxes[x][y].setBackground(appleColor);
		listener = new boxPropertyChangeListener();
		boxes[x][y].addPropertyChangeListener("background", listener);
	}
	public  void desetApplePos(int x, int y) {
		boxes[x][y].setBackground(fieldColor);
	}
	public  static void setSerpentPos(int x, int y) {
		if(x != -1 && x != 20 && y != -1 && y != 18 && boxes[x][y].getBackground() != serpColor) {
			boxes[x][y].setBackground(serpColor);
		}
		else {
			stopTimeTimer();
			stopSerpentMovement();
			getFrame().setEnabled(false);
			@SuppressWarnings("unused")
			Derrota D = new Derrota(score, seg0 + seg1*10 + min0*60 + min1*600, levelSpeed);
		}
	}
	 
	public  void appleSpawn() {
		do {
			xApple = (int)(Math.random()*20);
			yApple = (int)(Math.random()*18);
		} while( boxes[xApple][yApple].getBackground() == serpColor );
		setApplePos(xApple,yApple);
	}

	public  void startSerpentMovement(int levelSpeed) {
	    serpentTimer = new Timer(serpMovementTimer, e -> serpentMove());
	    if(levelSpeed < 4) {
	    	serpentTimer.setInitialDelay(speedArray[levelSpeed]);
	    }
	    else {
	    	serpentTimer.setInitialDelay(speedArray[4]);
	    }
	    
	    serpentTimer.start();
	}
	
	public static  void stopSerpentMovement() {
	    serpentTimer.stop();
	}
	
	public  void startTimeTimer() {
		//System.out.print("time started\n");
	    timeTimer = new Timer(1000, e -> timeAdder());
	    timeTimer.start();
	}
	
	public static  void stopTimeTimer() {
	    timeTimer.stop();
	}
	
	public void serpentMove() {
			
		switch(serp.getDir()) {
		
		case("Right"):
			serp.setXHead(serp.getXHead()+1);
			if(serp.getXHead()!=20 || serp.getXHead()!=-1 || serp.getYHead()!=18 || serp.getYHead()!=-1) {
				setSerpentPos(serp.getXHead(),serp.getYHead());
			}
			serpDeleteTail();
			serp.addListLast("Right");
			break;
				
			case("Up"):
				serp.setYHead(serp.getYHead()-1);
				if(serp.getXHead()!=20 || serp.getXHead()!=-1 || serp.getYHead()!=18 || serp.getYHead()!=-1) {
					setSerpentPos(serp.getXHead(),serp.getYHead());
				}
				serpDeleteTail();
				serp.addListLast("Up");
				break;
			
			case("Left"):
				serp.setXHead(serp.getXHead()-1);
				if(serp.getXHead()!=20 || serp.getXHead()!=-1 || serp.getYHead()!=18 || serp.getYHead()!=-1) {
					setSerpentPos(serp.getXHead(),serp.getYHead());
				}

				serpDeleteTail();
				serp.addListLast("Left");
				break;
				
			case("Down"):
				serp.setYHead(serp.getYHead()+1);
				if(serp.getXHead()!=20 || serp.getXHead()!=-1 || serp.getYHead()!=18 || serp.getYHead()!=-1) {
					setSerpentPos(serp.getXHead(),serp.getYHead());
				}
				serpDeleteTail();
				serp.addListLast("Down");
				break;
			}
		enProceso = false;
	}
	
	public void timeAdder() {
		
		seg0++;
		if(seg0 == 10) {
			seg1++;
			if(seg1 == 6) {
				min0++;
				if(min0 == 10) {
					min1++;
					min0 = 0;
				}
				seg1 = 0;
			}
			seg0 = 0;
		}
		
		lblMinSeg.setText(""+min1+min0+":"+seg1+seg0);
		
	}
	
	public  void initializeSerpentPos() {
		setSerpentPos(serp.getXHead(),serp.getYHead());
		setSerpentPos(serp.getXHead()-1,serp.getYHead());
		setSerpentPos(serp.getXHead()-2,serp.getYHead());
		setSerpentPos(serp.getXHead()-3,serp.getYHead());
	}
	
	public  void serpDeleteTail() {
		if(serp.getXTail() >= 0 && serp.getXTail() < 20
				&& serp.getYTail() >= 0 && serp.getYTail() < 18) {
					boxes[serp.getXTail()][serp.getYTail()].setBackground(new Color(210, 255, 151));
				}
		
		switch(serp.getTailDir()) {
		
		case("Right"):
			serp.setXTail(serp.getXTail()+1);
			break;
			
		case("Up"):
			serp.setYTail(serp.getYTail()-1);
			break;
			
		case("Left"):
			serp.setXTail(serp.getXTail()-1);
			break;
			
		case("Down"):
			serp.setYTail(serp.getYTail()+1);
			break;
		}
		serp.removelistZero();
	}

	public class arrowListener implements KeyListener {
		
	    @Override
	    public void keyPressed(KeyEvent e) {
	    	
	    	Optional<Timer> optionalSerpiente = Optional.ofNullable(serpentTimer);
	    	
	    	if(optionalSerpiente.isPresent() && optionalSerpiente.get().isRunning()) {
	    		
	    		//System.out.print("serpent distinto de null\n");
	    		
	    		if (e.getKeyCode() == KeyEvent.VK_RIGHT && serp.getDir() != "Left" && serp.getDir() != "Right" && !enProceso) {
		        	enProceso = true;
		        	stopSerpentMovement();
		            serp.setDir("Right");
		            startSerpentMovement(levelSpeed);
		            //System.out.print("right\n");
		        }
		        else if (e.getKeyCode() == KeyEvent.VK_UP && serp.getDir() != "Down" && serp.getDir() != "Up" && !enProceso) {
		        	enProceso = true;
		        	stopSerpentMovement();
		            serp.setDir("Up");
		            startSerpentMovement(levelSpeed);
		            //System.out.print("up\n");
		        }
		        else if (e.getKeyCode() == KeyEvent.VK_LEFT && serp.getDir() != "Right" && serp.getDir() != "Left" && !enProceso) {
		        	enProceso = true;
		        	stopSerpentMovement();
		            serp.setDir("Left");
		            startSerpentMovement(levelSpeed);
		            //System.out.print("left\n");
		        }
		        else if (e.getKeyCode() == KeyEvent.VK_DOWN && serp.getDir() != "Up" && serp.getDir() != "Down" && !enProceso) {
		        	enProceso = true;
		        	stopSerpentMovement();
		            serp.setDir("Down");
		            startSerpentMovement(levelSpeed);
		            //System.out.print("down\n");
		        }
	    		
	    	}
	    	else {
	    		//System.out.print("serpent = null o no corriendo\n");
	    		
	    		switch(e.getKeyCode()) {
	    		case(KeyEvent.VK_RIGHT): serp.setDir("Right"); break;
	    		case(KeyEvent.VK_UP): serp.setDir("Up"); break;
	    		//case(KeyEvent.VK_LEFT): serp.setDir("Left"); break;
	    		case(KeyEvent.VK_DOWN): serp.setDir("Down"); break;
	    		default: 
	    		}
	    		
	    		startSerpentMovement(levelSpeed);
	    		//System.out.print("Start serpent timer\n");
	    		
	    		startTimeTimer();
    			//System.out.print("Start time timer\n");
	    	}
	        
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        // Este método se llama cuando se suelta una tecla
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {
	        // Este método se llama cuando se presiona y suelta una tecla que produce un carácter Unicode
	    }
	}
	
	public class TopBarMouseListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent evt) {
			int xScreen = evt.getXOnScreen();
			int yScreen = evt.getYOnScreen();
			getFrame().setLocation(xScreen - xMouse,yScreen - yMouse);
		}
		

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			xMouse = e.getX();
			yMouse = e.getY();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class boxPropertyChangeListener implements PropertyChangeListener {
	    @Override
	    public void propertyChange(PropertyChangeEvent evt) {
	        boxes[xAppleToDelete][yAppleToDelete].removePropertyChangeListener("background", listener);
	        score++;
	        lblScoreNumber.setText(score.toString());
	        appleSpawn();
	        
	        switch(serp.getTailDir()) {
	        
	        case("Right"):
	        	serp.setXTail(serp.getXTail() - 1);
	        	serp.addListFirst("Right");
	        break;
	        
	        case("Up"):
	        	serp.setYTail(serp.getYTail() + 1);
	        	serp.addListFirst("Up");
		    break;
	        
	        case("Left"):
	        	serp.setXTail(serp.getXTail() + 1);
	        	serp.addListFirst("Left");
		    break;
	        
	        case("Down"):
	        	serp.setYTail(serp.getYTail() - 1);
	        	serp.addListFirst("Down");
		    break;
	        
	        }
	    }
	}
	
	public static JFrame getFrameInst() {
		return Mapa.getFrame();
	}

	public static JFrame getFrame() {
		return frame;
	}
	

}