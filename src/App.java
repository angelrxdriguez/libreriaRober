import javax.swing.JOptionPane;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<Usuario> usuariosRegistrados = new ArrayList<>();
        ArrayList<Libro> librosDisponibles = new ArrayList<>();
        ArrayList<Reserva> listaReservas = new ArrayList<>();
        Usuario usuarioActual = null;

        // Agregar algunos usuarios y libros disponibles para el ejemplo
        usuariosRegistrados.add(new Usuario("Juan", "Perez", 123456789, "juan@movidas.com","abc123"));
        usuariosRegistrados.add(new Usuario("Maria", "Gomez", 987654321, "maria@locuras.com", "abc123."));
        librosDisponibles.add(new Libro("Libro 1", "Autor 1", 123456));
        librosDisponibles.add(new Libro("Libro 2", "Autor 2", 789012));

        int opcion;

        do {
            opcion = mostrarMenu();

            switch (opcion) {
                case 1:
                    // Iniciar sesión
                    usuarioActual = iniciarSesion(usuariosRegistrados);
                    break;

                case 2:
                    // Registrarse
                    registrarUsuario(usuariosRegistrados);
                    break;

                case 3:
                    // Ver libros disponibles
                    if (usuarioActual != null) {
                        mostrarLibrosDisponibles(librosDisponibles);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesion primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4:
                    // Ver libros pendientes de devolver
                    if (usuarioActual != null) {
                        mostrarLibrosPendientes(usuarioActual);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesion primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 5:
                    // Pedir un libroo
                    if (usuarioActual != null) {
                        pedirLibro(usuarioActual, librosDisponibles, listaReservas);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesion primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 6:
                    // Devolver un libro
                    if (usuarioActual != null) {
                        devolverLibro(usuarioActual, librosDisponibles);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesion primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 7:
                    // Consultar lista de reserva para un libro
                    consultarListaReserva(listaReservas);
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "chao chao", "Salir", JOptionPane.INFORMATION_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "para salir dale a salir", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }

        } while (opcion != 0);
    }

    private static Usuario iniciarSesion(ArrayList<Usuario> usuariosRegistrados) {
        String email = JOptionPane.showInputDialog(null, "Ingrese su email:", "Iniciar sesión", JOptionPane.QUESTION_MESSAGE);
        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Iniciar sesión", JOptionPane.QUESTION_MESSAGE);

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.verificarAutenticacion(email, contrasena)) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return usuario;
            }
        }

        JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    private static void registrarUsuario(ArrayList<Usuario> usuariosRegistrados) {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre:", "Registro de usuario", JOptionPane.QUESTION_MESSAGE);
        String apellido = JOptionPane.showInputDialog(null, "Ingrese su apellido:", "Registro de usuario", JOptionPane.QUESTION_MESSAGE);
        int telefono = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese su número de teléfono:", "Registro de usuario", JOptionPane.QUESTION_MESSAGE));
        String email = JOptionPane.showInputDialog(null, "Ingrese su email:", "Registro de usuario", JOptionPane.QUESTION_MESSAGE);
        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:", "Registro de usuario", JOptionPane.QUESTION_MESSAGE);

        usuariosRegistrados.add(new Usuario(nombre, apellido, telefono, email, contrasena));
        JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarLibrosDisponibles(ArrayList<Libro> librosDisponibles) {
        StringBuilder librosDisponiblesMsg = new StringBuilder("Libros disponibles:\n");
        for (Libro libro : librosDisponibles) {
            librosDisponiblesMsg.append(libro.getTitulo()).append(" - ").append(libro.getAutor()).append(" - ISBN: ").append(libro.getIsbn()).append("\n");
        }
        JOptionPane.showMessageDialog(null, librosDisponiblesMsg.toString(), "Libros Disponibles", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarLibrosPendientes(Usuario usuario) {
        StringBuilder librosPendientesMsg = new StringBuilder("Libros pendientes de devolver para ").append(usuario.getNombre()).append(":\n");
        for (Libro libro : usuario.getLibrosPendientes()) {
            librosPendientesMsg.append(libro.getTitulo()).append(" - ").append(libro.getAutor()).append(" - ISBN: ").append(libro.getIsbn()).append("\n");
        }
        JOptionPane.showMessageDialog(null, librosPendientesMsg.toString(), "Libros Pendientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void pedirLibro(Usuario usuario, ArrayList<Libro> librosDisponibles, ArrayList<Reserva> listaReservas) {
        String tituloPedido = JOptionPane.showInputDialog(null, "Ingrese el titulo del libro que desea pedir:", "Pedir un libro", JOptionPane.QUESTION_MESSAGE);
        if (tituloPedido != null && !tituloPedido.isEmpty()) {
            boolean encontrado = false;
            for (Libro libro : librosDisponibles) {
                if (libro.getTitulo().equalsIgnoreCase(tituloPedido)) {
                    if (libroEstaReservado(libro, listaReservas)) {
                        JOptionPane.showMessageDialog(null, "El libro está reservado por otro usuario.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        usuario.agregarLibroPendiente(libro);
                        librosDisponibles.remove(libro);
                        JOptionPane.showMessageDialog(null, "Libro pedido con correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                int opcion = JOptionPane.showConfirmDialog(null, "El libro no esta disponible. ¿Desea realizar una reserva?");
                if (opcion == JOptionPane.YES_OPTION) {
                    // Verificar si el libro ya está reservado
                    boolean reservado = false;
                    for (Reserva reserva : listaReservas) {
                        if (reserva.getLibro().getTitulo().equalsIgnoreCase(tituloPedido)) {
                            reservado = true;
                            break;
                        }
                    }
                    if (reservado) {
                        JOptionPane.showMessageDialog(null, "El libro ya está reservado por otro usuario.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        listaReservas.add(new Reserva(new Libro(tituloPedido, "", 0), usuario));
                        JOptionPane.showMessageDialog(null, "Libro reservado con exito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un título de libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static boolean libroEstaReservado(Libro libro, ArrayList<Reserva> listaReservas) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getLibro().equals(libro)) {
                return true;
            }
        }
        return false;
    }

    private static void devolverLibro(Usuario usuario, ArrayList<Libro> librosDisponibles) {
        String tituloDevolucion = JOptionPane.showInputDialog(null, "Ingrese el titulo del libro que desea devolver:", "Devolver un libro", JOptionPane.QUESTION_MESSAGE);
        if (tituloDevolucion != null && !tituloDevolucion.isEmpty()) {
            boolean encontrado = false;
            for (Libro libro : usuario.getLibrosPendientes()) {
                if (libro.getTitulo().equalsIgnoreCase(tituloDevolucion)) {
                    librosDisponibles.add(libro);
                    usuario.devolverLibro(libro);
                    JOptionPane.showMessageDialog(null, "Libro devuelto con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "El libro no está en su lista de libros pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un titulo de libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void consultarListaReserva(ArrayList<Reserva> listaReservas) {
        String tituloLibro = JOptionPane.showInputDialog(null, "Ingrese el titulo del libro:", "Consultar lista de reserva", JOptionPane.QUESTION_MESSAGE);
        if (tituloLibro != null && !tituloLibro.isEmpty()) {
            boolean encontrado = false;
            StringBuilder listaReservaMsg = new StringBuilder("Lista de reserva para el libro \"" + tituloLibro + "\":\n");
            for (Reserva reserva : listaReservas) {
                if (reserva.getLibro().getTitulo().equalsIgnoreCase(tituloLibro)) {
                    listaReservaMsg.append(reserva.getUsuario().getNombre()).append("\n");
                    encontrado = true;
                }
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "No hay reservas para este libro.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, listaReservaMsg.toString(), "Lista de Reserva", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar el titulo del libro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private static int mostrarMenu() {
        String[] opciones = {"Salir", "Iniciar Sesion", "Registrarse", "Ver libros disponibles", "Ver libros pendientes de devolver", "Pedir un libro", "Devolver un libro", "Consultar lista de reserva"};
        return JOptionPane.showOptionDialog(null, "Seleccione una opcion:", "**Menu**", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }
}
