/*
 * Decompiled with CFR 0_115.
 */
package agora.server;

import agora.server.Connection;
import agora.system.Control;
import agora.system.Output;
import agora.system.Report;
import agora.system.Subroutine;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Bridge {
    Socket auxiliarySocket;
    OutputStream auxiliaryStream;
    int currentPort = 25561;
    String serverIP;
    HashMap<String, Connection> subConnections = new HashMap();

    public Bridge(String inputServerIP) throws IOException {
        Output.log(new Report("Attempting connection to " + inputServerIP), "connection");
        this.serverIP = inputServerIP;
        this.auxiliarySocket = new Socket(this.serverIP, 25561);
        this.auxiliaryStream = this.auxiliarySocket.getOutputStream();
        new Subroutine(new CloseManager());
        ++this.currentPort;
        Output.log(new Report("Connection successful " + inputServerIP), "connection");
    }

    public Connection subConnection(String name) throws IOException {
        Connection connection = new Connection(new Socket(this.serverIP, this.currentPort));
        this.subConnections.put(name, connection);
        ++this.currentPort;
        return connection;
    }

    public class CloseManager
    extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Bridge.this.auxiliaryStream.write(1);
                    Bridge.this.auxiliaryStream.flush();
                }
                catch (Exception e) {
                    Output.log(new Report("Bridge closed to IP " + Bridge.this.serverIP), "connection");
                    break;
                }
                Control.sleep(1);
            } while (true);
        }
    }

}

