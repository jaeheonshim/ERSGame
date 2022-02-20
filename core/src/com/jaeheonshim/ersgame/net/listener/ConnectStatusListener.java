package com.jaeheonshim.ersgame.net.listener;

import com.jaeheonshim.ersgame.net.model.ConnectionStatus;

public interface ConnectStatusListener {
    void onStatusChange(ConnectionStatus newStatus);
}
