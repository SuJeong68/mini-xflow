package com.nhnacademy.aiot;

import com.nhnacademy.aiot.node.HttpServerNode;
import com.nhnacademy.aiot.node.SocketInNode;
import com.nhnacademy.aiot.node.SocketOutNode;
import com.nhnacademy.aiot.wire.BufferedWire;
import com.nhnacademy.aiot.wire.Wire;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static int port = 80;

    public static void main(String[] args) {
        if (args.length != 0) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                HttpServerNode httpServerNode = new HttpServerNode();
                SocketInNode socketInNode = new SocketInNode(socket.getInputStream());
                SocketOutNode socketOutNode = new SocketOutNode(socket.getOutputStream());

                Wire inWire = new BufferedWire();
                httpServerNode.connectInputWires(0, inWire);
                socketInNode.connectOutputWire(0, inWire);

                Wire outWire = new BufferedWire();
                httpServerNode.connectOutputWire(0, outWire);
                socketOutNode.connectInputWires(0, outWire);

                httpServerNode.start();
                socketInNode.start();
                socketOutNode.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
