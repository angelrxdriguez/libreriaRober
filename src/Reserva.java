public class Reserva {
    private Libro libro;
    private Usuario usuario;

    public Reserva(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}