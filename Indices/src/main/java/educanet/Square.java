package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    private float[] vertices;

    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };
    private int squareVboId;
    private int squareVaoId;
    private int squareEboId;
    private int squareColorId;

    public Square(float x, float y, float size) {
        float maxrange = (float) Math.sqrt(20);

        float a1 = (float) Math.sqrt(((x+size) * (x+size)) + (y*y));

        float b1 = (float) Math.sqrt(((x+size) * (x+size)) + ((y-size) * (y-size)));

        float c1 = (float) Math.sqrt((x*x) + ((y-size) * (y-size)));

        float d1 = (float) Math.sqrt((x*x) + (y*y));

        float a2 = (a1 / maxrange);

        float b2 = (b1 / maxrange);

        float c2 = (c1 / maxrange);

        float d2 = (d1 / maxrange);

        float[] colours = {
                (float) (a2 + 0.0), (float) (a2 + 0.0), a2, 0f,

                (float) (b2 + 0.0), (float) (b2 + 0.0), b2, 0f,

                (float) (c2 + 0.0), (float) (c2 + 0.0), c2, 0f,

                (float) (d2 + 0.0), (float) (d2 + 0.0), d2, 0f,
        };

        //Ať to není celý černy :D
        float[] ver = {
                x + size, y - size, 0.1f,
                x + size, y, 0.1f,
                x, y, 0.1f,
                x, y - size, 0.1f,

        };

        this.vertices = ver;

        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();

        GL33.glBindVertexArray(squareVaoId);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();

        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        
        FloatBuffer cfb = BufferUtils.createFloatBuffer(colours.length).put(colours)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cfb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        MemoryUtil.memFree(cfb);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        
        FloatBuffer fb = BufferUtils.createFloatBuffer(ver.length)
                .put(ver)
                .flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
        MemoryUtil.memFree(fb);
    }

    public void render() {
        GL33.glUseProgram(educanet.Shaders.shaderProgramId);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }
}