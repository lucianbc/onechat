package com.lucianbc.onechat.client.model.impl;

import javax.swing.*;
import java.util.Vector;

public class MyAbstractListModel<T> extends AbstractListModel<T> {
    final Vector<T> delegate;

    MyAbstractListModel() {
        this.delegate = new Vector<>();
    }

    @Override
    public int getSize() {
        return this.delegate.size();
    }

    @Override
    public T getElementAt(int i) {
        return this.delegate.elementAt(i);
    }

    void add(T el) {
        this.delegate.add(el);
        this.fireIntervalAdded(this, delegate.size() - 1, delegate.size() - 1);
    }

    void remove(T el) {
        int var2 = this.delegate.indexOf(el);
        this.delegate.removeElement(el);
        if (var2 >= 0) {
            this.fireIntervalRemoved(this, var2, var2);
        }
    }
}
