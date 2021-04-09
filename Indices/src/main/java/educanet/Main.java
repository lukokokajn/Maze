package educanet;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

public class Main {

    public static void main(String[] args) throws Exception {
        String maze =
                "101111111000111011101010000001\n" +
                "100000101101101100111011111111\n" +
                "110011100111000110100010000100\n" +
                "010110000000010010101110110111\n" +
                "011100111101110110111010100001\n" +
                "100011100111001100100000111011\n" +
                "100000100010011000101111101010\n" +
                "110011101110010111111000000010\n" +
                "010110100001110000001010111010\n" +
                "111100101111001111011010101011\n" +
                "001000001000001010010110101001\n" +
                "111011101011101011110100101101\n" +
                "101010101010111000001110100111\n" +
                "100010111010000111001010100001\n" +
                "111110000011001101011010101101\n" +
                "100100111101011001110001101001\n" +
                "101100100101000011011011001001\n" +
                "011001100101111010010010001011\n" +
                "110111001110001110110110011010\n" +
                "100100111011100010101100110010\n" +
                "100100000000111010101011101011\n" +
                "101111011110001011001010001001\n" +
                "100001010011001001101011001101\n" +
                "110011011101101100101001111001\n" +
                "011010000100100110101011001001\n" +
                "001011001101100010001000011001\n" +
                "111001111001011010111011110011\n" +
                "010000100001010011100010001110\n" +
                "011110001111010000000111001000\n" +
                "110011111001011111111101111111";

        String[] split = maze.split("\n");

        int a = split.length;
        float flo = 2 / (float) a;

        char[][] txtinput = new char[a][a];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                txtinput[i][j] = split[i].charAt(j);
            }
        }
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        long window = GLFW.glfwCreateWindow(1000, 800, "MAZE, GRADIENT DRIP", 0, 0);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL33.glViewport(0, 0, 1000, 800);
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });
        Shaders.initShaders();

        Square[][] pole = new Square[a][a];

        for (int i = 0; i < txtinput.length; i++) {
            for (int j = 0; j < a; j++) {
                if (txtinput[i][j] == '1') {
                    pole[i][j] = new Square((j * flo - 1), 1 - (i * flo), flo);
                }
                else {
                    pole[i][j] = null;
                }
            }
        }
        while (!GLFW.glfwWindowShouldClose(window)) {

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);
            
            GL33.glClearColor(0f, 0f, 0f, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            for (int i = 0; i < pole.length; i++) {
                for (int j = 0; j < pole.length; j++) {
                    if (pole[i][j] != null) pole[i][j].render();
                }
            }

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        GLFW.glfwTerminate();
    }
}