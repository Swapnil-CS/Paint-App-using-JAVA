import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Canvas implements MouseMotionListener {
	JFrame frame = new JFrame();
	JPanel canvasPanel, toolBar;
	JButton btn;
	JTextField col, sz;
	String prev, bg;
	
	Canvas() {
		canvasPanel = new JPanel();
		canvasPanel.addMouseMotionListener(this);
	}

	public void CanvasView() {
		
		canvasPanel.setBounds(30, 160, 1170, 480);
		canvasPanel.setBackground(Color.white);
		canvasPanel.setLayout(null);
		frame.add(canvasPanel);
		frame.setTitle("Paint App");
		canvasPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String cl = col.getText();
				int rad = Integer.parseInt(sz.getText());
				Graphics grap = canvasPanel.getGraphics();
				grap.setColor(Color.decode(cl));
				grap.fillRect(e.getX(), e.getY(), rad, rad);
			}
		});
	
		toolBar = new JPanel();
		toolBar.setBounds(30, 10, 1170, 130);
		toolBar.setBackground(Color.white);
		toolBar.setLayout(null);
		frame.add(toolBar);
		addButton(1100, 20, "#0000FF");
		addButton(1050, 20, "#FFFF00");
		addButton(1000, 20, "#808080");
		addButton(950, 20, "#FF0000");
		addButton(900, 20, "#00FF00");
		addButton(850, 20, "#00CC00");
		addButton(800, 20, "#000000");
		addButton(800, 70, "#FFFFFF");
		addButton(850, 70, "#DDDDFF");
		addButton(900, 70, "#FFA500");
		addButton(950, 70, "#A020F0");
		addButton(1000, 70, "#FFC0CB");
		addButton(1050, 70, "#964B00");
		addButton(1100, 70, "#C32148");
	
		JLabel cllabel = new JLabel("CUSTOM COLORS");
		cllabel.setBounds(630, 18, 150, 25);
		toolBar.add(cllabel);
		col = new JTextField();
		col.setBounds(610, 40, 140, 30);
		col.setText("#000000");
		toolBar.add(col);
	
		bg = "#FFFFFF";
		JButton bc = new JButton("SET BACKGROUND");
		bc.setBounds(610, 80, 140, 30);
		toolBar.add(bc);
		bc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bg = col.getText();
				canvasPanel.setBackground(Color.decode(bg));
			}
		});
	
		JLabel szlabel = new JLabel("SET BRUSH/ERASER SIZE");
		szlabel.setBounds(420, 20, 170, 30);
		toolBar.add(szlabel);
		JButton add = new JButton("+");
		add.setBounds(530, 55, 50, 50);
		toolBar.add(add);
		sz = new JTextField();
		sz.setBounds(470, 60, 40, 40);
		sz.setText("5");
		toolBar.add(sz);
		JButton sub = new JButton("-");
		sub.setBounds(400, 55, 50, 50);
		toolBar.add(sub);
		sub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sz.setText(Integer.parseInt(sz.getText())-1+"");
			}
		});
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sz.setText(Integer.parseInt(sz.getText())+1+"");
			}
		});
	
		JButton brush = new JButton("BRUSH");
		brush.setBounds(270, 40, 100, 30);
		toolBar.add(brush);
		JButton erase = new JButton("ERASER");
		erase.setBounds(270, 80, 100, 30);
		toolBar.add(erase);
		prev = "#000000";
		brush.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col.setText(prev);
			}
		});
		erase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!col.getText().equals(bg))
					prev = col.getText();
				col.setText(bg);
			}
		});
		
		JButton clean = new JButton("CLEAN ALL");
		clean.setBounds(150, 80, 100, 30);
		toolBar.add(clean);
		clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvasPanel.removeAll();
				canvasPanel.repaint();
			}
		});
		
		JButton save = new JButton("SAVE");
		save.setBounds(150, 40, 100, 30);
		toolBar.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tm = java.time.LocalTime.now()+"";
				tm = tm.substring(0, 2) + tm.substring(3, 5) + tm.substring(6, 8) + tm.substring(9, 12) + ".png";
				try {
				BufferedImage image = getImg(canvasPanel);
				ImageIO.write(image, "png", new File(tm));
				}
				catch(Exception ex) {
					
				}
			}
		});
		
        JButton newFileBtn = new JButton("NEW");
        newFileBtn.setBounds(30, 40, 100, 30);
        toolBar.add(newFileBtn);
        newFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });
        
        JButton openFileBtn = new JButton("OPEN");
        openFileBtn.setBounds(30, 80, 100, 30);
        toolBar.add(openFileBtn);
        openFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
     
		
		frame.setSize(1250, 700);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.decode("#000000"));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		String cl = col.getText();
		int rad = Integer.parseInt(sz.getText());
		Graphics grap = canvasPanel.getGraphics();
		grap.setColor(Color.decode(cl));
		grap.fillOval(e.getX(), e.getY(), rad, rad);
	}
	
	public void mousePressed( MouseEvent e ) {
      
    }

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	

	
	public void addButton(int x, int y, String clr) {
		this.btn = new JButton();
		btn.setBounds(x, y, 40, 40);
		btn.setBackground(Color.decode(clr));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col.setText(clr);
			}
		});
		toolBar.add(btn);
	}
	
	public BufferedImage getImg(Component comp) throws AWTException {
	    Point p = comp.getLocationOnScreen();
	    Dimension dim = comp.getSize();
	    BufferedImage capture = new Robot().createScreenCapture(new Rectangle(p, dim));
	    return capture;
	}
	
	
	private void newFile() {
        canvasPanel.removeAll();
        canvasPanel.repaint();
    }

	private void openFile() {
	    JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
	    fileChooser.setFileFilter(filter);

	    int result = fileChooser.showOpenDialog(frame);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        try {
	            BufferedImage image = ImageIO.read(selectedFile);
	            Graphics g = canvasPanel.getGraphics();
	            g.drawImage(image, 0, 0, canvasPanel);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}

	
	
		
	public static void main(String[] args) {
		Canvas canvas = new Canvas();
		canvas.CanvasView();
	}
}