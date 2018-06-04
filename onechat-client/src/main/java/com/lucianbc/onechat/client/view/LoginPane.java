package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.data.UserIdentity;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

class LoginPane extends JPanel {
    private final AbstractListModel<UserIdentity> users;
    private final LoginController loginController;

    private void init() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JList<UserIdentity> usersJList = new JList<>(this.users);
        usersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersJList.setVisibleRowCount(-1);
        usersJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jList, Object o, int i, boolean b, boolean b1) {
                super.getListCellRendererComponent(jList, o, i, b, b1);
                setText(((UserIdentity) o).getUsername());
                return this;
            }
        });
        JScrollPane scrollPane = new JScrollPane(usersJList);
        scrollPane.setPreferredSize(new Dimension(0, 200));


        JButton selectButton = new JButton("select");
        selectButton.addActionListener(e -> loginController.selectUser(usersJList.getSelectedValue()));
        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(e -> {
            UserIdentity ui = usersJList.getSelectedValue();
            try {
                loginController.removeUser(ui);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        JButton addButton = new JButton("add");
        addButton.addActionListener(e -> {
            String value = JOptionPane.showInputDialog("Username: ");
            try {
                loginController.addUser(value);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        JButton loginAnonButton = new JButton("anonymous");
        loginAnonButton.addActionListener(e -> loginController.loginAnonymous());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 2));
        controlPanel.add(selectButton);
        controlPanel.add(removeButton);
        controlPanel.add(addButton);
        controlPanel.add(loginAnonButton);

        content.add(scrollPane);
        content.add(controlPanel);
        this.setLayout(new GridBagLayout());
        this.add(content);
    }

    LoginPane(AbstractListModel<UserIdentity> users, LoginController loginController) {
        this.users = users;
        this.loginController = loginController;
        init();
    }
}
