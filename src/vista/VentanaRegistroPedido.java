package vista;

import controlador.GestorPedidos;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// Clase que representa la ventana de registro de pedidos


public class VentanaRegistroPedido  extends JFrame{

    // Controlador para gestionar los pedidos
    private GestorPedidos gestorPedidos;

    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JTextField txtPeso;

    private JComboBox<String> cmbTipo;

    private JButton btnGuardar;

    private JPanel panelFormulario;
    private JPanel panelAdicional;

    // Constructor
    public VentanaRegistroPedido(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;

        // Configuración de la ventana
        setTitle("SpeedFast - Registrar pedido");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Crear componentes
        txtId = new JTextField();
        txtDireccion = new JTextField();
        txtDistancia = new JTextField();
        txtPeso = new JTextField(10);

        cmbTipo = new JComboBox<>(new String[]{
                "Comida", "Encomienda", "Express"
        });

        btnGuardar = new JButton("Guardar pedido");

        // Panel principal del formulario
        panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        panelFormulario.add(new JLabel("ID del pedido:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Distancia (km):"));
        panelFormulario.add(txtDistancia);

        panelFormulario.add(new JLabel("Tipo de pedido:"));
        panelFormulario.add(cmbTipo);

        // Panel para los campos adicionales
        panelAdicional = new JPanel(new GridLayout(0, 2, 10, 10));

        // Panel para organizar los formularios
        JPanel panelSuperior = new JPanel(new BorderLayout(10,10));

        panelSuperior.add(panelFormulario, BorderLayout.NORTH);
        panelSuperior.add(panelAdicional, BorderLayout.CENTER);

        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(btnGuardar, BorderLayout.SOUTH);

        // Mostrar campos según el tipo de pedido
        actualizarCampos();

        // Eventos de los componentes
        cmbTipo.addActionListener(e -> actualizarCampos());
        btnGuardar.addActionListener(e -> guardarPedido());
    }

        // Método para actualizar los campos según el tipo de pedido
        private void actualizarCampos() {

            panelAdicional.removeAll();

            String tipo = (String) cmbTipo.getSelectedItem();

            if (tipo.equals("Encomienda")) {
                panelAdicional.add(new JLabel("Peso (kg):"));
                panelAdicional.add(txtPeso);
            }
            panelAdicional.revalidate();
            panelAdicional.repaint();
        }

        // Método para guardar un pedido
        private void guardarPedido(){

            // Validar campos obligatorios
            if(txtId.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty() || txtDistancia.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(this, "Debe completar todos los campos obligatorios");
                return;
            }

            try {
                // Obtener datos del formulario
                int id = Integer.parseInt(txtId.getText().trim());
                String direccion = txtDireccion.getText().trim();
                double distancia = Double.parseDouble(txtDistancia.getText().trim());

                // Validar valores numéricos
                if (id <= 0 || !Double.isFinite(distancia) || distancia <= 0){
                    JOptionPane.showMessageDialog(this, "El ID y la Distancia deben ser mayores a cero.");
                    return;
                }

                // Verificar que el ID no esté registrado
                if(gestorPedidos.buscarPedido(id) != null){
                    JOptionPane.showMessageDialog(this, "Ya existe un pedido con ese ID.");
                    return;
                }
                Pedido pedido;
                String tipo = (String) cmbTipo.getSelectedItem();

                // Crrear el pedido según el tipo seleccionado
                if(tipo.equals("Comida")){

                    pedido = new PedidoComida(id, direccion, distancia);
                } else if (tipo.equals("Encomienda")){
                    // Validar peso
                    double peso = Double.parseDouble(txtPeso.getText().trim());

                    if (!Double.isFinite(peso) || peso <= 0){
                        JOptionPane.showMessageDialog(this, "El Peso debe ser mayor a cero.");
                        return;
                    }

                    pedido = new PedidoEncomienda(id, direccion, distancia, peso);
                }else{
                    pedido = new PedidoExpress(id,direccion, distancia);
                }

                // Registrar el pedido en el controlador
                if(gestorPedidos.registrarPedido(pedido)){
                    JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(this,"No se pudo registrar el pedido.");
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos");
            }
        }

}
