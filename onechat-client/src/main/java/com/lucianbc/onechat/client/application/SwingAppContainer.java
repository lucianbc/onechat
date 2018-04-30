package com.lucianbc.onechat.client.application;

import javax.swing.*;

class SwingAppContainer extends JFrame {

    private ApplicationContext context;

    SwingAppContainer(ApplicationContext context) {
        this.context = context;
    }

    void run() {
        this.setSize(300, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        showLogin();
    }

    private void showLogin() {
        replaceView(context.getViewContext().getLoginScreen());
    }

    private void replaceView(JPanel newView) {
        SwingUtilities.invokeLater(() -> {
            this.getContentPane().removeAll();
            this.getContentPane().add(newView);
            this.repaint();
        });
    }
}
