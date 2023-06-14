package FORM;



//Nombres:
//André Portella
//Vania Vasquez
//Diego Cayetano
//Alvaro Chavez
//Daniel Garcia

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;

import java.text.DecimalFormat;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import CLASES.Estudiante;
import CLASES.Laboratorio;
import CLASES.Teorico;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vist extends JFrame {

	private JPanel contentPane;
	private JTextField textDNI;
	private JTable table;
	private JTextField textName;
	private JTextField textCurso;
	private JRadioButton rdbtnMasculino;
	private JRadioButton rdbtnFemenino;
	private JComboBox comboBox;
	private JLabel lblT1content;
	private JLabel lblT2content;
	private JLabel lblEPcontent;
	private JLabel lblEFcontent;
	private JLabel lblNFcontent;
	private JLabel lblNumeroCalif;
	private Hashtable<Integer, Estudiante> lista = new Hashtable<Integer, Estudiante>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vist frame = new Vist();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vist() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setBounds(21, 31, 46, 14);
		contentPane.add(lblDNI);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(21, 68, 54, 14);
		contentPane.add(lblNombre);
		
		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(21, 93, 46, 14);
		contentPane.add(lblGenero);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(21, 134, 46, 14);
		contentPane.add(lblTipo);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(21, 179, 46, 14);
		contentPane.add(lblCurso);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarNumerosAleatorios();
				calcularPromedio();
			}
		});
		btnGenerar.setBounds(21, 353, 89, 23);
		contentPane.add(btnGenerar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dni = Integer.parseInt(textDNI.getText());
		        String nombre = textName.getText();
		        String genero = "";
		        String oh = textCurso.getText();
		        
		        if (rdbtnMasculino.isSelected()==true) {
		        	genero = "Masculino";
		        }
		        else if (rdbtnFemenino.isSelected()==true) {
		        	genero = "Femenino";
		        }
		     
		        double t1 = Double.parseDouble(lblT1content.getText());
		        double t2 = Double.parseDouble(lblT2content.getText());
		        double ep = Double.parseDouble(lblEPcontent.getText());
		        double ef = Double.parseDouble(lblEFcontent.getText());
		        double nf = Double.parseDouble(lblNFcontent.getText());

		        int tipo = comboBox.getSelectedIndex();
		        
		        switch(tipo) {
		            case 1:
		                lista.put(dni,new Teorico(dni,nombre,genero,oh, t1,t2,ep,ef,nf));
		                break;
		            case 2:
		                lista.put(dni,new Laboratorio(dni,nombre,genero,oh, t1,t2,ep,ef,nf));
		                break;
		        }
		        
		        cargar();
		        promedioCalificacion();
			}
		});
		btnAgregar.setBounds(171, 305, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnLimpiar.setBounds(139, 353, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModificar.setBounds(270, 305, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnCargar.setBounds(238, 353, 89, 23);
		contentPane.add(btnCargar);
		
		JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        if (!textDNI.getText().isEmpty()) {
				            int dni = Integer.parseInt(textDNI.getText());
				            Estudiante estudiante = buscar(dni);
				            if (estudiante != null) {
				                int confirmar = JOptionPane.showConfirmDialog(null, "¿Está seguro?", "Eliminar", JOptionPane.YES_NO_OPTION);
				                if (confirmar == JOptionPane.YES_OPTION) {
				                    lista.remove(dni);
				                    limpiar();
				                    cargar();
				                    promedioCalificacion();
				                    JOptionPane.showMessageDialog(null, estudiante.getName() + " ha sido eliminado");
				                }
				            } else {
				                JOptionPane.showMessageDialog(null, "El DNI " + dni + " no existe");
				            }
				        } else {
				            textDNI.requestFocus();
				        }
				    }
				});

		btnEliminar.setBounds(369, 305, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnGuardar.setBounds(337, 353, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargar();
			}
		});
		btnListar.setBounds(468, 305, 89, 23);
		contentPane.add(btnListar);
		
		JButton btnOrdenar = new JButton("Ordenar");
		btnOrdenar.setBounds(436, 353, 89, 23);
		contentPane.add(btnOrdenar);
		
		JButton btnAprobados = new JButton("Aprobados");
		btnAprobados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Aprobado();
			}
		});
		btnAprobados.setBounds(567, 305, 115, 23);
		contentPane.add(btnAprobados);
		
		JButton btnDesaprobados = new JButton("Desaprobados");
		btnDesaprobados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DesAprobado();
			}
		});
		btnDesaprobados.setBounds(535, 353, 115, 23);
		contentPane.add(btnDesaprobados);
		
		JButton btnMasculino = new JButton("Masculino");
		btnMasculino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Masculino();
			}
		});
		btnMasculino.setBounds(688, 305, 89, 23);
		contentPane.add(btnMasculino);
		
		JButton btnFemenino = new JButton("Femenino");
		btnFemenino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Femenino();
			}
		});
		btnFemenino.setBounds(660, 353, 89, 23);
		contentPane.add(btnFemenino);
		
		textDNI = new JTextField();
		textDNI.setBounds(77, 28, 86, 20);
		contentPane.add(textDNI);
		textDNI.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(200, 30, 577, 235);
		contentPane.add(scrollPane);
		
		table = new JTable();table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.getSelectedRow();
				if (fila>=0) {
					int valuex = Integer.parseInt(table.getValueAt(fila, 0).toString());
					Estudiante m11 = buscar(valuex);
					textName.setText(m11.getName());
					textCurso.setText(m11.getCurso());
					textDNI.setText(String.valueOf(m11.getDNI()));
					if (m11.getGenero().equals("Masculino")) {
						rdbtnMasculino.setSelected(true);
						rdbtnFemenino.setSelected(false);
					}
					else if (m11.getGenero().equals("Femenino")) {
						rdbtnFemenino.setSelected(true);
						rdbtnMasculino.setSelected(false);
					}
					String type = m11.getTipo();
					comboBox.setSelectedItem(type);
					lblT1content.setText(String.valueOf(m11.getT1()));
					lblT2content.setText(String.valueOf(m11.getT2()));
					lblEPcontent.setText(String.valueOf(m11.getEp()));
					lblEFcontent.setText(String.valueOf(m11.getEf()));
					lblNFcontent.setText(String.valueOf(m11.getNf()));
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblListadeEstudiantes = new JLabel("Lista de Estudiantes");
		lblListadeEstudiantes.setBounds(444, 11, 161, 14);
		contentPane.add(lblListadeEstudiantes);
		
		JLabel lblPromediodeCalificaciones = new JLabel("Promedio de Calificaciones:");
		lblPromediodeCalificaciones.setBounds(238, 276, 171, 14);
		contentPane.add(lblPromediodeCalificaciones);
		
		textName = new JTextField();
		textName.setBounds(77, 65, 86, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		textCurso = new JTextField();
		textCurso.setBounds(77, 176, 86, 20);
		contentPane.add(textCurso);
		textCurso.setColumns(10);
		
		rdbtnMasculino = new JRadioButton("M");
		rdbtnMasculino.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnMasculino.isSelected()==true) {
					rdbtnFemenino.setSelected(false);
				}
			}
		});
		rdbtnMasculino.setBounds(73, 89, 46, 23);
		contentPane.add(rdbtnMasculino);
		
		rdbtnFemenino = new JRadioButton("F");
		rdbtnFemenino.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnFemenino.isSelected()==true) {
					rdbtnMasculino.setSelected(false);
				}
			}
		});
		rdbtnFemenino.setBounds(119, 89, 46, 23);
		contentPane.add(rdbtnFemenino);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Teorico", "Laboratorio"}));
		comboBox.setBounds(77, 130, 86, 22);
		contentPane.add(comboBox);
		
		JLabel lblT1 = new JLabel("T1:");
		lblT1.setBounds(37, 220, 46, 14);
		contentPane.add(lblT1);
		
		JLabel lblT2 = new JLabel("T2:");
		lblT2.setBounds(37, 245, 46, 14);
		contentPane.add(lblT2);
		
		JLabel lblEP = new JLabel("EP:");
		lblEP.setBounds(37, 270, 46, 14);
		contentPane.add(lblEP);
		
		JLabel lblEF = new JLabel("EF:");
		lblEF.setBounds(37, 295, 46, 14);
		contentPane.add(lblEF);
		
		JLabel lblNF = new JLabel("NF:");
		lblNF.setBounds(37, 326, 46, 14);
		contentPane.add(lblNF);
		
		lblT1content = new JLabel("0");
		lblT1content.setBounds(77, 220, 46, 14);
		contentPane.add(lblT1content);
		
		lblT2content = new JLabel("0");
		lblT2content.setBounds(77, 245, 46, 14);
		contentPane.add(lblT2content);
		
		lblEPcontent = new JLabel("0");
		lblEPcontent.setBounds(77, 270, 46, 14);
		contentPane.add(lblEPcontent);
		
		lblEFcontent = new JLabel("0");
		lblEFcontent.setBounds(77, 295, 46, 14);
		contentPane.add(lblEFcontent);
		
		lblNFcontent = new JLabel("0");
		lblNFcontent.setBounds(77, 328, 46, 14);
		contentPane.add(lblNFcontent);
		
		lblNumeroCalif = new JLabel("00");
		lblNumeroCalif.setBounds(397, 276, 46, 14);
		contentPane.add(lblNumeroCalif);
		
		lista.put(7010390, new Laboratorio(7010390, "Firulais", "Masculino","Algoritmos",17.2,17,17,17,17));
		lista.put(7010391, new Laboratorio(7010391, "Firulais", "Femenino","Algoritmos",17.2,17,17,17,17));
		cargar();
		promedioCalificacion();
		
		
	}
	
	
	void cargar() {
		DefaultTableModel model = new DefaultTableModel(
			new String[] {"DNI", "Nombre", "Genero", "Curso","Tipo", "T1", "T2", "EP", "EF","NF"}, 0){
			private static final long serialVersionUID = 1L;
			Class<?>[] columnTypes = new Class[] {
					 Integer.class,
			           String.class,
			           String.class,
			           String.class,
			           String.class,
			           Double.class,
			           Double.class,
			           Double.class,
			           Double.class,
			           Double.class,
				};
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};

		Enumeration<Estudiante> e = lista.elements();

		while(e.hasMoreElements()) {
			Estudiante m = e.nextElement();
			model.addRow(new Object[] {
					m.getDNI(),
	        		m.getName(),
	        		m.getGenero(),
	        		m.getCurso(),
	        		m.getTipo(),
	        		m.getT1(),
	        		m.getT2(),
	        		m.getEp(),
	        		m.getEf(),
	        		m.getNf()
					});
		}
		table.setModel(model);
	}
	
	Estudiante buscar (int codigo) {
		Enumeration<Estudiante> e = lista.elements();
		while (e.hasMoreElements()) {
			Estudiante m = e.nextElement();
			if(m.getDNI()==codigo) {
				return m;
			}
		}
		return null;
	}
	

	public void limpiar() {
	
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
        
    }
	
	
	void Masculino() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();

	    for (int n = rowCount - 1; n >= 0; n--) {
	        String determinante = (String) model.getValueAt(n, 2);
	        if (!determinante.equals("Masculino")) {
	            model.removeRow(n);
	        }
	    }
	}	
	
	void Femenino() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();

	    for (int n = rowCount - 1; n >= 0; n--) {
	        String determinante = (String) model.getValueAt(n, 2);
	        if (!determinante.equals("Femenino")) {
	            model.removeRow(n);
	        }
	    }
	}
	
	void Aprobado() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();

	    for (int n = rowCount - 1; n >= 0; n--) {
	        double determinante = (double) model.getValueAt(n, 9);
	        if (determinante<11) {
	            model.removeRow(n);
	        }
	    }
	}
	
	void DesAprobado() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int rowCount = model.getRowCount();

	    for (int n = rowCount - 1; n >= 0; n--) {
	        double determinante = (double) model.getValueAt(n, 9);
	        if (determinante>=11) {
	            model.removeRow(n);
	        }
	    }
	}
	
	 private void generarNumerosAleatorios() {
	        // Generar números aleatorios en el rango de 0 a 20
	        int numeroT1 = (int) (Math.random() * 20);
	        int numeroT2 = (int) (Math.random() * 20);
	        int numeroEv1 = (int) (Math.random() * 20);
	        int numeroEF = (int) (Math.random() * 20);

	        // Mostrar los números generados en los JTextField
	        lblT1content.setText(Integer.toString(numeroT1));
	        lblT2content.setText(Integer.toString(numeroT2));
	        lblEPcontent.setText(Integer.toString(numeroEv1));
	        lblEFcontent.setText(Integer.toString(numeroEF));
	    }

	    private void calcularPromedio() {
	        // Obtener los valores de los JTextField
	        int t1 = Integer.parseInt(lblT1content.getText());
	        int t2 = Integer.parseInt(lblT2content.getText());
	        int ev1 = Integer.parseInt(lblEPcontent.getText());
	        int ef = Integer.parseInt(lblEFcontent.getText());

	        // Calcular el promedio
	        double promedio = 0.6 * ((t1 + t2) / 2.0) + 0.4 * ((ev1 + ef) / 2.0);

	        // Mostrar el promedio en el JLabel con dos decimales
	        DecimalFormat decimalFormat = new DecimalFormat("0.00");
	        String prom = String.valueOf(decimalFormat.format(promedio));
	        lblNFcontent.setText(prom);
	    }
	    
	    private void promedioCalificacion() {

	    	DecimalFormat dFormat = new DecimalFormat("#.##");
	    	int fil = table.getRowCount();
	    	double califinal = 0;
	    	for(int i = 0; i<fil ; i++) {
	    		double sum = (double) table.getValueAt(i, 9);
	    		califinal+=sum;
	    	}
	    	double sumresult = califinal/(lista.size());
	    	String rfinal = dFormat.format(sumresult);
	    }
	    
}