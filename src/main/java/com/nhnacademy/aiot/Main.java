package com.nhnacademy.aiot;

import com.nhnacademy.aiot.node.*;
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
                HttpRequestParsingNode httpRequestParsingNode = new HttpRequestParsingNode();

                GetProcessingNode getProcessingNode = new GetProcessingNode();
                SocketOutNode socketOutNode = new SocketOutNode(socket.getOutputStream());

                Wire socketInWire = new BufferedWire();
                socketInNode.connectOutputWire(0, socketInWire);
                httpServerNode.connectInputWires(0, socketInWire);

                Wire httpRequestParsingWire = new BufferedWire();
                httpServerNode.connectOutputWire(0, httpRequestParsingWire);
                httpRequestParsingNode.connectInputWires(0, httpRequestParsingWire);

                Wire getProcessingWire = new BufferedWire();
                httpRequestParsingNode.connectOutputWire(0, getProcessingWire);
                getProcessingNode.connectInputWires(0, getProcessingWire);

                Wire socketOutWire = new BufferedWire();
                getProcessingNode.connectOutputWire(0, socketOutWire);
                socketOutNode.connectInputWires(0, socketOutWire);

                httpServerNode.start();
                socketInNode.start();
                httpRequestParsingNode.start();
                getProcessingNode.start();
                socketOutNode.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
