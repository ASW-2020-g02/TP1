package otros;

//Esta clase contiene funciones comunes que son usadas en todo el sistema
public class FuncionesComunes {
	
	//Funcion isInteger:
	//Esta funcion devuelve True si la cadena enviada es nuemerica
	//De lo contrario devuelve False
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//Funcion isAlpha
	//Esta funcion devuelve true si la cadena enviada por parametro tiene solo caracteres
	//devuelve false si alguno de sus caracteres no son alfabeticos
	public static boolean isAlpha(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c) && c!=' ') {
				return false;
			}
		}

		return true;
	}
	
	
	//Funcion isValidUserOrPass:
	//La funcion sirve para validar usuario y password del sistema
	//Devuelve true si ambos son correctos
	//false: si alguno de los dos no son correctos.
	public static boolean isValidUserOrPass(String pass) {
		char[] chars = pass.toCharArray();

		for (char c : chars) {
			if (!Character.isLetter(c) && !isInteger(String.valueOf(c))) {
				return false;
			}
		}

		return true;
	}
	

}
