package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.model.ChatRoom;

import javax.swing.*;
import java.awt.*;

public class SwingAppContainer extends JFrame implements AppContainer {

    private final AppContext appContext;
    private CardLayout cl = new CardLayout();
    private JPanel content;
    private AppPane appPane;

    public SwingAppContainer(AppContext appContext) {
        this.appContext = appContext;
    }

    private void init() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);

        content = new JPanel(cl);

        this.setLayout(new BorderLayout());
        this.content.add(new LoginPane(appContext.getLocalUsersList(), appContext.getLoginController()), "1");
        this.appPane = new AppPane(appContext.getConnectedUsers(), appContext.getDispatcher());
        this.content.add(this.appPane, "2");
        this.add(content, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void loadLogin() {
        SwingUtilities.invokeLater(() -> cl.show(content, "1"));
    }

    @Override
    public void loadApp() {
        appPane.setLoggedUser(appContext.getCurrentUser());
        appPane.init();
        SwingUtilities.invokeLater(() -> cl.show(content, "2"));
    }

    @Override
    public void openChatRoom(ChatRoom room) {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jFrame.add(new ChatPane(room, appContext.getDispatcher()));
        jFrame.setSize(500, 400);
        jFrame.setLocation(300, 300);
        jFrame.setVisible(true);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(this::init);
    }
}
