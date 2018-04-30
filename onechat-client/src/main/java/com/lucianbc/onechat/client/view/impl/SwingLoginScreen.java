package com.lucianbc.onechat.client.view.impl;

import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.view.LoginActionsBinder;
import com.lucianbc.onechat.client.view.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingLoginScreen extends JPanel implements LoginScreen {
    private LoginActionsBinder actionsBinder;

    private JList<UserIdentity> userList;
    private DefaultListModel<UserIdentity> userListModel;

    SwingLoginScreen() {
        actionsBinder = new LoginActionsBinder();
        render();
    }

    private void render() {
        this.setBackground(Color.BLUE);
        this.setSize(new Dimension(200, 300));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setCellRenderer((l, value, index, selected, cellfocus)  -> {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setIconTextGap(12);
            label.setText(value.getUsername());
            if (selected) {
                label.setBackground(new Color(0, 0, 120));
                label.setForeground(Color.white);
            } else {
                label.setBackground(Color.white);
                label.setForeground(Color.black);
            }
            return label;
        });
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setVisibleRowCount(-1);
        JScrollPane scrollPane = new JScrollPane(userList);
        this.add(scrollPane);



        JButton selectExistingBtn = new JButton();
        selectExistingBtn.setText("Select");

        JButton removeElementBtn = new JButton();
        removeElementBtn.setText("Remove");
        removeElementBtn.addActionListener(e -> removeUser(userList.getSelectedValue()));

        JButton addElementBtn = new JButton();
        addElementBtn.setText("Add");
        addElementBtn.addActionListener(e -> {
            String val = JOptionPane.showInputDialog("Userame: ");
            addUser(val);
        });

        this.add(selectExistingBtn);
        this.add(removeElementBtn);
        this.add(addElementBtn);
    }

    @Override
    public void removeUser(UserIdentity user) {
        actionsBinder.getRemoveUserAction().accept(user);
    }

    @Override
    public void addUser(String username) {
        actionsBinder.getAddUserAction().accept(username);
    }

    @Override
    public void selectUser(UserIdentity user) {
        actionsBinder.getSelectUserAction().accept(user);
    }

    @Override
    public void receiveUsers(List<UserIdentity> users) {
//        List<UserIdentity> users2 = new LinkedList<>();
//        users2.add(new UserIdentity("id1", "user1"));
//        users2.add(new UserIdentity("id2", "user2"));
        userListModel.removeAllElements();
        users.forEach(userListModel::addElement);
    }

    @Override
    public void onUserRemoved(UserIdentity user) {
        userListModel.removeElement(user);
    }

    @Override
    public void onUserAdded(UserIdentity user) {
        userListModel.addElement(user);
    }

    @Override
    public LoginActionsBinder getActionBinder() {
        return this.actionsBinder;
    }
}
