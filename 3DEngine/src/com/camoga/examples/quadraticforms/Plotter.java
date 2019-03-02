package com.camoga.examples.quadraticforms;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.camoga.engine.Engine;
import com.camoga.engine.geom.Matrix;
import com.camoga.engine.geom.Vec3;
import com.camoga.engine.gfx.Screen;
import com.camoga.engine.model.Wireframe;
import com.camoga.engine.model.models.CubeFrame;
import com.camoga.examples.pca.PCA;

public class Plotter extends Engine {

	Wireframe points = new Wireframe();
	Wireframe axes = new Wireframe();
	
	public boolean replot = false;
	
	JSlider[] sliders = new JSlider[10];
	JTextField[] txtf = new JTextField[10];
	CardLayout cardlayout = new CardLayout();
	
	public Plotter() {
		super("Quadric Surfaces - by MrCamoga");
		scene.add(points);
		CubeFrame box = new CubeFrame(1.5, 1, 0xff0000ff);
		scene.add(box);
		
		plot(new double[] {1,4,1,-4,2,0,0,0,0,-1});
		gui();
		
	}
	
	public void gui() {
		JPanel cards = new JPanel(cardlayout);
		

		//////////////////SLIDERS/////////////////////////////////////
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				c = new double[] {
						sliders[0].getValue()/100.0,
						sliders[1].getValue()/100.0,
						sliders[2].getValue()/100.0,
						sliders[3].getValue()/100.0,
						sliders[4].getValue()/100.0,
						sliders[5].getValue()/100.0,
						sliders[6].getValue()/100.0,
						sliders[7].getValue()/100.0,
						sliders[8].getValue()/100.0,
						sliders[9].getValue()/100.0};
				for(int i = 0; i < c.length; i++) {
					txtf[i].setText(c[i]+"");
				}
				replot = true;
				frame.setVisible(true);
			}
		};
		
		JPanel sliderpanel = new JPanel(new GridLayout(10, 2));
		for(int i = 0; i < sliders.length; i++) {
			sliders[i] = new JSlider(JSlider.HORIZONTAL, -500, 500, (int) (c[i]*100));
			sliders[i].addChangeListener(listener);
			sliderpanel.add(sliders[i]);
			sliderpanel.add(new JLabel(Character.toString((char)(0x41+i))));
		}
		
		//////////////////TEXTFIELDS/////////////////////////////////////
		ActionListener listener2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = new double[] {
						Double.parseDouble(txtf[0].getText()),
						Double.parseDouble(txtf[1].getText()),
						Double.parseDouble(txtf[2].getText()),
						Double.parseDouble(txtf[3].getText()),
						Double.parseDouble(txtf[4].getText()),
						Double.parseDouble(txtf[5].getText()),
						Double.parseDouble(txtf[6].getText()),
						Double.parseDouble(txtf[7].getText()),
						Double.parseDouble(txtf[8].getText()),
						Double.parseDouble(txtf[9].getText())};
				System.out.println(Arrays.toString(c));
				for(int i = 0; i < c.length; i++) {
					sliders[i].setValue((int) (c[i]*100));
					txtf[i].setText(""+c[i]);
				}
				replot = true;
				frame.setVisible(true);
			}
		};
		JPanel txtfpanel = new JPanel(new GridLayout(10, 2));
		for(int i = 0; i < txtf.length; i++) {
			txtf[i] = new JTextField(c[i]+"");
			txtf[i].addActionListener(listener2);
			txtfpanel.add(txtf[i]);
			txtfpanel.add(new JLabel(Character.toString((char)(0x41+i))));
		}
		
		cards.add(sliderpanel, "Sliders");
		cards.add(txtfpanel, "Text Fields");
		
		ActionListener changeformatlistener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) cards.getLayout(); 
				layout.show(cards, e.getActionCommand());
				frame.pack();
				frame.setVisible(true);
			}
		};
		
		JMenuBar menu = new JMenuBar();
			JMenu input = new JMenu("Input");
				JMenu informat = new JMenu("Input Format");
					JMenuItem insli = new JMenuItem("Sliders");
					insli.addActionListener(changeformatlistener);
					JMenuItem intxtf = new JMenuItem("Text Fields");
					intxtf.addActionListener(changeformatlistener);
				informat.add(insli);
				informat.add(intxtf);
			input.add(informat);
			
			JMenu transform = new JMenu("Transform");
				JMenuItem changebasis = new JMenuItem("Change of Basis");
				changebasis.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JPanel mainpanel = new JPanel(new GridLayout(2, 1));
						mainpanel.add(new JLabel("Enter basis vectors as rows"), BorderLayout.NORTH);
						JPanel matrixpanel = new JPanel(new GridLayout(3, 4));
						JTextField[][] txt = new JTextField[3][3];
						for(int i = 0; i < 3; i++) {
							matrixpanel.add(new JLabel("v"+(i+1)));
							for(int j = 0; j < 3; j++) {
								txt[j][i] = new JTextField(P.matrix[j][i]+"");
								matrixpanel.add(txt[j][i]);
							}
						}
						mainpanel.add(matrixpanel, BorderLayout.CENTER);
						
						int i = JOptionPane.showConfirmDialog(frame, mainpanel, "Change of Basis", JOptionPane.OK_CANCEL_OPTION);
						if(i == JOptionPane.OK_OPTION) {
							t = 0;
							animatechangebasis = true;
							P = new Matrix(new double[][] {
								{Double.parseDouble(txt[0][0].getText()),Double.parseDouble(txt[0][1].getText()),Double.parseDouble(txt[0][2].getText())},
								{Double.parseDouble(txt[1][0].getText()),Double.parseDouble(txt[1][1].getText()),Double.parseDouble(txt[1][2].getText())},
								{Double.parseDouble(txt[2][0].getText()),Double.parseDouble(txt[2][1].getText()),Double.parseDouble(txt[2][2].getText())}
							});
							
						}
					}
				});
				
				JMenuItem orthogon = new JMenuItem("Orthogonally Diagonalize");
				orthogon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						orthogonalize();
					}
				});
			transform.add(changebasis);
			transform.add(orthogon);
			
			JMenu plot = new JMenu("Plot");
				JMenuItem replot = new JMenuItem("Replot");
				replot.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Plotter.this.replot = true;
					}
				});
			
				JMenuItem setplot = new JMenuItem("Plot Settings");
				setplot.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JPanel panel = new JPanel(new GridLayout(3, 2));
						JLabel reslab = new JLabel("Resolution");
						reslab.setToolTipText("Resolution in points per axis");
						JTextField res = new JTextField(Settings.res+"");
						panel.add(reslab);
						panel.add(res);
						
						JLabel precisionlab = new JLabel("Precision");
						precisionlab.setToolTipText("Distance from true surface at which the points are to be drawn");
						JTextField precision = new JTextField(Settings.precision+"");
						panel.add(precisionlab);
						panel.add(precision);
						
						JLabel maxpointslab = new JLabel("Max points");
						reslab.setToolTipText("Resolution in points per axis");
						JTextField maxpoints = new JTextField(Settings.maxpoints+"");
						panel.add(maxpointslab);
						panel.add(maxpoints);
						
						JCheckBox replot = new JCheckBox("Replot?");
						panel.add(replot);
						if(JOptionPane.showConfirmDialog(frame, panel, "Plot Settings", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							Settings.maxpoints = (int)Double.parseDouble(maxpoints.getText());
							Settings.precision = Double.parseDouble(precision.getText());
							Settings.res = Double.parseDouble(res.getText());
							if(replot.isSelected()) Plotter.this.replot = true;
						}
					}
				});
			plot.add(replot);
			plot.add(setplot);
			
			JMenu help = new JMenu("Help");
				JMenuItem controls = new JMenuItem("Controls");
				controls.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JPanel helppanel = new JPanel(new GridLayout(2,5));
						helppanel.add(new JLabel("Rotate camera: "));
						helppanel.add(new JLabel("WASD QE"));
						helppanel.add(new JLabel("Move camera: "));
						helppanel.add(new JLabel("↑→←↓ 4 1"));
						JOptionPane.showMessageDialog(frame, helppanel, "Controls", JOptionPane.INFORMATION_MESSAGE);
					}
				});
			help.add(controls);
		menu.add(input);
		menu.add(plot);
		menu.add(transform);
		menu.add(help);
		
		frame.add(menu,BorderLayout.NORTH);
		
		
		frame.add(cards, BorderLayout.EAST);
		frame.pack();
	}
	
	double[] c;
	
	double t = 0;
	boolean animatechangebasis = false;
	Matrix Plast = Matrix.ID(3);
	Matrix P = Matrix.ID(3);
	
	public void plot(double[] c) {
		this.c = c;
		points.vertices.clear();
		points.transform.clear();
		outter:for(double z_ = -1.5; z_ < 1.5; z_+=3/Settings.res) {
			double z = z_;
			for(double y_ = -1.5; y_ < 1.5; y_+=3/Settings.res) {
				double y = y_;
				for(double x_ = -1.5; x_ < 1.5; x_+=3/Settings.res) {
					double x = x_;
					
					Vec3 pos = new Vec3(x_,y_,z_);
					
					Vec3 pos_ = Plast.multVec(pos).mul(1-t).add(P.multVec(pos).mul(t));
					x = pos_.x;
					y = pos_.y;
					z = pos_.z;

					if(Math.abs(quadraticform(x, y, z) + c[6]*x + c[7]*y + c[8]*z + c[9]) < Settings.precision) {
						points.addVertex(x_, y_, z_);
					}
					if(points.vertices.size() > 30000) break outter;
				}
			}
		}
	}
	
	public double quadraticform(double x, double y, double z) {
		return c[0]*x*x+c[1]*y*y+c[2]*z*z + c[3]*x*y + c[4]*x*z + c[5]*y*z;
	}
	
	public double bilinear(double x1, double y1, double z1, double x2, double y2, double z2) {
		return 0.5*(quadraticform(x1+x2, y1+y2, z1+z2)-quadraticform(x1, y1, z1)-quadraticform(x2, y2, z2));
	}
	
	public Vec3[] orthogonalize() {
		double a_ = c[1]*c[2]-0.5*c[5];
		double b_ = 0.5*c[3]*c[2]-0.25*c[5]*c[4];
		double c_ = 0.25*c[3]*c[5]-0.5*c[1]*c[5];
		double determinant = c[0]*a_-0.5*c[3]*b_+0.5*c[4]*c_;
		double rank = 3;
		if(determinant == 0) {
			if(a_==0 && b_ == 0 && c_ == 0) rank = 1;
			else rank = 2;
		}
//		
		Vec3 v1,v2 = null,v3 = null;
		double q1 = 0,q2 = 0,q3 = 0;
		if(c[0] != 0) {
			v1 = new Vec3(1,0,0);
			q1 = c[0];
		}
		else if(c[1] != 0) {
			v1 = new Vec3(0,1,0);
			q1 = c[1];
		} else if(c[2] != 0) {
			v1 = new Vec3(0,0,1);
			q1 = c[2];
		}
		
		else {
			double x,y,z;
			do {
				x = Math.random();
				y = Math.random();
				z = Math.random();
			}
			while(quadraticform(x,y,z) == 0 && (x == 0 && y == 0 && z == 0));
			v1 = new Vec3(x,y,z);
			q1 = quadraticform(x,y,z);
		}
		
		Matrix A = new Matrix(new double[][] {
			{c[0],0.5*c[3],0.5*c[4]},
			{0.5*c[3],c[1],0.5*c[5]},
			{0.5*c[4],0.5*c[5],c[2]}
		});

		Vec3 v1t = A.vecMult(v1);
		//V1T
		Vec3 k11, k12;
		if(v1t.x == 0) {
			k11 = new Vec3(1,0,0);
			if(v1t.y == 0) k12 = new Vec3(0,1,0);
			else k12 = new Vec3(0,-v1t.z/v1t.y,1);
		} else if(v1t.y == 0) {
			k11 = new Vec3(0,1,0);
			if(v1t.z == 0) k12 = new Vec3(0,0,1);
			else k12 = new Vec3(-v1t.z/v1t.x,0,1);
		} else if(v1t.z == 0){
			k11 = new Vec3(0,0,1);
			k12 = new Vec3(-v1t.y/v1t.x,1,0);
		} else {
			k11 = new Vec3(-v1t.y/v1t.x,1,0);
			k12 = new Vec3(-v1t.z/v1t.x,0,1);
		}
		
		double q = 0;
		boolean allisotropes = false;
		if((q = quadraticform(k11.x, k11.y, k11.z)) != 0) {
			v2 = k11;
			q2 = q;
		} else if((q = quadraticform(k12.x, k12.y, k12.z)) != 0) {
			v2 = k12;
			q2 = q;
		} else if(rank==1){
			
			v2 = k11;
			v3 = k12;
			q2 = 0;
			q3 = 0;
		} else {
			allisotropes = true;
		}
		while(allisotropes) {
			Vec3 v = k11.mul(Math.random()).add(k12.mul(Math.random()));
			if((q = quadraticform(v.x, v.y, v.z)) != 0) {
				v2 = v;
				q2 = q;
				System.out.println(v2+", " + q2);
				allisotropes = false;
			}
		}
		
		if(v3 == null) {
			Vec3 v2t = A.vecMult(v2);
			double a = v2t.x*k11.x+v2t.y*k11.y+v2t.z*k11.z;
			double b = v2t.x*k12.x+v2t.y*k12.y+v2t.z*k12.z;
			
			double k2 = 1;
			double k1 = -b/a;
			
			v3 = k11.mul(k1).add(k12.mul(k2));
			q3 = quadraticform(v3.x, v3.y, v3.z);
		}
		
		if(q1 != 0) v1.div(Math.sqrt(Math.abs(q1)));
		if(q2 != 0) v2.div(Math.sqrt(Math.abs(q2)));
		if(q3 != 0) v3.div(Math.sqrt(Math.abs(q3)));

//		System.out.println(bilinear(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z));
//		System.out.println(bilinear(v1.x, v1.y, v1.z, v3.x, v3.y, v3.z));
//		System.out.println(bilinear(v3.x, v3.y, v3.z, v2.x, v2.y, v2.z));
//		System.out.println(v1.mod());
//		System.out.println(v2.mod());
//		System.out.println(v3.mod());
		t = 0;
		animatechangebasis = true;
		P = new Matrix(v1,v2,v3);
		System.out.println(Arrays.deepToString(P.transpose().multiply(A).multiply(P).matrix));
		return null;
	}
	
	public void tick(double dt) {
		super.tick(dt);
		if(replot) plot(c);
		replot = false;
		
		if(animatechangebasis && clock%3 == 0) {
			System.out.println("Animate " + t);
			if(t <= 1) {
				plot(c);
				t+=0.2;
			} else {
				t = 0;
				animatechangebasis = false;
				Plast = new Matrix(P.matrix);
			}
		}
	}
	
	public static void main(String[] args) {
		new Plotter().start();
	}
	
	public void predraw(Screen screen) {
		
	}

	public void postdraw(Graphics g) {
		g.drawString("Ax^2 + "+"By^2 + "+"Cz^2 + " + "Dxy + " + "Exz + " + "Fyz + " +"Gx + " + "Hy + " + "Iz + " + " J = 0", 300, 25);
		g.drawString(c[0]+"x^2 + "+c[1]+"y^2 + "+c[2]+"z^2 + " + c[3]+"xy + " + c[4]+"xz + " + c[5]+"yz + " + c[6]+"x + " + c[7]+"y + " + c[8]+"z + " + c[9]+" = 0", 300, 50);
	
	}

}
