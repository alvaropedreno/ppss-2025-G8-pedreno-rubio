package ppss;

public class ReservaTestable extends Reserva {
    String validLogin;
    String validPassword;

    public void setValidLogin(String validLogin) {
        this.validLogin = validLogin;
    }

    public void setValidPassword(String validPassword) {
        this.validPassword = validPassword;
    }

    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu){
        if (validLogin.equals(login) && validPassword.equals(password)){
            return true;
        }
        return false;
    }
}
