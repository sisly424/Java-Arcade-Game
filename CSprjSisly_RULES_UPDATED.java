import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CSprjSisly_RULES_UPDATED {
    private static Clip backgroundMusic;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Feeding Frenzy");
            GamePanel panel = new GamePanel();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            playSound("bgm.wav", true);
            panel.startTimers();
        });
    }

    /**
     * 播放 WAV 文件。
     * 文件应与 CSprjSisly.java 放在同一个项目文件夹中。
     */
    private static void playSound(String filename, boolean loop) {
        try {
            File soundFile = new File(filename);

            if (!soundFile.exists()) {
                System.out.println("找不到音频文件：" + soundFile.getAbsolutePath());
                return;
            }

            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(soundFile.getAbsoluteFile());

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);

            if (loop) {
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                backgroundMusic.start();
            }
        } catch (Exception ex) {
            System.out.println("音乐播放失败。");
            ex.printStackTrace();
        }
    }
}

enum GameState {
    START,
    PLAYING,
    GAME_OVER
}

class GamePanel extends JPanel {
    private static final int WINDOW_WIDTH = 1440;
    private static final int WINDOW_HEIGHT = 900;
    private static final int GAME_WIDTH = 1120;
    private static final int ROUND_SECONDS = 90;
    private static final int PLAYER_STEP = 40;

    private final List<Fish> fishes = new ArrayList<>();

    private final Image startBackground;
    private final Image seaBackground;

    private final Timer gameTimer;
    private final Timer secondTimer;

    private GameState state = GameState.START;

    private int playerX = 0;
    private int playerY = 0;
    private int playerSize = 40;
    private int score = 0;
    private int elapsedSeconds = 0;

