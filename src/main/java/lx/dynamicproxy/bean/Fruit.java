package lx.dynamicproxy.bean;

import lx.Log;

public interface Fruit {
    void pB();


    public static class Apple implements Fruit {
        private void _pA() {
            Log.d("i am Fruit _pA()");
            _pC();
        }

        @Override
        public void pB() {
            Log.d("i am Fruit pB()");
            _pA();
        }

        private void _pC() {
            Log.d("i am Fruit _pC()");
            Log.d("run thread");

            new FruitThread().start();
        }

        static class FruitThread extends Thread {
            @Override
            public void run() {
                setName("lxxxx");
                try {
                    _ftA();
                } finally {
                    Log.d("Thread run finally");
                }

                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("FruitThread exit");
            }

            private void _ftA() {
                try {
                    int b = 1 / 0;
                } catch (Exception e) {
                    Log.d("exception catched " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    Log.d("_ftA() finally");
                }
            }
        }
    }
}
