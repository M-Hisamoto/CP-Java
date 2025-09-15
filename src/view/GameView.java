package view;

import controller.GameController;
import dao.GameDAO;
import model.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

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
        JButton readBtt = new JButton("listar");
        JButton updateBtt = new JButton("Atualizar");
        JButton deleteBtt = new JButton("Excluir");
        JButton clearBtt = new JButton("Limpar campos");

        buttons.add(clearBtt);
        buttons.add(registerBtt);
        buttons.add(readBtt);
        buttons.add(updateBtt);
        buttons.add(deleteBtt);

        JPanel search = new JPanel();
        JButton searchBtt = new JButton("Buscar");
        search.add(new JLabel("Nome:"));
        search.add(searchFd);
        search.add(searchBtt);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.WEST);
        add(buttons, BorderLayout.SOUTH);
        add(search, BorderLayout.NORTH);

        clearBtt.addActionListener(e -> clearFields());
        registerBtt.addActionListener(e -> clearFields());

    }

    private void clearFields() {
        idFd.setText("");
        titleFd.setText("");
        genreFd.setText("");
        platformCb.setSelectedIndex(0);
        statusFd.setText("");
    }

}





