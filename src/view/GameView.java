package view;

import controller.GameController;
import dao.GameDAO;
import db.DBInitializer;
import model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GameView extends JFrame {

    private final JTextField idFd = new JTextField(5);
    private final JTextField titleFd = new JTextField(20);
    private final JTextField genreFd = new JTextField(20);
    private final JTextField realeseYearFd = new JTextField(4);
    private final JTextField statusFd = new JTextField(20);
    private final JTextField ratingFd = new JTextField(4);

    private final JComboBox<String> platformCb =
            new JComboBox<>(new String[]{"PC", "Xbox", "PlayStation", "Nintendo"});
    private final JTextField searchFd = new JTextField(15);

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Título", "Gênero", "Plataforma", "Ano", "Status", "Rating"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    private final GameDAO dao = new GameDAO();
    private final GameController controller = new GameController(dao);

    public GameView() {
        super("CP04 Biblioteca Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 520);
        setLocationRelativeTo(null);

        buildLayout();
        bindEvents();
        loadTable();
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4,4,4,4);
        g.fill = GridBagConstraints.HORIZONTAL;
        int y = 0;

        addField(form, g, y++, "Id:", idFd);
        addField(form, g, y++, "Título:", titleFd);
        addField(form, g, y++, "Gênero:", genreFd);
        addField(form, g, y++, "Plataforma:", platformCb);
        addField(form, g, y++, "Ano de Lançamento:", realeseYearFd);
        addField(form, g, y++, "Status:", statusFd);
        addField(form, g, y++, "Rating:", ratingFd);

        JPanel buttons = new JPanel();
        JButton bSave = new JButton("Registrar");
        JButton bUpdate = new JButton("Atualizar");
        JButton bDelete = new JButton("Excluir");
        JButton bClear = new JButton("Limpar");
        buttons.add(bSave);
        buttons.add(bUpdate);
        buttons.add(bDelete);
        buttons.add(bClear);

        JPanel searchPanel = new JPanel();
        JButton bSearch = new JButton("Buscar");
        searchPanel.add(new JLabel("Título:"));
        searchPanel.add(searchFd);
        searchPanel.add(bSearch);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        right.add(form, BorderLayout.NORTH);
        right.add(searchPanel, BorderLayout.CENTER);
        right.add(buttons, BorderLayout.SOUTH);

        add(right, BorderLayout.EAST);

        // Ações
        bSave.addActionListener(e -> save());
        bUpdate.addActionListener(e -> update());
        bDelete.addActionListener(e -> delete());
        bClear.addActionListener(e -> clearFields());
        bSearch.addActionListener(e -> search());
    }

    private void addField(JPanel panel, GridBagConstraints g, int y, String label, JComponent comp) {
        g.gridx = 0; g.gridy = y; g.weightx = 0;
        panel.add(new JLabel(label), g);
        g.gridx = 1; g.weightx = 1;
        panel.add(comp, g);
    }

    private void bindEvents() {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int i = table.getSelectedRow();
            if (i >= 0) {
                idFd.setText(String.valueOf(model.getValueAt(i, 0)));
                titleFd.setText(String.valueOf(model.getValueAt(i, 1)));
                genreFd.setText(String.valueOf(model.getValueAt(i, 2)));
                platformCb.setSelectedItem(model.getValueAt(i, 3));
                realeseYearFd.setText(String.valueOf(model.getValueAt(i, 4)));
                statusFd.setText(String.valueOf(model.getValueAt(i, 5)));
                ratingFd.setText(String.valueOf(model.getValueAt(i, 6)));
            }
        });
    }

    private void clearFields() {
        idFd.setText("");
        titleFd.setText("");
        genreFd.setText("");
        platformCb.setSelectedIndex(0);
        realeseYearFd.setText("");
        statusFd.setText("");
        ratingFd.setText("");
        table.clearSelection();
    }

    private Game buildFromForm() {
        try {
            String title = titleFd.getText();
            String genre = genreFd.getText();
            String platform = (String) platformCb.getSelectedItem();
            int year = Integer.parseInt(realeseYearFd.getText().trim());
            String status = statusFd.getText();
            int rating = Integer.parseInt(ratingFd.getText().trim());
            Game g = new Game(title, genre, platform, year, status, rating);
            controller.validateGame(g);
            return g;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ano e Rating devem ser números válidos.");
        } catch (Exception ex) {
            throw new RuntimeException("erro ao receber os dados");
        }
    }

    private void save() {
        try {
            Game g = buildFromForm();
            dao.registerNewGame(g);
            JOptionPane.showMessageDialog(this, "Jogo registrado com sucesso.");
            loadTable();
            clearFields();
        } catch (Exception ex) {
            showError("Erro ao salvar", ex);
        }
    }

    private void update() {
        try {
            if (idFd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro.");
                return;
            }
            Game g = buildFromForm();
            g.setId(Integer.parseInt(idFd.getText()));
            dao.updateGame(g);
            JOptionPane.showMessageDialog(this, "Atualizado com sucesso.");
            loadTable();
            clearFields();
        } catch (Exception ex) {
            showError("Erro ao atualizar", ex);
        }
    }

    private void delete() {
        try {
            if (idFd.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro.");
                return;
            }
            int id = Integer.parseInt(idFd.getText());
            if (JOptionPane.showConfirmDialog(this, "Excluir jogo ID " + id + "?", "Confirmação",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dao.deleteGame(id);
                JOptionPane.showMessageDialog(this, "Excluído.");
                loadTable();
                clearFields();
            }
        } catch (Exception ex) {
            showError("Erro ao excluir", ex);
        }
    }

    private void search() {
        try {
            List<Game> list = dao.searchByTitle(searchFd.getText());
            insertTable(list);
        } catch (SQLException ex) {
            showError("Erro na busca", ex);
        }
    }

    private void loadTable() {
        try {
            DBInitializer.ensureCreated();
            List<Game> list = dao.listById();
            insertTable(list);
        } catch (SQLException ex) {
            showError("Erro ao carregar tabela", ex);
        }
    }

    private void insertTable(List<Game> list) {
        model.setRowCount(0);
        for (Game g : list) {
            model.addRow(new Object[]{
                    g.getId(), g.getTitle(), g.getGenre(), g.getPlatform(),
                    g.getRealeseYear(), g.getStatus(), g.getRating()
            });
        }
    }

    private void showError(String title, Exception ex) {
        JOptionPane.showMessageDialog(this,
                title + ": " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
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