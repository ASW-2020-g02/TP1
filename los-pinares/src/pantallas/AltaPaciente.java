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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entidades.Paciente;
import otros.Constantes;
import otros.Encriptacion;

public class AltaPaciente extends JDialog {
	private JTextField tfNombrePaciente;
	private JTextField tfCodPaciente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AltaPaciente dialog = new AltaPaciente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AltaPaciente() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				MainDatos mainDatos = new MainDatos();
				mainDatos.setVisible(true);
			}
		});
		setTitle("Alta de paciente");

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		getContentPane().setBackground(new Color(153, 204, 153));
		setBounds(100, 100, 289, 282);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(153, 204, 153));
			buttonPane.setBounds(0, 189, 259, 33);
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(52, 0, 59, 25);
				okButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						if (generaAltaPaciente(tfCodPaciente, tfNombrePaciente, Constantes.archivoPacientes)) {
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
				cancelButton.setBounds(134, 0, 91, 25);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MainDatos mainDatos = new MainDatos();
						mainDatos.setVisible(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			tfNombrePaciente = new JTextField();
			tfNombrePaciente.setToolTipText("El nombre del paciente permite hasta 30 caracteres.");
			tfNombrePaciente.setBounds(25, 143, 206, 20);
			getContentPane().add(tfNombrePaciente);
			tfNombrePaciente.setColumns(10);
		}
		{
			tfCodPaciente = new JTextField();
			tfCodPaciente.setToolTipText("El c\u00F3digo de paciente debe ser un n\u00FAmero de 4 d\u00EDgitos.");
			tfCodPaciente.setBounds(25, 76, 206, 20);
			getContentPane().add(tfCodPaciente);
			tfCodPaciente.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("C\u00F3digo de Paciente:");
			lblNewLabel.setBounds(25, 51, 182, 14);
			getContentPane().add(lblNewLabel);
		}
		{
			JLabel lblNombreDePaciente = new JLabel("Nombre de Paciente:");
			lblNombreDePaciente.setBounds(25, 118, 182, 14);
			getContentPane().add(lblNombreDePaciente);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Alta de Paciente");
			lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(25, 13, 218, 25);
			getContentPane().add(lblNewLabel_1);
		}
		setLocationRelativeTo(null);

	}

	private void limpiaCampos() {
		// Esta funcion limpia los campos de texto de las pantallas
		this.tfCodPaciente.setText("");
		this.tfNombrePaciente.setText("");
	}

	private ArrayList<Paciente> leerArchivoPacientes(String archivo) {
		// Este metodo devuelve una lista de pacientes
		// Los mismos son los pacinetes registrados en el sistema
		try {
			// lee archivo de pacientes
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String s = "";
			ArrayList<Paciente> ret = new ArrayList<Paciente>();

			// Guarda en array cada paciente
			while ((s = entrada.readLine()) != null) {
				String cd = "";
				try {
					// Desencripta los datos antes de guardarlos en arreglo
					cd = Encriptacion.Desencriptar(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// split por , para diferenciar nombre y codigo
				String[] cadena = cd.split(",");
				int cod = Integer.parseInt(cadena[0]);
				String nom = cadena[1];
				ret.add(new Paciente(cod, nom));
			}
			entrada.close();
			return ret;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean esCodigoValido(String str) {
		// Este metodo valida el codigo de paciente ingresado
		int d;
		try {
			// Obtiene un entero de la cadena
			d = Integer.parseInt(str);

			// Valida que este en el rango correcto
			if (d < 1 || d > 10000) {
				return false;
			}

			// Valida que tenga cuatro digitos
			if (str.length() != 4) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean generaAltaPaciente(JTextField tfCodPaciente, JTextField tfNombrePaciente, String pathdatosPac) {
		// Método generaAltaPaciente
		// se da de alta el paciente en los archivos del sistema.
		// En el mismo se hacen las validaciones necesarias

		/////// validaciones de campos de texto/////////
		if (esCodigoValido(tfCodPaciente.getText().trim())) {
			if ((tfNombrePaciente.getText().trim().length() <= 30 && tfNombrePaciente.getText().trim().length() > 0)) {
				// si es valido primero se fija si el paciente ya existe
				boolean existePaciente = false;
				ArrayList<Paciente> alpacientes = leerArchivoPacientes(pathdatosPac);
				for (Paciente p : alpacientes) {
					if (p.getCodigo() == Integer.parseInt(tfCodPaciente.getText().trim()))
						// el paciente existe
						existePaciente = true;
				}

				if (existePaciente) {
					// Error -> Paciente existe
					JOptionPane.showMessageDialog(null, "¡El código de paciente ingresado ya existe!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// El paciente no existe -> se peude grabar!
					try {
						// Inicia grabación en archivo
						FileWriter datopac = new FileWriter(pathdatosPac, true);
						String registro = Encriptacion
								.Encriptar(tfCodPaciente.getText().trim() + "," + tfNombrePaciente.getText().trim());
						datopac.write(registro + "\n");
						datopac.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					// Salio todo bien -> mostrar cartel
					JOptionPane.showMessageDialog(null, "¡Alta realizada satisfactoriamente!", "Confirmación",
							JOptionPane.INFORMATION_MESSAGE);
					return true;
				}
			} else {
				// Error: La cantidad de caracteres o el tipo en el nombre son incorrectos ->
				// mostrar
				JOptionPane.showMessageDialog(null,
						"¡Debe ingresar un nombre de paciente de hasta 30 caracteres y los mismos deben ser letras!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// Error: La cantidad o tipo de digitos para el codigo son invalidos -> Mostrar
			JOptionPane.showMessageDialog(null,
					"¡Debe ingresar un código de paciente numérico de 4 dígitos y mayor a 0!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

}
