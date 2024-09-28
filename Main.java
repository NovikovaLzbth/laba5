import java.util.Random;

class AnimalThread extends Thread {
    private String name;
    private int priority;
    private int distance = 0; // расстояние, пройденное животным
    private boolean running = true; // остановка потока

    public AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public void run() {
        int first = (int)(Math.random() * 10);
        int second = (int)(Math.random()* 10);

        while (first > 0 && second > 0 && distance < 100) { // пока животное не достигло 100 метров
            distance += (priority == Thread.MAX_PRIORITY) ? first : second; // кролик (приоритет MAX) бегает быстрее
            System.out.println(name + " пробежал(а) " + distance + " метров");
            try {
                Thread.sleep(100); // пауза для видимости работы потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Динамическая корректировка приоритета
            if (distance > 50 && priority == Thread.NORM_PRIORITY) {
                setPriority(Thread.MAX_PRIORITY); // увеличение приоритета
            }
        }
        running = false; // остановить поток после достижения 100 метров
        System.out.println(name + " финишировал");
    }

}

public class Main {
    public static void main(String[] args) {
        // объекты класса
        AnimalThread rabbit = new AnimalThread("Кролик", Thread.MAX_PRIORITY);
        AnimalThread turtle = new AnimalThread("Черепаха", Thread.NORM_PRIORITY);

        // запуск потоков
        turtle.start();
        rabbit.start();

        // завершение обоих потоков
        try {
            rabbit.join();
            turtle.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // результаты
        System.out.println("Гонка завершена!");
    }
}
