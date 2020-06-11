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

public class ModificacionMedico extends JDialog {
	private JTextField tfCodMedico;
	private JTextField tfNombreMedico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificacionMedico dialog = new ModificacionMedico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificacionMedico() {
		setResizable(false);
		setTitle("Modificaci\u00F3n m\u00E9dico");
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				MainDatos mainDatos = new MainDatos();
				mainDatos.setVisible(true);
			}
		});
		JComboBox cboEspecializacion = new JComboBox();
		cboEspecializacion.setBounds(47, 229, 206, 20);
		getContentPane().add(cboEspecializacion);

		try {
			BufferedReader entrada = new BufferedReader(new FileReader(Constantes.archivoEspecializaciones));
			String s = "";
			while ((s = entrada.readLine()) != null) {
				cboEspecializacion.addItem(s);
			}
			entrada.close();

		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		getContentPane().setBackground(new Color(153, 204, 153));
		setBounds(100, 100, 321, 383);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(153, 204, 153));
			buttonPane.setBounds(0, 262, 391, 33);
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(64, 5, 65, 25);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (generaModificacionMedico(tfCodMedico, tfNombreMedico,
								cboEspecializacion.getSelectedItem().toString()))
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
				cancelButton.setBounds(147, 5, 94, 25);
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
			JLabel lblModificacinDeMdico = new JLabel("Modificaci\u00F3n de M\u00E9dico");
			lblModificacinDeMdico.setHorizontalAlignment(SwingConstants.CENTER);
			lblModificacinDeMdico.setFont(new Font("Arial", Font.BOLD, 18));
			lblModificacinDeMdico.setBounds(37, 26, 228, 25);
			getContentPane().add(lblModificacinDeMdico);
		}
		{
			JLabel lblCdigoDeMdico = new JLabel("C\u00F3digo de M\u00E9dico:");
			lblCdigoDeMdico.setBounds(47, 64, 142, 14);
			getContentPane().add(lblCdigoDeMdico);
		}
		{
			tfCodMedico = new JTextField();
			tfCodMedico.setColumns(10);
			tfCodMedico.setBounds(47, 89, 206, 20);
			getContentPane().add(tfCodMedico);
		}
		{
			JLabel lblNombreDeMdico = new JLabel("Nombre de M\u00E9dico:");
			lblNombreDeMdico.setBounds(47, 131, 142, 14);
			getContentPane().add(lblNombreDeMdico);
		}
		{
			tfNombreMedico = new JTextField();
			tfNombreMedico.setColumns(10);
			tfNombreMedico.setBounds(47, 156, 206, 20);
			getContentPane().add(tfNombreMedico);
		}
		{
			JLabel label = new JLabel("Especializaci\u00F3n:");
			label.setBounds(47, 202, 170, 14);
			getContentPane().add(label);
		}

		setLocationRelativeTo(null);
	}

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

	private boolean generaModificacionMedico(JTextField tfCodMedico, JTextField tfNombreMedico,
			String tfEspecializacion) {

		if (esCodigoValido(tfCodMedico.getText().trim())) {
			if (tfNombreMedico.getText().trim().length() > 0 && tfNombreMedico.getText().trim().length() <= 30) {
				// si es valido primero se fija si el paciente ya existe
				boolean existeMedico = false;
				ArrayList<Medico> medicos = leerArchivoMedicos(Constantes.archivoMedicos);
				for (Medico p : medicos) {
					if (p.getCodigo() == Integer.parseInt(tfCodMedico.getText().trim()))
						existeMedico = true;
				}

				if (existeMedico) {
					try {
						Medico medMod = new Medico(Integer.valueOf(tfCodMedico.getText()), tfNombreMedico.getText(),
								tfEspecializacion);

						// Borro archivo viejo y escrivo el nuevo.
						FileWriter datopac1 = new FileWriter(Constantes.archivoMedicos);
						for (Medico m : medicos) {
							String registro = "";
							if (m.getCodigo() == medMod.getCodigo()) {
								registro = Encriptacion.Encriptar(medMod.getCodigo() + "," + medMod.getNombre() + ","
										+ medMod.getEspecializacion());
							} else {
								registro = Encriptacion.Encriptar(m.getCodigo() + "," + m.getNombre() + "," + m.getEspecializacion());
							}
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
					JOptionPane.showMessageDialog(null, "¡El código del médico ingresado no existe!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "¡Debe ingresar un nombre de médico de hasta 30 caracteres!",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else

		{
			JOptionPane.showMessageDialog(null, "¡Debe ingresar un código de médico numerico de 4 dígitos!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return false;

	}

	public void limpiaCampos() {
		this.tfCodMedico.setText("");
		this.tfNombreMedico.setText("");
	}
}
