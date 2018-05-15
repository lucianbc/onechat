package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.data.UserIdentity;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class UsersContainer extends JPanel {
    private ListModel<UserIdentity> users;
    private LinkedHashMap<String, UserCard> cards = new LinkedHashMap<>();

    public UsersContainer(ListModel<UserIdentity> users) {
        this.users = users;
        users.addListDataListener(getActionBinding());
    }

    private ListDataListener getActionBinding() {
        return new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent listDataEvent) {
                for (int i = listDataEvent.getIndex0(); i <= listDataEvent.getIndex1(); i++) {
                    UserIdentity ui = users.getElementAt(i);
                    addCard(ui);
                }
            }

            @Override
            public void intervalRemoved(ListDataEvent listDataEvent) {
                System.out.println(listDataEvent);
            }

            @Override
            public void contentsChanged(ListDataEvent listDataEvent) {

            }
        };
    }

    private void addCard(UserIdentity ui) {
        UserCard card = new UserCard(ui);
        card.onChat(u -> System.out.println("Chat with " + u));
        cards.put(card.user.getId(), card);
        this.add(card);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void removeCard(UserIdentity ui) {
        UserCard card = cards.get(ui.getId());
        if (card == null) return;
        this.remove(card);
        cards.remove(card.user.getId());
    }

    class UserCard extends JPanel {
        private Consumer<UserIdentity> chatStarted;
        private final UserIdentity user;

        UserCard(UserIdentity user) {
            this.user = user;
            init();
        }

        private void init() {
            JLabel label = new JLabel(user.getUsername());
            JButton button = new JButton();
            button.setText("Chat!");
            button.addActionListener(e -> {
                if (chatStarted != null) chatStarted.accept(user);
            });
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = 1;
            c.weightx = 1;
            c.gridx = 0;
            c.gridy = 0;
            this.add(label, c);
            c.gridy = 1;
            this.add(button, c);
        }

        void onChat(Consumer<UserIdentity> handler) {
            this.chatStarted = handler;
        }
    }
}