    GamePanel() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);

        startBackground = loadImage("bp.jpg");
        seaBackground = loadImage("sea.jpg");

        createFishes();
        installKeyBindings();

        // 大约每秒 60 帧。
        gameTimer = new Timer(16, e -> {
            if (state == GameState.PLAYING) {
                updateGame();
            }
            repaint();
        });

        // 每秒更新一次倒计时。
        secondTimer = new Timer(1000, e -> {
            if (state == GameState.PLAYING) {
                elapsedSeconds++;

                if (elapsedSeconds >= ROUND_SECONDS) {
                    state = GameState.GAME_OVER;
                }
            }
        });
    }

    void startTimers() {
        gameTimer.start();
        secondTimer.start();
    }

    private Image loadImage(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("找不到图片：" + file.getAbsolutePath());
            return null;
        }

        return new ImageIcon(file.getAbsolutePath()).getImage();
    }

    private void createFishes() {
        fishes.clear();

        addFish(60, 50, 2, 2, 40, 0, 500, 20, 300,
                new Color(248, 252, 2));
        addFish(100, 200, 3, 3, 30, 100, 900, 200, 700,
                new Color(65, 244, 145));
        addFish(500, 80, 2, 2, 50, 500, 900, 80, 700,
                new Color(108, 109, 103));
        addFish(200, 350, 2, 2, 40, 200, 750, 350, 700,
                new Color(247, 171, 39));
        addFish(300, 100, 2, 2, 40, 300, 650, 100, 550,
                new Color(21, 216, 34));
        addFish(200, 70, 3, 3, 30, 200, 650, 70, 550,
                new Color(126, 7, 244));
        addFish(240, 170, 3, 3, 30, 240, 1000, 170, 550,
                new Color(224, 62, 124));
        addFish(110, 250, 2, 2, 50, 110, 900, 250, 800,
                new Color(108, 109, 103));
        addFish(140, 150, 2, 2, 50, 140, 1020, 150, 300,
                new Color(108, 109, 103));
        addFish(30, 30, 3, 3, 30, 30, 1100, 30, 650,
                new Color(196, 51, 186));
        addFish(70, 40, 3, 3, 30, 70, 1040, 40, 700,
                new Color(43, 198, 219));
        addFish(300, 350, 2, 2, 50, 300, 750, 350, 700,
                new Color(108, 109, 103));
        addFish(200, 400, 2, 2, 40, 200, 1080, 400, 840,
                new Color(160, 97, 46));
        addFish(120, 100, 2, 2, 40, 120, 600, 100, 840,
                new Color(252, 81, 1));
        addFish(130, 140, 3, 3, 50, 130, 430, 140, 840,
                new Color(108, 109, 103));
        addFish(170, 100, 3, 3, 40, 170, 1080, 100, 540,
                new Color(158, 66, 244));
        addFish(60, 80, 3, 3, 50, 60, 380, 80, 670,
                new Color(108, 109, 103));
        addFish(100, 500, 3, 3, 30, 100, 1040, 500, 840,
                new Color(111, 239, 0));
        addFish(170, 450, 3, 3, 30, 170, 1040, 450, 840,
                new Color(203, 0, 239));
        addFish(140, 60, 3, 3, 40, 140, 500, 60, 840,
                new Color(239, 203, 0));
        addFish(400, 60, 3, 3, 40, 400, 900, 60, 840,
                new Color(239, 231, 0));
        addFish(550, 40, 3, 3, 40, 550, 900, 40, 450,
                new Color(36, 4, 239));
        addFish(550, 570, 3, 3, 50, 550, 1000, 570, 830,
                new Color(108, 109, 103));
        addFish(570, 500, 3, 3, 30, 570, 1040, 500, 840,
                new Color(135, 19, 193));
        addFish(540, 20, 3, 3, 30, 540, 1040, 20, 340,
                new Color(244, 75, 66));
        addFish(20, 650, 3, 3, 30, 20, 540, 650, 830,
                new Color(249, 74, 142));
    }

    private void addFish(
            int x,
            int y,
            int dx,
            int dy,
            int size,
            int minX,
            int maxX,
            int minY,
            int maxY,
            Color color
    ) {
        fishes.add(new Fish(
                x, y, dx, dy, size,
                minX, maxX, minY, maxY,
                color
        ));
    }

    private void installKeyBindings() {
        InputMap inputMap =
                getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        bindKey(inputMap, actionMap, "SPACE", KeyEvent.VK_SPACE, this::startOrRestart);
        bindKey(inputMap, actionMap, "LEFT", KeyEvent.VK_LEFT,
                () -> movePlayer(-PLAYER_STEP, 0));
        bindKey(inputMap, actionMap, "RIGHT", KeyEvent.VK_RIGHT,
                () -> movePlayer(PLAYER_STEP, 0));
        bindKey(inputMap, actionMap, "UP", KeyEvent.VK_UP,
                () -> movePlayer(0, -PLAYER_STEP));
        bindKey(inputMap, actionMap, "DOWN", KeyEvent.VK_DOWN,
                () -> movePlayer(0, PLAYER_STEP));
    }

    private void bindKey(
            InputMap inputMap,
            ActionMap actionMap,
            String name,
            int keyCode,
            Runnable action
    ) {
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), name);

        actionMap.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void startOrRestart() {
        if (state == GameState.START || state == GameState.GAME_OVER) {
            resetGame();
            state = GameState.PLAYING;
        }
    }

    private void resetGame() {
        playerX = 0;
        playerY = 0;
        playerSize = 40;
        score = 0;
        elapsedSeconds = 0;
        createFishes();
    }

    private void movePlayer(int dx, int dy) {
        if (state != GameState.PLAYING) {
            return;
        }

        playerX = clamp(
                playerX + dx,
                0,
                GAME_WIDTH - playerSize
        );

        playerY = clamp(
                playerY + dy,
                0,
                WINDOW_HEIGHT - playerSize
        );
    }

    private int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private void updateGame() {
        balanceDangerousFish();

        for (Fish fish : fishes) {
            // 被吃掉的鱼约一秒后会按照玩家当前大小重新生成。
            fish.updateRespawn(playerSize, GAME_WIDTH, WINDOW_HEIGHT);

            if (!fish.isVisible()) {
                continue;
            }

            // 危险鱼会保持比玩家略大，但不会大得过分。
            fish.keepDangerousSizeAbove(playerSize);

            fish.update();
            checkCollision(fish);
        }
    }

    /**
     * 玩家越大，危险鱼占据的面积越大，因此允许存在的危险鱼数量会减少。
     */
    private int getTargetDangerousFishCount() {
        if (playerSize < 70) {
            return 4;
        }

        if (playerSize < 110) {
            return 3;
        }

        return 2;
    }

    /**
     * 当危险鱼太多时，把多余的危险鱼转换成可食用的小鱼，
     * 防止后期整个画面都被大型危险鱼占满。
     */
    private void balanceDangerousFish() {
        int allowed = getTargetDangerousFishCount();
        int dangerousSeen = 0;

        for (Fish fish : fishes) {
            if (!fish.isVisible() || !fish.isDangerous()) {
                continue;
            }

            dangerousSeen++;

            if (dangerousSeen > allowed) {
                fish.convertToSafeFish(playerSize);
            }
        }
    }

    private int countDangerousFish() {
        int count = 0;

        for (Fish fish : fishes) {
            if (fish.isVisible() && fish.isDangerous()) {
                count++;
            }
        }

        return count;
    }

    private void checkCollision(Fish fish) {
        double playerCenterX = playerX + playerSize / 2.0;
        double playerCenterY = playerY + playerSize / 2.0;

        double fishCenterX = fish.getX() + fish.getSize() / 2.0;
        double fishCenterY = fish.getY() + fish.getSize() / 2.0;

        double distance = Math.hypot(
                fishCenterX - playerCenterX,
                fishCenterY - playerCenterY
        );

        double collisionDistance =
                playerSize / 2.0 + fish.getSize() / 2.0;

        boolean colliding = distance <= collisionDistance;

        if (!colliding) {
            fish.setTouchingPlayer(false);
            return;
        }

        /*
         * 先判断“危险鱼身份”，不能只比较当前像素大小。
         * 只要是灰色危险鱼，碰到就一定扣 15 分，绝不会被吃掉加分。
         */
        if (fish.isDangerous()) {
            if (!fish.isTouchingPlayer()) {
                score -= 15;
                fish.setTouchingPlayer(true);
            }
            return;
        }

        // 非危险鱼才允许被玩家吃掉。
        if (playerSize >= fish.getSize()) {
            score += 10;
            playerSize += 2;

            /*
             * 如果场上的危险鱼少于 5 条，
             * 被吃掉的鱼重生时会强制成为危险鱼。
             */
            boolean mustRespawnAsDangerous =
                    countDangerousFish() < getTargetDangerousFishCount();

            fish.markEaten(mustRespawnAsDangerous);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics.create();

        try {
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (state == GameState.START) {
                drawStartScreen(g);
            } else if (state == GameState.PLAYING) {
                drawGame(g);
            } else {
                drawGameOver(g);
            }
        } finally {
            g.dispose();
        }
    }

    private void drawStartScreen(Graphics2D g) {
        drawBackground(g, startBackground, 0, 0, GAME_WIDTH, WINDOW_HEIGHT,
                new Color(28, 105, 180));

        g.setColor(new Color(255, 240, 38));
        g.setFont(new Font("SansSerif", Font.BOLD, 105));
        g.drawString("Feeding Frenzy", 170, 430);

        g.setColor(new Color(8, 25, 219));
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        g.drawString("PRESS SPACE TO ENTER THE GAME", 210, 700);

        drawRules(g);
    }

    private void drawGame(Graphics2D g) {
        drawBackground(g, seaBackground, 0, 0, GAME_WIDTH, WINDOW_HEIGHT,
                new Color(20, 155, 210));

        for (Fish fish : fishes) {
            if (fish.isVisible()) {
                fish.draw(g);
            }
        }

        drawPlayer(g);
        drawScore(g);
        drawRules(g);
        drawTimer(g);
    }

    private void drawBackground(
            Graphics2D g,
            Image image,
            int x,
            int y,
            int width,
            int height,
            Color fallback
    ) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, this);
        } else {
            g.setColor(fallback);
            g.fillRect(x, y, width, height);
        }
    }

    private void drawPlayer(Graphics2D g) {
        g.setColor(new Color(244, 166, 221));

        int x = playerX;
        int y = playerY;
        int size = playerSize;

        int[] xPoints = {
                x,
                x + size / 5,
                x + size / 5,
                x + size,
                x + size / 5,
                x + size / 5,
                x
        };

        int[] yPoints = {
                y + size * 35 / 100,
                y + size * 42 / 100,
                y + size / 10,
                y + size / 2,
                y + size * 9 / 10,
                y + size * 58 / 100,
                y + size * 65 / 100
        };

        g.fillPolygon(xPoints, yPoints, 7);

        g.setColor(Color.BLACK);
        g.fillOval(
                x + size * 7 / 10,
                y + size * 45 / 100,
                Math.max(4, size / 10),
                Math.max(4, size / 10)
        );
    }

    private void drawScore(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRoundRect(430, 20, 260, 50, 15, 15);

        g.setColor(Color.RED);
        g.setFont(new Font("Serif", Font.BOLD, 26));
        g.drawString("SCORE: " + score, 485, 54);
    }

    private void drawTimer(Graphics2D g) {
        int secondsLeft =
                Math.max(0, ROUND_SECONDS - elapsedSeconds);

        g.setColor(new Color(252, 252, 141));
        g.fillRect(GAME_WIDTH, 800, 320, 100);

        g.setColor(new Color(10, 18, 252));
        g.setFont(new Font("SansSerif", Font.PLAIN, 34));
        g.drawString("TIME LEFT: " + secondsLeft, 1140, 860);
    }

    private void drawRules(Graphics2D g) {
        g.setColor(new Color(250, 252, 249));
        g.fillRect(GAME_WIDTH, 0, 320, WINDOW_HEIGHT);

        g.setColor(new Color(4, 200, 239));
        g.setFont(new Font("Serif", Font.BOLD, 22));
        g.drawString("GAME RULES", 1210, 35);

        drawArrowKeys(g);

        g.setColor(new Color(244, 166, 221));
        g.setFont(new Font("Serif", Font.BOLD, 17));
        g.drawString("PINK FISH: USER CONTROL", 1150, 200);

        g.setColor(Color.RED);
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.drawString("AVOID FISH BIGGER", 1175, 245);
        g.drawString("THAN YOUR FISH!", 1185, 272);

        /*
         * 所有彩色小鱼都属于加分鱼。
         * 用多种颜色展示，避免规则栏只出现黄色和绿色。
         */
        drawRuleFish(g, 1150, 305, 25, new Color(65, 244, 145));
        drawRuleFish(g, 1190, 303, 28, new Color(248, 252, 2));
        drawRuleFish(g, 1235, 305, 25, new Color(247, 171, 39));
        drawRuleFish(g, 1275, 303, 28, new Color(126, 7, 244));
        drawRuleFish(g, 1320, 305, 25, new Color(224, 62, 124));
        drawRuleFish(g, 1360, 303, 28, new Color(43, 198, 219));

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 16));
        g.drawString("ALL COLORED SMALL FISH", 1160, 365);
        g.setFont(new Font("Serif", Font.BOLD, 21));
        g.drawString("EAT = +10", 1220, 395);

        /*
         * 灰色鱼是危险鱼：它会保持比玩家略大。
         */
        drawRuleFish(g, 1175, 425, 48, new Color(108, 109, 103));

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 16));
        g.drawString("GRAY DANGER FISH", 1240, 448);
        g.setFont(new Font("Serif", Font.BOLD, 21));
        g.drawString("TOUCH = -15", 1235, 478);

        g.setColor(new Color(26, 146, 244));
        g.setFont(new Font("Serif", Font.BOLD, 23));
        g.drawString("REMINDER:", 1200, 545);

        g.setFont(new Font("Serif", Font.BOLD, 19));
        g.drawString("Eat colored fish smaller", 1145, 585);
        g.drawString("than you and avoid gray fish!", 1135, 615);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 27));
        g.drawString("GAME TIME:", 1180, 720);

        g.setColor(new Color(168, 93, 37));
        g.drawString("90 SECONDS", 1180, 770);
    }

    private void drawArrowKeys(Graphics2D g) {
        g.drawRect(1270, 60, 50, 50);
        g.drawRect(1220, 110, 50, 50);
        g.drawRect(1270, 110, 50, 50);
        g.drawRect(1320, 110, 50, 50);

        g.drawString("↑", 1287, 95);
        g.drawString("←", 1235, 145);
        g.drawString("↓", 1287, 145);
        g.drawString("→", 1335, 145);
    }

    private void drawRuleFish(
            Graphics2D g,
            int x,
            int y,
            int size,
            Color color
    ) {
        g.setColor(color);

        int[] xPoints = {
                x,
                x + size / 5,
                x + size / 2,
                x + size,
                x + size / 2,
                x
        };

        int[] yPoints = {
                y + size / 5,
                y + size / 2,
                y + size,
                y + size / 2,
                y,
                y + size * 4 / 5
        };

        g.fillPolygon(xPoints, yPoints, 6);

        g.setColor(Color.BLACK);
        g.fillOval(
                x + size * 7 / 10,
                y + size * 2 / 5,
                Math.max(4, size / 10),
                Math.max(4, size / 10)
        );
    }

    private void drawGameOver(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 130));
        g.drawString("GAME OVER", 270, 420);

        g.setFont(new Font("SansSerif", Font.PLAIN, 55));
        g.drawString("YOUR SCORE: " + score, 450, 550);

        g.setFont(new Font("SansSerif", Font.BOLD, 28));
        g.drawString("PRESS SPACE TO PLAY AGAIN", 470, 650);
    }
}

