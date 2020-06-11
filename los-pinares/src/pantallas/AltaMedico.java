package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entidades.Medico;
import otros.Constantes;
import otros.Encriptacion;
import otros.FuncionesComunes;

//Esta clase adminsitra la alta de cada medico
//La misma tiene tanto la logica de la ventana como tambien
//Los metodos para grabar los datos en disco
public class AltaMedico extends JDialog {
	private JTextField tfCodMedico;
	private JTextField tfNombreMedico;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		// Main de la ventana
		try {
			AltaMedico dialog = new AltaMedico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public AltaMedico() {
		// Función AltaMedico:
		// Se encarga de armar la ventana con Swing
		setResizable(false);

		// Listener para cerrar la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainDatos mainDatos = new MainDatos();
				mainDatos.setVisible(true);
			}
		});
		setTitle("Alta m\u00E9dico");

		// Combo de especialización de los medicos
		JComboBox cboEspecializacion = new JComboBox();
		cboEspecializacion.setBounds(38, 223, 206, 20);
		getContentPane().add(cboEspecializacion);

		try {
			// Trae las especializaciones de los medicos para poneralas en el combo
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoEspecializaciones));
			String s = "";
			while ((s = entrada.readLine()) != null) {
				cboEspecializacion.addItem(s);
			}
			entrada.close();

		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

		setType(Type.UTILITY);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(153, 204, 153));
		setBounds(100, 100, 304, 348);

		// Armado de panel principal
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(153, 204, 153));
			buttonPane.setBounds(0, 256, 391, 33);
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(60, 5, 62, 25);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (generaAltaMedico(tfCodMedico, tfNombreMedico,
								cboEspecializacion.getSelectedItem().toString(), Constantes.archivoMedicos)) {
							limpiaCampos();
							dispose();
						}

					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setBounds(141, 5, 94, 25);

				// Listener del boton cancelar
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JLabel lblAltaDeMdico = new JLabel("Alta de M\u00E9dico");
			lblAltaDeMdico.setHorizontalAlignment(SwingConstants.CENTER);
			lblAltaDeMdico.setFont(new Font("Arial", Font.BOLD, 18));
			lblAltaDeMdico.setBounds(52, 24, 170, 25);
			getContentPane().add(lblAltaDeMdico);
		}
		{
			JLabel lblCdigoDeMdico = new JLabel("C\u00F3digo de M\u00E9dico:");
			lblCdigoDeMdico.setBounds(38, 62, 170, 14);
			getContentPane().add(lblCdigoDeMdico);
		}
		{
			tfCodMedico = new JTextField();
			tfCodMedico.setToolTipText("El c\u00F3digo de m\u00E9dico debe ser de 4 d\u00EDgitos y mayor a 0");
			tfCodMedico.setColumns(10);
			tfCodMedico.setBounds(38, 87, 206, 20);
			getContentPane().add(tfCodMedico);
		}
		{
			JLabel lblNombreDeMdico = new JLabel("Nombre de M\u00E9dico:");
			lblNombreDeMdico.setBounds(38, 129, 170, 14);
			getContentPane().add(lblNombreDeMdico);
		}
		{
			tfNombreMedico = new JTextField();
			tfNombreMedico.setToolTipText("El nombre de m\u00E9dico debe ser de hasta 30 caract\u00E9res.");
			tfNombreMedico.setColumns(10);
			tfNombreMedico.setBounds(38, 154, 206, 20);
			getContentPane().add(tfNombreMedico);
		}
		{
			JLabel lblEspecializacin = new JLabel("Especializaci\u00F3n:");
			lblEspecializacin.setBounds(38, 196, 170, 14);
			getContentPane().add(lblEspecializacin);
		}
		setLocationRelativeTo(null);
	}

	// Funcion leerArchivoMedicos:
	// Esta funcion obtiene la lista de medicos almacenados en el sistema.
	// Su motivo es la necesidad de no ingresar dos veces el mismo medico
	private ArrayList<Medico> leerArchivoMedicos(String archivo) {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String s = "";
			ArrayList<Medico> ret = new ArrayList<Medico>();
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] cadena = cd.split(",");
				int cod = Integer.parseInt(cadena[0]);
				String nom = cadena[1];
				String esp = cadena[2];
				ret.add(new Medico(cod, nom, esp));
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Metodo esCodigoValido:
	// Valida el codigo del medico para que sea:
	// numerico
	// con 4 caracteres
	private boolean esCodigoValido(String str) {
		int d;
		try {
			d = Integer.parseInt(str);

			if (d < 1 || d > 10000) {
				return false;
			}
			if (str.length() != 4) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean generaAltaMedico(JTextField tfCodMedico, JTextField tfNombreMedico, String espec,
			String pathdatosMed) {
		// Método generaAltaMedico
		// se da de alta el medico en los archivos del sistema.
		// En el mismo se hacen las validaciones necesarias

		if (esCodigoValido(tfCodMedico.getText().trim())) {
			if ((tfNombreMedico.getText().trim().length() > 0 && tfNombreMedico.getText().trim().length() <= 30)
					&& FuncionesComunes.isAlpha(tfNombreMedico.getText().trim())) {
				// si es valido primero se fija si el paciente ya existe
				boolean existeMedico = false;
				ArrayList<Medico> medicos = leerArchivoMedicos(pathdatosMed);
				for (Medico p : medicos) {
					if (p.getCodigo() == Integer.parseInt(tfCodMedico.getText().trim()))
						existeMedico = true;
				}

				// Si existe medico -> no se peude ingresar
				if (existeMedico) {
					JOptionPane.showMessageDialog(null, "¡El código del médico ingresado ya existe!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// no existe -> se ingresa!
					try {
						// Grabacion del archivo
						FileWriter datopac = new FileWriter(pathdatosMed, true);
						String registro = Encriptacion.Encriptar(
								tfCodMedico.getText().trim() + "," + tfNombreMedico.getText().trim() + "," + espec);
						datopac.write(registro + "\n");
						datopac.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					// Salio todo bien!
					JOptionPane.showMessageDialog(null, "¡Alta realizada satisfactoriamente!", "Confirmación",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}

			} else {
				// Error en la cantidad de caracteres en el nombre
				JOptionPane.showMessageDialog(null,
						"¡Debe ingresar un nombre de médico de hasta 30 caracteres  y los mismos deben ser letras!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// Error en la cantidad/tipo de digitos para el codigo
			JOptionPane.showMessageDialog(null, "¡Debe ingresar un código de médico numerico de 4 dígitos y mayor a 0!",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public void limpiaCampos() {
		// Funcion limpiaCampos:
		// Se encarga de limpiar los textos de las cajas de texto.
		this.tfCodMedico.setText("");
		this.tfNombreMedico.setText("");
	}
}
