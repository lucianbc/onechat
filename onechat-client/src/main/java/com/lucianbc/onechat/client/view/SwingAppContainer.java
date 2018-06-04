package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.client.model.ChatRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SwingAppContainer extends JFrame implements AppContainer {

    private static final Logger logger = LoggerFactory.getLogger(SwingAppContainer.class);

    private final AppContext appContext;
    private final ChatRoomsController roomsController;
    private CardLayout cl = new CardLayout();
    private JPanel content;
    private AppPane appPane;

    public SwingAppContainer(AppContext appContext) {
        this.appContext = appContext;
        this.roomsController = appContext.getRoomsController();
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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                roomsController.closeRooms();
                close();
            }
        });

        this.setVisible(true);
    }

    public void close() {
        try {
            if (appContext.getNetworkEndpoint() != null) this.appContext.getNetworkEndpoint().stop();
        } catch (IOException e) {
            logger.error("Could not stop network endpoint", e);
        }
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
        ChatPane chatPane = new ChatPane(room, appContext.getDispatcher(), roomsController);
        jFrame.add(chatPane);
        jFrame.setSize(500, 400);
        jFrame.setLocation(300, 300);
        jFrame.setVisible(true);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                chatPane.exit();
            }
        });
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(this::init);
    }
}
