public class Cronometro extends Thread {
    private boolean rodando = false;
    private long inicio;
    private long tempoPausado = 0;
    private long acumulado = 0;
    private String timerFormatado = "00:00:000";

    public synchronized void startar() {
        if (!rodando) {
            rodando = true;
            inicio = System.currentTimeMillis();
            this.start();
        }
        notify();
    }

    public void run() {
        while (true) {
            synchronized (this) {
                while (!rodando) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            long agora = System.currentTimeMillis();
            long decorrido = acumulado + (agora - inicio);

            long minutos = (decorrido / 60000) % 60;
            long seg = (decorrido / 1000) % 60;
            long milisRestantes = decorrido % 1000;

            timerFormatado = String.format("%02d:%02d:%03d", minutos, seg, milisRestantes);

            try {
                Thread.sleep(10); // Atualiza a cada 10 ms (suficiente)
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void parar() {
        if (rodando) {
            acumulado += System.currentTimeMillis() - inicio;
            rodando = false;
        }
    }

    public void zerar() {
        acumulado = 0;
        inicio = System.currentTimeMillis();
        timerFormatado = "00:00:000";
    }

    public String getTimer() {
        return timerFormatado;
    }

    public long getTimerInt() {
        return acumulado + (rodando ? System.currentTimeMillis() - inicio : 0);
    }
}
