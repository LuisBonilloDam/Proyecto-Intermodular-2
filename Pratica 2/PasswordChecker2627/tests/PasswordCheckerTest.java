import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordCheckerTest { //Declaro la Clase
    PasswordChecker checker;

    @BeforeEach
    void  setUp(){
         checker = new PasswordChecker(); //Creo el objeto que vamos a utilizar para las comprobaciones
         checker.setMinLength(7); //Asigno el valor minimo que puede tener la clave
         checker.setMaxLength(12); //Asigno el valor maximo que puede tener la clave


    }

    @Test //Test para comprobar el Minimo de caracteres en la contrase;ia
    void lengthBelowMinimum(){
        int result = checker.check("abcdef", "abcdef");
        assertEquals(PasswordChecker.ERR_LENGTH, result);
    }

    @Test//Test para comprobar si con justo el minimo la contrasenia funciona
    void lengthJustAtMinimum(){
        int result = checker.check("abcdefg", "abcdefg");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test//Test para comprobar si con justo con el maximo la contrasenia funciona
    void lengthJustAtMaximum(){
        int result = checker.check("abcdefghijkl", "abcdefghijkl");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test // Comprobacion de que el maximo de 12 caracteres se esta guardando
    void maxLengthIsStored(){
        int result = checker.getMaxLength();
        assertEquals(12, result);
    }


    @Test //Test para comprobar el maximo de caracteres en la contrase;a
    void lengthAboveTheMaximum(){
        int result = checker.check("abcdefghijklm", "abcdefghijklm");
        assertEquals(PasswordChecker.ERR_LENGTH, result);
    }

    @Test //Comprobamos las minusculas en la contrase;a

    void lowerMissingCheck(){ //Comprobar si hay minusculas
        checker.setCheckLowers(true);
        int result = checker.check("ABCDEFG", "ABCDEFG");
        assertEquals(PasswordChecker.ERR_LOWERS, result);
    }

    @Test
    void lowerAndUpperCheck(){ //Comprobar si funciona con una combinacion de mayusculas y minisculas
        checker.setCheckLowers(true);
        int result = checker.check("ABCDefg", "ABCDefg");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test
    void AllLowerLetters(){ //Comprobamos si funciona con todas las letras en minusculas
        checker.setCheckLowers(true);
        int result = checker.check("abcdefg", "abcdefg");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test
    void digitsCheck(){ //Comprobamos si hay digitos en la contrase;ia
        checker.setCheckDigits(true);
        int result = checker.check("ABcdefgh", "ABcdefgh" );
        assertEquals(PasswordChecker.ERR_DIGITS, result);
    }

    @Test
    void passWordOk(){ //Comprobamos si la contrase;a esta correcta
        int result = checker.check("Abcd1234", "Abcd1234" );
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test //Comprobamos si la contrase;a esta vacia
    void passInBlank(){
        int result = checker.check("", "" );
        assertEquals(PasswordChecker.ERR_BLANK, result);
    }
    @Test //Comprobamos los espacios en blanco en la contrase;a
    void espaceInBlankPassword(){
        int result = checker.check("        ", "        ");
        assertEquals(PasswordChecker.ERR_BLANK, result);
    }
    @Test //Error si la contrase;ia no es igual
    void passNoMatch(){
        int result = checker.check("ABcd1234", "Abcd1234");
        assertEquals(PasswordChecker.ERR_NO_MATCH, result);
    }
    @Test // Con dígitos y la comprobación activada: debe aceptarla
    void digitsPresentCheckOn(){
        checker.setCheckDigits(true);
        int result = checker.check("Abcd1234", "Abcd1234");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test // Sin dígitos y comprobación sin activar (valor por defecto): debe aceptarla
    void digitsMissingCheckOff(){
        int result = checker.check("ABcdefgh", "ABcdefgh");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    //
    @Test //Test para comprobar si nos faltan minusculas
    void lowersMissingCheckOff(){
        int result = checker.check("ABCDEFG", "ABCDEFG");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    //
    @Test // Test para comprobar si nos faltan mayusculas
    void uppersMissingCheckOn(){
        checker.setCheckUppers(true);
        int result = checker.check("abcdefgh", "abcdefgh");
        assertEquals(PasswordChecker.ERR_UPPERS, result);
    }

    @Test //Test con la comprobacion de mayusculas en la contrase;ia
    void uppersPresentCheckOn(){
        checker.setCheckUppers(true);
        int result = checker.check("Abcdefgh", "Abcdefgh");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }

    @Test
    void uppersMissingCheckOff(){ //Test solamente con minusculas sin la comprobacion
        int result = checker.check("abcdefgh", "abcdefgh");
        assertEquals(PasswordChecker.PASSWORD_OK, result);
    }



    @Test
    void minLengthNegativeThrows(){ //Si setMinLength tiene un valor negativo lanzara un exepcion
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> checker.setMinLength(-1));
        assertEquals(PasswordChecker.ERR_INVALID_MIN_LENGTH, ex.getMessage());
    }

    @Test // setMinLength(0) NO es inferior a cero, así que no debe lanzar excepción
    void minLengthZeroDoesNotThrow(){
        assertDoesNotThrow(() -> checker.setMinLength(0));
    }

    @Test
    void maxLengthBelowMinThrows(){ //Si el valor es maximo de 6 nos lanzara una excepcion
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> checker.setMaxLength(6));
        assertEquals(PasswordChecker.ERR_INVALID_MAX_LENGTH, ex.getMessage());
    }

    @Test // setMaxLength igual al mínimo (7) NO es inferior, así que no debe lanzar excepción
    void maxLengthEqualsMinDoesNotThrow(){
        assertDoesNotThrow(() -> checker.setMaxLength(7));
    }


    @Test // Dado que comprobe que el valor maximo se guarda, agrego este para verificar si el valor minimo tambien se guarda
    void minLengthIsStored(){
        assertEquals(7, checker.getMinLength());
    }
    @Test // getCheckDigits, getCheckLowers y getCheckUppers valen false por defecto segun el HTML dado
    void flagsDefaultToFalse(){
        assertFalse(checker.getCheckDigits());
        assertFalse(checker.getCheckLowers());
        assertFalse(checker.getCheckUppers());
    }
}