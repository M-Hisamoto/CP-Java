package view;

import controller.GameController;
import dao.GameDAO;
import db.DBInitializer;
import model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class GameView extends JFrame {

    private JTextField idFd = new JTextField(5);
    private JTextField titleFd = new JTextField(20);
    private JTextField genreFd = new JTextField(20);
    private JTextField realeseYearFd = new JTextField(4);
    private JTextField statusFd = new JTextField(20);
    private JTextField ratingFd = new JTextField(4);


    private JComboBox<String> platformCb = new JComboBox<>(new String[]{ "PC", "Xbox", "PlayStation", "Nintendo" });
    private JTextField searchFd = new JTextField(15);

    private DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", " Título", "Gênero", "Plataforma", "Ano de Lançamento", "Status", "Rating"}, 0);
    private JTable table = new JTable(model);

    private GameDAO dao = new GameDAO();
    private GameController controller = new GameController(dao);

    public GameView() {
        super("CP04 Biblioteca Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBc = new GridBagConstraints();
        gridBc.insets = new Insets(5,5,5,5);
        gridBc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Id:"), gridBc);
        gridBc.gridx=0; gridBc.gridy=y; panel.add(idFd, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Título:"), gridBc);
        gridBc.gridx=1; gridBc.gridy=y; panel.add(titleFd, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Gênero:"), gridBc);
        gridBc.gridx=1; gridBc.gridy=y; panel.add(genreFd, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Plataforma:"), gridBc);
        gridBc.gridx=1; gridBc.gridy=y; panel.add(platformCb, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Ano de Lançamento:"), gridBc);
        gridBc.gridx=2; gridBc.gridy=y; panel.add(realeseYearFd, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Status:"), gridBc);
        gridBc.gridx=2; gridBc.gridy=y; panel.add(statusFd, gridBc);

        y++;
        gridBc.gridx=0; gridBc.gridy=y; panel.add(new JLabel("Rating:"), gridBc);
        gridBc.gridx=2; gridBc.gridy=y; panel.add(ratingFd, gridBc);



        JPanel buttons = new JPanel();
        JButton registerBtt = new JButton("Registrar");
        JButton updateBtt = new JButton("Atualizar");
        JButton deleteBtt = new JButton("Excluir");
        JButton clearBtt = new JButton("Limpar campos");

        buttons.add(registerBtt);
        buttons.add(updateBtt);
        buttons.add(deleteBtt);
        buttons.add(clearBtt);

        JPanel search = new JPanel();
        JButton searchBtt = new JButton("Buscar");
        search.add(new JLabel("Nome:"));
        search.add(searchFd);
        search.add(searchBtt);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.WEST);
        add(buttons, BorderLayout.SOUTH);
        add(search, BorderLayout.NORTH);

        registerBtt.addActionListener(e -> save());
        updateBtt.addActionListener(e -> update());
        deleteBtt.addActionListener(e -> delete());
        clearBtt.addActionListener(e -> clearFields());
        searchBtt.addActionListener(e -> search());

    }

    private void clearFields() {
        idFd.setText("");
        titleFd.setText("");
        genreFd.setText("");
        platformCb.setSelectedIndex(0);
        statusFd.setText("");
    }

    private Game buildFromForm() throws SQLException {
        String title = titleFd.getText();
        String genre = genreFd.getText();
        String platform = (String) platformCb.getSelectedItem();
        int realeseYear = Integer.parseInt(realeseYearFd.getText());
        String status = statusFd.getText();
        int rating = Integer.parseInt(ratingFd.getText());
        Game g = new Game(title, genre, platform, realeseYear, status, rating);
        // Regras de negócio
        controller.validateGame(g);
        return g;
    }

    private void save() {
        try {
            Game g = buildFromForm();
            dao.registerNewGame(g);
            JOptionPane.showMessageDialog(this,
                    "Novo jogo registrado com sucesso");
            loadTable();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void update() {
        try {
            if (idFd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro da tabela.");
                return;
            }
            Game g = buildFromForm();
            g.setId(Integer.parseInt(idFd.getText()));
            dao.updateGame(g);
            loadTable();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {
        try {
            if (idFd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro da tabela.");
                return;
            }
            int id = Integer.parseInt(idFd.getText());
            dao.deleteGame(id);
            loadTable();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        try {
            List<Game> list = dao.searchByTitle(searchFd.getText());
            insertTable(list);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        try {
            DBInitializer.ensureCreated();
            List<Game> list = dao.listById();
            insertTable(list);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertTable(List<Game> list) {
        model.setRowCount(0);
        for (Game g : list) {
            model.addRow(new Object[]{ g.getId(), g.getTitle(), g.getGenre(), g.getPlatform(), g.getRealeseYear(), g.getStatus(), g.getRating() });
        }
        // clique para editar
        table.getSelectionModel().addListSelectionListener(e -> {
            int i = table.getSelectedRow();
            if (i >= 0) {
                idFd.setText(model.getValueAt(i, 0).toString());
                titleFd.setText(model.getValueAt(i, 1).toString());
                genreFd.setText(model.getValueAt(i, 2).toString());
                platformCb.setSelectedItem(model.getValueAt(i, 3).toString());
                realeseYearFd.setText(model.getValueAt(i, 3).toString());
                statusFd.setText(model.getValueAt(i, 4).toString());
                ratingFd.setText(model.getValueAt(i, 5).toString());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DBInitializer.ensureCreated();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Falha ao inicializar banco: " + e.getMessage());
            }
            new GameView().setVisible(true);
        });
    }

}





