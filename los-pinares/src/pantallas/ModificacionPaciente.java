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

public class ModificacionPaciente extends JDialog {
	private JTextField tfCodPaciente;
	private JTextField tfNombrePaciente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificacionPaciente dialog = new ModificacionPaciente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificacionPaciente() {
		setResizable(false);
		setTitle("Modificaci\u00F3n paciente");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				MainDatos mainDatos = new MainDatos();
				mainDatos.setVisible(true);
			}
		});
		String path = "C:\\";
		String datosPac = "datopac.txt";

		setType(Type.UTILITY);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(153, 204, 153));
		setBounds(100, 100, 311, 283);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(153, 204, 153));
			buttonPane.setBounds(0, 186, 391, 33);
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(78, 5, 58, 25);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (generaModificacionPaciente(tfCodPaciente, tfNombrePaciente, path, datosPac))
							dispose();
					}
				});
				buttonPane.setLayout(null);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setBounds(148, 5, 92, 25);
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
			JLabel label = new JLabel("C\u00F3digo de Paciente:");
			label.setBounds(47, 61, 130, 14);
			getContentPane().add(label);
		}
		{
			tfCodPaciente = new JTextField();
			tfCodPaciente.setColumns(10);
			tfCodPaciente.setBounds(47, 86, 206, 20);
			getContentPane().add(tfCodPaciente);
		}
		{
			JLabel label = new JLabel("Nombre de Paciente:");
			label.setBounds(47, 128, 130, 14);
			getContentPane().add(label);
		}
		{
			tfNombrePaciente = new JTextField();
			tfNombrePaciente.setColumns(10);
			tfNombrePaciente.setBounds(47, 153, 206, 20);
			getContentPane().add(tfNombrePaciente);
		}
		{
			JLabel lblModificacionDePaciente = new JLabel("Modificaci\u00F3n de Paciente");
			lblModificacionDePaciente.setHorizontalAlignment(SwingConstants.CENTER);
			lblModificacionDePaciente.setFont(new Font("Arial", Font.BOLD, 18));
			lblModificacionDePaciente.setBounds(36, 23, 228, 25);
			getContentPane().add(lblModificacionDePaciente);
		}
		setLocationRelativeTo(null);
	}

	private ArrayList<Paciente> leerArchivoPacientes(String archivo) {
		try {
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String s = "";
			ArrayList<Paciente> ret = new ArrayList<Paciente>();
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
		int d;
		try {
			d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		if (d < 1 && d >= 10000) {
			return false;
		}
		if (str.length() != 4) {
			return false;
		}
		return true;
	}

	private boolean generaModificacionPaciente(JTextField tfCodPaciente, JTextField tfNombrePaciente, String path,
			String datosPac) {
		/////// validaciones de campos de texto/////////
		if (esCodigoValido(tfCodPaciente.getText().trim())) {
			if (tfNombrePaciente.getText().trim().length() <= 30 && tfNombrePaciente.getText().trim().length() > 0) {
				// si es valido primero se fija si el paciente ya existe
				boolean existePaciente = false;
				ArrayList<Paciente> pacientes = leerArchivoPacientes(Constantes.archivoPacientes);
				for (Paciente p : pacientes) {
					if (p.getCodigo() == Integer.parseInt(tfCodPaciente.getText().trim()))
						existePaciente = true;
				}

				if (existePaciente) {

					try {
						Paciente pacMod = new Paciente(Integer.valueOf(tfCodPaciente.getText()),
								tfNombrePaciente.getText());

						// Borro archivo viejo y escrivo el nuevo.
						FileWriter datopac1 = new FileWriter(Constantes.archivoPacientes);
						for (Paciente p : pacientes) {
							String registro = "";
							if (p.getCodigo() == pacMod.getCodigo())
								registro = Encriptacion.Encriptar(pacMod.getCodigo() + "," + pacMod.getNombre());
							else
								registro = Encriptacion.Encriptar(p.getCodigo() + "," + p.getNombre());
							datopac1.write(registro + "\n");
						}
						datopac1.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "¡Modificación realizada satisfactoriamente!", "Confirmación",
							JOptionPane.INFORMATION_MESSAGE);
					return true;

				} else {

					JOptionPane.showMessageDialog(null, "¡El código de paciente ingresado no existe!", "Error",
							JOptionPane.ERROR_MESSAGE);

				}
			} else {
				JOptionPane.showMessageDialog(null, "¡Debe ingresar un nombre de paciente de hasta 30 caracteres!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "¡Debe ingresar un código de paciente numerico de 4 dígitos!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return false;
	}

	public void limpiaCampos() {
		this.tfCodPaciente.setText("");
		this.tfNombrePaciente.setText("");
	}

}
