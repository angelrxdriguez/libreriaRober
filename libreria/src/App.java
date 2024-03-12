import javax.swing.JOptionPane;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        Usuario[] usuarios = {
            new Usuario("Juan", "Perez", 123456789, "juan@movidas.com"),
            new Usuario("Maria", "Gomez", 987654321, "maria@locuras.com")
        };

        ArrayList<Libro> librosDisponibles = new ArrayList<>();
        Usuario usuarioActual = null;

        // Agregar algunos libros disponibles para el ejemplo
        librosDisponibles.add(new Libro("Libro 1", "Autor 1", 123456));
        librosDisponibles.add(new Libro("Libro 2", "Autor 2", 789012));

        int opcion;

        do {
            opcion = mostrarMenu();

            switch (opcion) {
                case 1:
                    // Acceder a un usuario
                    String nombreUsuario = JOptionPane.showInputDialog(null, "Ingrese su nombre:", "Acceder a un usuario", JOptionPane.QUESTION_MESSAGE);
                    if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                        usuarioActual = buscarUsuario(nombreUsuario, usuarios);
                        if (usuarioActual == null) {
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    // Ver libros disponibles
                    if (usuarioActual != null) {
                        StringBuilder librosDisponiblesMsg = new StringBuilder("Libros disponibles:\n");
                        for (Libro libro : librosDisponibles) {
                            librosDisponiblesMsg.append(libro.getTitulo()).append(" - ").append(libro.getAutor()).append(" - ISBN: ").append(libro.getIsbn()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, librosDisponiblesMsg.toString(), "Libros Disponibles", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe acceder a un usuario primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 3:
                    // Ver libros pendientes de devolver
                    if (usuarioActual != null) {
                        StringBuilder librosPendientesMsg = new StringBuilder("Libros pendientes de devolver para ").append(usuarioActual.getNombre()).append(":\n");
                        for (Libro libro : usuarioActual.getLibrosPendientes()) {
                            librosPendientesMsg.append(libro.getTitulo()).append(" - ").append(libro.getAutor()).append(" - ISBN: ").append(libro.getIsbn()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, librosPendientesMsg.toString(), "Libros Pendientes", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe acceder a un usuario primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 4:
                    // Pedir un libro
                    if (usuarioActual != null) {
                        String tituloPedido = JOptionPane.showInputDialog(null, "Ingrese el título del libro que desea pedir:", "Pedir un libro", JOptionPane.QUESTION_MESSAGE);
                        if (tituloPedido != null && !tituloPedido.isEmpty()) {
                            boolean encontrado = false;
                            for (Libro libro : librosDisponibles) {
                                if (libro.getTitulo().equalsIgnoreCase(tituloPedido)) {
                                    usuarioActual.agregarLibroPendiente(libro);
                                    librosDisponibles.remove(libro);
                                    JOptionPane.showMessageDialog(null, "Libro pedido con exito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                JOptionPane.showMessageDialog(null, "El libro no esta disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe ingresar un titulo de libro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe acceder a un usuario primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                
                case 5:
                    // Devolver un libro
                    if (usuarioActual != null) {
                        String tituloDevolucion = JOptionPane.showInputDialog(null, "Ingrese el titulo del libro que desea devolver:", "Devolver un libro", JOptionPane.QUESTION_MESSAGE);
                        if (tituloDevolucion != null && !tituloDevolucion.isEmpty()) {
                            boolean encontrado = false;
                            for (Libro libro : usuarioActual.getLibrosPendientes()) {
                                if (libro.getTitulo().equalsIgnoreCase(tituloDevolucion)) {
                                    librosDisponibles.add(libro);
                                    usuarioActual.devolverLibro(libro);
                                    JOptionPane.showMessageDialog(null, "Libro devuelto con exito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                JOptionPane.showMessageDialog(null, "El libro no está en su lista de libros pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe ingresar un título de libro.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe acceder a un usuario primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Chao chao", "Salir", JOptionPane.INFORMATION_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Para salir dale a salir", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }

        } while (opcion != 0);
    }

    private static Usuario buscarUsuario(String nombre, Usuario[] usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    private static int mostrarMenu() {
        String[] opciones = {"Salir", "Acceder a un usuario", "Ver libros disponibles", "Pendientes de devolver", "Pedir un libro", "Devolver un libro"};
        return JOptionPane.showOptionDialog(null, "Seleccione una opcion:", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }
}
