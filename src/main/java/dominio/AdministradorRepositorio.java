package dominio;

import infraestructura.BaseRepositorio;
import infraestructura.ResultadoQuery;

import java.util.Optional;

public class AdministradorRepositorio {

    public AdministradorRepositorio() {}

    public Optional<Administrador> obtenCredenciales(String nick, String pass) {
        Optional<Administrador> resultado;
        ResultadoQuery rq = BaseRepositorio.buscaAdministradorPorCredenciales(nick, pass);

        if (rq == null) {
            resultado = Optional.empty();
        }
        else {
            Administrador administrador = new Administrador(rq.next());
            resultado = Optional.of(administrador);
        }

        return resultado;
    }

}