class Fish {
    private static final Color DANGEROUS_COLOR =
            new Color(108, 109, 103);

    private static final Color[] SAFE_COLORS = {
            new Color(248, 252, 2),
            new Color(65, 244, 145),
            new Color(247, 171, 39),
            new Color(21, 216, 34),
            new Color(126, 7, 244),
            new Color(224, 62, 124),
            new Color(43, 198, 219),
            new Color(196, 51, 186),
            new Color(111, 239, 0),
            new Color(239, 203, 0),
            new Color(244, 75, 66)
    };

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    private Color color;

    /*
     * dangerous 表示这条鱼属于危险鱼。
     * dangerSizeGap 表示它至少比玩家大多少。
     */
    private boolean dangerous;
    private int dangerSizeGap;

    private boolean visible = true;
    private boolean touchingPlayer = false;

    private int respawnFrames = 0;
    private boolean forceDangerousRespawn = false;

    Fish(
            int x,
            int y,
            int dx,
            int dy,
            int size,
            int minX,
            int maxX,
            int minY,
            int maxY,
            Color color
    ) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.color = color;

        this.dangerous = DANGEROUS_COLOR.equals(color);
        this.dangerSizeGap = Math.max(10, size - 40);
    }

    /**
     * 危险鱼不会只在重生时变大，而是每一帧都会检查。
     * 只要玩家长大到接近它，它就会同步变成玩家大小 + gap。
     */
    void keepDangerousSizeAbove(int playerSize) {
        if (!dangerous) {
            return;
        }

        /*
         * 危险鱼始终比玩家略大，但差距只保持在 8～18，
         * 避免后期危险鱼大到占据大量屏幕空间。
         */
        size = playerSize + dangerSizeGap;
    }

    /**
     * 将多余的危险鱼转换成比玩家小的普通鱼。
     */
    void convertToSafeFish(int playerSize) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        dangerous = false;
        dangerSizeGap = 0;
        touchingPlayer = false;

        size = Math.max(
                20,
                playerSize - random.nextInt(8, 26)
        );

        color = SAFE_COLORS[
                random.nextInt(SAFE_COLORS.length)
        ];
    }

    void update() {
        x += dx;
        y += dy;

        if (x <= minX || x + size >= maxX) {
            dx = -dx;
            x = Math.max(minX, Math.min(x, maxX - size));
        }

        if (y <= minY || y + size >= maxY) {
            dy = -dy;
            y = Math.max(minY, Math.min(y, maxY - size));
        }
    }

    void markEaten(boolean mustBecomeDangerous) {
        visible = false;
        touchingPlayer = false;
        respawnFrames = 60;
        forceDangerousRespawn = mustBecomeDangerous;
    }

    void updateRespawn(
            int playerSize,
            int gameWidth,
            int gameHeight
    ) {
        if (visible) {
            return;
        }

        if (respawnFrames > 0) {
            respawnFrames--;
            return;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        boolean dangerous =
                forceDangerousRespawn || random.nextInt(100) < 15;

        if (dangerous) {
            // 记录固定差值，之后玩家继续变大时，这条鱼也会继续变大。
            this.dangerous = true;
            dangerSizeGap = random.nextInt(8, 19);
            size = playerSize + dangerSizeGap;
            color = DANGEROUS_COLOR;
        } else {
            this.dangerous = false;
            dangerSizeGap = 0;

            // 比玩家小，因此可以吃掉加分。
            size = Math.max(
                    20,
                    playerSize - random.nextInt(5, 26)
            );
            color = SAFE_COLORS[
                    random.nextInt(SAFE_COLORS.length)
            ];
        }

        minX = 0;
        maxX = gameWidth;
        minY = 20;
        maxY = gameHeight - 20;

        y = random.nextInt(
                minY,
                Math.max(minY + 1, maxY - size)
        );

        int speedX = random.nextInt(2, 5);
        int speedY = random.nextInt(1, 4);

        // 从左右边缘进入，避免直接生成在玩家身上。
        if (random.nextBoolean()) {
            x = minX;
            dx = speedX;
        } else {
            x = maxX - size;
            dx = -speedX;
        }

        dy = random.nextBoolean() ? speedY : -speedY;

        visible = true;
        touchingPlayer = false;
        forceDangerousRespawn = false;
    }

    void draw(Graphics2D g) {
        g.setColor(color);

        int[] xPoints = {
                x,
                x + size / 5,
                x + size / 2,
                x + size,
                x + size / 2,
                x
        };

        int[] yPoints = {
                y + size / 5,
                y + size / 2,
                y + size,
                y + size / 2,
                y,
                y + size * 4 / 5
        };

        g.fillPolygon(xPoints, yPoints, 6);

        g.setColor(Color.BLACK);
        g.fillOval(
                x + size * 7 / 10,
                y + size * 2 / 5,
                Math.max(4, size / 10),
                Math.max(4, size / 10)
        );
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getSize() {
        return size;
    }

    boolean isVisible() {
        return visible;
    }

    boolean isDangerous() {
        return dangerous;
    }

    boolean isTouchingPlayer() {
        return touchingPlayer;
    }

    void setTouchingPlayer(boolean touchingPlayer) {
        this.touchingPlayer = touchingPlayer;
    }
}
