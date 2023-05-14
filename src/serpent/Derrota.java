package serpent;

import java.text.DecimalFormat;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Cursor;
import java.awt.EventQueue;

import java.text.DecimalFormat;
public class Derrota {

	private ImageIcon imagen;
	private JFrame frame;
	private int xMouse,yMouse;
	private JTextField textField;
	private Double score = (double) 0;

	public Derrota(int apples, int seconds, int levels) {
		frame = new JFrame();
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					restart();
				}
			}
		});
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(600, 200, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		
		JPanel topBar1 = new JPanel();
		topBar1.setBackground(new Color(0, 0, 0));
		topBar1.setBounds(1, 0, 448, 20);
		topBar1.addMouseListener(new TopBarMouseListener());
		topBar1.addMouseMotionListener(new TopBarMouseListener());
		//topBar1.addMouseListener(new MouseAdapterForText1());
		frame.getContentPane().add(topBar1);
		
		JPanel topBar2 = new JPanel();
		topBar2.setBounds(1, 21, 448, 20);
		frame.getContentPane().add(topBar2);
		topBar2.setLayout(null);
		
		JPanel body = new JPanel();
		body.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if( ( (e.getX() < 158) || (e.getX() > 269) ||
				    (e.getY() < 94) || (e.getX() > 114) ) &&
					(textField.getForeground() == Color.LIGHT_GRAY ) ||
					(textField.getText().isBlank() || textField.getText().isEmpty() ) ){
					
					
					textField.setText("Tu Nombre...");
					textField.setForeground(Color.LIGHT_GRAY);
					
				}
				textField.setFocusable(false);
			}
		});
		//body.addMouseListener(new MouseAdapterForText1());
		body.setBackground(new Color(210, 255, 151));
		body.setBounds(1, 41, 448, 258);
		frame.getContentPane().add(body);
		body.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("¡PERDISTE!");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 40));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(57, 0, 338, 72);
		body.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("SCORE:");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(158, 69, 47, 14);
		body.add(lblNewLabel_2);
		
		//System.out.print("Apples: "+apples+"\n");
		//System.out.print("Seconds: "+seconds+"\n");
		//System.out.print("Levels: "+levels+"\n");
		
		Double scoreD = (double) apples;
		Double secondsD = (double) seconds;
		Double levelsD = (double) levels;
		
		//System.out.print("ApplesD: "+scoreD+"\n");
		//System.out.print("SecondsD: "+secondsD+"\n");
		//System.out.print("LevelsD: "+levelsD+"\n");
		if(secondsD == 0) {
			score = (double) 0;
		}
		else {
			score = (scoreD*1000/secondsD)*levelsD;
		}
		DecimalFormat df = new DecimalFormat("#.###");
		JLabel lblNewLabel_2_1 = new JLabel(df.format(score).toString());
		lblNewLabel_2_1.setFont(new Font("Arial Black", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(210, 69, 58, 14);
		body.add(lblNewLabel_2_1);
		
		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textField.getText().equals("Tu Nombre...")) {
					textField.setText("");
					textField.setForeground(Color.black);
				}
				textField.setFocusable(true);
				textField.requestFocus();
			}
		});
		textField.setText("Tu Nombre...");
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(158, 94, 111, 20);
		textField.setFocusable(false);
		body.add(textField);
		
		JPanel panel = new JPanel();
		panel.setBounds(195, 121, 33, 20);
		body.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("OK");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 0, 33, 20);
		panel.add(lblNewLabel_3);
		
		JPanel botonBack = new JPanel();
		botonBack.setBackground(new Color(192, 192, 192));
		botonBack.setBounds(160, 142, 105, 105);
		// Crear el borde de línea con color negro
		Border borde = BorderFactory.createLineBorder(Color.BLACK);

		// Añadir el borde al JPanel
		botonBack.setBorder(borde);
		body.add(botonBack);
		botonBack.setLayout(null);
		
		
		JLabel lblRestart = new JLabel("");
		botonBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				restart();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// Obtener el icono actual del JLabel
				Icon iconoActual = lblRestart.getIcon();

				// Convertir el icono en un ImageIcon para obtener la imagen
				ImageIcon imagenActual = (ImageIcon) iconoActual;

				// Obtener la imagen
				Image imagen = imagenActual.getImage();

				// Escalar la imagen al nuevo tamaño
				Image imagenEscalada = imagen.getScaledInstance(100, 100, Image.SCALE_DEFAULT);

				// Crear un nuevo ImageIcon con la imagen escalada
				ImageIcon nuevoIcono = new ImageIcon(imagenEscalada);

				// Asignar el nuevo icono al JLabel
				lblRestart.setIcon(nuevoIcono);
				
				botonBack.setBackground(new Color(192, 192, 192));
				botonBack.setBounds(160, 142, 105, 105);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// Obtener el icono actual del JLabel
				Icon iconoActual = lblRestart.getIcon();

				// Convertir el icono en un ImageIcon para obtener la imagen
				ImageIcon imagenActual = (ImageIcon) iconoActual;

				// Obtener la imagen
				Image imagen = imagenActual.getImage();

				// Escalar la imagen al nuevo tamaño
				Image imagenEscalada = imagen.getScaledInstance(98, 98, Image.SCALE_DEFAULT);

				// Crear un nuevo ImageIcon con la imagen escalada
				ImageIcon nuevoIcono = new ImageIcon(imagenEscalada);

				// Asignar el nuevo icono al JLabel
				lblRestart.setIcon(nuevoIcono);
				//botonBack.setBounds(160, 142, 105, 105);
				botonBack.setBounds(161, 143, 103, 103);
				botonBack.setBackground(new Color(114, 114, 114));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
		});

		lblRestart.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon im =  new ImageIcon("src/img/restart.png"); 
		Icon ic = new ImageIcon(im.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		lblRestart.setIcon(ic);
		lblRestart.setBounds(0, 0, 107, 107);
		botonBack.add(lblRestart);
		
		JLabel lblNewLabel = new JLabel("X");
		lblNewLabel.setBounds(430, 0, 20, 20);
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
		topBar1.setLayout(null);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 0, 0));
		topBar1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	protected void restart() {
		// TODO Auto-generated method stub
		System.out.print("Reiniciando juego...\n");
		Mapa.getFrame().dispose();
		frame.dispose();
		Mapa.main(null);
	}

	public class TopBarMouseListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent evt) {
			int xScreen = evt.getXOnScreen();
			int yScreen = evt.getYOnScreen();
			frame.setLocation(xScreen - xMouse,yScreen - yMouse);
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
}

//AGREGAR LOGIN PARA GUARDAR RECORD