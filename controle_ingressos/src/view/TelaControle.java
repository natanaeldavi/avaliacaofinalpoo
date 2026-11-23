package view;

import controller.GerenciadorIngressos;
import exception.CupomInvalidoException;
import exception.FilmeInvalidoException;
import exception.IngressoInvalidoException;
import exception.SessaoInvalidaException;
import model.Filme;
import model.Ingresso;
import model.IngressoInteira;
import model.IngressoMeia;
import model.SessaoCinema;

import javax.print.attribute.standard.JobKOctetsProcessed;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaControle extends JFrame {

    private final GerenciadorIngressos gerenciador;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Aba 1: Filmes
    private JTextField txtNomeFilme;
    private JTextField txtClassificacao;
    private JTextField txtDescricaoFilme;
    private JTextField txtNotaMedia;
    private JTextField txtValorIngressoFilme;
    private JButton btnCadastrarFilme;
    private JTable tabelaFilmes;
    private DefaultTableModel modeloTabelaFilmes;

    // Aba 2: Sessões
    private JComboBox<Filme> cmbFilmeSessao;
    private JTextField txtDataHoraSessao;
    private JTextField txtSalaSessao;
    private JTextField txtVagasSessao;
    private JButton btnCadastrarSessao;
    private JTable tabelaSessoes;
    private DefaultTableModel modeloTabelaSessoes;

    // Aba 3: Bilheteria e Portaria
    private JComboBox<SessaoCinema> cmbSessaoIngresso;
    private JTextField txtNomeComprador;
    private JComboBox<String> cmbTipoIngresso;
    private JTextField txtCarteirinha;
    private JButton btnComprar;
    private JTable tabelaIngressos;
    private DefaultTableModel modeloTabelaIngressos;

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
        setTitle("Sistema de Gestão de Cinema - Bilheteria e Sessões");
        setSize(920, 660);
        setMinimumSize(new Dimension(840, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(243, 245, 248));
    }

    private void inicializarComponentes() {
        JTabbedPane abas = new JTabbedPane();
        abas.setFont(new Font("SansSerif", Font.BOLD, 13));
        abas.setBorder(new EmptyBorder(8, 8, 8, 8));

        abas.addTab("1. Filmes", criarPainelFilmes());
        abas.addTab("2. Sessões", criarPainelSessoes());
        abas.addTab("3. Bilheteria & Portaria", criarPainelBilheteria());

        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarPainelFilmes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(243, 245, 248));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Cadastrar Novo Filme",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13), new Color(31, 35, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0.35; form.add(new JLabel("Nome do Filme"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.20; form.add(new JLabel("Classificação"), gbc);
        gbc.gridx = 2; gbc.weightx = 0.20; form.add(new JLabel("Nota Média (0-10)"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25; form.add(new JLabel("Valor do Ingresso (R$)"), gbc);

        gbc.gridy = 1;
        txtNomeFilme = new JTextField();
        txtClassificacao = new JTextField();
        txtNotaMedia = new JTextField();
        txtValorIngressoFilme = new JTextField();

        gbc.gridx = 0; form.add(txtNomeFilme, gbc);
        gbc.gridx = 1; form.add(txtClassificacao, gbc);
        gbc.gridx = 2; form.add(txtNotaMedia, gbc);
        gbc.gridx = 3; form.add(txtValorIngressoFilme, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 3; form.add(new JLabel("Descrição / Sinopse"), gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 3;
        txtDescricaoFilme = new JTextField();
        gbc.gridx = 0; gbc.gridwidth = 3; form.add(txtDescricaoFilme, gbc);
        gbc.gridwidth = 1;

        btnCadastrarFilme = new JButton("Cadastrar Filme");
        estilizarBotao(btnCadastrarFilme, new Color(29, 99, 184), Color.WHITE);
        gbc.gridx = 3; form.add(btnCadastrarFilme, gbc);

        painel.add(form, BorderLayout.NORTH);

        String[] colunas = {"ID", "Filme", "Classificação", "Nota", "Valor Base", "Descrição", "Faturamento"};
        modeloTabelaFilmes = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFilmes = new JTable(modeloTabelaFilmes);
        configurarEstiloTabela(tabelaFilmes);
        tabelaFilmes.getColumnModel().getColumn(0).setPreferredWidth(45);
        tabelaFilmes.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabelaFilmes.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabelaFilmes.getColumnModel().getColumn(3).setPreferredWidth(60);
        tabelaFilmes.getColumnModel().getColumn(4).setPreferredWidth(95);
        tabelaFilmes.getColumnModel().getColumn(5).setPreferredWidth(230);
        tabelaFilmes.getColumnModel().getColumn(6).setPreferredWidth(110);

        JScrollPane scroll = new JScrollPane(tabelaFilmes);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(208, 215, 222)));
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelSessoes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(243, 245, 248));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Agendar Nova Sessão",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13), new Color(31, 35, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0.35; form.add(new JLabel("Filme em Cartaz"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25; form.add(new JLabel("Data e Hora (dd/MM/yyyy HH:mm)"), gbc);
        gbc.gridx = 2; gbc.weightx = 0.20; form.add(new JLabel("Sala"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.20; form.add(new JLabel("Total de Vagas"), gbc);

        gbc.gridy = 1;
        cmbFilmeSessao = new JComboBox<>();
        txtDataHoraSessao = new JTextField(LocalDateTime.now().plusHours(2).format(formatter));
        txtSalaSessao = new JTextField();
        txtVagasSessao = new JTextField();

        gbc.gridx = 0; form.add(cmbFilmeSessao, gbc);
        gbc.gridx = 1; form.add(txtDataHoraSessao, gbc);
        gbc.gridx = 2; form.add(txtSalaSessao, gbc);
        gbc.gridx = 3; form.add(txtVagasSessao, gbc);

        gbc.gridy = 2;
        btnCadastrarSessao = new JButton("Criar Sessão");
        estilizarBotao(btnCadastrarSessao, new Color(29, 99, 184), Color.WHITE);
        gbc.gridx = 3; form.add(btnCadastrarSessao, gbc);

        painel.add(form, BorderLayout.NORTH);

        String[] colunas = {"ID", "Filme", "Data / Hora", "Sala", "Vagas Disponíveis", "Lotação Total"};
        modeloTabelaSessoes = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaSessoes = new JTable(modeloTabelaSessoes);
        configurarEstiloTabela(tabelaSessoes);
        tabelaSessoes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaSessoes.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabelaSessoes.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaSessoes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelaSessoes.getColumnModel().getColumn(4).setPreferredWidth(130);
        tabelaSessoes.getColumnModel().getColumn(5).setPreferredWidth(110);

        JScrollPane scroll = new JScrollPane(tabelaSessoes);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(208, 215, 222)));
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelBilheteria() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(new Color(243, 245, 248));
        painel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelSuperior = new JPanel(new GridBagLayout());
        painelSuperior.setBackground(Color.WHITE);
        painelSuperior.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Emitir Novo Ingresso",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 13), new Color(31, 35, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0.40; painelSuperior.add(new JLabel("Selecione a Sessão"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25; painelSuperior.add(new JLabel("Nome do Comprador"), gbc);
        gbc.gridx = 2; gbc.weightx = 0.17; painelSuperior.add(new JLabel("Tipo de Ingresso"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.18; painelSuperior.add(new JLabel("ID Estudante"), gbc);

        gbc.gridy = 1;
        cmbSessaoIngresso = new JComboBox<>();
        cmbSessaoIngresso.setRenderer(new SessaoComboRenderer());
        txtNomeComprador = new JTextField();
        cmbTipoIngresso = new JComboBox<>(new String[]{"Inteira", "Meia-Entrada"});
        txtCarteirinha = new JTextField();
        txtCarteirinha.setEnabled(false);

        gbc.gridx = 0; painelSuperior.add(cmbSessaoIngresso, gbc);
        gbc.gridx = 1; painelSuperior.add(txtNomeComprador, gbc);
        gbc.gridx = 2; painelSuperior.add(cmbTipoIngresso, gbc);
        gbc.gridx = 3; painelSuperior.add(txtCarteirinha, gbc);

        gbc.gridy = 2;
        btnComprar = new JButton("Comprar Ingresso");
        estilizarBotao(btnComprar, new Color(29, 99, 184), Color.WHITE);
        gbc.gridx = 3; painelSuperior.add(btnComprar, gbc);

        painel.add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"Código", "Sessão / Filme", "Comprador", "Tipo", "Preço Final", "Status"};
        modeloTabelaIngressos = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaIngressos = new JTable(modeloTabelaIngressos);
        configurarEstiloTabela(tabelaIngressos);
        tabelaIngressos.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());

        tabelaIngressos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaIngressos.getColumnModel().getColumn(1).setPreferredWidth(230);
        tabelaIngressos.getColumnModel().getColumn(2).setPreferredWidth(170);
        tabelaIngressos.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabelaIngressos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaIngressos.getColumnModel().getColumn(5).setPreferredWidth(110);

        JScrollPane scrollTabela = new JScrollPane(tabelaIngressos);
        scrollTabela.getViewport().setBackground(Color.WHITE);
        scrollTabela.setBorder(BorderFactory.createLineBorder(new Color(208, 215, 222)));
        painel.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(0, 8));
        painelInferior.setOpaque(false);

        JPanel painelAcoes = new JPanel(new GridBagLayout());
        painelAcoes.setBackground(Color.WHITE);
        painelAcoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(208, 215, 222)), "Ações Rápidas (Portaria e Cupons)",
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
        lblReceitaTotal.setBorder(new EmptyBorder(4, 0, 2, 0));
        painelInferior.add(lblReceitaTotal, BorderLayout.SOUTH);

        painel.add(painelInferior, BorderLayout.SOUTH);
        return painel;
    }

    private void configurarEstiloTabela(JTable tabela) {
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabela.getTableHeader().setBackground(new Color(234, 238, 242));
        tabela.getTableHeader().setForeground(new Color(31, 35, 40));
        tabela.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(225, 228, 232));
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
        btnCadastrarFilme.addActionListener(e -> realizarCadastroFilme());
        btnCadastrarSessao.addActionListener(e -> realizarCadastroSessao());

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
                Object codigo = modeloTabelaIngressos.getValueAt(linhaSelecionada, 0);
                txtCodigoAcao.setText(codigo.toString());
            }
        });

        btnComprar.addActionListener(e -> realizarCompra());
        btnValidar.addActionListener(e -> executarValidacao());
        btnCancelar.addActionListener(e -> executarCancelamento());
        btnAplicarCupom.addActionListener(e -> executarAplicacaoCupom());
    }

    // =========================================================================
    // IMPLEMENTAÇÃO DOS MÉTODOS DE LÓGICA E INTEGRAÇÃO
    // =========================================================================

    private void realizarCadastroFilme() {

        String nomeFilme = txtNomeFilme.getText().trim();
        String classificacaoFilme = txtClassificacao.getText().trim();
        String descricaoFilme = txtDescricaoFilme.getText().trim();
        try {
            Double notaMedia = Double.parseDouble(txtNotaMedia.getText().trim().replace(",", "."));
            Double valorIngressoFilme = Double.parseDouble(txtValorIngressoFilme.getText().trim().replace(",", "."));
            if (nomeFilme.isEmpty() ||
                    classificacaoFilme.isEmpty() ||
                    descricaoFilme.isEmpty() ||
                    notaMedia.isNaN() ||
                    valorIngressoFilme.isNaN()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos para prosseguir!");
                return;
            }

            if (valorIngressoFilme <= 0 ) {
                JOptionPane.showMessageDialog(this, "Valor do ingresso incorreto!");
                return;
            }
            if (notaMedia > 10) {
                JOptionPane.showMessageDialog(this, "Nota acima do valor maximo!");
                return;
            }
            if (notaMedia < 0) {
                JOptionPane.showMessageDialog(this, "Nota abaixo do valor minimo!");
                return;
            }

            Filme filme = new Filme(nomeFilme, classificacaoFilme, descricaoFilme, notaMedia, valorIngressoFilme);

            gerenciador.cadastrarFilme(filme);
            txtNomeFilme.setText("");
            txtClassificacao.setText("");
            txtDescricaoFilme.setText("");
            txtNotaMedia.setText("");
            txtValorIngressoFilme.setText("");

            atualizarTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe valores numéricos válidos para nota e valor!");
        }


    }

    private void realizarCadastroSessao() {
        Filme filme = (Filme) cmbFilmeSessao.getSelectedItem();
        if (filme == null) {
            JOptionPane.showMessageDialog(this, "Selecione um filme para continuar!");
            return;
        }

        try {
            String dataHoraString = txtDataHoraSessao.getText().trim();
            String salaSessao = txtSalaSessao.getText().trim();
            int vagasSessao = Integer.parseInt(txtVagasSessao.getText().trim());

            if (dataHoraString.isEmpty() || salaSessao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos para prosseguir!");
                return;
            }

            if (vagasSessao <= 0) {
                JOptionPane.showMessageDialog(this, "Número de vagas da sessão inválido/incorreto! Tente novamente");
                return;
            }

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraString, formatter);
            SessaoCinema sessao = new SessaoCinema(dataHora, filme, vagasSessao, salaSessao);
            gerenciador.cadastrarSessao(sessao);

            txtDataHoraSessao.setText("");
            txtSalaSessao.setText("");
            txtVagasSessao.setText("");

            cmbFilmeSessao.setSelectedIndex(0);
            atualizarTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe valores numéricos válidos para nota e valor!");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data em branco ou fora do padrão!");
        }


    }

    private void realizarCompra() {
        SessaoCinema sessao = (SessaoCinema) cmbSessaoIngresso.getSelectedItem();
        if (sessao == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão para continuar!");
            return;
        }
        String comprador = txtNomeComprador.getText().trim();
        String tipo = (String) cmbTipoIngresso.getSelectedItem();

        if (comprador.isEmpty() || tipo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos para prosseguir!");
            return;
        }

        try {
            if (tipo.equals("Meia-Entrada")) {
                int carteirinha = Integer.parseInt(txtCarteirinha.getText().trim());
                Ingresso ingresso = new IngressoMeia(sessao, comprador, carteirinha);
                gerenciador.cadastrarIngresso(ingresso);
            } else {
                Ingresso ingresso = new IngressoInteira(sessao, comprador);
                gerenciador.cadastrarIngresso(ingresso);
            }

            txtNomeComprador.setText("");
            txtCarteirinha.setText("");
            cmbTipoIngresso.setSelectedIndex(0);
            atualizarTela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido para a carteirinha!");
        } catch (SessaoInvalidaException e) {
            throw new RuntimeException(e);
        }
    }

    private void executarValidacao() {
        try {
            int codigoIngresso = Integer.parseInt(txtCodigoAcao.getText().trim());
            gerenciador.processarValidacao(codigoIngresso);
            JOptionPane.showMessageDialog(this, "Ingresso válidado com sucesso!");

            atualizarTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um numero valido para o codigo de ingresso!");
        } catch (IngressoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void executarCancelamento() {
        try {
            int codigoIngresso = Integer.parseInt(txtCodigoAcao.getText().trim());
            gerenciador.solicitarCancelamento(codigoIngresso);
            JOptionPane.showMessageDialog(this, "Ingresso cancelado com sucesso!");

            atualizarTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um numero valido para o codigo de ingresso!");
        } catch (IngressoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void executarAplicacaoCupom() {
        try {
            int codigoIngresso = Integer.parseInt(txtCodigoAcao.getText().trim());
            String cupom = txtCupom.getText().trim();
            if (cupom.isEmpty()) { JOptionPane.showMessageDialog(this, "Campo de cupom em branco!"); return; }
            gerenciador.validarCupom(codigoIngresso, cupom);
            JOptionPane.showMessageDialog(this, "Cupom aplicado com sucesso!");

            txtCodigoAcao.setText("");
            txtCupom.setText("");
            atualizarTela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um numero valido para o codigo de ingresso!");
        } catch (IngressoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (CupomInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

    }

    private void atualizarTela() {
        modeloTabelaFilmes.setRowCount(0);
        cmbFilmeSessao.removeAllItems();
          gerenciador.listaFilmes.forEach(f -> {
            cmbFilmeSessao.addItem(f);
              try {
                  modeloTabelaFilmes.addRow(new Object[]{
                          f.getIdFilme(),
                          f.getNomeFilme(),
                          f.getClassificacaoEtaria(),
                          String.format("%.1f",f.getNotaMedia()),
                          String.format("R$ %.2f", f.getValorIngresso()),
                          f.getDescricaoFilme(),
                          String.format("R$ %.2f", gerenciador.calcularFaturamentoTotalFilme(f.getIdFilme()))
                  });
              } catch (FilmeInvalidoException ex) {
                  JOptionPane.showMessageDialog(this, ex.getMessage());
              }
          });

        modeloTabelaSessoes.setRowCount(0);
        cmbSessaoIngresso.removeAllItems();
        gerenciador.listaSessoes.forEach(s -> {
            cmbSessaoIngresso.addItem(s);
            modeloTabelaSessoes.addRow(new Object[]{
                    s.getIdSessao(),
                    s.getFilmeSessao().getNomeFilme(),
                    s.getDataHoraSessao().format(formatter),
                    s.getSalaSessao(),
                    s.getVagasDisponiveis(),
                    s.getVagasSessao()
            });
        });

        modeloTabelaIngressos.setRowCount(0);
        gerenciador.listaIngressos.forEach(i -> {
            String tipo = (i instanceof IngressoMeia meia) ? "Meia (" + meia.getNumeroCarteirinha() + ")" : "Inteira";
            modeloTabelaIngressos.addRow(new Object[]{
                    i.getCodigo(),
                    i.getSessao().getFilmeSessao().getNomeFilme(),
                    i.getNomeComprador(),
                    tipo,
                    String.format("R$ %.2f", i.calcularValorFinal()),
                    i.getStatus().name()
            });
        });

        lblReceitaTotal.setText(String.format("Receita Total: R$ %.2f", gerenciador.calcularFaturamentoTotal()));
    }

    private class SessaoComboRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof SessaoCinema s) {
                label.setText(String.format("Sessão #%d - %s | %s | Sala %s | R$ %.2f (%d vagas)",
                        s.getIdSessao(),
                        s.getFilmeSessao().getNomeFilme(),
                        s.getDataHoraSessao().format(formatter),
                        s.getSalaSessao(),
                        s.getFilmeSessao().getValorIngresso(),
                        s.getVagasDisponiveis()));
            }
            return label;
        }
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