package com.lucianbc.onechat.client.view.impl;

import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.view.LoginActionsBinder;
import com.lucianbc.onechat.client.view.LoginScreen;

import java.util.List;
import java.util.Scanner;

public class CliLoginScreen implements LoginScreen {
    private final LoginActionsBinder actionBinder;
    public CliLoginScreen() {
        this.actionBinder = new LoginActionsBinder();
        startReadUser();
    }

    @Override
    public void removeUser(UserIdentity user) {
        actionBinder.getRemoveUserAction().accept(user);
    }

    @Override
    public void addUser(String user) {
        actionBinder.getAddUserAction().accept(user);
    }

    @Override
    public void selectUser(UserIdentity user) {
        actionBinder.getSelectUserAction().accept(user);
    }

    @Override
    public void receiveUsers(List<UserIdentity> users) {
        System.out.println("Current users saved on the machine are: ");
        users.forEach(System.out::println);
    }

    @Override
    public void onUserRemoved(UserIdentity user) {
        System.out.println("Removing user from the list view");
        System.out.println(user);
    }

    @Override
    public void onUserAdded(UserIdentity user) {
        System.out.println("Adding user to the list view");
        System.out.println(user);
    }

    @Override
    public LoginActionsBinder getActionBinder() {
        return this.actionBinder;
    }

    private void startReadUser() {
        Runnable runnable = this::readUser;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void readUser() {
        Scanner keyboard = new Scanner(System.in);
        String value = keyboard.next();
        addUser("username");

    }
}
