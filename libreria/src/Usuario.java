import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String apellido;
    private int telefono;
    private String email;
    private ArrayList<Libro> librosPendientes;

    public Usuario(String nombre, String apellido, int telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.librosPendientes = new ArrayList<>();
    }

    public ArrayList<Libro> getLibrosPendientes() {
        return librosPendientes;
    }

    public void agregarLibroPendiente(Libro libro) {
        librosPendientes.add(libro);
    }

    public void devolverLibro(Libro libro) {
        librosPendientes.remove(libro);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
