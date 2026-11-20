package view;

import controller.GerenciadorIngressos;
import exception.CupomInvalidoException;
import exception.IngressoInvalidoException;
import model.Ingresso;
import model.IngressoInteira;
import model.IngressoMeia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaControle extends JFrame {

    private final GerenciadorIngressos gerenciador;

    private JTextField txtNomeComprador;
    private JTextField txtNomeFilme;
    private JTextField txtValorBase;
    private JComboBox<String> cmbTipoIngresso;
    private JTextField txtCarteirinha;
    private JButton btnComprar;

    private JTable tabelaIngressos;
    private DefaultTableModel modeloTabela;

    private JTextField txtCodigoAcao;
    private JTextField txtCupom;
    private JButton btnValidar;
    private JButton btnCancelar;
    private JButton btnAplicarCupom;
    private JLabel lblReceitaTotal;

    public TelaControle() {
        this.gerenciador = new GerenciadorIngressos();
        configurarJanela();
        inicializarComponentes();
        configurarEventos();
        atualizarTela();
    }

    private void configurarJanela() {
        setTitle("Controle de Ingressos");
        setSize(850, 620);
        setMinimumSize(new Dimension(780, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(243, 245, 248));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(12, 12, 12, 12));
    }

    private void inicializarComponentes() {
        JPanel painelSuperior = new JPanel(new GridBagLayout());
        painelSuperior.setBackground(Color.WHITE);
        painelSuperior.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Novo Ingresso",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13), new Color(31, 35, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.weightx = 0.30;
        gbc.gridx = 0; painelSuperior.add(new JLabel("Nome do Comprador"), gbc);
        gbc.weightx = 0.25;
        gbc.gridx = 1; painelSuperior.add(new JLabel("Filme"), gbc);
        gbc.weightx = 0.22;
        gbc.gridx = 2; painelSuperior.add(new JLabel("Tipo de Ingresso"), gbc);
        gbc.weightx = 0.23;
        gbc.gridx = 3; painelSuperior.add(new JLabel("ID Estudante (Carteirinha)"), gbc);

        gbc.gridy = 1;
        txtNomeComprador = new JTextField();
        txtNomeFilme = new JTextField();
        cmbTipoIngresso = new JComboBox<>(new String[]{"Inteira", "Meia-Entrada"});
        txtCarteirinha = new JTextField();
        txtCarteirinha.setEnabled(false);

        gbc.gridx = 0; painelSuperior.add(txtNomeComprador, gbc);
        gbc.gridx = 1; painelSuperior.add(txtNomeFilme, gbc);
        gbc.gridx = 2; painelSuperior.add(cmbTipoIngresso, gbc);
        gbc.gridx = 3; painelSuperior.add(txtCarteirinha, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; painelSuperior.add(new JLabel("Preço Base (R$)"), gbc);

        gbc.gridy = 3;
        txtValorBase = new JTextField();
        gbc.gridx = 0; painelSuperior.add(txtValorBase, gbc);

        btnComprar = new JButton("Comprar Ingresso");
        estilizarBotao(btnComprar, new Color(29, 99, 184), Color.WHITE);
        gbc.gridx = 3; painelSuperior.add(btnComprar, gbc);

        add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Código", "Filme", "Comprador", "Tipo", "Preço Final", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaIngressos = new JTable(modeloTabela);
        tabelaIngressos.setRowHeight(28);
        tabelaIngressos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabelaIngressos.getTableHeader().setBackground(new Color(234, 238, 242));
        tabelaIngressos.getTableHeader().setForeground(new Color(31, 35, 40));
        tabelaIngressos.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabelaIngressos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaIngressos.setGridColor(new Color(225, 228, 232));

        tabelaIngressos.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());

        tabelaIngressos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaIngressos.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabelaIngressos.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabelaIngressos.getColumnModel().getColumn(3).setPreferredWidth(110);
        tabelaIngressos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaIngressos.getColumnModel().getColumn(5).setPreferredWidth(110);

        JScrollPane scrollTabela = new JScrollPane(tabelaIngressos);
        scrollTabela.getViewport().setBackground(Color.WHITE);
        scrollTabela.setBorder(BorderFactory.createLineBorder(new Color(208, 215, 222)));
        add(scrollTabela, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(0, 10));
        painelInferior.setOpaque(false);

        JPanel painelAcoes = new JPanel(new GridBagLayout());
        painelAcoes.setBackground(Color.WHITE);
        painelAcoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Ações Rápidas",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13), new Color(31, 35, 40)
        ));

        GridBagConstraints gbcAcoes = new GridBagConstraints();
        gbcAcoes.insets = new Insets(5, 6, 5, 6);
        gbcAcoes.fill = GridBagConstraints.HORIZONTAL;

        gbcAcoes.gridy = 0;
        gbcAcoes.gridx = 0; gbcAcoes.weightx = 0.15;
        painelAcoes.add(new JLabel("Código do Ingresso:"), gbcAcoes);

        txtCodigoAcao = new JTextField();
        gbcAcoes.gridx = 1; gbcAcoes.weightx = 0.45;
        painelAcoes.add(txtCodigoAcao, gbcAcoes);

        btnValidar = new JButton("Validar Entrada");
        estilizarBotao(btnValidar, new Color(35, 134, 54), Color.WHITE);
        gbcAcoes.gridx = 2; gbcAcoes.weightx = 0.20;
        painelAcoes.add(btnValidar, gbcAcoes);

        btnCancelar = new JButton("Cancelar Ingresso");
        estilizarBotao(btnCancelar, new Color(201, 59, 59), Color.WHITE);
        gbcAcoes.gridx = 3; gbcAcoes.weightx = 0.20;
        painelAcoes.add(btnCancelar, gbcAcoes);

        gbcAcoes.gridy = 1;
        gbcAcoes.gridx = 0; gbcAcoes.weightx = 0.15;
        painelAcoes.add(new JLabel("Cupom de Desconto:"), gbcAcoes);

        txtCupom = new JTextField();
        gbcAcoes.gridx = 1; gbcAcoes.weightx = 0.45;
        painelAcoes.add(txtCupom, gbcAcoes);

        btnAplicarCupom = new JButton("Aplicar Cupom");
        estilizarBotao(btnAplicarCupom, new Color(29, 99, 184), Color.WHITE);
        gbcAcoes.gridx = 3; gbcAcoes.weightx = 0.20;
        painelAcoes.add(btnAplicarCupom, gbcAcoes);

        painelInferior.add(painelAcoes, BorderLayout.CENTER);

        lblReceitaTotal = new JLabel("Receita Total: R$ 0,00", SwingConstants.CENTER);
        lblReceitaTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblReceitaTotal.setForeground(new Color(31, 35, 40));
        lblReceitaTotal.setBorder(new EmptyBorder(4, 0, 4, 0));
        painelInferior.add(lblReceitaTotal, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);
    }

    private void estilizarBotao(JButton botao, Color corFundo, Color corTexto) {
        botao.setBackground(corFundo);
        botao.setForeground(corTexto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setMargin(new Insets(6, 12, 6, 12));
    }

    private void configurarEventos() {
        cmbTipoIngresso.addActionListener(e -> {
            boolean isMeia = "Meia-Entrada".equals(cmbTipoIngresso.getSelectedItem());
            txtCarteirinha.setEnabled(isMeia);
            if (!isMeia) {
                txtCarteirinha.setText("");
            }
        });

        tabelaIngressos.getSelectionModel().addListSelectionListener(e -> {
            int linhaSelecionada = tabelaIngressos.getSelectedRow();
            if (linhaSelecionada != -1) {
                Object codigo = modeloTabela.getValueAt(linhaSelecionada, 0);
                txtCodigoAcao.setText(codigo.toString());
            }
        });

        btnComprar.addActionListener(e -> realizarCompra());
        btnValidar.addActionListener(e -> executarValidacao());
        btnCancelar.addActionListener(e -> executarCancelamento());
        btnAplicarCupom.addActionListener(e -> executarAplicacaoCupom());
    }

    private void realizarCompra() {
        try {
            String comprador = txtNomeComprador.getText().trim();
            String filme = txtNomeFilme.getText().trim();
            String valorTexto = txtValorBase.getText().trim().replace(",", ".");

            if (comprador.isEmpty() || filme.isEmpty() || valorTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha comprador, filme e preço base.", "Campos Obrigatórios", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double valorBase = Double.parseDouble(valorTexto);
            if (valorBase <= 0) {
                JOptionPane.showMessageDialog(this, "O preço base deve ser maior que zero.", "Valor Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Ingresso novoIngresso;
            if ("Meia-Entrada".equals(cmbTipoIngresso.getSelectedItem())) {
                String carteirinhaTexto = txtCarteirinha.getText().trim();
                if (carteirinhaTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o número da carteirinha para meia-entrada.", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int numeroCarteirinha = Integer.parseInt(carteirinhaTexto);
                novoIngresso = new IngressoMeia(comprador, filme, valorBase, numeroCarteirinha);
            } else {
                novoIngresso = new IngressoInteira(comprador, filme, valorBase);
            }

            gerenciador.cadastrarIngresso(novoIngresso);
            limparCamposCompra();
            atualizarTela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique os valores numéricos (Preço Base ou Carteirinha).", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarValidacao() {
        try {
            int codigo = lerCodigoAcao();
            gerenciador.processarValidacao(codigo);
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Ingresso #" + codigo + " validado com sucesso!", "Entrada Liberada", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um código numérico válido.", "Código Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (IngressoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operação Não Permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void executarCancelamento() {
        try {
            int codigo = lerCodigoAcao();
            gerenciador.solicitarCancelamento(codigo);
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Ingresso #" + codigo + " cancelado.", "Cancelamento Concluído", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um código numérico válido.", "Código Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (IngressoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operação Não Permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void executarAplicacaoCupom() {
        try {
            int codigo = lerCodigoAcao();
            String cupom = txtCupom.getText().trim();
            if (cupom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o código do cupom.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            gerenciador.validarCupom(codigo, cupom);
            txtCupom.setText("");
            atualizarTela();
            JOptionPane.showMessageDialog(this, "Cupom aplicado com sucesso ao ingresso #" + codigo + "!", "Desconto Aplicado", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um código de ingresso numérico válido.", "Código Inválido", JOptionPane.WARNING_MESSAGE);
        } catch (IngressoInvalidoException | CupomInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cupom Não Aplicado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int lerCodigoAcao() throws NumberFormatException {
        String texto = txtCodigoAcao.getText().trim();
        if (texto.isEmpty()) {
            throw new NumberFormatException("Campo vazio");
        }
        return Integer.parseInt(texto);
    }

    private void limparCamposCompra() {
        txtNomeComprador.setText("");
        txtNomeFilme.setText("");
        txtValorBase.setText("");
        txtCarteirinha.setText("");
        cmbTipoIngresso.setSelectedIndex(0);
    }

    private void atualizarTela() {
        modeloTabela.setRowCount(0);
        for (Ingresso i : gerenciador.listaIngressos) {
            String tipo = (i instanceof IngressoMeia)
                    ? "Meia (" + ((IngressoMeia) i).getNumeroCarteirinha() + ")"
                    : "Inteira";

            modeloTabela.addRow(new Object[]{
                    i.getCodigo(),
                    i.getNomeFilme(),
                    i.getNomeComprador(),
                    tipo,
                    String.format("R$ %.2f", i.calcularValorFinal()),
                    i.getStatus().name()
            });
        }

        lblReceitaTotal.setText(String.format("Receita Total: R$ %.2f", gerenciador.calcularFaturamentoTotal()));
    }

    private static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 11));
            label.setOpaque(true);

            String status = value != null ? value.toString() : "";
            switch (status) {
                case "DISPONIVEL" -> {
                    label.setBackground(new Color(35, 134, 54));
                    label.setForeground(Color.WHITE);
                }
                case "UTILIZADO" -> {
                    label.setBackground(new Color(108, 117, 125));
                    label.setForeground(Color.WHITE);
                }
                case "CANCELADO" -> {
                    label.setBackground(new Color(201, 59, 59));
                    label.setForeground(Color.WHITE);
                }
                default -> {
                    label.setBackground(table.getBackground());
                    label.setForeground(table.getForeground());
                }
            }
            return label;
        }
    }
}