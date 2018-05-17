package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.model.ChatRoom;

import javax.swing.*;
import java.awt.*;

public class ChatPane extends JPanel {

    private JTextArea messagesArea;
    private JTextArea myTextArea;
    private JButton sendBtn;
    private final ActionDispatcher actionDispatcher;
    private final ChatRoom room;

    ChatPane(ChatRoom room, ActionDispatcher dispatcher) {
        this.room = room;
        this.room.bindView(this);
        this.actionDispatcher = dispatcher;
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.weightx = 100;
        c.weighty = 70;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;

        JPanel messagesPanel = initMsgPanel();
        this.add(messagesPanel, c);

        JPanel writePanel = initWritePanel();
        c.gridwidth = 1;
        c.weighty = 30;
        c.weightx = 75;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 5, 5);
        this.add(writePanel, c);

        JPanel buttonsPanel = initButtonsPanel();
        c.weightx = 35;
        c.gridx = 1;
        c.insets = new Insets( 0, 0, 5, 5);
        this.add(buttonsPanel, c);

        disableWrite();
    }

    private JPanel initMsgPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.CYAN);
        p.setLayout(new BorderLayout());

        messagesArea = new JTextArea();
        messagesArea.setWrapStyleWord(true);
        messagesArea.setLineWrap(true);
        messagesArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messagesArea);
        scrollPane.setPreferredSize(new Dimension(0, 0));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        p.add(scrollPane);

        return p;
    }

    private JPanel initWritePanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.PINK);
        p.setLayout(new BorderLayout());

        myTextArea = new JTextArea();
        myTextArea.setLineWrap(true);
        myTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(myTextArea);
        scrollPane.setPreferredSize(new Dimension(0, 0));
        p.add(scrollPane);
        return p;
    }

    private JPanel initButtonsPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        sendBtn = new JButton("Send!");
        sendBtn.setPreferredSize(new Dimension(0, 0));
        sendBtn.addActionListener(e -> this.sendAction());
        JButton askPermissionBtn = new JButton("Rise hand!");
        askPermissionBtn.setPreferredSize(new Dimension(0, 0));
        askPermissionBtn.addActionListener(e -> this.askPermissionAction());
        p.setBackground(Color.GREEN);
        p.add(sendBtn);
        p.add(askPermissionBtn);
        return p;
    }

    public void addMessage(String message, String sender) {
        String content = String.format("%s: %s\n", sender, message);
        messagesArea.append(content);
    }

    private void sendAction() {
        String content = myTextArea.getText();
        if (content == null || content.isEmpty()) return;

        addMessage(content, "You");
        myTextArea.setText("");
        actionDispatcher.dispatch(actionDispatcher.factory().sendMessage(room, content));
    }

    private void askPermissionAction() {
        actionDispatcher.dispatch(actionDispatcher.factory().requestWriteAccess(room));
    }

    public void enableWrite() {
        this.myTextArea.setEditable(true);
        this.sendBtn.setEnabled(true);
    }

    public void disableWrite() {
        this.myTextArea.setEditable(false);
        this.sendBtn.setEnabled(false);
    }
}
