package org.itstep;

public class Work implements Runnable
{

    Network network = new Network();

    @Override
    public void run() {
        network.testNetwork();
        network.eduNetwork();
        network.testNetwork();
    }




    private void testIndikator() {
        network.setIndikatorOne();
        network.echoIndikator();
        network.setIndikatorSeven();
        network.echoIndikator();
    }
}
