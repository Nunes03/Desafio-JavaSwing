package br.com.lucasnunes.domain;
import java.sql.*;
import br.com.lucasnunes.dal.ModuloDeConexao;
import java.awt.CardLayout;
import java.awt.Color;
import net.proteanit.sql.DbUtils;

public class TelaPrincipal extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public boolean existeSala = false, existeEspacoCafe = false;
    public int tamanhoSalaEvento, tamanhoEspacoCafe, quantidadeDePessoasSala, quantidadeDePessoasEspaco;
    public int variavelCafe, variavelSala;
    
    /*----------------------------------Inserir Sala Evento----------------------------------------------------------------*/
    public void insertSalasDoEvento(){
        String sql = "insert into sala_do_evento (nome, lotacao) values (?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeSalaDoEvento.getText());
            pst.setInt(2, Integer.parseInt(txtLotacaoSalaDoEvento.getText()));
            
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*--------------------------------------Inserir Espaco Café------------------------------------------------------------*/
    public void insertEspacosCafe(){
        String sql = "insert into espaco_cafe (nome, lotacao) values (?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeEspacoCafe.getText());
            pst.setInt(2, Integer.parseInt(txtLotacaoEspacoCafe.getText()));
            
            pst.executeUpdate();
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Inserir Pessoa----------------------------------------------------------*/
    public void insertPessoas(){
        String sql = "insert into pessoa (nome, sobrenome, espaco_cafe_id, sala_do_evento_id) values (?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomePessoa.getText());
            pst.setString(2, txtSobrenomePessoa.getText());
            pst.setInt(3, variavelCafe);
            pst.setInt(4, variavelSala);
            
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*---------------------------------------Veriifca se a sala existe-----------------------------------------------------------*/
    public void existeSala(){
        String sql = "select nome from sala_do_evento where nome = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtSalaPessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                existeSala = true;
            } else {
                existeSala = false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Verifica se o Espaço Café existe----------------------------------------------------------*/
    public void existeEspaco(){
        String sql = "select nome from espaco_cafe where nome = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEspacoCafePessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                existeEspacoCafe = true;
            } else {
                existeEspacoCafe = false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Tamanho da Sala----------------------------------------------------------*/
    public void tamanhoSala(){
        String sql = "select lotacao from sala_do_evento where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtSalaPessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                tamanhoSalaEvento = rs.getInt("lotacao");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Tamanho do Espaço Café----------------------------------------------------------*/
    public void tamanhoEspaco(){
        String sql = "select lotacao from espaco_cafe where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEspacoCafePessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                tamanhoEspacoCafe = rs.getInt("lotacao");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Verifica se a Sala está Cheia----------------------------------------------------------*/
    public void salaCheia(){
        String sql = "select count(pessoa.id) from pessoa inner join sala_do_evento on pessoa.sala_do_evento_id = sala_do_evento.id where sala_do_evento.nome = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtSalaPessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                quantidadeDePessoasSala = rs.getInt("count(pessoa.id)");
            } 
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*----------------------------------------Verifica se o Espaco Café está cheio----------------------------------------------------------*/
    public void espacoCafeCheio(){
        String sql = "select count(pessoa.id) from pessoa inner join espaco_cafe on pessoa.espaco_cafe_id = espaco_cafe.id where espaco_cafe.nome = ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEspacoCafePessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                quantidadeDePessoasEspaco = rs.getInt("count(pessoa.id)");
            } 
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*---------------------------------------Verifica o id da Sala-----------------------------------------------------------*/
    public void verificaSala(){
        String sql = "select id from sala_do_evento where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtSalaPessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                variavelSala = rs.getInt("sala_do_evento.id");
            } 
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*---------------------------------------------Verifica o id do Espaço Café-----------------------------------------------------*/
    public void verificaEspaco(){
        String sql = "select id from espaco_cafe where nome = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtEspacoCafePessoa.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                variavelCafe = rs.getInt("espaco_cafe.id");
            } 
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /*--------------------------------------------------------------------------------------------------*/
    
    /*-------------------------------------Consultar Sala pelo nome------------------------------------------*/
    public void consultarSalaDoEvento(){
        String sql = "select pessoa.nome 'Nome', pessoa.sobrenome 'Sobrenome', sala_do_evento.nome 'Sala' from pessoa inner join sala_do_evento on pessoa.sala_do_evento_id = sala_do_evento.id where sala_do_evento.nome like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%"+txtNomeSalaDoEventoConsultar.getText()+"%");
            
            rs = pst.executeQuery();
            
            tbConsultas.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------Consultar Sala pelo nome------------------------------------------*/
    public void consultarEspacoCafe(){
        String sql = "select pessoa.nome 'Nome', pessoa.sobrenome 'Sobrenome', espaco_cafe.nome 'Espaço Café' from pessoa inner join espaco_cafe on pessoa.espaco_cafe_id = espaco_cafe.id where espaco_cafe.nome like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%"+txtNomeEspacoCafeConsultar.getText()+"%");
            
            rs = pst.executeQuery();
            
            tbConsultas.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------Consultar Sala pelo nome------------------------------------------*/
    public void consultarPessoa(){
        String sql = "select pessoa.nome 'Nome', pessoa.sobrenome 'Sobrenome', espaco_cafe.nome 'Espaço Café', sala_do_evento.nome 'Sala' from pessoa inner\n" +
                        "join espaco_cafe on pessoa.espaco_cafe_id = espaco_cafe.id inner\n" +
                        "join sala_do_evento on pessoa.sala_do_evento_id = sala_do_evento.id\n" +
                        " where pessoa.nome like ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%"+txtNomeEspacoCafeConsultar.getText()+"%");
            
            rs = pst.executeQuery();
            
            tbConsultas.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*-------------------------------------------------------------------------------*/
    
    public TelaPrincipal() {
        initComponents();
        CardLayout cl = (CardLayout)panelPai.getLayout();
        cl.show(panelPai, "cardHome");
        btnHome.setVisible(false);
        btnCadastro.setVisible(false);
        btnConsulta.setVisible(false);
        
        conexao = ModuloDeConexao.conector();
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnCadastro = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        panelPai = new javax.swing.JPanel();
        panelCadastro = new javax.swing.JPanel();
        panelCadastroSalasDoEvento = new javax.swing.JPanel();
        txtNomeSalaDoEvento = new javax.swing.JTextField();
        txtLotacaoSalaDoEvento = new javax.swing.JTextField();
        btnSalvarSalaDoEvento = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblMensagemErroSalaDoEvento = new javax.swing.JLabel();
        panelCadastroEspacoCafe = new javax.swing.JPanel();
        txtNomeEspacoCafe = new javax.swing.JTextField();
        txtLotacaoEspacoCafe = new javax.swing.JTextField();
        btnSalvarEspacosCafe = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblMensagemErroEspacosCafe = new javax.swing.JLabel();
        panelCadastroPessoa = new javax.swing.JPanel();
        txtNomePessoa = new javax.swing.JTextField();
        txtSobrenomePessoa = new javax.swing.JTextField();
        btnSalvarPessoa = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblMensagemErroPessoa = new javax.swing.JLabel();
        txtSalaPessoa = new javax.swing.JTextField();
        txtEspacoCafePessoa = new javax.swing.JTextField();
        panelConsulta = new javax.swing.JPanel();
        panelConsultaSalasDoEvento = new javax.swing.JPanel();
        txtNomeSalaDoEventoConsultar = new javax.swing.JTextField();
        btnConsultarSalaDoEvento = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblMensagemErroSalaDoEventoConsultar = new javax.swing.JLabel();
        panelConsultaEspacoCafe = new javax.swing.JPanel();
        txtNomeEspacoCafeConsultar = new javax.swing.JTextField();
        btnConsultarEspacoCafe = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblMensagemErroEspacoCafeConsultar = new javax.swing.JLabel();
        panelConsultaPessoa = new javax.swing.JPanel();
        txtNomePessoaConsultar = new javax.swing.JTextField();
        btnConsultarPessoa = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblMensagemErroPessoaConsultar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbConsultas = new javax.swing.JTable();
        panelHome = new javax.swing.JPanel();
        lblMsg1 = new javax.swing.JLabel();
        lblMsg2 = new javax.swing.JLabel();
        lblMsg3 = new javax.swing.JLabel();
        lblMsg4 = new javax.swing.JLabel();
        lblMsg5 = new javax.swing.JLabel();
        lblMsg6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblHamburguer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela Principal");
        setMinimumSize(new java.awt.Dimension(950, 785));
        setPreferredSize(new java.awt.Dimension(950, 785));

        panelPrincipal.setBackground(new java.awt.Color(42, 169, 224));

        btnCadastro.setBackground(new java.awt.Color(42, 169, 224));
        btnCadastro.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        btnCadastro.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastro.setText("• Cadastro");
        btnCadastro.setBorderPainted(false);
        btnCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastro.setFocusPainted(false);
        btnCadastro.setFocusable(false);
        btnCadastro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCadastroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCadastroMouseExited(evt);
            }
        });
        btnCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroActionPerformed(evt);
            }
        });

        btnConsulta.setBackground(new java.awt.Color(42, 169, 224));
        btnConsulta.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        btnConsulta.setForeground(new java.awt.Color(255, 255, 255));
        btnConsulta.setText("• Consulta");
        btnConsulta.setBorderPainted(false);
        btnConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsulta.setFocusPainted(false);
        btnConsulta.setFocusable(false);
        btnConsulta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConsultaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConsultaMouseExited(evt);
            }
        });
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        btnHome.setBackground(new java.awt.Color(42, 169, 224));
        btnHome.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setText("• Home");
        btnHome.setBorderPainted(false);
        btnHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHome.setFocusPainted(false);
        btnHome.setFocusable(false);
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btnCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPai.setLayout(new java.awt.CardLayout());

        panelCadastro.setBackground(new java.awt.Color(255, 255, 255));
        panelCadastro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        panelCadastroSalasDoEvento.setBackground(new java.awt.Color(255, 255, 255));
        panelCadastroSalasDoEvento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomeSalaDoEvento.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomeSalaDoEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        txtLotacaoSalaDoEvento.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtLotacaoSalaDoEvento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Lotação", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        btnSalvarSalaDoEvento.setBackground(new java.awt.Color(42, 169, 224));
        btnSalvarSalaDoEvento.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnSalvarSalaDoEvento.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarSalaDoEvento.setText("Salvar");
        btnSalvarSalaDoEvento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnSalvarSalaDoEvento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvarSalaDoEvento.setFocusPainted(false);
        btnSalvarSalaDoEvento.setFocusable(false);
        btnSalvarSalaDoEvento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarSalaDoEventoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarSalaDoEventoMouseExited(evt);
            }
        });
        btnSalvarSalaDoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarSalaDoEventoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel4.setText("Salas do Evento");

        lblMensagemErroSalaDoEvento.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroSalaDoEvento.setText("Para salvar prencha os campos corretamente.");

        javax.swing.GroupLayout panelCadastroSalasDoEventoLayout = new javax.swing.GroupLayout(panelCadastroSalasDoEvento);
        panelCadastroSalasDoEvento.setLayout(panelCadastroSalasDoEventoLayout);
        panelCadastroSalasDoEventoLayout.setHorizontalGroup(
            panelCadastroSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroSalasDoEventoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroSalaDoEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvarSalaDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtLotacaoSalaDoEvento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeSalaDoEvento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCadastroSalasDoEventoLayout.setVerticalGroup(
            panelCadastroSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroSalasDoEventoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addComponent(txtNomeSalaDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtLotacaoSalaDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCadastroSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvarSalaDoEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(panelCadastroSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroSalaDoEvento)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelCadastroEspacoCafe.setBackground(new java.awt.Color(255, 255, 255));
        panelCadastroEspacoCafe.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomeEspacoCafe.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomeEspacoCafe.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        txtLotacaoEspacoCafe.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtLotacaoEspacoCafe.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Lotação", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        btnSalvarEspacosCafe.setBackground(new java.awt.Color(42, 169, 224));
        btnSalvarEspacosCafe.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnSalvarEspacosCafe.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarEspacosCafe.setText("Salvar");
        btnSalvarEspacosCafe.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnSalvarEspacosCafe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvarEspacosCafe.setFocusPainted(false);
        btnSalvarEspacosCafe.setFocusable(false);
        btnSalvarEspacosCafe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarEspacosCafeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarEspacosCafeMouseExited(evt);
            }
        });
        btnSalvarEspacosCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarEspacosCafeActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel5.setText("Espaços Café");

        lblMensagemErroEspacosCafe.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroEspacosCafe.setText("Para salvar prencha os campos corretamente.");

        javax.swing.GroupLayout panelCadastroEspacoCafeLayout = new javax.swing.GroupLayout(panelCadastroEspacoCafe);
        panelCadastroEspacoCafe.setLayout(panelCadastroEspacoCafeLayout);
        panelCadastroEspacoCafeLayout.setHorizontalGroup(
            panelCadastroEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroEspacoCafeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroEspacoCafeLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroEspacosCafe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 427, Short.MAX_VALUE)
                        .addComponent(btnSalvarEspacosCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtLotacaoEspacoCafe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeEspacoCafe, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroEspacoCafeLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCadastroEspacoCafeLayout.setVerticalGroup(
            panelCadastroEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroEspacoCafeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addComponent(txtNomeEspacoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtLotacaoEspacoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCadastroEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalvarEspacosCafe, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(panelCadastroEspacoCafeLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroEspacosCafe)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelCadastroPessoa.setBackground(new java.awt.Color(255, 255, 255));
        panelCadastroPessoa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomePessoa.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomePessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        txtSobrenomePessoa.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtSobrenomePessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Sobrenome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        btnSalvarPessoa.setBackground(new java.awt.Color(42, 169, 224));
        btnSalvarPessoa.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnSalvarPessoa.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarPessoa.setText("Salvar");
        btnSalvarPessoa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnSalvarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvarPessoa.setFocusPainted(false);
        btnSalvarPessoa.setFocusable(false);
        btnSalvarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarPessoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarPessoaMouseExited(evt);
            }
        });
        btnSalvarPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPessoaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel6.setText("Pessoas");

        lblMensagemErroPessoa.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroPessoa.setText("Para salvar prencha os campos corretamente.");

        txtSalaPessoa.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtSalaPessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Sala", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        txtEspacoCafePessoa.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtEspacoCafePessoa.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Espaço Café", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N

        javax.swing.GroupLayout panelCadastroPessoaLayout = new javax.swing.GroupLayout(panelCadastroPessoa);
        panelCadastroPessoa.setLayout(panelCadastroPessoaLayout);
        panelCadastroPessoaLayout.setHorizontalGroup(
            panelCadastroPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroPessoaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomePessoa)
                    .addComponent(txtSobrenomePessoa)
                    .addComponent(txtSalaPessoa)
                    .addComponent(txtEspacoCafePessoa)
                    .addGroup(panelCadastroPessoaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelCadastroPessoaLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroPessoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvarPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCadastroPessoaLayout.setVerticalGroup(
            panelCadastroPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroPessoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addComponent(txtNomePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtSobrenomePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSalaPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEspacoCafePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(panelCadastroPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMensagemErroPessoa)
                    .addComponent(btnSalvarPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout panelCadastroLayout = new javax.swing.GroupLayout(panelCadastro);
        panelCadastro.setLayout(panelCadastroLayout);
        panelCadastroLayout.setHorizontalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCadastroSalasDoEvento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastroEspacoCafe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastroPessoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCadastroLayout.setVerticalGroup(
            panelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(panelCadastroSalasDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCadastroEspacoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCadastroPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPai.add(panelCadastro, "cardCadastro");

        panelConsulta.setBackground(new java.awt.Color(255, 255, 255));
        panelConsulta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        panelConsultaSalasDoEvento.setBackground(new java.awt.Color(255, 255, 255));
        panelConsultaSalasDoEvento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomeSalaDoEventoConsultar.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomeSalaDoEventoConsultar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N
        txtNomeSalaDoEventoConsultar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeSalaDoEventoConsultarKeyReleased(evt);
            }
        });

        btnConsultarSalaDoEvento.setBackground(new java.awt.Color(42, 169, 224));
        btnConsultarSalaDoEvento.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnConsultarSalaDoEvento.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarSalaDoEvento.setText("Consultar");
        btnConsultarSalaDoEvento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnConsultarSalaDoEvento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarSalaDoEvento.setFocusPainted(false);
        btnConsultarSalaDoEvento.setFocusable(false);
        btnConsultarSalaDoEvento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConsultarSalaDoEventoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConsultarSalaDoEventoMouseExited(evt);
            }
        });
        btnConsultarSalaDoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarSalaDoEventoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setText("Salas do Evento");

        lblMensagemErroSalaDoEventoConsultar.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroSalaDoEventoConsultar.setText("Para consultar prencha os campos corretamente.");

        javax.swing.GroupLayout panelConsultaSalasDoEventoLayout = new javax.swing.GroupLayout(panelConsultaSalasDoEvento);
        panelConsultaSalasDoEvento.setLayout(panelConsultaSalasDoEventoLayout);
        panelConsultaSalasDoEventoLayout.setHorizontalGroup(
            panelConsultaSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsultaSalasDoEventoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelConsultaSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroSalaDoEventoConsultar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                        .addComponent(btnConsultarSalaDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNomeSalaDoEventoConsultar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConsultaSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConsultaSalasDoEventoLayout.setVerticalGroup(
            panelConsultaSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaSalasDoEventoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(15, 15, 15)
                .addComponent(txtNomeSalaDoEventoConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(panelConsultaSalasDoEventoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConsultarSalaDoEvento, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(panelConsultaSalasDoEventoLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroSalaDoEventoConsultar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelConsultaEspacoCafe.setBackground(new java.awt.Color(255, 255, 255));
        panelConsultaEspacoCafe.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomeEspacoCafeConsultar.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomeEspacoCafeConsultar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N
        txtNomeEspacoCafeConsultar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeEspacoCafeConsultarKeyReleased(evt);
            }
        });

        btnConsultarEspacoCafe.setBackground(new java.awt.Color(42, 169, 224));
        btnConsultarEspacoCafe.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnConsultarEspacoCafe.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarEspacoCafe.setText("Consultar");
        btnConsultarEspacoCafe.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnConsultarEspacoCafe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarEspacoCafe.setFocusPainted(false);
        btnConsultarEspacoCafe.setFocusable(false);
        btnConsultarEspacoCafe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConsultarEspacoCafeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConsultarEspacoCafeMouseExited(evt);
            }
        });
        btnConsultarEspacoCafe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEspacoCafeActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel8.setText("Espaços Café");

        lblMensagemErroEspacoCafeConsultar.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroEspacoCafeConsultar.setText("Para consultar prencha os campos corretamente.");

        javax.swing.GroupLayout panelConsultaEspacoCafeLayout = new javax.swing.GroupLayout(panelConsultaEspacoCafe);
        panelConsultaEspacoCafe.setLayout(panelConsultaEspacoCafeLayout);
        panelConsultaEspacoCafeLayout.setHorizontalGroup(
            panelConsultaEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsultaEspacoCafeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelConsultaEspacoCafeLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroEspacoCafeConsultar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                        .addComponent(btnConsultarEspacoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNomeEspacoCafeConsultar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConsultaEspacoCafeLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConsultaEspacoCafeLayout.setVerticalGroup(
            panelConsultaEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaEspacoCafeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(15, 15, 15)
                .addComponent(txtNomeEspacoCafeConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(panelConsultaEspacoCafeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConsultarEspacoCafe, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(panelConsultaEspacoCafeLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroEspacoCafeConsultar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        panelConsultaPessoa.setBackground(new java.awt.Color(255, 255, 255));
        panelConsultaPessoa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        txtNomePessoaConsultar.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        txtNomePessoaConsultar.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true), "Nome", 0, 0, new java.awt.Font("Calibri", 0, 14))); // NOI18N
        txtNomePessoaConsultar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomePessoaConsultarKeyReleased(evt);
            }
        });

        btnConsultarPessoa.setBackground(new java.awt.Color(42, 169, 224));
        btnConsultarPessoa.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnConsultarPessoa.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultarPessoa.setText("Consultar");
        btnConsultarPessoa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(42, 169, 224), 2, true));
        btnConsultarPessoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarPessoa.setFocusPainted(false);
        btnConsultarPessoa.setFocusable(false);
        btnConsultarPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConsultarPessoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConsultarPessoaMouseExited(evt);
            }
        });
        btnConsultarPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarPessoaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel9.setText("Pessoa");

        lblMensagemErroPessoaConsultar.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        lblMensagemErroPessoaConsultar.setText("Para consultar prencha os campos corretamente.");

        javax.swing.GroupLayout panelConsultaPessoaLayout = new javax.swing.GroupLayout(panelConsultaPessoa);
        panelConsultaPessoa.setLayout(panelConsultaPessoaLayout);
        panelConsultaPessoaLayout.setHorizontalGroup(
            panelConsultaPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConsultaPessoaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelConsultaPessoaLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroPessoaConsultar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                        .addComponent(btnConsultarPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNomePessoaConsultar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConsultaPessoaLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConsultaPessoaLayout.setVerticalGroup(
            panelConsultaPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaPessoaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(15, 15, 15)
                .addComponent(txtNomePessoaConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(panelConsultaPessoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConsultarPessoa, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addGroup(panelConsultaPessoaLayout.createSequentialGroup()
                        .addComponent(lblMensagemErroPessoaConsultar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tbConsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Título 1"
            }
        ));
        tbConsultas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbConsultasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbConsultas);

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConsultaSalasDoEvento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConsultaEspacoCafe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConsultaPessoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelConsultaSalasDoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsultaEspacoCafe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsultaPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        panelPai.add(panelConsulta, "cardConsulta");

        panelHome.setBackground(new java.awt.Color(255, 255, 255));
        panelHome.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblMsg1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        lblMsg1.setText("Bem vindo!");

        lblMsg2.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblMsg2.setText("Para cadastrar ou fazer ");

        lblMsg3.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblMsg3.setText("qualquer tipo de consulta");

        lblMsg4.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        lblMsg4.setText("use o menu ao seu lado esquerdo!");

        lblMsg5.setFont(new java.awt.Font("Calibri Light", 0, 11)); // NOI18N
        lblMsg5.setText("Contato: lucasnunes.ln365@gmail.com ");

        lblMsg6.setFont(new java.awt.Font("Calibri Light", 0, 11)); // NOI18N
        lblMsg6.setText("Autor: Lucas Nunes");

        javax.swing.GroupLayout panelHomeLayout = new javax.swing.GroupLayout(panelHome);
        panelHome.setLayout(panelHomeLayout);
        panelHomeLayout.setHorizontalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                        .addComponent(lblMsg6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMsg5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomeLayout.createSequentialGroup()
                        .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMsg3)
                            .addComponent(lblMsg2)
                            .addComponent(lblMsg4)
                            .addComponent(lblMsg1))
                        .addContainerGap(218, Short.MAX_VALUE))))
        );
        panelHomeLayout.setVerticalGroup(
            panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomeLayout.createSequentialGroup()
                .addContainerGap(265, Short.MAX_VALUE)
                .addComponent(lblMsg1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMsg2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMsg3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMsg4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addGroup(panelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMsg5)
                    .addComponent(lblMsg6))
                .addGap(5, 5, 5))
        );

        panelPai.add(panelHome, "cardHome");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblHamburguer.setBackground(new java.awt.Color(255, 255, 255));
        lblHamburguer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/lucasnunes/imagens/icon_hamburguer.png"))); // NOI18N
        lblHamburguer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHamburguerMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHamburguer, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblHamburguer, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(panelPai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelPai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(966, 778));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /*-------------------------------------------------------------------------------*/
    private void lblHamburguerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHamburguerMouseClicked
        // TODO add your handling code here:
        Color corLblHamburguer = lblHamburguer.getBackground();
        String corStringLblHamburguer = String.valueOf(corLblHamburguer);
        
        if(corStringLblHamburguer.equals("java.awt.Color[r=255,g=255,b=255]")){
            btnHome.setVisible(true);
            btnCadastro.setVisible(true);
            btnConsulta.setVisible(true);
            lblHamburguer.setBackground(new Color(255, 255, 254));
        } else{
            btnHome.setVisible(false);
            btnCadastro.setVisible(false);
            btnConsulta.setVisible(false);
            lblHamburguer.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_lblHamburguerMouseClicked
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnCadastroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCadastroMouseEntered
        // TODO add your handling code here:
        btnCadastro.setBackground(new Color(255,255,255));
        btnCadastro.setForeground(Color.BLACK);
        
    }//GEN-LAST:event_btnCadastroMouseEntered

    private void btnCadastroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCadastroMouseExited
        // TODO add your handling code here:
        btnCadastro.setBackground(new Color(42,169,224));
        btnCadastro.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnCadastroMouseExited
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnConsultaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultaMouseEntered
        // TODO add your handling code here:
        btnConsulta.setBackground(new Color(255,255,255));
        btnConsulta.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnConsultaMouseEntered
    
    private void btnConsultaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultaMouseExited
        // TODO add your handling code here:
        btnConsulta.setBackground(new Color(42,169,224));
        btnConsulta.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnConsultaMouseExited
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout)panelPai.getLayout();
        cl.show(panelPai, "cardCadastro");
    }//GEN-LAST:event_btnCadastroActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout)panelPai.getLayout();
        cl.show(panelPai, "cardConsulta");
    }//GEN-LAST:event_btnConsultaActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        // TODO add your handling code here:
        btnHome.setBackground(new Color(255,255,255));
        btnHome.setForeground(Color.BLACK);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        // TODO add your handling code here:
        btnHome.setBackground(new Color(42,169,224));
        btnHome.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnHomeMouseExited
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout)panelPai.getLayout();
        cl.show(panelPai, "cardHome");
    }//GEN-LAST:event_btnHomeActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarSalaDoEventoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarSalaDoEventoMouseEntered
        // TODO add your handling code here:
        btnSalvarSalaDoEvento.setBackground(Color.white);
        btnSalvarSalaDoEvento.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnSalvarSalaDoEventoMouseEntered

    private void btnSalvarSalaDoEventoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarSalaDoEventoMouseExited
        // TODO add your handling code here:
        btnSalvarSalaDoEvento.setBackground(new Color(42,169,224));
        btnSalvarSalaDoEvento.setForeground(Color.white);
    }//GEN-LAST:event_btnSalvarSalaDoEventoMouseExited
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarEspacosCafeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarEspacosCafeMouseExited
        // TODO add your handling code here:
        btnSalvarEspacosCafe.setBackground(new Color(42,169,224));
        btnSalvarEspacosCafe.setForeground(Color.white);
    }//GEN-LAST:event_btnSalvarEspacosCafeMouseExited

    private void btnSalvarEspacosCafeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarEspacosCafeMouseEntered
        // TODO add your handling code here:
        btnSalvarEspacosCafe.setBackground(Color.white);
        btnSalvarEspacosCafe.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnSalvarEspacosCafeMouseEntered
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarPessoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarPessoaMouseEntered
        // TODO add your handling code here:
        btnSalvarPessoa.setBackground(Color.white);
        btnSalvarPessoa.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnSalvarPessoaMouseEntered

    private void btnSalvarPessoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarPessoaMouseExited
        // TODO add your handling code here:
        btnSalvarPessoa.setBackground(new Color(42,169,224));
        btnSalvarPessoa.setForeground(Color.white);
    }//GEN-LAST:event_btnSalvarPessoaMouseExited
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarSalaDoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarSalaDoEventoActionPerformed
        // TODO add your handling code here:
        String nomeSalaDoEvento = txtNomeSalaDoEvento.getText();
        try{
            int lotacaoSalaDoEvento = Integer.parseInt(txtLotacaoSalaDoEvento.getText());
            
            if(nomeSalaDoEvento.isEmpty() || lotacaoSalaDoEvento == 0){
                lblMensagemErroSalaDoEvento.setText("Campos inválidos!");
                lblMensagemErroSalaDoEvento.setForeground(Color.red);
            } else{
                lblMensagemErroSalaDoEvento.setText("Salvo com sucesso!");
                lblMensagemErroSalaDoEvento.setForeground(new Color(46,189,89));
                insertSalasDoEvento();
            }
            
        } catch(NumberFormatException e){
            lblMensagemErroSalaDoEvento.setText("Campos inválidos!");
            lblMensagemErroSalaDoEvento.setForeground(Color.red);
        } 
    }//GEN-LAST:event_btnSalvarSalaDoEventoActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarEspacosCafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarEspacosCafeActionPerformed
        // TODO add your handling code here:
        String nomeEspacoCafe = txtNomeEspacoCafe.getText();
        try{
            int lotacaoEspacoCafe = Integer.parseInt(txtLotacaoEspacoCafe.getText());
            
            if(nomeEspacoCafe.isEmpty() || lotacaoEspacoCafe == 0){
                lblMensagemErroEspacosCafe.setText("Campos inválidos!");
                lblMensagemErroEspacosCafe.setForeground(Color.red);
            } else{
                lblMensagemErroEspacosCafe.setText("Salvo com sucesso!");
                lblMensagemErroEspacosCafe.setForeground(new Color(46,189,89));
                insertEspacosCafe();
            }
            
        } catch(NumberFormatException e){
            lblMensagemErroEspacosCafe.setText("Campos inválidos!");
            lblMensagemErroEspacosCafe.setForeground(Color.red);
        } 
    }//GEN-LAST:event_btnSalvarEspacosCafeActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnSalvarPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarPessoaActionPerformed
        // TODO add your handling code here:
        String nomePessoa = txtNomePessoa.getText();
        String sobreNomePessoa = txtSobrenomePessoa.getText();
        String salaPessoa = txtSalaPessoa.getText();
        String espacoCafePessoa = txtEspacoCafePessoa.getText();
        existeSala();
        existeEspaco();
            
        if(nomePessoa.isEmpty() || sobreNomePessoa.isEmpty() || salaPessoa.isEmpty() || espacoCafePessoa.isEmpty()){
            lblMensagemErroPessoa.setText("Campos inválidos!");
            lblMensagemErroPessoa.setForeground(Color.red);
        } else{
            existeSala();
            existeEspaco();
            verificaSala();
            verificaEspaco();

            if(existeEspacoCafe == true && existeSala == true){
                
                tamanhoSala();
                tamanhoEspaco();
                salaCheia();
                espacoCafeCheio();
                
                if(quantidadeDePessoasSala >= tamanhoSalaEvento || quantidadeDePessoasEspaco >= tamanhoEspacoCafe){
                    lblMensagemErroPessoa.setText("Salas ou Espaços de Café cheios");
                    lblMensagemErroPessoa.setForeground(Color.red);
                } else {
                    insertPessoas();
                    lblMensagemErroPessoa.setText("Salvo com sucesso!");
                    lblMensagemErroPessoa.setForeground(new Color(46,189,89));
                }
            } else {
                lblMensagemErroPessoa.setText("Salas ou Espaços de Café inválidos!");
                lblMensagemErroPessoa.setForeground(Color.red);
            } 
        }   
    }//GEN-LAST:event_btnSalvarPessoaActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnConsultarSalaDoEventoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarSalaDoEventoMouseEntered
        // TODO add your handling code here:
        btnConsultarSalaDoEvento.setBackground(Color.white);
        btnConsultarSalaDoEvento.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnConsultarSalaDoEventoMouseEntered

    private void btnConsultarSalaDoEventoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarSalaDoEventoMouseExited
        // TODO add your handling code here:
        btnConsultarSalaDoEvento.setBackground(new Color(42,169,224));
        btnConsultarSalaDoEvento.setForeground(Color.white);
    }//GEN-LAST:event_btnConsultarSalaDoEventoMouseExited

    private void btnConsultarSalaDoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarSalaDoEventoActionPerformed
        // TODO add your handling code here:
        String nomeSalaDoEventoConsultar = txtNomeSalaDoEventoConsultar.getText();
        
        if(nomeSalaDoEventoConsultar.isEmpty()){
            lblMensagemErroSalaDoEventoConsultar.setText("Campos inválidos!");
            lblMensagemErroSalaDoEventoConsultar.setForeground(Color.red);
        } else { 
            consultarSalaDoEvento();
            lblMensagemErroSalaDoEventoConsultar.setText("Consulta feita com sucesso!");
            lblMensagemErroSalaDoEventoConsultar.setForeground(new Color(46,189,89));
        }
    }//GEN-LAST:event_btnConsultarSalaDoEventoActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnConsultarEspacoCafeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarEspacoCafeMouseEntered
        // TODO add your handling code here:
        btnConsultarEspacoCafe.setBackground(Color.white);
        btnConsultarEspacoCafe.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnConsultarEspacoCafeMouseEntered

    private void btnConsultarEspacoCafeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarEspacoCafeMouseExited
        // TODO add your handling code here:
        btnConsultarEspacoCafe.setBackground(new Color(42,169,224));
        btnConsultarEspacoCafe.setForeground(Color.white);
    }//GEN-LAST:event_btnConsultarEspacoCafeMouseExited

    private void btnConsultarEspacoCafeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEspacoCafeActionPerformed
        // TODO add your handling code here:
        String nomeEspacoCafeConsultar = txtNomeEspacoCafeConsultar.getText();
        
        if(nomeEspacoCafeConsultar.isEmpty()){
            lblMensagemErroEspacoCafeConsultar.setText("Campos inválidos!");
            lblMensagemErroEspacoCafeConsultar.setForeground(Color.red);
        } else { 
            consultarEspacoCafe();
            lblMensagemErroEspacoCafeConsultar.setText("Consulta feita com sucesso!");
            lblMensagemErroEspacoCafeConsultar.setForeground(new Color(46,189,89));
        }
    }//GEN-LAST:event_btnConsultarEspacoCafeActionPerformed
    /*-------------------------------------------------------------------------------*/
    
    /*-------------------------------------------------------------------------------*/
    private void btnConsultarPessoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarPessoaMouseEntered
        // TODO add your handling code here:
        btnConsultarPessoa.setBackground(Color.white);
        btnConsultarPessoa.setForeground(new Color(42,169,224));
    }//GEN-LAST:event_btnConsultarPessoaMouseEntered

    private void btnConsultarPessoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConsultarPessoaMouseExited
        // TODO add your handling code here:
        btnConsultarPessoa.setBackground(new Color(42,169,224));
        btnConsultarPessoa.setForeground(Color.white);
    }//GEN-LAST:event_btnConsultarPessoaMouseExited

    private void btnConsultarPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarPessoaActionPerformed
        // TODO add your handling code here:
        String nomePessoaConsultar = txtNomePessoaConsultar.getText();
        
        if(nomePessoaConsultar.isEmpty()){
            lblMensagemErroPessoaConsultar.setText("Campos inválidos!");
            lblMensagemErroPessoaConsultar.setForeground(Color.red);
        } else { 
            consultarPessoa();
            lblMensagemErroPessoaConsultar.setText("Consulta feita com sucesso!");
            lblMensagemErroPessoaConsultar.setForeground(new Color(46,189,89));
        }
    }//GEN-LAST:event_btnConsultarPessoaActionPerformed

    private void txtNomeSalaDoEventoConsultarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeSalaDoEventoConsultarKeyReleased
        // TODO add your handling code here:
        consultarSalaDoEvento();
    }//GEN-LAST:event_txtNomeSalaDoEventoConsultarKeyReleased

    private void txtNomeEspacoCafeConsultarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEspacoCafeConsultarKeyReleased
        // TODO add your handling code here:
        consultarEspacoCafe();
    }//GEN-LAST:event_txtNomeEspacoCafeConsultarKeyReleased

    private void txtNomePessoaConsultarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePessoaConsultarKeyReleased
        // TODO add your handling code here:
        consultarPessoa();
    }//GEN-LAST:event_txtNomePessoaConsultarKeyReleased

    private void tbConsultasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbConsultasKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbConsultasKeyReleased
    /*-------------------------------------------------------------------------------*/
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastro;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnConsultarEspacoCafe;
    private javax.swing.JButton btnConsultarPessoa;
    private javax.swing.JButton btnConsultarSalaDoEvento;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnSalvarEspacosCafe;
    private javax.swing.JButton btnSalvarPessoa;
    private javax.swing.JButton btnSalvarSalaDoEvento;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHamburguer;
    private javax.swing.JLabel lblMensagemErroEspacoCafeConsultar;
    private javax.swing.JLabel lblMensagemErroEspacosCafe;
    private javax.swing.JLabel lblMensagemErroPessoa;
    private javax.swing.JLabel lblMensagemErroPessoaConsultar;
    private javax.swing.JLabel lblMensagemErroSalaDoEvento;
    private javax.swing.JLabel lblMensagemErroSalaDoEventoConsultar;
    private javax.swing.JLabel lblMsg1;
    private javax.swing.JLabel lblMsg2;
    private javax.swing.JLabel lblMsg3;
    private javax.swing.JLabel lblMsg4;
    private javax.swing.JLabel lblMsg5;
    private javax.swing.JLabel lblMsg6;
    private javax.swing.JPanel panelCadastro;
    private javax.swing.JPanel panelCadastroEspacoCafe;
    private javax.swing.JPanel panelCadastroPessoa;
    private javax.swing.JPanel panelCadastroSalasDoEvento;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelConsultaEspacoCafe;
    private javax.swing.JPanel panelConsultaPessoa;
    private javax.swing.JPanel panelConsultaSalasDoEvento;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelPai;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tbConsultas;
    private javax.swing.JTextField txtEspacoCafePessoa;
    private javax.swing.JTextField txtLotacaoEspacoCafe;
    private javax.swing.JTextField txtLotacaoSalaDoEvento;
    private javax.swing.JTextField txtNomeEspacoCafe;
    private javax.swing.JTextField txtNomeEspacoCafeConsultar;
    private javax.swing.JTextField txtNomePessoa;
    private javax.swing.JTextField txtNomePessoaConsultar;
    private javax.swing.JTextField txtNomeSalaDoEvento;
    private javax.swing.JTextField txtNomeSalaDoEventoConsultar;
    private javax.swing.JTextField txtSalaPessoa;
    private javax.swing.JTextField txtSobrenomePessoa;
    // End of variables declaration//GEN-END:variables
}
