import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import javax.swing.filechooser.FileSystemView;

class TextAreaOutputStream extends OutputStream {
    private final JTextArea textArea;
    private final StringBuilder sb = new StringBuilder();

    public TextAreaOutputStream(final JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void flush() {}

    @Override
    public void close() {}

    @Override
    public void write(int b) throws IOException {
        if (b == '\r') return;

        if (b == '\n') {
            final String text = sb.toString() + "\n";
            textArea.append(text);
            sb.setLength(0);
        } else {
            sb.append((char) b);
        }
    }
}

public class Interfaz extends JFrame {
    
    private final JTextArea textAreaCodigo;
    private final JTextArea textAreaMensaje;
    private final JButton botonNuevo;
    private final JButton botonBuscar;
    private final JButton botonGuardar;
    private final JButton botonCompilar;
    private final JButton botonEjecutar;
    private final JLabel labelStatus;

    public Interfaz() {
        super("Interfaz Compilador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textAreaCodigo = new JTextArea();
        textAreaMensaje = new JTextArea();

        textAreaCodigo.setRows(20);
        textAreaCodigo.setColumns(50);
        textAreaMensaje.setRows(20);
        textAreaMensaje.setColumns(50);

        JScrollPane scrollCodigo = new JScrollPane(textAreaCodigo);
        JScrollPane scrollMensaje = new JScrollPane(textAreaMensaje);

        botonNuevo = new JButton("Nuevo");
        botonBuscar = new JButton("Buscar");
        botonGuardar = new JButton("Guardar");
        botonCompilar = new JButton("Compilar");
        botonEjecutar = new JButton("Ejecutar");

        labelStatus = new JLabel("1. Léxico OK    2. Sintaxis OK", SwingConstants.CENTER);

        botonBuscar.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                try {
                    textAreaCodigo.setText(new String(Files.readAllBytes(selectedFile.toPath())));
                } catch (IOException ioException) {
                    textAreaCodigo.setText("Error al leer el archivo: " + ioException.getMessage());
                }
            }
        });

        botonEjecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoFuente = textAreaCodigo.getText();
                File tempFile;
                try {
                    tempFile = File.createTempFile("codigoFuente", ".txt");
                    Files.write(tempFile.toPath(), codigoFuente.getBytes());
                } catch (IOException ioException) {
                    textAreaMensaje.setText("Error al crear el archivo temporal: " + ioException.getMessage());
                    return;
                }
        
                // Guarda la salida estándar original.
                PrintStream originalOut = System.out;
        
                // Redirige la salida estándar al JTextArea de resultados.
                System.setOut(new PrintStream(new TextAreaOutputStream(textAreaMensaje), true));
        
                
                 lexico lex = new lexico(tempFile.getAbsolutePath());  
        
                // Restaurar la salida estándar original.
                System.setOut(originalOut);
        
                
                if (!lex.ErroEncontrado) {
                    textAreaMensaje.append("Análisis léxico y sintáctico terminado correctamente.");
                } else {
                    textAreaMensaje.append("Se encontraron errores durante el análisis.");
                }
                
            }
        });

        JPanel panelBotonesSuperior = new JPanel();
        panelBotonesSuperior.add(botonNuevo);
        panelBotonesSuperior.add(botonBuscar);
        panelBotonesSuperior.add(botonGuardar);
        panelBotonesSuperior.add(botonCompilar);
        panelBotonesSuperior.add(botonEjecutar);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(scrollCodigo, BorderLayout.NORTH);
        panelCentral.add(labelStatus, BorderLayout.CENTER);
        panelCentral.add(scrollMensaje, BorderLayout.SOUTH);

        add(panelBotonesSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz().setVisible(true));
    }
}